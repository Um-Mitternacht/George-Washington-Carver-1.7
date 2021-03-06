package growthcraft.bees.common.block;

import growthcraft.api.core.util.BlockFlags;
import growthcraft.bees.GrowthCraftBees;
import growthcraft.bees.client.renderer.RenderBeeHive;
import growthcraft.core.common.block.GrcBlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockBeeHive extends GrcBlockBase {
    @SideOnly(Side.CLIENT)


    public BlockBeeHive() {
        super(Material.PLANTS);
        this.setHardness(0.6F);
        //setStepSound(soundTypeGrass);
        this.setUnlocalizedName("grc.beeHive");
        this.setCreativeTab(GrowthCraftBees.tab);
    }

    /************
     * TICK
     ************/
    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, BlockPos pos, Random random) {
        if (random.nextInt(24) == 0) {
            world.playSound((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F),
                    "grcbees:buzz", 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
        }
    }

    /************
     * TRIGGERS
     ************/
    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        super.onBlockAdded(world, pos, state);
        this.setDefaultDirection(world, pos);
    }

    private void setDefaultDirection(World world, BlockPos pos, IBlockState state) {
        if (!world.isRemote) {
            final Block zneg = world.getBlockState(x, y, z - 1);
            final Block zpos = world.getBlockState(x, y, z + 1);
            final Block xneg = world.getBlockState(x - 1, y, z);
            final Block xpos = world.getBlockState(x + 1, y, z);
            byte b0 = 3;

            if (zneg.isFullBlock() && !zpos.isFullBlock()) {
                b0 = 3;
            }

            if (zpos.isFullBlock() && !zneg.isFullBlock()) {
                b0 = 2;
            }

            if (xneg.isFullBlock() && !xpos.isFullBlock()) {
                b0 = 5;
            }

            if (xpos.isFullBlock() && !xneg.isFullBlock()) {
                b0 = 4;
            }

            world.setBlockState(pos, state, BlockFlags.SYNC);
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, entity, stack);
        final int face = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (face == 0) {
            world.setBlockState(pos, state, BlockFlags.SYNC);
        }

        if (face == 1) {
            world.setBlockState(pos, state, BlockFlags.SYNC);
        }

        if (face == 2) {
            world.setBlockState(pos, state, BlockFlags.SYNC);
        }

        if (face == 3) {
            world.setBlockState(pos, state, BlockFlags.SYNC);
        }
    }

    /************
     * CONDITIONS
     ************/
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return super.canPlaceBlockAt(world, pos) && canBlockStay(world, pos);
    }

    public void onNeighborChange(World world, BlockPos pos, Block par5) {
        super.onNeighborChange(world, pos, par5);
        if (!this.canBlockStay(world, pos)) {
            dropBlockAsItem(world, pos, world.getBlockState(pos), 0);
            world.setBlockToAir(pos);
        }
    }

    public boolean canBlockStay(World world, BlockPos pos) {
        if (world.isAirBlock(x, y + 1, z)) {
            return false;
        }
        return world.getBlockState(x, y + 1, z).isLeaves(world, x, y + 1, z);
    }

    /************
     * STUFF
     ************/
    @Override
    public boolean canSilkHarvest(World world, EntityPlayer player, BlockPos pos, int metadata) {
        return true;
    }

    /************
     * DROPS
     ************/
    @Override
    public Item getItemDropped(int par1, Random rand, int par3) {
        return GrowthCraftBees.items.bee.getItem();
    }

    @Override
    public int quantityDropped(Random rand) {
        return rand.nextInt(8);
    }

    @Override
    public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, int par5, float par6, int par7, int fortune) {
        super.dropBlockAsItemWithChance(world, pos, state, par5, fortune);
        if (!world.isRemote) {
            final int max = world.rand.nextInt(8);
            if (max > 0) {
                for (int i = 0; i < max; i++) {
                    if (world.rand.nextInt(2) == 0) {
                        dropBlockAsItem(world, pos, GrowthCraftBees.items.honeyCombEmpty.asStack(), fortune);
                    } else {
                        dropBlockAsItem(world, pos, GrowthCraftBees.items.honeyCombFilled.asStack(), fortune);
                    }
                }
            }
        }
    }

    /************
     * TEXTURES
     ************/
    @Override
    @SideOnly(Side.CLIENT)

    /************
     * RENDERS
     ************/
    @Override
    public int getRenderType() {
        return RenderBeeHive.id;
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
     * BOXES
     ************/
    //@Override
    //public void setBlockBoundsForItemRender()
    //{
    //	final float f = 0.0625F;
    //	this.getBoundingBox(2*f, 0.0F, 2*f, 14*f, 1.0F, 14*f);
    //}

    //@Override
    //@SuppressWarnings({"rawtypes", "unchecked"})
    //public void getCollisionBoundingBox(World world, BlockPos pos, AxisAlignedBB axis, List list, Entity entity)
    //{
    //	final float f = 0.0625F;
    //	this.getBoundingBox(4*f, 0.0F, 4*f, 12*f, 14*f, 12*f);
    //	super.getCollisionBoundingBox(world, pos, axis, list, entity);
    //	this.getBoundingBox(3*f, 1*f, 3*f, 13*f, 13*f, 13*f);
    //	super.getCollisionBoundingBox(world, pos, axis, list, entity);
    //	this.getBoundingBox(2*f, 4*f, 2*f, 14*f, 10*f, 14*f);
    //	super.getCollisionBoundingBox(world, pos, axis, list, entity);
    //	this.getBoundingBox(7*f, 14*f, 7*f, 9*f, 1.0F, 9*f);
    //	super.getCollisionBoundingBox(world, pos, axis, list, entity);
    //	this.setBlockBoundsForItemRender();
}
