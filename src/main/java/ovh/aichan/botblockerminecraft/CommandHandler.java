package ovh.aichan.botblockerminecraft;

import org.bukkit.command.CommandSender;

public class CommandHandler {

    private final BotBlocker plugin;

    /**
     * Constructor for CommandHandler.
     * @param plugin BotBlocker plugin
     */
    public CommandHandler(BotBlocker plugin) {
        this.plugin = plugin;
    }

    /**
     * Enable the BotBlocker plugin.
     * @param sender Command sender
     */
    public void enable(CommandSender sender) {
        plugin.setPluginEnabled(true);
        sender.sendMessage("BotBlocker enabled.");
        plugin.saveConfig();
    }

    /**
     * Disable the BotBlocker plugin.
     * @param sender Command sender
     */
    public void disable(CommandSender sender) {
        plugin.setPluginEnabled(false);
        sender.sendMessage("BotBlocker disabled.");
        plugin.saveConfig();
    }

    /**
     * Show wether BotBlocker is enabled or disabled.
     * @param sender Command sender
     */
    public void status(CommandSender sender) {
        sender.sendMessage("BotBlocker status: " + (plugin.isEnabled() ? "§a§lENABLED" : "§c§lDISABLED"));
    }

    /**
     * Set the time limit for detecting bots. Default is 5 seconds.
     * @param sender Command sender
     * @param args Command arguments
     */
    public void setTimeLimit(CommandSender sender, String[] args) {
        if(args.length > 1) {
            try {
                int timeLimit = Integer.parseInt(args[1]);
                plugin.setTimeLimit(timeLimit);
                sender.sendMessage("Time limit set to " + timeLimit + " seconds.");
            } catch (NumberFormatException e) {
                sender.sendMessage("Invalid number format. Please enter a valid integer.");
            }
        } else {
            sender.sendMessage("Usage: /botblocker setTimeLimit <timeLimit>");
        }
    }

    /**
     * Display the configured time limit for detecting bots.
     * @param sender Command sender
     */
    public void getTimeLimit(CommandSender sender) {
        sender.sendMessage("Time limit set to " + plugin.getTimeLimit() + " seconds.");
    }

    /**
     * Set the ban message.
     * @param sender Command sender
     * @param args Command arguments
     */
    public void setBanMessage(CommandSender sender, String[] args) {
        if(args.length > 1) {
            StringBuilder message = new StringBuilder();
            for(int i = 1; i < args.length; i++) {
                message.append(args[i]).append(" ");
            }
            plugin.getConfig().set("ban-message", message.toString());
            plugin.saveConfig();
            sender.sendMessage("Ban message set to: " + message.toString());
        } else {
            sender.sendMessage("Usage: /botblocker setBanMessage <message>");
        }
    }

    /**
     * Display the configured ban message.
     * @param sender Command sender
     */
    public void getBanMessage(CommandSender sender) {
        sender.sendMessage("Ban message: " + plugin.getConfig().getString("ban-message"));
    }

}