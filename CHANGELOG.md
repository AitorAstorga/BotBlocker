# Changelog

## 1.4.0
### Added or Changed
- Added a permissions system. Whenever a command or action is initiated, check if the player has the required permission.
    * `botblocker.enable` - Allows the user to enable the BotBlocker plugin.
    * `botblocker.disable` - Allows the user to disable the BotBlocker plugin.
    * `botblocker.status` - Allows the user to see whether BotBlocker is enabled or disabled.
    * `botblocker.settimelimit` - Allows the user to set the time limit for detecting bots.
    * `botblocker.gettimelimit` - Allows the user to display the configured time limit for detecting bots.
    * `botblocker.setbanmessage` - Allows the user to set the ban message.
    * `botblocker.getbanmessage` - Allows the user to display the configured ban message.

- Added support for BanManager. If the plugin is installed, the `BmAPI.ban` command is used. Else, the default Bukkit ban adds the corresponding entry to `banned-players.json`.

## 1.3.0

### Added or Changed
- Added `ban-message` to the `config.yml`
- Lowered time limit to 5 seconds
- Added the following commands:
    * `/BotBlocker status` - Show wether BotBlocker is enabled or disabled.
    * `/BotBlocker getTimeLimit` - Display the configured time limit for detecting bots.
    * `/BotBlocker setBanMessage [message]` - Set the ban message.
    * `/BotBlocker getBanMessage` - Display the configured ban message.
- Added brief instructions for compiling this Bukkit/Spigot plugin using Maven (so I won't forget next time).
- Fixed package names to be compliant with naming conventions.
- Added usage hint to the setTimeLimit command.
- Created `CommandHandler` class to improve readability.
- Updated `README.md` and this `CHANGELOG.md` :)

### Removed
- Maven target