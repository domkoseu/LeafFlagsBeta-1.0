package com.leafsmp.flags;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Reads %geolocation_countryCode% for a player, puts them into a
 * "flag_<code>" scoreboard team whose suffix is the flag head, and remembers
 * the last-applied code so the PAPI expansion can expose it as text.
 */
public class FlagApplier {

    private final FlagsPlugin plugin;
    private final Map<UUID, String> appliedCode = new HashMap<>();

    public FlagApplier(FlagsPlugin plugin) {
        this.plugin = plugin;
    }

    public String appliedCode(UUID uuid) {
        return appliedCode.get(uuid);
    }

    /** Schedules applyFlag() one second after join, same as the "wait 1 second" in the Skript. */
    public void applyOnJoin(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.isOnline()) {
                    apply(player);
                }
            }
        }.runTaskLater(plugin, 20L);
    }

    public void apply(Player player) {
        if (!plugin.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return;
        }

        String raw = PlaceholderAPI.setPlaceholders(player, "%geolocation_countryCode%");
        if (raw == null) return;
        String code = raw.toLowerCase();

        if (code.length() != 2) return;
        if (!plugin.storage().hasFlag(code)) return;

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        String teamName = "flag_" + code;
        Team team = scoreboard.getTeam(teamName);
        if (team == null) {
            team = scoreboard.registerNewTeam(teamName);
        }

        Component suffix = Component.text(" ").append(FlagIcon.build(code, plugin.storage()));
        team.suffix(suffix);
        team.addEntry(player.getName());

        appliedCode.put(player.getUniqueId(), code);
    }

    /** Re-applies every online player's flag, e.g. right after /setflag changes a texture. */
    public void reapplyAll() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            apply(p);
        }
    }

    /** Removes a scoreboard team, used by /delflag. */
    public void removeTeam(String code) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam("flag_" + code);
        if (team != null) {
            team.unregister();
        }
    }
}
