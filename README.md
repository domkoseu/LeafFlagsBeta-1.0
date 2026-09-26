## Placeholders

### PlaceholderAPI (`%flags%`)
- `%flags%` / `%flags_code%` -> Two-letter country code of the player, e.g., `cz` (returns an empty string if the player doesn't have an assigned flag yet).
- `%flags_has%` -> `yes` / `no`

*(Note: PlaceholderAPI always returns plain text, not a rich Component, so `%flags%` cannot render the player head icon directly. Icon rendering is handled automatically via the scoreboard-team suffix by FlagApplier, exactly like the original Skript did.)*

### MiniPlaceholders (`<flags_...>`)
If you need the flag as a true icon inside plugins that parse MiniMessage (such as **TAB**), ensure you have MiniPlaceholders installed. The plugin registers:
- `<flags_flag>` — returns the player head icon component
- `<flags_code>` — returns the country code component

## Port Notes
- `Component.object(...)` / `ObjectContents.playerHead()...` (embedding player heads directly into text) requires Minecraft 1.21.9+ and Adventure 4.25.0+ — version 1.21.11 fully supports this.
- Country code lists and the "on join -> wait 1s -> applyFlag" logic are 1:1 ported from the original Skript.
- Data is saved locally to `plugins/LeafFlags/flags.yml` (replacing the old Skript variable `{flags::*}`).
