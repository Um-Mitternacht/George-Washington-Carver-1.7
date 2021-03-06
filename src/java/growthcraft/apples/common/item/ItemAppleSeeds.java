package growthcraft.apples.common.item;

import growthcraft.apples.GrowthCraftApples;
import growthcraft.core.GrowthCraftCore;
import growthcraft.core.common.item.GrcItemBase;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class ItemAppleSeeds extends GrcItemBase implements IPlantable {
    private Block cropBlock;

    public ItemAppleSeeds() {
        super();
        this.cropBlock = GrowthCraftApples.blocks.appleSapling.getBlockState();
        this.setUnlocalizedName("grc.appleSeeds");
        this.setCreativeTab(GrowthCraftCore.creativeTab);
    }

    /************
     * MAIN
     ************/
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, Block block, World world, BlockPos pos, int EnumFacing, float par8, float par9, float par10, IBlockState state) {
        block = (Block) world.getBlockState(pos);
        if (block == Blocks.SNOW_LAYER && (world.getBlockState(pos) & 7) < 1) {
            EnumFacing = 1;
        } else if (block != Blocks.VINE && block != Blocks.TALLGRASS && block != Blocks.DEADBUSH) {
            if (EnumFacing == 0) {
                --y;
            }

            if (EnumFacing == 1) {
                ++y;
            }

            if (EnumFacing == 2) {
                --z;
            }

            if (EnumFacing == 3) {
                ++z;
            }

            if (EnumFacing == 4) {
                --x;
            }

            if (EnumFacing == 5) {
                ++x;
            }
        }


        if (!player.canPlayerEdit(pos, EnumFacing, stack)) {
            return false;
        } else if (stack.stackSize == 0) {
            return false;
        } else {
            if (world.canPlaceEntityOnSide(cropBlock, pos, false, EnumFacing, (Entity) null, stack)) {
                final int meta = cropBlock.onBlockPlaced(world, pos, EnumFacing, par8, par9, par10, 0);

                if (world.setBlockState(pos, cropBlock, meta, 3)) {
                    if (world.getBlockState(pos) == cropBlock) {
                        cropBlock.onBlockPlacedBy(world, pos, state, player, stack);
                        cropBlock.onPostBlockPlaced(world, pos, meta);
                    }

                    world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), cropBlock.stepSound.func_150496_b(), (cropBlock.stepSound.getVolume() + 1.0F) / 2.0F, cropBlock.stepSound.getPitch() * 0.8F);
                    --stack.stackSize;
                }
            }

            return true;
        }
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Plains;
    }

    @Override
    public Block getPlant(IBlockAccess world, BlockPos pos) {
        return cropBlock;
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, BlockPos pos) {
        return 0;
    }

    /************
     * TEXTURES
     ************/
    //@Override
    //@SideOnly(Side.CLIENT)
    //public void registerIcons(IIconRegister reg)
    //{
    //	this.itemIcon = reg.registerIcon("grcapples:apple_seed");
    //}
}
