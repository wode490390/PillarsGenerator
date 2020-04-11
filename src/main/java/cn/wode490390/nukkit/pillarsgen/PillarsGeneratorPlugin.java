package cn.wode490390.nukkit.pillarsgen;

import cn.nukkit.block.Block;
import cn.nukkit.level.GlobalBlockPalette;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.wode490390.nukkit.pillarsgen.util.MetricsLite;

import java.util.NoSuchElementException;

public class PillarsGeneratorPlugin extends PluginBase {

    private static PillarsGeneratorPlugin INSTANCE;

    private PillarsGeneratorSettings settings;

    @Override
    public void onLoad() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        try {
            new MetricsLite(this, 7077);
        } catch (Throwable ignore) {

        }

        this.saveDefaultConfig();
        Config config = this.getConfig();

        String node = "overworld";
        boolean overworld = true;
        try {
            overworld = config.getBoolean(node, overworld);
        } catch (Exception e) {
            this.logConfigException(node, e);
        }

        node = "nether";
        boolean nether = true;
        try {
            nether = config.getBoolean(node, nether);
        } catch (Exception e) {
            this.logConfigException(node, e);
        }

        node = "material";
        int material = Block.OBSIDIAN;
        try {
            material = config.getInt(node, material);
        } catch (Exception e) {
            this.logConfigException(node, e);
        }

        node = "meta";
        int meta = 0;
        try {
            meta = config.getInt(node, meta);
        } catch (Exception e) {
            this.logConfigException(node, e);
        }

        try {
            GlobalBlockPalette.getOrCreateRuntimeId(material, 0);
            try {
                GlobalBlockPalette.getOrCreateRuntimeId(material, meta);
            } catch (NoSuchElementException e) {
                meta = 0;
                this.getLogger().warning("Invalid block meta. Use the default value.");
            }
        } catch (NoSuchElementException e) {
            material = Block.OBSIDIAN;
            meta = 0;
            this.getLogger().warning("Invalid block ID. Use the default value.");
        }

        this.settings = new PillarsGeneratorSettings(material, meta);

        if (overworld) {
            Generator.addGenerator(PillarsGenerator.class, "default", Generator.TYPE_INFINITE);
            Generator.addGenerator(PillarsGenerator.class, "normal", Generator.TYPE_INFINITE);
        }
        if (nether) {
            Generator.addGenerator(PillarsNetherGenerator.class, "nether", Generator.TYPE_NETHER);
        }
    }

    public PillarsGeneratorSettings getSettings() {
        return this.settings;
    }

    private void logConfigException(String node, Throwable t) {
        this.getLogger().alert("An error occurred while reading the configuration '" + node + "'. Use the default value.", t);
    }

    public static PillarsGeneratorPlugin getInstance() {
        return INSTANCE;
    }
}
