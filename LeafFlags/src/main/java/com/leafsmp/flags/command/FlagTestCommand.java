package com.leafsmp.flags.command;

import com.leafsmp.flags.FlagIcon;
import com.leafsmp.flags.FlagsPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class FlagTestCommand implements CommandExecutor, TabCompleter {

    private final FlagsPlugin plugin;

    public FlagTestCommand(FlagsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Tento prikaz muze pouzit jen hrac.");
            return true;
        }
        if (args.length < 1) {
            player.sendMessage(Component.text("Pouziti: /flagtest <kod zeme>", NamedTextColor.RED));
            return true;
        }

        String code = args[0].toLowerCase();
        if (!plugin.storage().hasFlag(code)) {
            player.sendMessage(Component.text("Zadna ulozena vlajka pro: ", NamedTextColor.RED)
                    .append(Component.text(code, NamedTextColor.WHITE)));
            return true;
        }

        Component flag = FlagIcon.build(code, plugin.storage());
        player.sendMessage(flag.append(Component.text(" " + code)));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            String prefix = args[0].toLowerCase();
            return plugin.storage().addedCodes().stream()
                    .filter(c -> c.startsWith(prefix))
                    .collect(Collectors.toList());
        }
        return List.of();
    }
}
