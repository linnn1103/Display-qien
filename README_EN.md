# Display Qien
[![License](https://img.shields.io/github/license/linnn1103/Display-qien?style=flat-square)](https://github.com/linnn1103/Display-qien/blob/main/LICENSE)
[![Release](https://img.shields.io/github/v/release/linnn1103/Display-qien?include_prereleases&style=flat-square)](https://github.com/linnn1103/Display-qien/releases)
[![Github Release Downloads](https://img.shields.io/github/downloads/linnn1103/Display-qien/total?label=Github%20Release%20Downloads&style=flat-square)](https://github.com/linnn1103/Display-qien/releases)

[中文](https://github.com/linnn1103/Display-qien/blob/main/README.md)

A Fabric mod for Minecraft servers that recreates the functionality of LMBishop's [DisplayItem](https://www.spigotmc.org/resources/displayitem-abandoned.28931/) plugin as much as possible through a mod to compensate for the inability of Fabric servers to use plugins and to support higher versions.

## Features

* Use the command `/displayitem` to share information about the item you are holding, including but not limited to its name and status.
* Player Messages with `[item]` Treated as `/displayitem`
## Module Settings
Create a file named `display-qien-configs.storage` in the `./config` directory. Set `dosEnable` to **true** to enable message monitoring.
## Issues Encountered
* Trying to change the message sender from the server to the command user, but this seems impossible.
* Trying to change the trigger method to the `[item]` in the message sent by the player, but the original message cannot be deleted or replaced,
