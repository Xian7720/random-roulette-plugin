package io.github.xian7720.randomroulette;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class RandomRoulettePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Plugin Start");
        Objects.requireNonNull(getCommand("roulette")).setExecutor(new RandomRoulette());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Plugin end");
    }
}
