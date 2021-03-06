package growthcraft.hops.common.item;

import growthcraft.core.GrowthCraftCore;
import growthcraft.core.common.item.GrcItemBase;
import growthcraft.core.util.BlockCheck;
import growthcraft.hops.GrowthCraftHops;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public abstract class ItemHopSeeds extends GrcItemBase implements IPlantable {
    public ItemHopSeeds() {
        super();
        this.setUnlocalizedName("grc.hopSeeds");
        this.setCreativeTab(GrowthCraftCore.creativeTab);
    }

    /************
     * MAIN
     ************/
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing face, int EnumFacing, float par8, float par9, float par10) {
        if (EnumFacing != 1) {
            return false;
        } else if (player.canPlayerEdit(pos, face, stack) && player.canPlayerEdit(x, y + 1, z, EnumFacing, stack)) {
            if (BlockCheck.canSustainPlant(world, pos, face, GrowthCraftHops.blocks.hopVine.getBlockState()) && BlockCheck.isRope(world, x, y + 1, z)) {
                world.setBlockState(x, y + 1, z, GrowthCraftHops.blocks.hopVine.getBlockState());
                --stack.stackSize;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /************
     * TEXTURES
     ************/
    //@Override
    //@SideOnly(Side.CLIENT)
    //public void registerIcons(IIconRegister reg)
    //{
    //	this.itemIcon = reg.registerIcon("grchops:hop_seeds");
    //}
    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }

    @Override
    public Block getPlant(IBlockAccess world, BlockPos pos) {
        return GrowthCraftHops.blocks.hopVine.getBlockState();
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, BlockPos pos) {
        return 0;
    }
}
