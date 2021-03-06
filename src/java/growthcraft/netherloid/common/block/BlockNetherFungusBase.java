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
import growthcraft.core.util.BlockCheck;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public abstract class BlockNetherFungusBase extends BlockBush implements IPlantable, IGrowable {
    public BlockNetherFungusBase() {
        super();
        setTickRandomly(true);
    }

    protected boolean func_149854_a(Block block) {
        return Blocks.NETHERRACK == block || Blocks.SOUL_SAND == block;
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos) {
        return BlockCheck.canSustainPlant(world, x, y - 1, z, EnumFacing.UP, this);
    }

    protected void growFungus(World world, BlockPos pos) {
        if (world.isAirBlock(x, y, z) && canBlockStay(world, x, y, z)) {
            world.setBlockState(x, y, z, this, 0, BlockFlags.SYNC);
        }
    }

    public boolean canFungusSpread(World world, BlockPos pos) {
        for (BlockCheck.BlockDirection EnumFacing : BlockCheck.DIR8) {
            if (world.isAirBlock(x + EnumFacing.offsetX, y, z + EnumFacing.offsetZ) && canBlockStay(world, x + EnumFacing.offsetX, y, z + EnumFacing.offsetZ)) {
                return true;
            }
        }
        return false;
    }

    /* Can this accept bonemeal? */
    @Override
    public boolean func_149851_a(World world, BlockPos pos, boolean isClient) {
        return canFungusSpread(world, x, y, z);
    }

    /* SideOnly(Side.SERVER) Can this apply bonemeal effect? */
    @Override
    public boolean func_149852_a(World world, Random random, BlockPos pos) {
        return true;
    }

    /* Apply bonemeal effect */
    @Override
    public void func_149853_b(World world, Random random, BlockPos pos) {
        final BlockCheck.BlockDirection EnumFacing = BlockCheck.randomDirection8(random);
        growFungus(world, x + EnumFacing.offsetX, y, z + EnumFacing.offsetZ);
    }

    protected abstract float getSpreadRate(World world, BlockPos pos);

    @Override
    public void updateTick(World world, BlockPos pos, Random random) {
        if (!this.canBlockStay(world, x, y, z)) {
            this.dropBlockAsItem(world, x, y, z, world.getBlockState(x, y, z), 0);
            world.setBlockState(x, y, z, Blocks.AIR, 0, BlockFlags.SYNC);
        } else if (random.nextFloat() <= getSpreadRate(world, x, y, z)) {
            final BlockCheck.BlockDirection EnumFacing = BlockCheck.randomDirection8(random);
            growFungus(world, x + EnumFacing.offsetX, y, z + EnumFacing.offsetZ);
        }
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Nether;
    }

    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        return this;
    }

    public int getPlantMetadata(IBlockAccess world, BlockPos pos) {
        return 0;
    }
}
