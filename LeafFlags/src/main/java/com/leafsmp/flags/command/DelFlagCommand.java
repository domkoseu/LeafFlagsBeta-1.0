package com.leafsmp.flags.command;

import com.leafsmp.flags.FlagsPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;
import java.util.stream.Collectors;

public class DelFlagCommand implements CommandExecutor, TabCompleter {

    private final FlagsPlugin plugin;

    public DelFlagCommand(FlagsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(Component.text("Pouziti: /delflag <kod zeme>", NamedTextColor.RED));
            return true;
        }

        String code = args[0].toLowerCase();
        if (!plugin.storage().hasFlag(code)) {
            sender.sendMessage(Component.text("Zadna ulozena vlajka pro: ", NamedTextColor.RED)
                    .append(Component.text(code, NamedTextColor.WHITE)));
            return true;
        }

        plugin.storage().deleteHash(code);
        plugin.applier().removeTeam(code);

        sender.sendMessage(Component.text("Vlajka ", NamedTextColor.GREEN)
                .append(Component.text(code, NamedTextColor.WHITE))
                .append(Component.text(" smazana.", NamedTextColor.GREEN)));
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
