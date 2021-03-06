/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 IceDragon200
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
package growthcraft.milk.common.block;

import growthcraft.core.common.block.GrcBlockContainer;
import growthcraft.milk.GrowthCraftMilk;
import growthcraft.milk.client.render.RenderCheeseVat;
import growthcraft.milk.common.tileentity.TileEntityCheeseVat;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockCheeseVat extends GrcBlockContainer {
    public BlockCheeseVat() {
        super(Material.IRON);
        setResistance(10.0F);
        setHardness(5.0F);
        //setStepSound(soundTypeMetal);
        setUnlocalizedName("grcmilk.CheeseVat");
        setCreativeTab(GrowthCraftMilk.creativeTab);
        setTileEntityType(TileEntityCheeseVat.class);
        //setBlockTextureName("grcmilk:cheese_vat");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, BlockPos pos, Random random) {
        if (random.nextInt(12) == 0) {
            final TileEntityCheeseVat te = getTileEntity(world, pos);
            if (te != null) {
                if (te.isWorking()) {
                    for (int i = 0; i < 3; ++i) {
                        final double px = x + 0.5d + (random.nextFloat() - 0.5d);
                        final double py = y + (1d / 16d);
                        final double pz = z + 0.5d + (random.nextFloat() - 0.5d);
                        world.spawnParticle("smoke", px, py, pz, 0.0D, 1d / 32d, 0.0D);
                        world.playSoundEffect((double) x, (double) y, (double) z, "liquid.lavapop", 0.3f, 0.5f);
                    }
                }
            }
        }
    }

    @Override
    public void setBlockBoundsForItemRender(IBlockState state, IBlockAccess source, BlockPos pos) {
        this.getBoundingBox(state, source, pos);
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void getCollisionBoundingBox(World world, BlockPos pos, AxisAlignedBB axis, List list, Entity entity, IBlockState state, IBlockAccess source) {
        final float unit = 1f / 16f;
        this.getBoundingBox(state, source, pos);
        super.getCollisionBoundingBox(state, world, pos);

        this.getBoundingBox(state, source, pos);
        super.getCollisionBoundingBox(state, world, pos);

        this.getBoundingBox(state, source, pos);
        super.getCollisionBoundingBox(state, world, pos);

        this.getBoundingBox(state, source, pos);
        super.getCollisionBoundingBox(state, world, pos);

        this.getBoundingBox(state, source, pos);
        super.getCollisionBoundingBox(state, world, pos);

        this.setBlockBoundsForItemRender(state, source, pos);
    }

    @Override
    public int getRenderType() {
        return RenderCheeseVat.RENDER_ID;
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

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, BlockPos pos, int par5) {
        final TileEntityCheeseVat te = getTileEntity(world, pos);
        if (te != null) {
            return te.calcRedstone();
        }
        return 0;
    }
}
