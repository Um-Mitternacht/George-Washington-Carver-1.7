package growthcraft.cellar.common.block;

import growthcraft.api.core.util.BlockFlags;
import growthcraft.cellar.GrowthCraftCellar;
import growthcraft.cellar.client.render.RenderFruitPress;
import growthcraft.cellar.common.tileentity.TileEntityFruitPress;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockFruitPress extends BlockCellarContainer {
    @SideOnly(Side.CLIENT)


    public BlockFruitPress() {
        super(Material.WOOD);
        setTileEntityType(TileEntityFruitPress.class);
        setHardness(2.0F);
        //setStepSound(soundTypeWood);
        setUnlocalizedName("grc.fruitPress");
        setCreativeTab(GrowthCraftCellar.tab);
    }

    private Block getPresserBlock() {
        return GrowthCraftCellar.blocks.fruitPresser.getBlockState();
    }

    @Override
    public boolean isRotatable(IBlockAccess world, BlockPos pos, EnumFacing side) {
        return true;
    }

    @Override
    public void doRotateBlock(World world, BlockPos pos, EnumFacing side) {
        world.setBlockState(pos, world.getBlockState(pos) ^ 1, BlockFlags.SYNC);
        world.setBlockState(x, y + 1, z, world.getBlockState(x, y + 1, z) ^ 1, BlockFlags.SYNC);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        super.onBlockAdded(world, pos, state);
        this.setDefaultDirection(world, pos);
        world.setBlockState(x, y + 1, z, getPresserBlock(), world.getBlockState(pos), 2);
    }

    private void setDefaultDirection(World world, BlockPos pos, IBlockState state) {
        if (!world.isRemote) {
            final Block block = world.getBlockState(x, y, z - 1);
            final Block block1 = world.getBlockState(x, y, z + 1);
            final Block block2 = world.getBlockState(x - 1, y, z);
            final Block block3 = world.getBlockState(x + 1, y, z);
            byte meta = 3;

            if (block.isFullBlock() && !block1.isFullBlock()) {
                meta = 3;
            }

            if (block1.isFullBlock() && !block.isFullBlock()) {
                meta = 2;
            }

            if (block2.isFullBlock() && !block3.isFullBlock()) {
                meta = 5;
            }

            if (block3.isFullBlock() && !block2.isFullBlock()) {
                meta = 4;
            }

            world.setBlockState(pos, state, BlockFlags.UPDATE_AND_SYNC);
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, EntityLivingBase entity, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, entity, stack);
        final int a = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (a == 0 || a == 2) {
            world.setBlockState(x, y, z, 0, BlockFlags.SYNC);
        } else if (a == 1 || a == 3) {
            world.setBlockState(x, y, z, 1, BlockFlags.SYNC);
        }

        world.setBlockState(x, y + 1, z, getPresserBlock(), world.getBlockState(x, y, z), BlockFlags.SYNC);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, int m, EntityPlayer player) {
        if (player.capabilities.isCreativeMode && (m & 8) != 0 && presserIsAbove(world, x, y, z)) {
            world.destroyBlock(x, y + 1, z, true);
            world.getTileEntity(x, y + 1, z).invalidate();
        }
    }

    @Override
    public void onNeighborChange(World world, BlockPos pos, Block block) {
        if (!this.canBlockStay(world, pos)) {
            world.destroyBlock(pos, true);
        }
    }

    /************
     * CONDITIONS
     ************/
    @Override
    public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing side) {
        final int meta = world.getBlockState(pos);

        if (meta == 0) {
            return side == EnumFacing.EAST || side == EnumFacing.WEST;
        } else if (meta == 1) {
            return side == EnumFacing.NORTH || side == EnumFacing.SOUTH;
        }

        return isNormalCube(world, pos);
    }

    /************
     * STUFF
     ************/

    /**
     * @param world - world block is in
     * @param x     - x coord
     * @param y     - y coord
     * @param z     - z coord
     * @return true if the BlockFruitPresser is above this block, false otherwise
     */
    public boolean presserIsAbove(World world, BlockPos pos) {
        return getPresserBlock() == world.getBlockState(x, y + 1, z);
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos) {
        return presserIsAbove(world, pos);
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        if (pos.getY() >= 255) return false;

        return World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) &&
                super.canPlaceBlockAt(world, pos) &&
                super.canPlaceBlockAt(world, x, y + 1, z);
    }

    @Override
    public int quantityDropped(Random random) {
        return 1;
    }

    /************
     * TEXTURES
     ************/
    //@Override
    //@SideOnly(Side.CLIENT)

    //{
    //	this.icons = new IIcon[6];
//
    //	icons[0] = reg.registerIcon("grccellar:fruit_press_wood_bottom");
    //	icons[1] = reg.registerIcon("grccellar:fruit_press_wood_top");
    //	icons[2] = reg.registerIcon("grccellar:fruit_press_wood_side");
    //	icons[3] = reg.registerIcon("grccellar:fruit_press_metal_bottom");
    //	icons[4] = reg.registerIcon("grccellar:fruit_press_metal_top");
    //	icons[5] = reg.registerIcon("grccellar:fruit_press_metal_side");
    //}

    //@SideOnly(Side.CLIENT)
    //public IIcon getIconByIndex(int index)
    //{
    //	return icons[index];
    //}

    //@Override
    //@SideOnly(Side.CLIENT)
    //
    //{
    //	if (side == 0)
    //	{
    //		return icons[0];
    //	}
    //	else if (side == 1)
    //	{
    //		return icons[1];
    //	}
    //	return icons[2];
    //}

    /************
     * RENDERS
     ************/
    @Override
    public int getRenderType() {
        return RenderFruitPress.RENDER_ID;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, BlockPos pos, int side) {
        return true;
    }

    /************
     * COMPARATOR
     ************/
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, BlockPos pos, int par5) {
        final TileEntityFruitPress te = getTileEntity(world, pos);
        if (te != null) {
            return te.getFluidAmountScaled(15, 0);
        }
        return 0;
    }
}
