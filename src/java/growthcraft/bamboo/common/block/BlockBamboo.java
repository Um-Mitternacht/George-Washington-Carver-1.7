package growthcraft.bamboo.common.block;

import growthcraft.bamboo.GrowthCraftBamboo;
import growthcraft.core.common.block.GrcBlockBase;
import net.minecraft.block.material.Material;


public class BlockBamboo extends GrcBlockBase {
    public BlockBamboo() {
        super(Material.WOOD);
        //setStepSound(soundTypeWood);
        setResistance(5.0F);
        setHardness(2.0F);
        setCreativeTab(GrowthCraftBamboo.creativeTab);
        setUnlocalizedName("grc.bambooBlock");
    }
}
