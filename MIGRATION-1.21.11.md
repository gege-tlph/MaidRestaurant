# Fabric 1.21.11 migration status

Branch: `port/1.21.11-fabric`

The Fabric 1.21.11 migration is implemented for the retained upstream feature
set. The common gameplay layer, client screen/render layer, Farmers Delight
Refabricated compatibility, and optional Patchouli integration compile and
package successfully, while Bakeries compatibility is explicitly archived.
Release sign-off still requires the in-world manual verification listed below.

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

Client smoke command:

```powershell
.\gradlew.bat runClient --no-daemon --no-configuration-cache
```

The complete development modpack reaches the main menu after resource and
renderer initialization. The development account emits expected Mojang/Realms
401 warnings, but those do not prevent offline client startup.

## Development instances

- `run/client` is the client instance. Maid Restaurant is supplied from the
  Loom development classes; TLM, Kaleidoscope Cookery, Farmers Delight,
  Patchouli, Fiber, and Forge Config API Port are supplied by
  `modLocalRuntime`.
- `run/client/mods` contains IMBlocker 6.1.4 for Fabric
  (`1.21.9`-`1.21.11`) as a client-only input-method compatibility mod.
- `run/server` is the dedicated-server instance. It uses the common runtime
  dependency set and does not contain IMBlocker.

The split run directories are intentional: copying the built Maid Restaurant
JAR into the client `mods` directory would duplicate the mod already supplied
by Loom, while sharing one `mods` directory would expose client-only utilities
to the server instance.

## Migrated architecture

- NeoForge mod/event entrypoints were replaced with Fabric initializers and
  Fabric lifecycle callbacks.
- NeoForge payload registration was replaced with Fabric payload registries
  and server/client networking receivers. Client networking is kept out of
  the common initializer for dedicated-server safety.
- TLM extension registration uses the `little_maid_extension` entrypoint and
  TLM `TaskDataKey` registration.
- The TLM task-enable callback is registered through its Fabric event, and
  request-handler defaults plus queue mutations use explicit task-data sync.
- Player/world transient state uses Fabric data attachments.
- TLM's relocated item-handler API is used for maid inventories. Adapters
  preserve compatibility with list-backed Kaleidoscope inventories and the
  Farmers Delight Refabricated handler.
- Minecraft 1.21.11 recipe lookup is centralized in `RecipeAccess`; recipe
  identifiers are converted to `ResourceKey<Recipe<?>>` at the server recipe
  manager boundary.
- The custom sitting entity uses the 1.21.11 `ValueInput`/`ValueOutput`
  persistence contract.
- The sitting entity renderer and selected-table item indicators use Fabric's
  1.21.11 extraction/submit rendering split. Item models are resolved during
  extraction and submitted through `SubmitNodeCollector`.
- Selected table positions are synchronized explicitly from the server before
  rendering. The server also sends the target snapshot used to open the
  ordering screen, keeping the interaction authoritative.

## Included in the current JAR

- Common maid cook/waiter task logic
- Pot, stockpot, and steamer integration for Kaleidoscope Cookery
- Farmers Delight Refabricated cooking-pot compatibility
- Optional Patchouli guide-book recipe, loot data, and login delivery
- Request/task data serialization and Fabric networking payloads
- Client ordering/cook/serve screens and their open-screen payload routes
- Sitting entity renderer and selected-table world indicators
- Tools & Utilities creative-tab entries for both addon items
- Common Mixins

## Intentionally isolated

The following compatibility source is stored outside the active source set
because it is explicitly archived:

- `archive/bakeries/src/**` (see `archive/bakeries/README.md`)

The adapters remain available for historical reference and are not part of the
active 1.21.11 build.

## Behavior and validation notes

- The client ordering/cook/serve screens now compile against the 1.21.11
  input and `GuiGraphics` APIs, and server-authoritative payloads route to
  them. In-world interaction still needs a gameplay pass.
- The ordering screen does not resolve recipes on the client. Server-side
  order handling now resolves the authoritative output and serializes an item
  id/count snapshot on `CookRequest`, which the client card renders directly.
  This keeps recipe lookup server-authoritative while preserving the existing
  screen behavior.
- `RestaurantConfig` now persists `sit_while_cooking` and
  `give_patchouli_book` in `config/maid_restaurant.json`.
- Patchouli book delivery is implemented as an optional Fabric-side login
  reward and uses the registered `patchouli:book` data component.
- Fabric datagen now has a real entrypoint and writes the 1.21.11 item-model
  definitions plus the block/item tags to its configured output directory.
- `OrderItem` now has a persisted/networked request-state component; no gameplay
  producer currently attaches that component, so the default item remains the
  no-requests model.
- Farmers Delight Refabricated 3.4.9 has an upstream 1.21.11 enum-extension
  defect: its bundled early-riser does not add `FARMERSDELIGHT_COOKING`.
  Compatibility Mixins provide safe crafting-category fallbacks for both the
  client initializer and the common recipe-book synchronization path, allowing
  the full modpack to reach the main menu and enter an integrated-server world.
- Universal cooking and guide-book recipe ingredients use the 1.21.11
  string-or-array ingredient codec rather than the removed `{item: ...}` and
  `{tag: ...}` object forms.

## Manual release verification

1. Exercise the migrated client screens in-world, including table selection,
   open-screen payloads, request result snapshots, and world indicators.
2. Accept the EULA manually in a disposable development instance when ready,
   then continue dedicated-server world testing.
3. Exercise order creation, request distribution, maid cooking, serving,
   save/reload, and multiplayer synchronization.
4. Keep Bakeries archived unless a verified Fabric 1.21.11 artifact/API is
   intentionally reintroduced.
5. Add a gameplay producer for the order-state component if the Order Item
   becomes a live request carrier.

## Evidence sources

- `C:\Users\steve\Desktop\claude`: completed TLM Tsumugi Fabric 1.21.11 port
- `C:\Users\steve\Desktop\claude-local-assets\reference-mods`: verified
  Kaleidoscope Cookery and Farmers Delight Refabricated artifacts
- `C:\Users\steve\Desktop\claude-local-assets\evidence\思维链工作流.txt`:
  migration decision and validation rules
