package growthcraft.bamboo.common.block;

import growthcraft.bamboo.GrowthCraftBamboo;
import net.minecraft.block.BlockStairs;

public class BlockBambooStairs extends BlockStairs {
    public BlockBambooStairs() {
        super(GrowthCraftBamboo.blocks.bambooBlock.getBlockState());
        this.useNeighborBrightness = true;
        setCreativeTab(GrowthCraftBamboo.creativeTab);
        setUnlocalizedName("grc.bambooStairs");
    }
}
