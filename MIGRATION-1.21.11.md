# Fabric 1.21.11 migration status

Branch: `port/1.21.11-fabric`

This is a migration baseline, not a feature-complete release. The common
gameplay layer, client screen layer, and Farmers Delight Refabricated
compatibility compile and package successfully, while world rendering and
Bakeries compatibility remain isolated.

## Verified baseline

- Minecraft 1.21.11
- Java 21
- Fabric Loom 1.17.14
- Fabric Loader 0.18.1
- Fabric API 0.141.4+1.21.11
- Gradle 9.5.0
- Touhou Little Maid Tsumugi 0.8.4 development jar
- Kaleidoscope Cookery 1.3.0.9
- Farmers Delight Refabricated 3.4.9
- Patchouli 1.21.11-94.1-FABRIC is available from the maintained public fork

Verification command:

```powershell
.\gradlew.bat build --no-daemon --no-configuration-cache
```

The command currently completes successfully, including Java compilation,
resource processing, tests (none are present), Mixin remapping, the normal
JAR, and the sources JAR.

Dedicated-server smoke command:

```powershell
.\gradlew.bat runServer --no-daemon --no-configuration-cache
```

The full target dependency set loads, all mod entrypoints and Maid Restaurant
Mixins initialize, and the server reaches the normal EULA gate. The EULA was
not accepted automatically, so world creation and gameplay were not started.

## Migrated architecture

- NeoForge mod/event entrypoints were replaced with Fabric initializers and
  Fabric lifecycle callbacks.
- NeoForge payload registration was replaced with Fabric payload registries
  and server/client networking receivers. Client networking is kept out of
  the common initializer for dedicated-server safety.
- TLM extension registration uses the `little_maid_extension` entrypoint and
  TLM `TaskDataKey` registration.
- Player/world transient state uses Fabric data attachments.
- TLM's relocated item-handler API is used for maid inventories. Adapters
  preserve compatibility with list-backed Kaleidoscope inventories and the
  Farmers Delight Refabricated handler.
- Minecraft 1.21.11 recipe lookup is centralized in `RecipeAccess`; recipe
  identifiers are converted to `ResourceKey<Recipe<?>>` at the server recipe
  manager boundary.
- The custom sitting entity uses the 1.21.11 `ValueInput`/`ValueOutput`
  persistence contract.

## Included in the current JAR

- Common maid cook/waiter task logic
- Pot, stockpot, and steamer integration for Kaleidoscope Cookery
- Farmers Delight Refabricated cooking-pot compatibility
- Request/task data serialization and Fabric networking payloads
- Common Mixins

## Intentionally isolated

The following source sets are excluded in `build.gradle` until their target
APIs are verified:

- `client/render/**`
- `compat/bakeries/**`

They must not be treated as removed features. Restore each exclusion only
after that module compiles and its behavior can be tested.

## Known behavior gaps

- `ClientSetup` and render-stage integration are placeholders.
- The client ordering/cook/serve screens now compile against the 1.21.11
  input and `GuiGraphics` APIs, and the open-screen payload routes to them.
- The ordering screen cannot yet resolve a recipe by identifier through the
  public client recipe API, so a cook-request card currently uses an empty
  result icon until a server-sent result field is added.
- `GivePatchouliBookConfigTrigger` is a no-op. Patchouli availability is
  verified, but the book reward flow still needs a Fabric implementation.
- `ICookTask` default recipe ingredient/result methods are temporary safe
  defaults. Every cook task that depends on those defaults must be audited;
  successful compilation is not evidence of correct recipe behavior.
- `RestaurantConfig` currently uses static defaults and has no persisted
  Fabric configuration backend.
- Data generation providers are placeholders.
- Client startup has not yet been exercised with a complete runtime modpack.

## Next verification gates

1. Port GUI classes to the 1.21.11 screen/render API and restore the screen
   payload handler.
2. Restore entity renderer registration and block-selection rendering.
3. Accept the EULA manually in a disposable development instance when ready,
   then continue dedicated-server world testing.
4. Exercise order creation, request distribution, maid cooking, serving,
   save/reload, and multiplayer synchronization.
5. Port Bakeries only when a verified Fabric 1.21.11 artifact/API is present.
6. Replace configuration, Patchouli reward, and data-generation placeholders.

## Evidence sources

- `C:\Users\steve\Desktop\claude`: completed TLM Tsumugi Fabric 1.21.11 port
- `C:\Users\steve\Desktop\claude-local-assets\reference-mods`: verified
  Kaleidoscope Cookery and Farmers Delight Refabricated artifacts
- `C:\Users\steve\Desktop\claude-local-assets\evidence\思维链工作流.txt`:
  migration decision and validation rules
