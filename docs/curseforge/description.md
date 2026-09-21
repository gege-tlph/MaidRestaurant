# Maid Restaurant: Tsumugi

> **这是非官方移植版本 / This is an unofficial port**
>
> 本项目是 [KomeijiMarisa 的 Maid Restaurant](https://www.curseforge.com/minecraft/mc-mods/maid-restaurant) 的非官方 Fabric 移植版，面向 Minecraft 1.21.11，依原项目的 BSD-3-Clause 协议发布，与原作者无隶属关系。
> **Forge / NeoForge 玩家以及 1.20.1、1.21.1 版本请使用[上游原版项目](https://www.curseforge.com/minecraft/mc-mods/maid-restaurant)。**
>
> This is an unofficial Fabric port of [Maid Restaurant by KomeijiMarisa](https://www.curseforge.com/minecraft/mc-mods/maid-restaurant), targeting Minecraft 1.21.11 and distributed under the original project's BSD-3-Clause license. It is not affiliated with or endorsed by the original author.
> **Forge / NeoForge players, and anyone on 1.20.1 or 1.21.1, should use the [upstream project](https://www.curseforge.com/minecraft/mc-mods/maid-restaurant) instead.**

---

## 简介

Maid Restaurant: Tsumugi 是 [东方小女仆 Tsumugi](https://www.curseforge.com/minecraft/mc-mods/touhou-little-maid-tsumugi) 的附属模组，为女仆提供餐厅自动化能力：厨师女仆负责烹饪，服务女仆负责取餐并把成品送到你指定的桌位。

## Overview

Maid Restaurant: Tsumugi is an addon for [Touhou Little Maid: Tsumugi](https://www.curseforge.com/minecraft/mc-mods/touhou-little-maid-tsumugi). It turns your maids into a working kitchen crew: cook maids handle the recipes, waiter maids pick the dishes up and deliver them to the tables you choose.

---

## 功能 / Features

- **厨师与服务女仆的协作流程** — 下单、烹饪、取餐、上菜全程自动。
  **Cook and waiter maids working together** — ordering, cooking, pickup and serving all run on their own.
- **餐厅菜单、订单队列与目标桌位选择** — 用菜单物品编排要做什么、送到哪张桌子。
  **Restaurant menu, order queue and table targeting** — use the menu item to decide what gets cooked and where it goes.
- **Kaleidoscope Cookery 配方支持** — 支持锅、汤锅与蒸笼。
  **Kaleidoscope Cookery support** — pots, stockpots and steamers.
- **Farmer's Delight 烹饪锅兼容（可选）**
  **Optional Farmer's Delight cooking pot compatibility**
- **Patchouli 游戏内餐厅手册（可选）**
  **Optional in-game Patchouli restaurant guide**
- **客户端界面与桌位指示，客户端与服务端数据同步**
  **Client-side UI and table highlighting, with full client/server synchronisation**

本模组自身注册这些物品，锅具、桌子与食材由对应依赖模组提供：
The mod itself registers these items; cookware, tables and ingredients come from the dependencies:

- `maid_restaurant:order_menu` — 餐厅菜单 / Restaurant menu
- `maid_restaurant:order_item` — 订单 / Order

---

## 环境要求 / Requirements

| | |
|---|---|
| Minecraft | 1.21.11 |
| 加载器 / Loader | Fabric Loader 0.18.1+ |
| Java | 21 |
| 安装位置 / Install on | 客户端与服务端 / Both client and server |

服务器与客户端必须安装同一版本的本模组及其依赖。
Every client joining a server must run the same versions of this mod and its dependencies as the server.

### 必需依赖 / Required dependencies

- [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api)
- [Touhou Little Maid: Tsumugi](https://www.curseforge.com/minecraft/mc-mods/touhou-little-maid-tsumugi) 0.8.8 或更高 / or newer
- [Kaleidoscope Cookery Refabricated](https://www.curseforge.com/minecraft/mc-mods/kaleidoscope-cookery-refabricated)

### 可选依赖 / Optional dependencies

- [Farmer's Delight Refabricated](https://www.curseforge.com/minecraft/mc-mods/farmers-delight-refabricated) — 烹饪锅兼容 / cooking pot compatibility
- [Patchouli（1.21.11 Fabric 维护版）](https://github.com/gege-tlph/Patchouli/releases/latest) — 游戏内手册。1.21.11 的 Fabric 构建目前仅在 GitHub 发布，CurseForge 上的 Patchouli 没有对应版本。
  [Patchouli (1.21.11 Fabric maintenance build)](https://github.com/gege-tlph/Patchouli/releases/latest) — in-game guide. The 1.21.11 Fabric build is only published on GitHub; the CurseForge Patchouli project has no matching version.
- [IMBlocker Original](https://modrinth.com/mod/imblocker-original) — 仅客户端输入法冲突修复，**请勿装到服务端**。
  Client-side IME fix only — **do not install it on a server.**

---

## 安装 / Installation

1. 安装适用于 Minecraft 1.21.11 的 Fabric Loader。
   Install Fabric Loader for Minecraft 1.21.11.
2. 把本模组与上面的必需依赖放进客户端和服务端的 `mods` 目录。
   Drop this mod and the required dependencies into the `mods` folder on both client and server.
3. 按需加入可选依赖。
   Add the optional dependencies if you want them.

---

## 源码、问题反馈与协议 / Source, issues and license

- 本移植版源码 / Port source: <https://github.com/gege-tlph/MaidRestaurant>
- 问题反馈 / Issue tracker: <https://github.com/gege-tlph/MaidRestaurant/issues>
- 上游原项目 / Upstream project: <https://github.com/MasterMarisa/MaidRestaurant> · [CurseForge](https://www.curseforge.com/minecraft/mc-mods/maid-restaurant)
- 协议 / License: BSD-3-Clause，Copyright (c) 2025 MasterMarisa。移植版的修改同样按该协议发布。
  BSD-3-Clause, Copyright (c) 2025 MasterMarisa. Modifications in this port are released under the same license.

请把移植版的 bug 报到本项目的 issue 区，不要去打扰上游作者。
Please report port-specific bugs on this project's tracker rather than to the upstream author.
