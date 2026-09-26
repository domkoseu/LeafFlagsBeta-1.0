package com.leafsmp.flags.papi;

import com.leafsmp.flags.FlagsPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * PlaceholderAPI only ever returns plain text (a String), never a rich
 * Component, so this expansion cannot draw the actual flag head icon -
 * that stays applied directly as a scoreboard-team suffix by FlagApplier,
 * exactly like the original script did.
 *
 * What it DOES give any PAPI-reading plugin (Tab lists, chat formatters,
 * scoreboards, holograms, ...) is the player's current country code:
 *   %flags%          -> "cz"   (same as %flags_code%, empty string if unknown)
 *   %flags_code%     -> "cz"
 *   %flags_has%      -> "yes" / "no"
 */
public class FlagsExpansion extends PlaceholderExpansion {

    private final FlagsPlugin plugin;

    public FlagsExpansion(FlagsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "flags";
    }

    @Override
    public @NotNull String getAuthor() {
        return "LeafSMP";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(@Nullable OfflinePlayer offlinePlayer, @NotNull String params) {
        if (offlinePlayer == null || !offlinePlayer.isOnline()) {
            return "";
        }

        String code = plugin.applier().appliedCode(offlinePlayer.getUniqueId());

        if (params.isEmpty() || params.equalsIgnoreCase("code")) {
            return code == null ? "" : code;
        }
        if (params.equalsIgnoreCase("has")) {
            return code != null ? "yes" : "no";
        }
        return "";
    }
}
