package cn.wode490390.nukkit.pillarsgen;

public class PillarsGeneratorSettings {

    private final int blockId;
    private final int blockMeta;

    public PillarsGeneratorSettings(int blockId, int blockMeta) {
        this.blockId = blockId;
        this.blockMeta = blockMeta;
    }

    public int getBlockId() {
        return this.blockId;
    }

    public int getBlockMeta() {
        return this.blockMeta;
    }
}
