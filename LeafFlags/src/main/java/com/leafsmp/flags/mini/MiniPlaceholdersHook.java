package com.leafsmp.flags.mini;

import com.leafsmp.flags.FlagIcon;
import com.leafsmp.flags.FlagsPlugin;
import io.github.miniplaceholders.api.Expansion;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.entity.Player;

/**
 * Optional: only used if the "MiniPlaceholders" plugin is installed. Unlike
 * PlaceholderAPI, MiniPlaceholders tags carry a real Adventure Component, so
 * <flags_flag> can embed the actual head icon (not just the country-code
 * text) in anything that reads MiniMessage tags - e.g. TAB, chat plugins.
 */
public final class MiniPlaceholdersHook {

    private MiniPlaceholdersHook() {
    }

    public static void register(FlagsPlugin plugin) {
        Expansion.builder("flags")
                .audiencePlaceholder(Player.class, "flag", (player, queue, ctx) -> {
                    String code = plugin.applier().appliedCode(player.getUniqueId());
                    Component icon = code == null ? Component.empty() : FlagIcon.build(code, plugin.storage());
                    return Tag.selfClosingInserting(icon);
                })
                .audiencePlaceholder(Player.class, "code", (player, queue, ctx) -> {
                    String code = plugin.applier().appliedCode(player.getUniqueId());
                    return Tag.selfClosingInserting(Component.text(code == null ? "" : code));
                })
                .build()
                .register();
    }
}
