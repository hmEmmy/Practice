package me.emmy.practice.config;

import lombok.Getter;
import me.emmy.practice.Practice;
import me.emmy.practice.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Emmy
 * @project Practice
 * @date 08/12/2024 - 09:42
 */
@Getter
public class ConfigHandler {
    private final Map<String, File> configFiles = new HashMap<>();
    private final Map<String, FileConfiguration> fileConfigurations = new HashMap<>();

    private final FileConfiguration kitsConfig;

    private final String[] configFileNames = {
            "kits.yml"
    };

    /**
     * Constructor for the ConfigHandler class.
     */
    public ConfigHandler() {
        for (String fileName : this.configFileNames) {
            loadConfig(fileName);
        }

        this.kitsConfig = this.getConfig("kits.yml");
    }

    /**
     * Load a configuration file.
     *
     * @param fileName The name of the file.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void loadConfig(String fileName) {
        File configFile = new File(Practice.getInstance().getDataFolder(), fileName);
        this.configFiles.put(fileName, configFile);
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            Practice.getInstance().saveResource(fileName, false);
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        this.fileConfigurations.put(fileName, config);
    }

    /**
     * Reload all configurations.
     */
    public void reloadConfigs() {
        for (String fileName : this.configFileNames) {
            loadConfig(fileName);
        }
    }

    /**
     * Save a configuration file.
     *
     * @param configFile The file to save.
     * @param fileConfiguration The configuration to save.
     */
    public void saveConfig(File configFile, FileConfiguration fileConfiguration) {
        try {
            fileConfiguration.save(configFile);
            fileConfiguration.load(configFile);
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(CC.translate("&cFailed to save " + configFile.getName() + " file."));
        }
    }

    /**
     * Get a file configuration by its name.
     *
     * @param configName The name of the config file.
     * @return The file configuration.
     */
    public FileConfiguration getConfig(String configName) {
        return this.fileConfigurations.get(configName);
    }

    /**
     * Get a file by its name.
     *
     * @param fileName The name of the file.
     * @return The file.
     */
    public File getConfigFile(String fileName) {
        return this.configFiles.get(fileName);
    }
}