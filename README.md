# Display Qien
[![License](https://img.shields.io/github/license/linnn1103/Display-qien?style=flat-square)](https://github.com/linnn1103/Display-qien/blob/main/LICENSE)
[![Release](https://img.shields.io/github/v/release/linnn1103/Display-qien?include_prereleases&style=flat-square)](https://github.com/linnn1103/Display-qien/releases)
[![Github Release Downloads](https://img.shields.io/github/downloads/linnn1103/Display-qien/total?label=Github%20Release%20Downloads&style=flat-square)](https://github.com/linnn1103/Display-qien/releases)

[English](https://github.com/linnn1103/Display-qien/blob/main/README_EN.md)

一個伺服端的Minecraft Fabric模組，為彌補Fabric server無法使用插件的問題及支援更高的版本，透過模組的方式盡可能地重現了**LMBishop**的[DisplayItem](https://www.spigotmc.org/resources/displayitem-abandoned.28931/)插件的功能
## 功能
* 使用指令`/displayitem`可以分享手上拿的物品的資訊，包含但不限於名稱、狀態等
* 玩家傳送訊息物中包含`[item]`時視同使用`/displayitem`
## 模組設置
* 在目錄`./config`下建立了`display-qien-configs.storage`，將`dosEnable`設為**true**時啟用訊息監聽的方式
## 遇到的問題
* 試圖將發送訊息者由伺服端改成指令使用者，但這似乎不可能。
* 試圖將觸發方式改為由玩家發送的訊息中的`[item]`替換，但無法刪除、替換原有訊息，且遇到上一個問題
