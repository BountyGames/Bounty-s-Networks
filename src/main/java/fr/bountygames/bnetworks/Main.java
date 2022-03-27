package fr.bountygames.bnetworks;

import fr.bountygames.bnetworks.cmds.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public final class Main extends JavaPlugin {

    private String lang = getConfig().getString("lang");
    private FileConfiguration langConfig;

    @Override
    public void onEnable() {

        saveDefaultConfig();
        createLanguage();

        if(languageExists(lang)) {
            getLogger().severe("Language " + lang + " doesn't exist");
            getLogger().severe("Disabling plugin");
            getServer().getPluginManager().disablePlugin(this);
        } else {
            loadLanguage(lang);
        }

        // Commands registration
        new NetworksCommands(this);

        getLogger().info(Objects.requireNonNull(langConfig.getString("plugin_enabled"))
                .replace("&", "§"));
    }

    @Override
    public void onDisable() {
        getLogger().info(Objects.requireNonNull(langConfig.getString("plugin_disabled"))
                .replace("&", "§"));
    }

    /**
     * Check if language exists
     * @param language Language to check
     * @return true if language exists
     */
    private boolean languageExists(String language) {
        return getResource("lang/" + language + ".yml") == null;
    }

    /**
     * @param language Language to load
     */
    private void loadLanguage(String language) {
        lang = language;
        File langFile = new File(getDataFolder(), "lang/" + lang + ".yml");
        langConfig = YamlConfiguration.loadConfiguration(langFile);
    }

    private void createLanguage() {
        for(String language : getConfig().getStringList("langs")) {
            File langFile = new File(getDataFolder(), "lang/" + language + ".yml");
            if(!langFile.getParentFile().exists()) {
                langFile.getParentFile().mkdirs();
            }
            if(!langFile.exists()) {
                saveResource("lang/" + language + ".yml", false);
                getLogger().info("Language " + language + " created");
            }
        }
    }

    public FileConfiguration getLangConfig() {
        return langConfig;
    }

}
