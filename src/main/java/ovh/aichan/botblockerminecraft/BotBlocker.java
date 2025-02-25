package ovh.aichan.botblockerminecraft;

import org.bukkit.Bukkit;
import org.bukkit.BanList.Type;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.confuser.banmanager.common.api.BmAPI;
import me.confuser.banmanager.common.data.PlayerBanData;
import me.confuser.banmanager.common.data.PlayerData;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public class BotBlocker extends JavaPlugin implements Listener {

    private boolean pluginEnabled = true;
    private int timeLimit;  // In seconds
    private String banMessage;
    private HashMap<UUID, Long> joinTimes = new HashMap<>();
    private FileConfiguration playersCfg;
    private File playersFile;
    private CommandHandler commandHandler;

    @Override
    public void onEnable() {
        initialize();
        commandHandler = new CommandHandler(this);
    }

    /**
     * Initialize the plugin.
     */
    private void initialize() {
        saveDefaultConfig();
        pluginEnabled = getConfig().getBoolean("enabled", true);
        timeLimit = getConfig().getInt("time-limit", 5);  // Default to 5 seconds
        banMessage = getConfig().getString("ban-message", "Bot detected. If you are a legitimate user, please contact the admin.");
        Bukkit.getPluginManager().registerEvents(this, this);

        playersFile = new File(getDataFolder(), "players.yml");
        if (!playersFile.exists()) {
            saveResource("players.yml", false);
        }
        playersCfg = YamlConfiguration.loadConfiguration(playersFile);
        saveConfig();
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("botblocker")) {
            if (args.length > 0) {
                String botblockerCommand = args[0].toLowerCase();
                switch (botblockerCommand) {
                    case "enable":
                        commandHandler.enable(sender);
                        break;
                    case "disable":
                        commandHandler.disable(sender);
                        break;
                    case "status":
                        commandHandler.status(sender);
                        break;
                    case "settimelimit":
                        commandHandler.setTimeLimit(sender, args);
                        break;
                    case "gettimelimit":
                        commandHandler.getTimeLimit(sender);
                        break;
                    case "setbanmessage":
                        commandHandler.setBanMessage(sender, args);
                        break;
                    case "getbanmessage":
                        commandHandler.getBanMessage(sender);
                        break;
                    default:
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

                // Check if BanManager is installed and enabled
                Plugin banManager = Bukkit.getServer().getPluginManager().getPlugin("BanManager");
                if (banManager != null && banManager.isEnabled()) {
                    // Use BanManager to ban the player
                    banWithBanManager(event.getPlayer(), banMessage);
                } else {
                    // Fallback to Bukkit's default banning system
                    Bukkit.getBanList(Type.PROFILE).addBan(playerName, banMessage, null, "BotBlocker");
                }

                getLogger().info("Player '" + playerName + "' was banned for disconnecting within " + timeLimit + " seconds of joining for the first time - suspected bot.");
            } else {
                // Add the player to players.yml if it is not banned
                playersCfg.set(playerId.toString(), true);
                savePlayers();
            }
            joinTimes.remove(playerId);
        }
    }

    public void banWithBanManager(Player player, String reason) {
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            try {
                UUID playerUuid = player.getUniqueId();
                String playerName = player.getName();
                
                // Creating PlayerData for the player.
                PlayerData playerData = new PlayerData(playerUuid, playerName);
                
                // Using 'BotBlocker' as the system or admin name issuing the ban
                UUID actorUuid = UUID.randomUUID(); // This could be a specific UUID if you have one for the system/admin
                String actorName = "BotBlocker";
                PlayerData actorData = new PlayerData(actorUuid, actorName);
                
                // Construct the PlayerBanData object
                PlayerBanData ban = new PlayerBanData(playerData, actorData, reason, false);
                
                // Attempt to ban the player using BanManager
                BmAPI.ban(ban);
            } catch (Exception e) {
                getLogger().warning("[BotBlocker] An error occurred while trying to ban a player with BanManager. See logs for details.");
                getLogger().log(Level.SEVERE, e.toString(), e);
            }
        });
    }

    /**
     * Set the ban message.
     * @param message Ban message
     */
    public void setPluginEnabled(boolean enabled) {
        pluginEnabled = enabled;
    }

    /**
     * Show wether BotBlocker is enabled or disabled.
     * @return true if BotBlocker is enabled, false otherwise
     */
    public boolean isPluginEnabled() {
        return pluginEnabled;
    }

    /**
     * Set the time limit for detecting bots. Default is 5 seconds.
     * @param timeLimit Time limit in seconds
     */
    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
        getConfig().set("time-limit", timeLimit);
        saveConfig();
    }

    /**
     * Get the configured time limit for detecting bots.
     * @return Time limit in seconds
     */
    public int getTimeLimit() {
        return timeLimit;
    }

    /**
     * Save the players configuration in the players.yml file.
     */
    private void savePlayers() {
        try {
            playersCfg.save(playersFile);
        } catch (IOException e) {
            getLogger().warning("[BotBlocker] An error occurred while trying to save the players.yml file. See logs for details.");
            getLogger().log(Level.SEVERE, e.toString(), e);
        }
    }
    
}
