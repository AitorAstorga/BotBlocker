# Changelog

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