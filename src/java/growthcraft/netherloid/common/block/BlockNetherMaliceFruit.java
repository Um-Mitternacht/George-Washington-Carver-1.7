/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015, 2016 IceDragon200
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package growthcraft.netherloid.common.block;

import growthcraft.api.core.util.BlockFlags;
import growthcraft.core.client.renderer.RenderBlockFruit;
import growthcraft.core.common.block.ICropDataProvider;
import growthcraft.core.integration.AppleCore;
import growthcraft.netherloid.netherloid;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public abstract class BlockNetherMaliceFruit extends Block implements IGrowable, ICropDataProvider {
    @SideOnly(Side.CLIENT)


    private final int growth = netherloid.getConfig().maliceFruitGrowthRate;
    private final boolean dropRipeMaliceFruit = netherloid.getConfig().dropRipeMaliceFruit;
    private final int dropChance = netherloid.getConfig().maliceFruitDropChance;

    //@Override
    //@SideOnly(Side.CLIENT)
    //
    {
        return this.icons[meta];
    }

    public BlockNetherMaliceFruit() {
        super(Material.PLANTS);
        setTickRandomly(true);
        setHardness(0.2F);
        setResistance(5.0F);
        //setStepSound(soundTypeWood);
        setUnlocalizedName("grcnetherloid.netherMaliceFruit");
        setCreativeTab(null);
    }

    public float getGrowthProgress(IBlockAccess world, BlockPos pos, int meta) {
        return (float) meta / (float) MaliceFruitStage.MATURE;
    }

    void incrementGrowth(World world, BlockPos pos, int meta, IBlockState state) {
        world.setBlockState(pos, state, meta + 1, BlockFlags.SYNC);
        AppleCore.announceGrowthTick(this, world, pos, meta);
    }

    /* Can this accept bonemeal? */
    @Override
    public boolean func_149851_a(World world, BlockPos pos, boolean isClient) {
        return world.getBlockState(pos) < MaliceFruitStage.MATURE;
    }

    /* SideOnly(Side.SERVER) Can this apply bonemeal effect? */
    @Override
    public boolean func_149852_a(World world, Random random, BlockPos pos) {
        return true;
    }

    /* Apply bonemeal effect */
    @Override
    public void func_149853_b(World world, Random random, BlockPos pos) {
        incrementGrowth(world, pos, world.getBlockState(pos));
    }

    /**
     * Drops the block as an item and replaces it with air
     *
     * @param world - world to drop in
     * @param x     - x Coord
     * @param y     - y Coord
     * @param z     - z Coord
     */
    public void fellBlockAsItem(World world, BlockPos pos) {
        this.dropBlockAsItem(world, pos, world.getBlockState(pos), 0);
        world.setBlockToAir(pos);
    }

    @Override
    public void updateTick(World world, BlockPos pos, Random random, IBlockState state) {
        if (!this.canBlockStay(world, pos)) {
            this.dropBlockAsItem(world, pos, world.getBlockState(pos), 0);
            world.setBlockState(pos, Blocks.AIR, 0, BlockFlags.SYNC);
        } else {
            final Event.Result allowGrowthResult = AppleCore.validateGrowthTick(this, world, pos, random);
            if (allowGrowthResult == Event.Result.DENY)
                return;

            final boolean continueGrowth = random.nextInt(this.growth) == 0;
            if (allowGrowthResult == Event.Result.ALLOW || continueGrowth) {
                final int meta = world.getBlockState(pos);
                if (meta < MaliceFruitStage.MATURE) {
                    incrementGrowth(world, pos, meta, state);
                } else if (dropRipeMaliceFruit && world.rand.nextInt(this.dropChance) == 0) {
                    fellBlockAsItem(world, pos);
                }
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, EntityPlayer player, int EnumFacing, float par7, float par8, float par9) {
        if (world.getBlockState(pos) >= MaliceFruitStage.MATURE) {
            if (!world.isRemote) {
                fellBlockAsItem(world, pos);
            }
            return true;
        }
        return false;
    }

    @Override
    public void onNeighborChange(World world, BlockPos pos, Block block) {
        if (!this.canBlockStay(world, pos)) {
            fellBlockAsItem(world, pos);
        }
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos) {
        return netherloid.blocks.netherMaliceLeaves.getBlockState() == world.getBlockState(x, y + 1, z) &&
                (world.getBlockState(x, y + 1, z) & 3) == 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, BlockPos pos) {
        return netherloid.items.netherMaliceFruit.getItem();
    }

    @Override
    public Item getItemDropped(int meta, Random random, int par3) {
        return meta >= MaliceFruitStage.MATURE ? netherloid.items.netherMaliceFruit.getItem() : null;
    }

    @Override
    public int quantityDropped(Random random) {
        return 1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return RenderBlockFruit.id;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    //@Override
    //@SideOnly(Side.CLIENT)

    //{
    //	icons = new IIcon[MaliceFruitStage.COUNT];
//
    //	for (int i = 0; i < icons.length; ++i )
    //	{
    //		icons[i] = reg.registerIcon("grcnetherloid:malice_fruit_" + i);
    //	}
    //}

    public static class MaliceFruitStage {
        public static final int YOUNG = 0;
        public static final int MID = 1;
        public static final int MATURE = 2;
        public static final int COUNT = 3;

        private MaliceFruitStage() {
        }
    }

    //@Override
    //public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, BlockPos pos)
    //{
    //	this.setBlockBoundsBasedOnState(world, x, y, z);
    //	return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    //}

    //@Override
    //@SideOnly(Side.CLIENT)
    //public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, BlockPos pos)
    //{
    //	this.setBlockBoundsBasedOnState(world, x, y, z);
    //	return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    //}

    //@Override
    //public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos)
    //{
    //	final int meta = world.getBlockState(x, y, z);
    //	final float f = 0.0625F;
//
    //	if (meta == MaliceFruitStage.YOUNG)
    //	{
    //		this.getBoundingBox(6*f, 11*f, 6*f, 10*f, 15*f, 10*f);
    //	}
    //	else if (meta == MaliceFruitStage.MID)
    //	{
    //		this.getBoundingBox((float)(5.5*f), 10*f, (float)(5.5*f), (float)(10.5*f), 15*f, (float)(10.5*f));
    //	}
    //	else
    //	{
    //		this.getBoundingBox(5*f, 9*f, 5*f, 11*f, 15*f, 11*f);
    //	}
}
