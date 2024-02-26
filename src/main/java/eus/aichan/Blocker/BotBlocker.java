package eus.aichan.Blocker;

import org.bukkit.Bukkit;
import org.bukkit.BanList.Type;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class BotBlocker extends JavaPlugin implements Listener {

    private boolean pluginEnabled = true;
    private int timeLimit;  // In seconds
    private HashMap<UUID, Long> joinTimes = new HashMap<>();
    private FileConfiguration playersCfg;
    private File playersFile;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        timeLimit = getConfig().getInt("time-limit", 5);  // Default to 5 seconds
        Bukkit.getPluginManager().registerEvents(this, this);

        playersFile = new File(getDataFolder(), "players.yml");
        if (!playersFile.exists()) {
            saveResource("players.yml", false);
        }
        playersCfg = YamlConfiguration.loadConfiguration(playersFile);
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("botblocker")) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("enable")) {
                    pluginEnabled = true;
                    sender.sendMessage("BotBlocker enabled.");
                } else if (args[0].equalsIgnoreCase("disable")) {
                    pluginEnabled = false;
                    sender.sendMessage("BotBlocker disabled.");
                } else if (args[0].equalsIgnoreCase("setTimeLimit") && args.length > 1) {
                    try {
                        timeLimit = Integer.parseInt(args[1]);
                        getConfig().set("time-limit", timeLimit);
                        saveConfig();
                        sender.sendMessage("Time limit set to " + timeLimit + " seconds.");
                    } catch (NumberFormatException e) {
                        sender.sendMessage("Invalid number format. Please enter a valid integer.");
                    }
                } else {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!pluginEnabled) return;
        
        UUID playerId = event.getPlayer().getUniqueId();
        if (!playersCfg.contains(playerId.toString())) {
            joinTimes.put(playerId, System.currentTimeMillis());
            playersCfg.set(playerId.toString(), true);
            saveConfig();
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (!pluginEnabled) return;
        
        UUID playerId = event.getPlayer().getUniqueId();
        if (joinTimes.containsKey(playerId)) {
            long joinTime = joinTimes.get(playerId);
            long timeConnected = (System.currentTimeMillis() - joinTime) / 1000;
            if (timeConnected < timeLimit) {
                String playerName = event.getPlayer().getName();
                Bukkit.getBanList(Type.NAME).addBan(playerName, "Bot detected. If you are a legitimate user, please contact the admin.", null, "Sistema anti-bot");
                getLogger().info("Player '" + playerName + "' was banned for disconnecting within " + timeLimit + " seconds of joining for the first time - suspected bot.");
            } else {
                // Add the player to players.yml if it is not banned
                playersCfg.set(playerId.toString(), true);
                savePlayers();
            }
            joinTimes.remove(playerId);
        }
    }

    private void savePlayers() {
        try {
            playersCfg.save(playersFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
