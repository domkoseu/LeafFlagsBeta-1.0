package com.leafsmp.flags;

import com.leafsmp.flags.command.DelFlagCommand;
import com.leafsmp.flags.command.FlagTestCommand;
import com.leafsmp.flags.command.SetFlagCommand;
import com.leafsmp.flags.mini.MiniPlaceholdersHook;
import com.leafsmp.flags.papi.FlagsExpansion;
import org.bukkit.plugin.java.JavaPlugin;

public class FlagsPlugin extends JavaPlugin {

    private FlagStorage storage;
    private FlagApplier applier;

    @Override
    public void onEnable() {
        this.storage = new FlagStorage(this);
        this.applier = new FlagApplier(this);

        getServer().getPluginManager().registerEvents(new JoinListener(this), this);

        getCommand("setflag").setExecutor(new SetFlagCommand(this));
        getCommand("delflag").setExecutor(new DelFlagCommand(this));
        getCommand("flagtest").setExecutor(new FlagTestCommand(this));

        if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new FlagsExpansion(this).register();
            getLogger().info("PlaceholderAPI expansion 'flags' zaregistrovana (%flags%, %flags_code%, %flags_has%).");
        } else {
            getLogger().warning("PlaceholderAPI nenalezeno - %flags% placeholder nebude dostupny, GeoIP lookup pro applyFlag tez nepobezi.");
        }

        if (getServer().getPluginManager().isPluginEnabled("MiniPlaceholders")) {
            MiniPlaceholdersHook.register(this);
            getLogger().info("MiniPlaceholders expanze 'flags' zaregistrovana (<flags_flag>, <flags_code>).");
        }
    }

    public FlagStorage storage() {
        return storage;
    }

    public FlagApplier applier() {
        return applier;
    }
}
