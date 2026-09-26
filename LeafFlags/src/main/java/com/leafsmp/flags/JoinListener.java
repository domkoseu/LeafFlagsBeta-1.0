package com.leafsmp.flags;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private final FlagsPlugin plugin;

    public JoinListener(FlagsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        plugin.applier().applyOnJoin(event.getPlayer());
    }
}
