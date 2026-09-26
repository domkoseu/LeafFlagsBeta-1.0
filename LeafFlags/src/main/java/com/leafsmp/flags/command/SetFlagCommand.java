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

public class SetFlagCommand implements CommandExecutor, TabCompleter {

    private final FlagsPlugin plugin;

    public SetFlagCommand(FlagsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(Component.text("Pouziti: /setflag <kod zeme> <hash>", NamedTextColor.RED));
            return true;
        }

        String code = args[0].toLowerCase();
        String hash = args[1];

        if (!plugin.storage().isValidCode(code)) {
            sender.sendMessage(Component.text("Neznamy kod zeme: ", NamedTextColor.RED)
                    .append(Component.text(code, NamedTextColor.WHITE)));
            return true;
        }

        boolean existed = plugin.storage().hasFlag(code);
        plugin.storage().setHash(code, hash);
        plugin.applier().reapplyAll();

        sender.sendMessage(existed
                ? Component.text("Vlajka ", NamedTextColor.GREEN)
                    .append(Component.text(code, NamedTextColor.WHITE))
                    .append(Component.text(" aktualizovana.", NamedTextColor.GREEN))
                : Component.text("Vlajka ", NamedTextColor.GREEN)
                    .append(Component.text(code, NamedTextColor.WHITE))
                    .append(Component.text(" pridana.", NamedTextColor.GREEN)));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            String prefix = args[0].toLowerCase();
            return plugin.storage().missingCodes().stream()
                    .filter(c -> c.startsWith(prefix))
                    .collect(Collectors.toList());
        }
        return List.of();
    }
}
