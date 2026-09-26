package com.leafsmp.flags;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Persists {country code -> skin texture hash} in flags.yml and knows the
 * full list of valid ISO 3166-1 alpha-2 codes (+ "xk" for Kosovo), mirroring
 * the {-countries::*} list from the original Skript.
 */
public class FlagStorage {

    // Same list as the "on load" split-string in the original script.
    public static final List<String> ALL_CODES = List.of(
            ("ad ae af ag ai al am ao aq ar as at au aw ax az ba bb bd be bf bg bh bi bj bl bm bn bo bq br bs bt bv " +
             "bw by bz ca cc cd cf cg ch ci ck cl cm cn co cr cu cv cw cx cy cz de dj dk dm do dz ec ee eg eh er es " +
             "et fi fj fk fm fo fr ga gb gd ge gf gg gh gi gl gm gn gp gq gr gs gt gu gw gy hk hm hn hr ht hu id ie " +
             "il im in io iq ir is it je jm jo jp ke kg kh ki km kn kp kr kw ky kz la lb lc li lk lr ls lt lu lv ly " +
             "ma mc md me mf mg mh mk ml mm mn mo mp mq mr ms mt mu mv mw mx my mz na nc ne nf ng ni nl no np nr nu " +
             "nz om pa pe pf pg ph pk pl pm pn pr ps pt pw py qa re ro rs ru rw sa sb sc sd se sg sh si sj sk sl sm " +
             "sn so sr ss st sv sx sy sz tc td tf tg th tj tk tl tm tn to tr tt tv tw tz ua ug um us uy uz va vc ve " +
             "vg vi vn vu wf ws xk ye yt za zm zw").split(" ")
    );

    private final File file;
    private final FileConfiguration config;

    public FlagStorage(FlagsPlugin plugin) {
        this.file = new File(plugin.getDataFolder(), "flags.yml");
        if (!file.exists()) {
            plugin.getDataFolder().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Nepodarilo se vytvorit flags.yml: " + e.getMessage());
            }
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public boolean isValidCode(String code) {
        return ALL_CODES.contains(code);
    }

    public boolean hasFlag(String code) {
        return config.contains("flags." + code);
    }

    public String getHash(String code) {
        return config.getString("flags." + code);
    }

    public void setHash(String code, String hash) {
        config.set("flags." + code, hash);
        save();
    }

    public void deleteHash(String code) {
        config.set("flags." + code, null);
        save();
    }

    /** Codes that already have a flag saved. */
    public List<String> addedCodes() {
        if (config.getConfigurationSection("flags") == null) return List.of();
        return new ArrayList<>(config.getConfigurationSection("flags").getKeys(false));
    }

    /** Codes that do NOT have a flag yet. */
    public List<String> missingCodes() {
        List<String> added = addedCodes();
        List<String> missing = new ArrayList<>(ALL_CODES);
        missing.removeAll(added);
        return missing;
    }

    private void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
