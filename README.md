<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a name="readme-top"></a>

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]



<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/BotBlocker-Minecraft/BotBlocker">
    <img src="images/logo.png" alt="Logo" width="160" height="160">
  </a>

  <h3 align="center">BotBlocker</h3>

  <p align="center">
    A Minecraft Bukkit/Spigot plugin and Fabric mod designed to limit bot intrusion!
    <br />
    <a href="https://github.com/BotBlocker-Minecraft/BotBlocker"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/BotBlocker-Minecraft/BotBlocker/issues">Report Bug</a>
    ·
    <a href="https://github.com/BotBlocker-Minecraft/BotBlocker/issues">Request Feature</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li><a href="#versions-of-the-project">Versions of the Project</a></li>
    <li>
      <a href="#functionality">Functionality</a>
      <ul>
        <li><a href="#initialization">Initialization</a></li>
        <li><a href="#player-join">Player Join</a></li>
        <li><a href="#player-quit">Player Quit</a></li>
        <li><a href="#commands">Commands</a></li>
        <li><a href="#configuration-files">Configuration Files</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

BotBlocker is a solution for Minecraft servers designed to limit bot intrusion. It is available for both Bukkit/Spigot Minecraft servers and as a Fabric mod. If a user logs out too quickly after joining (indicating they might be a bot), the user is banned. This approach limits the number of login attempts from each UUID, effectively mitigating the consequences of a bot attack.

### Built With

* ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
* [Bukkit/Spigot](https://www.spigotmc.org)

<p align="right">(<a href="#readme-top">back to top</a>)</p>


## Versions of the Project
[Bukkit/Spigot BotBlocker](https://github.com/BotBlocker-Minecraft/BotBlocker)

[Fabric BotBlocker](https://github.com/BotBlocker-Minecraft/BotBlocker-Fabric)

<p align="right">(<a href="#readme-top">back to top</a>)</p>


## Functionality

### Initialization
When the server starts, BotBlocker initializes its configuration files (`config.yml` and `players.yml`). The initial default time limit for joining and leaving is set to 5 seconds. A message indicating the mod has successfully loaded with the set time limit is logged to the console.

### Player Join
When a player joins, if BotBlocker is enabled and the player is not already exempt from BotBlocker's checks, the mod/plugin records the player's UUID and the current time.

### Player Quit
When a player quits, BotBlocker calculates the duration of their connection. If this time is less than the set time limit and BotBlocker is enabled, the player is considered a bot. They get banned and a ban entry is added to the `players.yml` file. A disconnect message is sent to the player and a message is logged to the console. If the player stays longer than the time limit, they are deemed a legitimate player. Their UUID is added to the `players.yml` file as exempt.

### Commands
* `/BotBlocker enable` - Enables BotBlocker.
* `/BotBlocker disable` - Disables BotBlocker.
* `/BotBlocker setTimeLimit [seconds]` - Sets the time limit for detecting bots.

### Configuration Files
BotBlocker maintains its configuration and the list of player UUIDs in `config.yml` and `players.yml` files, respectively.

Note: Players identified as legitimate are marked as such in the `players.yml` file and are not checked again in the future. If BotBlocker is disabled, it stops checking players for potential bot activity.

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- GETTING STARTED -->
## Getting Started

To get this plugin up and running on your server, follow these steps:

### Prerequisites

* Java 18 or later
* A Bukkit/Spigot server

### Installation

1. Download the latest BotBlocker `.jar` file from the [releases page](https://github.com/BotBlocker-Minecraft/BotBlocker/releases).
2. Move the downloaded `.jar` file to the `plugins/` directory in your server.
3. Restart your server. This will generate a default configuration file.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

Once installed and enabled, BotBlocker works in the background without any intervention. If needed, you can adjust the bot protection parameters in the generated `config.yml` file.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- LICENSE -->
## License

Distributed under the GNU General Public License v3.0. See `LICENSE` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Aitor Astorga Saez de Vicuña - a.astorga.sdv@gmail.com

Project Link: [https://github.com/BotBlocker-Minecraft](https://github.com/BotBlocker-Minecraft)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments
* [https://github.com/othneildrew/Best-README-Template](https://github.com/othneildrew/Best-README-Template)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/BotBlocker-Minecraft/BotBlocker.svg?style=for-the-badge
[contributors-url]: https://github.com/BotBlocker-Minecraft/BotBlocker/graphs/contributors

[forks-shield]: https://img.shields.io/github/forks/BotBlocker-Minecraft/BotBlocker.svg?style=for-the-badge
[forks-url]: https://github.com/BotBlocker-Minecraft/BotBlocker/network/members

[stars-shield]: https://img.shields.io/github/stars/BotBlocker-Minecraft/BotBlocker.svg?style=for-the-badge
[stars-url]: https://github.com/BotBlocker-Minecraft/BotBlocker/stargazers

[issues-shield]: https://img.shields.io/github/issues/BotBlocker-Minecraft/BotBlocker.svg?style=for-the-badge
[issues-url]: https://github.com/BotBlocker-Minecraft/BotBlocker/issues

[license-shield]: https://img.shields.io/github/license/BotBlocker-Minecraft/BotBlocker.svg?style=for-the-badge
[license-url]: https://github.com/BotBlocker-Minecraft/BotBlocker/blob/master/LICENSE

[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/aitor-astorga-saez-de-vicuña

[product-screenshot]: images/screenshot.png
