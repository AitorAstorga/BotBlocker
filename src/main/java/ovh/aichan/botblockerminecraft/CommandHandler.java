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
     * Handle the command.
     * @param sender Command sender
     * @param permission Permission as defined in plugin.yml
     * @return true if the command was handled, false otherwise
     */
    private boolean hasPermission(CommandSender sender, String permission) {
        if(sender.hasPermission(permission)) {
            return true;
        } else {
            sender.sendMessage("You don't have permission to use this command.");
            return false;
        }
    }

    /**
     * Enable the BotBlocker plugin.
     * @param sender Command sender
     */
    public void enable(CommandSender sender) {
        if (!hasPermission(sender, "botblocker.enable")) return;
        plugin.setPluginEnabled(true);
        sender.sendMessage("BotBlocker enabled.");
        plugin.saveConfig();
    }

    /**
     * Disable the BotBlocker plugin.
     * @param sender Command sender
     */
    public void disable(CommandSender sender) {
        if (!hasPermission(sender, "botblocker.disable")) return;
        plugin.setPluginEnabled(false);
        sender.sendMessage("BotBlocker disabled.");
        plugin.saveConfig();
    }

    /**
     * Show wether BotBlocker is enabled or disabled.
     * @param sender Command sender
     */
    public void status(CommandSender sender) {
        if (!hasPermission(sender, "botblocker.status")) return;
        sender.sendMessage("BotBlocker status: " + (plugin.isEnabled() ? "§a§lENABLED" : "§c§lDISABLED"));
    }

    /**
     * Set the time limit for detecting bots. Default is 5 seconds.
     * @param sender Command sender
     * @param args Command arguments
     */
    public void setTimeLimit(CommandSender sender, String[] args) {
        if (!hasPermission(sender, "botblocker.settimelimit")) return;
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
        if (!hasPermission(sender, "botblocker.gettimelimit")) return;
        sender.sendMessage("Time limit set to " + plugin.getTimeLimit() + " seconds.");
    }

    /**
     * Set the ban message.
     * @param sender Command sender
     * @param args Command arguments
     */
    public void setBanMessage(CommandSender sender, String[] args) {
        if (!hasPermission(sender, "botblocker.setbanmessage")) return;
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
        if (!hasPermission(sender, "botblocker.getbanmessage")) return;
        sender.sendMessage("Ban message: " + plugin.getConfig().getString("ban-message"));
    }

}