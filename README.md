# Maid Restaurant

Maid Restaurant 是 [东方小女仆](https://github.com/gege-tlph/TouhouLittleMaid) 的附属模组，为女仆提供餐厅自动化能力：厨师女仆负责烹饪，服务女仆负责从厨师处取餐并将成品送到指定桌位。

当前开发分支为 Fabric 1.21.11 迁移分支：

- Minecraft：1.21.11
- Fabric Loader：0.19.3
- Fabric API：0.141.4+1.21.11
- Java：21
- 模组版本：`0.3.0-fabric.1.21.11`

## 功能

- 厨师女仆和服务女仆的协作流程
- 餐厅菜单、订单队列和目标桌位选择
- Kaleidoscope Cookery 的锅、汤锅和蒸笼配方支持
- Farmers Delight Refabricated 烹饪锅兼容
- 可选的 Patchouli 女仆餐厅手册
- Fabric 客户端 GUI、网络同步和桌位指示器
- 请求状态、存档和世界重载后的任务数据同步

本附属模组自身注册两个物品，并将它们加入原版“工具与实用物品”创造栏：

- `maid_restaurant:order_menu`
- `maid_restaurant:order_item`

锅、汤锅、蒸笼、桌子及食材等物品来自对应的依赖模组，不会在本模组中重复注册。

## 依赖

必需：

- [Fabric API](https://modrinth.com/mod/fabric-api)
- [东方小女仆 Tsumugi](https://github.com/gege-tlph/TouhouLittleMaid)
- [Kaleidoscope Cookery Refabricated](https://modrinth.com/mod/kaleidoscope-cookery)

可选：

- [Patchouli](https://modrinth.com/mod/patchouli)：提供女仆餐厅手册
- [Farmers Delight Refabricated](https://modrinth.com/mod/farmers-delight-refabricated)：启用烹饪锅兼容
- [IMBlocker Original](https://modrinth.com/mod/imblocker-original)：仅客户端输入法冲突修复，不能放入服务端

Bakeries 兼容代码已归档到 [`archive/bakeries`](archive/bakeries)，不参与当前 1.21.11 构建。

## 构建与开发

环境要求：

- Windows、Linux 或 macOS
- Java 21
- Git

构建：

```bash
./gradlew build
```

Windows PowerShell：

```powershell
.\gradlew.bat build
```

开发客户端和服务端使用独立运行目录：

```powershell
.\gradlew.bat runClient
.\gradlew.bat runServer
```

客户端专用模组放在 `run/client/mods`；不要把 IMBlocker 或其他客户端专用模组复制到 `run/server/mods`。

更完整的迁移说明、运行实例和验证记录见 [`MIGRATION-1.21.11.md`](MIGRATION-1.21.11.md)。

## 迁移状态

Fabric 1.21.11 的主逻辑、客户端界面、网络 payload、TLM 任务扩展、Patchouli 集成以及 Farmers Delight 兼容已经迁移并可构建。

当前已用隔离开发世界通过三类核心烹饪链路的代表性验证：

- Stockpot：猪骨汤成功烹饪并摆盘
- Pot：烤土豆成功烹饪并摆盘
- Steamer：馒头成功烹饪并摆盘

验证时每轮都会清理库存、请求、桌位和工位，并将世界时间重置到白天，以避免测试之间互相污染。

## 许可证

本项目使用 [BSD 3-Clause License](LICENSE.txt)。

## English summary

Maid Restaurant is a Fabric 1.21.11 addon for Touhou Little Maid. It adds cook and waiter maid tasks, restaurant orders, table targeting, Kaleidoscope Cookery integration, optional Patchouli documentation, and optional Farmers Delight Refabricated compatibility.

The active development branch is `port/1.21.11-fabric`. Bakeries compatibility is archived and excluded from the active build. See [`MIGRATION-1.21.11.md`](MIGRATION-1.21.11.md) for migration details and development-instance notes.
