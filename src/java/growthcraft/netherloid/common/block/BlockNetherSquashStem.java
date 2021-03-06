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
import growthcraft.api.core.util.RenderType;
import growthcraft.core.common.block.ICropDataProvider;
import growthcraft.core.integration.AppleCore;
import growthcraft.netherloid.netherloid;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.Random;

public class BlockNetherSquashStem extends BlockBush implements ICropDataProvider, IGrowable, IPlantable {
    private final Block fruitBlock;

    public BlockNetherSquashStem(Block block) {
        super();
        this.fruitBlock = block;
        setTickRandomly(true);
        setCreativeTab(null);
        //setBlockTextureName("grcnetherloid:soulsquash_stem");
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return false;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return false;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {

    }

    //@SideOnly(Side.CLIENT)
    //private IIcon stemConnectedIcon;

    @Override
    public float getGrowthProgress(IBlockAccess world, BlockPos pos, int meta) {
        return (float) meta / (float) StemStage.MATURE;
    }

    protected boolean func_149854_a(Block block) {
        return Blocks.SOUL_SAND == block;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return super.canPlaceBlockAt(world, x, y, z) && func_149854_a(world.getBlockState(x, y + 1, z));
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos) {
        return func_149854_a(world.getBlockState(x, y + 1, z));
    }

    public boolean hasGrownFruit(World world, BlockPos pos) {
        return fruitBlock == world.getBlockState(x, y - 1, z);
    }

    public boolean canGrowFruit(World world, BlockPos pos) {
        return world.isAirBlock(x, y - 1, z);
    }

    public void incrementGrowth(World world, BlockPos pos, int previousMeta) {
        final int meta = MathHelper.clamp_int(previousMeta + MathHelper.getRandomIntegerInRange(world.rand, 2, 5), 0, StemStage.MATURE);
        world.setBlockState(x, y, z, meta + 1, BlockFlags.SYNC);
        AppleCore.announceGrowthTick(this, world, x, y, z, previousMeta);
    }

    private void growStem(World world, BlockPos pos, int meta) {
        if (meta < StemStage.MATURE) {
            incrementGrowth(world, pos, meta);
        } else if (canGrowFruit(world, pos)) {
            world.setBlockState(x, y - 1, z, fruitBlock, world.rand.nextInt(4), BlockFlags.SYNC);
        }
    }

    @Override
    public void updateTick(World world, BlockPos pos, Random random, Block block, IBlockState state) {
        final Event.Result allowGrowthResult = AppleCore.validateGrowthTick(block, world, pos, random, state);
        if (allowGrowthResult == Event.Result.DENY)
            return;

        if (allowGrowthResult == Event.Result.ALLOW || random.nextInt(10) == 0) {
            final int meta = world.getBlockState(meta);
            growStem(world, pos, meta);
        }
    }

    /* Client Side: can bonemeal */
    @Override
    public boolean func_149851_a(World world, BlockPos pos, boolean isClient) {
        return world.getBlockState(x, y, z) < StemStage.MATURE || canGrowFruit(world, pos);
    }

    /* SideOnly(Side.SERVER) Can this apply bonemeal effect? */
    @Override
    public boolean func_149852_a(World world, Random random, BlockPos pos) {
        return true;
    }

    /* IGrowable: Apply bonemeal effect */
    @Override
    public void func_149853_b(World world, Random random, BlockPos pos) {
        growStem(world, pos, world.getBlockState(pos));
    }

    @Override
    public void dropBlockAsItemWithChance(World world, BlockPos pos, int meta, float f, int weight, IBlockState state, int fortune) {
        super.dropBlockAsItemWithChance(world, pos, state, meta, fortune);
        if (!world.isRemote) {
            final ItemStack item = netherloid.items.netherSquashSeeds.asStack();
            for (int i = 0; i < 3; ++i) {
                if (world.rand.nextInt(15) <= meta) {
                    //dropBlockAsItem_do(world, x, y, z, item);
                }
            }
        }
    }

    @Override
    public Item getItemDropped(int meta, Random random, int par3) {
        return null;
    }

    @Override
    public int quantityDropped(Random random) {
        return 1;
    }

    @Override
    public Item getItem(World world, BlockPos pos) {
        return netherloid.items.netherSquashSeeds.getItem();
    }

    @Override
    public int getRenderType() {
        return RenderType.BUSH;
    }

    public static class StemStage {
        public static final int MATURE = 7;

        private StemStage() {
        }
    }

    //@Override
    //@SideOnly(Side.CLIENT)

    //{
    //	this.blockIcon = reg.registerIcon(this.getTextureName() + "_disconnected");
    //	this.stemConnectedIcon = reg.registerIcon(this.getTextureName() + "_connected");
    //}

    //@Override
    //@SideOnly(Side.CLIENT)
    //
    //{
    //	if ((meta & 8) != 0)
    //	{
    //		return stemConnectedIcon;
    //	}
    //	return blockIcon;
    //}
}
