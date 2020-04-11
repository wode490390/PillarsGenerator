package cn.wode490390.nukkit.pillarsgen;

import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.generator.Normal;
import cn.nukkit.math.NukkitRandom;

import java.util.Collections;
import java.util.Map;

public class PillarsGenerator extends Normal {

    protected PillarsGeneratorSettings settings;

    public PillarsGenerator() {
        this(Collections.emptyMap());
    }

    public PillarsGenerator(Map<String, Object> options) {
        super(options);
        this.settings = PillarsGeneratorPlugin.getInstance().getSettings();
    }

    @Override
    public void populateChunk(int chunkX, int chunkZ) {
        super.populateChunk(chunkX, chunkZ);
        ChunkManager level = this.getChunkManager();
        NukkitRandom random = new NukkitRandom(0xdeadbeef ^ (chunkX << 8) ^ chunkZ ^ level.getSeed());

        if (random.nextBoundedInt(10) == 0) {
            int baseX = chunkX << 4;
            int baseZ = chunkZ << 4;

            int offset = 1 + random.nextBoundedInt(10);
            int radius = offset + 3;
            int max = offset * offset + 1;

            for (int cx = -radius; cx <= radius; ++cx) {
                int x = baseX + cx;
                for (int cz = -radius; cz <= radius; ++cz) {
                    int z = baseZ + cz;
                    if (cx * cx + cz * cz <= max) {
                        for (int y = 1; y < 256; ++y) {
                            level.setBlockAt(x, y, z, this.settings.getBlockId(), this.settings.getBlockMeta());
                        }
                    }
                }
            }
        }
    }
}
