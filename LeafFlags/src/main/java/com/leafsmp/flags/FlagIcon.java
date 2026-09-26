package com.leafsmp.flags;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.object.ObjectContents;
import net.kyori.adventure.text.object.PlayerHeadObjectContents;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Equivalent of the original Skript's flagComponent() function: turns a
 * saved skin-texture hash into an inline player-head object component
 * (requires Minecraft 1.21.9+ / Adventure 4.25.0+, which 1.21.11 has).
 */
public final class FlagIcon {

    private FlagIcon() {
    }

    public static Component build(String code, FlagStorage storage) {
        String hash = storage.getHash(code);
        if (hash == null) {
            return Component.empty();
        }

        String json = "{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/" + hash + "\"}}}";
        String base64 = Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));

        PlayerHeadObjectContents.ProfileProperty property =
                PlayerHeadObjectContents.property("textures", base64);

        ObjectContents contents = ObjectContents.playerHead()
                .profileProperty(property)
                .build();

        return Component.object(contents);
    }
}
