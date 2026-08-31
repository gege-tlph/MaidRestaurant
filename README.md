# Maid Restaurant

> [!IMPORTANT]
> 本仓库是 [MasterMarisa/MaidRestaurant](https://github.com/MasterMarisa/MaidRestaurant) 的非官方维护 fork，面向 Minecraft 1.21.11 与 Fabric。其他 Minecraft 版本请优先使用上游项目。

Maid Restaurant 是 [东方小女仆 Tsumugi](https://github.com/gege-tlph/TouhouLittleMaid-Tsumugi) 的附属模组，为女仆提供餐厅自动化能力：厨师女仆负责烹饪，服务女仆负责取餐并将成品送到指定桌位。

English: An unofficial Fabric 1.21.11 port of Maid Restaurant, providing automated cooking and table-service workflows for Touhou Little Maid: Tsumugi.

## 功能

- 厨师女仆与服务女仆的协作流程
- 餐厅菜单、订单队列和目标桌位选择
- Kaleidoscope Cookery 的锅、汤锅和蒸笼配方支持
- 可选的 Farmers Delight Refabricated 烹饪锅兼容
- 可选的 Patchouli 游戏内餐厅手册
- 客户端界面、桌位指示和客户端与服务端数据同步

本模组注册以下物品；锅具、桌子与食材由对应依赖模组提供：

- `maid_restaurant:order_menu`
- `maid_restaurant:order_item`

## 兼容性

| 组件 | 要求 |
|---|---|
| Minecraft | 1.21.11 |
| Fabric Loader | 0.18.1 或更高版本 |
| Java | 21 |
| 安装位置 | 客户端与服务端 |

### 必需依赖

- [Fabric API](https://modrinth.com/mod/fabric-api)：选择适用于 Minecraft 1.21.11 的版本
- [东方小女仆 Tsumugi](https://github.com/gege-tlph/TouhouLittleMaid-Tsumugi)
- [Kaleidoscope Cookery Refabricated](https://modrinth.com/mod/kaleidoscope-cookery)

### 可选依赖

- [Patchouli 1.21.11 Fabric 维护版](https://github.com/gege-tlph/Patchouli/releases/latest)：提供游戏内餐厅手册
- [Farmers Delight Refabricated](https://modrinth.com/mod/farmers-delight-refabricated)：提供烹饪锅兼容
- [IMBlocker Original](https://modrinth.com/mod/imblocker-original)：仅用于客户端输入法冲突修复，请勿安装到服务端

## 安装

1. 安装适用于 Minecraft 1.21.11 的 Fabric Loader。
2. 从 [GitHub Releases](https://github.com/gege-tlph/MaidRestaurant/releases/latest) 下载名称中不含 `sources` 的 JAR。
3. 下载上述必需依赖，并将模组与依赖放入客户端和服务端的 `mods` 目录。
4. 确保加入同一服务器的客户端使用兼容的模组与依赖版本。

## 从源码构建

需要 JDK 21。[东方小女仆 Tsumugi](https://github.com/gege-tlph/TouhouLittleMaid-Tsumugi) 未发布到 Maven 仓库，首次构建前先把它的 jar 放进 `libs/`：

```bash
curl -fsSL --create-dirs -o "libs/touhoulittlemaid-fabric-0.8.4-neo1.5.3+mc1.21.11.jar" https://github.com/gege-tlph/TouhouLittleMaid-Tsumugi/releases/download/v0.8.4%2Bmc1.21.11/touhoulittlemaid-fabric-0.8.4-neo1.5.3%2Bmc1.21.11.jar
```

也可以用 `TLM_DEV_JAR` 环境变量指向自己构建的移植版 jar。然后运行：

```bash
./gradlew build
```

Windows PowerShell：

```powershell
.\gradlew.bat build
```

构建产物位于 `build/libs/`。开发环境可分别使用 `runClient` 和 `runServer` 任务启动客户端与服务端。

## 相关项目

| 项目 | 关系 |
|---|---|
| [MasterMarisa/MaidRestaurant](https://github.com/MasterMarisa/MaidRestaurant) | 上游项目 |
| [东方小女仆 Tsumugi](https://github.com/gege-tlph/TouhouLittleMaid-Tsumugi) | 本模组的主体依赖 |
| [OpenYSM-Updated](https://github.com/gege-tlph/OpenYSM-Updated) | 为 Tsumugi 提供 YSM 模型兼容 |
| [Patchouli](https://github.com/gege-tlph/Patchouli) | 为本模组提供可选的游戏内手册支持 |

## 许可证

本项目采用 [BSD 3-Clause License](LICENSE.txt)。
