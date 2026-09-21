# 发布到 CurseForge / Publishing to CurseForge

本文件记录一次性的手动配置，以及之后每个版本的发布流程。
One-time manual setup, then the per-release flow.

## 一、创建 CurseForge 项目（仅一次）

CurseForge 的建项和审核只能在网页上做，无法用 API 完成。
Project creation and moderation review are web-only; the API cannot create projects.

在 <https://legacy.curseforge.com/project/create>（或新版控制台的 Create Project）填写：

| 字段 | 值 |
|---|---|
| Game | Minecraft |
| Project Type | Mods |
| Name | `Maid Restaurant: Tsumugi` |
| Slug / URL | `maid-restaurant-tsumugi` |
| Summary | 东方小女仆 Tsumugi 的餐厅自动化附属模组（Fabric 1.21.11 非官方移植）/ Restaurant automation addon for Touhou Little Maid: Tsumugi (unofficial Fabric 1.21.11 port) |
| License | BSD 3-Clause License |
| Categories | Addons、Food（与上游 Maid Restaurant 保持一致即可）|
| Description | 粘贴 [`description.md`](description.md) 的内容 |
| Logo / Avatar | `src/main/resources/logo.png`（480×480，符合 CF 要求）|

### 过审要点 / Getting through moderation

这是一个 fork，审核会重点看署名。CurseForge 的规定是：fork 必须在协议允许的前提下进行，**必须署名并链接原作者**，且相对原项目要有实质性的功能差异。

This is a fork, so moderation will focus on attribution. CurseForge requires forks to be permitted by the license, to **credit and link the original creator**, and to carry functional differences from the original.

我们已经满足：
- 上游协议是 BSD-3-Clause，明确允许再分发与修改。
- 项目描述开头就是醒目的「非官方移植」声明，并链接了上游的 CurseForge 与 GitHub 页面。
- 实质差异：Fabric 加载器移植 + Minecraft 1.21.11 支持，上游只有 Forge/NeoForge 的 1.20.1 与 1.21.1。
- 发布的 JAR 内带 `LICENSE_maid_restaurant.txt`，满足 BSD-3-Clause 第 2 条对二进制分发的要求。

如果审核员追问，直接引用上面四条即可。
If a moderator asks, point at those four facts.

## 二、配置自动上传（仅一次）

1. 项目建好后，在项目页侧栏记下 **Project ID**（纯数字）。
   After the project is approved, copy the numeric **Project ID** from the project page sidebar.
2. 在 <https://legacy.curseforge.com/account/api-tokens> 生成 API Token。
   Generate an API token.
   拿到 token 后的第一件事是跑一次本地 dry run（见下文）：它会拿真实的
   `/api/game/versions` 把 `Client`、`Server`、`Fabric`、`1.21.11` 解析成数字 ID，
   名字对不上会当场报错。这是唯一能在真正上传前验证标签名的方式。
   Run a dry run first: it resolves the tag names against the live API, so a wrong name fails there
   instead of during a real release.
3. 在 GitHub 仓库 Settings → Secrets and variables → Actions 配置：
   - Secret `CURSEFORGE_TOKEN` = 上一步的 token
   - Variable `CURSEFORGE_PROJECT_ID` = 项目的数字 ID
4. 把 CurseForge 项目地址补进 `src/main/resources/fabric.mod.json` 的 `contact.homepage`。
   Add the CurseForge URL to `contact.homepage` in `fabric.mod.json`.

两项都没配置时，Release 工作流会跳过上传并打一条 notice，不会失败——所以可以先把流水线合进去再建项目。
While either is unset the Release workflow skips the upload with a notice instead of failing, so the pipeline can land before the project exists.

## 三、发布一个版本 / Cutting a release

1. 更新 `gradle.properties` 的 `mod_version`。
2. 在 `docs/curseforge/changelog/<mod_version>.md` 写这个版本的更新日志（**必需**，缺失会让工作流直接失败）。
3. 提交后打 tag，tag 名必须等于 `mod_version`（可带 `v` 前缀）：

   ```bash
   git tag 0.3.2-fabric.1.21.11
   git push origin 0.3.2-fabric.1.21.11
   ```

4. `.github/workflows/release.yml` 会自动构建、校验 JAR 内的许可证、上传到 CurseForge，并创建带 JAR 附件的 GitHub Release。

标签与 `mod_version` 不一致时工作流会直接报错，避免把错版本号发上去。
A tag that disagrees with `mod_version` fails the run rather than publishing a wrong version number.

### 先试跑再真发 / Dry run first

Actions → Release → Run workflow，保持 `dry_run` 勾选，可以只解析并打印会提交给 CurseForge 的元数据而不真的上传。

本地试跑：
```bash
export CURSEFORGE_TOKEN=...
python .github/scripts/curseforge_upload.py \
  build/libs/maid_restaurant-0.3.2-fabric.1.21.11.jar \
  --changelog-file docs/curseforge/changelog/0.3.2-fabric.1.21.11.md \
  --dry-run
```

Windows PowerShell：
```powershell
$env:CURSEFORGE_TOKEN = "..."
python .github\scripts\curseforge_upload.py `
  build\libs\maid_restaurant-0.3.2-fabric.1.21.11.jar `
  --changelog-file docs\curseforge\changelog\0.3.2-fabric.1.21.11.md `
  --dry-run
```

## 四、标签与依赖关系 / Tags and relations

上传用的标签和依赖关系都在 [`.github/curseforge.json`](../../.github/curseforge.json)，改 Minecraft 版本或依赖时只动这个文件：

- `gameVersions`：`1.21.11`、`Fabric`、`Client`、`Server`、`Java 21`。
  环境标签（Client/Server）无论是否强制都应该带上，因为本模组客户端服务端都要装。
  注：CurseForgeGradle 的 README 称「自 2026-07-15 起 CurseForge 要求所有 Minecraft 模组定义环境标签」，
  但它未引用任何官方出处，而官方 Upload API 文档里必填项只有 `releaseType`。
  这条强制要求未经证实，不要当作依据。
  Note: the "mandatory since 2026-07-15" claim comes from CurseForgeGradle's README, which cites no
  official CurseForge source. Treat it as unverified; tag both environments because the mod needs both.
- `relations`：Fabric API、Touhou Little Maid: Tsumugi、Kaleidoscope Cookery Refabricated 为必需依赖，Farmer's Delight Refabricated 为可选依赖。

Patchouli 故意没有列进 relations：1.21.11 的 Fabric 构建只在 GitHub 上，CurseForge 的 Patchouli 项目没有对应版本，挂上去会把玩家指向装不了的文件。它在项目描述里以文字说明。

Patchouli is deliberately left out of `relations`: its 1.21.11 Fabric build only exists on GitHub, so a CurseForge relation would point players at a file they cannot use. The description covers it in prose instead.

脚本会把这些名字解析成 CurseForge 的数字 ID；名字对不上就直接报错，不会静默丢掉标签。
The script resolves those names to CurseForge's numeric ids and errors out on an unknown name rather than silently dropping a tag.

## 五、排查 / Troubleshooting

- **`gh run list` 说找不到 run：** 本仓库同时有 `origin` 和 `upstream` 两个远端，gh 会默认解析到
  upstream（MasterMarisa/MaidRestaurant），表现为 404，很容易误判成 CI 没触发。先跑：
  `gh repo set-default gege-tlph/MaidRestaurant`。
  gh resolves to `upstream` in this fork unless the default repo is set.
- **CurseForge 步骤绿的但什么都没传：** 看 Actions 页的 Summary。未配置时工作流会打一条
  warning 并在 Summary 里写明「已跳过」；只配了 token 或只配了 project id 会直接失败。
  A missing CurseForge config warns and is written to the job summary; a half-configured one fails.
