/*
 * The MIT License (MIT)
 *
 * Copyright (c) < 2014, Gwafu
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
package growthcraft.fishtrap.common.block;

import growthcraft.api.fishtrap.FishTrapRegistry;
import growthcraft.core.Utils;
import growthcraft.core.common.block.GrcBlockContainer;
import growthcraft.core.common.tileentity.feature.IInteractionObject;
import growthcraft.core.util.BlockCheck;
import growthcraft.fishtrap.GrowthCraftFishTrap;
import growthcraft.fishtrap.common.tileentity.TileEntityFishTrap;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public abstract class BlockFishTrap extends GrcBlockContainer {
    @SideOnly(Side.CLIENT)


    private final float chance = GrowthCraftFishTrap.getConfig().fishTrapCatchRate;
    private Random rand = new Random();

    public BlockFishTrap() {
        super(Material.WOOD);
        setTickRandomly(true);
        setHardness(0.4F);
        //setStepSound(soundTypeWood);
        setUnlocalizedName("grc.fishTrap");
        setTileEntityType(TileEntityFishTrap.class);
        setCreativeTab(GrowthCraftFishTrap.creativeTab);
    }

    protected boolean openGui(EntityPlayer player, World world, BlockPos pos) {
        final TileEntity te = getTileEntity(world, pos);
        if (te instanceof IInteractionObject) {
            player.openGui(GrowthCraftFishTrap.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, EntityPlayer player, int meta, float par7, float par8, float par9) {
        if (super.onBlockActivated(world, pos, player, meta, par7, par8, par9)) return true;
        return !player.isSneaking() && openGui(player, world, pos);
    }

    private boolean isWater(Block block) {
        return BlockCheck.isWater(block);
    }

    private float applyBiomeCatchModifier(World world, BlockPos pos, float f) {
        boolean isInWaterBiome;
        if (GrowthCraftFishTrap.getConfig().useBiomeDict) {
            final Biome biome = world.getBiome(pos);
            isInWaterBiome = BiomeDictionary.isBiomeOfType(biome, Type.WATER);
        } else {
            isInWaterBiome = Utils.isIDInList(world.getBiome(pos).biomeID(), GrowthCraftFishTrap.getConfig().biomesList);
        }

        if (isInWaterBiome) {
            f *= 1.75;
        }
        return f;
    }

    private float getCatchRate(World world, BlockPos pos) {
        final TileEntityFishTrap te = getTileEntity(world, pos);
        if (te == null) return 0.0f;
        final int checkSize = 3;
        final int i = pos.getX() - ((checkSize - 1) / 2);
        final int j = pos.getY() - ((checkSize - 1) / 2);
        final int k = pos.getZ() - ((checkSize - 1) / 2);
        float f = 1.0F;

        for (int loopy = 0; loopy <= checkSize; loopy++) {
            for (int loopx = 0; loopx <= checkSize; loopx++) {
                for (int loopz = 0; loopz <= checkSize; loopz++) {
                    final Block water = world.getBlockState(i + loopx, j + loopy, k + loopz);
                    float f1 = 0.0F;
                    //1.038461538461538;

                    if (water != null && isWater(water)) {
                        //f1 = 1.04F;
                        f1 = 3.0F;
                        //f1 = 17.48F;
                    }

                    f1 /= 4.0F;

                    f += f1;
                }
            }
        }
        f = applyBiomeCatchModifier(world, pos, f);
        return te.applyBaitModifier(f);
    }

    protected ItemStack pickCatch(World world, BlockPos pos) {
        float f = this.getCatchRate(world, pos);
        boolean flag;
        if (GrowthCraftFishTrap.getConfig().useBiomeDict) {
            final Biome biome = world.getBiome(pos);
            flag = BiomeDictionary.isBiomeOfType(biome, Type.WATER);
        } else {
            flag = Utils.isIDInList(world.getBiome(pos).biomeID(), GrowthCraftFishTrap.getConfig().biomesList);
        }

        if (flag) {
            f *= 1 + (75 / 100);
        }
        final String catchGroup = FishTrapRegistry.instance().getRandomCatchGroup(world.rand);
        GrowthCraftFishTrap.getLogger().debug("Picking Catch from group=%s x=%d y=%d z=%d dimension=%d", catchGroup, x, y, z, world.provider.dimensionId);
        return FishTrapRegistry.instance().getRandomCatchFromGroup(world.rand, catchGroup);
    }

    protected void doCatch(World world, BlockPos pos, TileEntityFishTrap te) {
        final ItemStack item = pickCatch(world, pos);
        if (item != null) {
            GrowthCraftFishTrap.getLogger().debug("Attempting to add item to inventory x=%d y=%d z=%d dimension=%d item=%s", x, y, z, world.provider.dimensionId, item);
            if (te.addStack(item)) {
                GrowthCraftFishTrap.getLogger().debug("Added item to inventory x=%d y=%d z=%d dimension=%d item=%s", x, y, z, world.provider.dimensionId, item);
                te.consumeBait();
            }
        }
    }

    protected void attemptCatch(World world, BlockPos pos, Random random, TileEntityFishTrap te, boolean debugFlag) {
        final float f = this.getCatchRate(world, pos);
        GrowthCraftFishTrap.getLogger().debug("Attempting Catch x=%d y=%d z=%d dimension=%d rate=%f", x, y, z, world.provider.dimensionId, f);
        if (random.nextInt((int) (this.chance / f) + 1) == 0 || debugFlag) {
            doCatch(world, pos, te);
        }
    }

    private boolean canCatch(World world, BlockPos pos) {
        return isWater(world.getBlockState(pos, pos, pos - 1)) ||
                isWater(world.getBlockState(x, y, z + 1)) ||
                isWater(world.getBlockState(x - 1, y, z)) ||
                isWater(world.getBlockState(x + 1, y, z));
    }

    @Override
    public void updateTick(World world, BlockPos pos, Random random, IBlockState state) {
        super.updateTick(world, pos, state, random);
        final TileEntityFishTrap te = getTileEntity(world, pos);
        if (te != null) {
            GrowthCraftFishTrap.getLogger().debug("Checking if fishtrap can catch x=%d y=%d z=%d dimension=%d", x, y, z, world.provider.dimensionId);
            if (canCatch(world, pos)) {
                attemptCatch(world, pos, random, te, false);
            }
        } else {
            GrowthCraftFishTrap.getLogger().warn("Missing TileEntityFishTrap at x=%d y=%d z=%d dimension=%d", x, y, z, world.provider.dimensionId);
        }
    }

    //@Override
    //@SideOnly(Side.CLIENT)

    //{
    //	this.icons = new IIcon[1];
//
    //	icons[0] = reg.registerIcon("grcfishtrap:fishtrap");
    //}

    //@Override
    //@SideOnly(Side.CLIENT)
    //
    //{
    //	return icons[0];
    //}

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
    public boolean shouldSideBeRendered(IBlockAccess world, BlockPos pos, int side, EnumFacing face, IBlockState state) {
        if (this == world.getBlockState(pos)) return false;
        return super.shouldSideBeRendered(state, world, pos, face);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return 0;
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, BlockPos pos, int par5) {
        final TileEntityFishTrap te = getTileEntity(world, pos);
        if (te != null) {
            return Container.calcRedstoneFromInventory(te);
        }
        return 0;
    }
}
