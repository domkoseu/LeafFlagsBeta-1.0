# LeafFlags

Java (Paper 1.21.11) port of your Skript file for player flags based on their GeoIP country.

## Build

```text
mvn clean package
```

The resulting JAR will be located at `target/LeafFlags.jar`. Put it into the `plugins/` folder alongside PlaceholderAPI.

It also requires the `geolocation` expansion, just like the previous Skript used `%%geolocation_countryCode%%`.

## Commands (same as the original)

* `/setflag <code> <hash>` — saves/updates a flag texture
* `/delflag <code>` — deletes a saved flag
* `/flagtest <code>` — displays a flag in chat (player only)

Tab completion works the same as before:

* `/setflag` suggests codes **WITHOUT** a flag
* `/delflag` and `/flagtest` suggest codes that **already HAVE** a flag

## PlaceholderAPI — %flags%

* `%flags%` / `%flags_code%` → two-letter country code of the player, e.g. `cz`
  (empty string if the player does not have a flag assigned yet)
* `%flags_has%` → `yes` / `no`

Important: PAPI always returns plain text, not a rich Component, so `%flags%` **CANNOT display the actual player-head icon** by itself. The icon is still handled directly by the scoreboard-team suffix (`FlagApplier`), just like the original Skript did.

If you need the flag as an actual icon somewhere that supports MiniMessage (e.g. TAB), the plugin additionally registers `<flags_flag>` and `<flags_code>` when the `MiniPlaceholders` plugin is installed. These return the actual icon.

## Port Notes

* `Component.object(...)` / `ObjectContents.playerHead(...)` (embedding heads directly into text) requires Minecraft 1.21.9+ / Adventure 4.25.0+. Paper 1.21.11 meets these requirements.
* The country code list and the `on join → wait 1s → applyFlag` logic are ported 1:1 from the original Skript.
* Data is stored in `plugins/LeafFlags/flags.yml` instead of the Skript variable `{flags::*}`.
