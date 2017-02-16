package growthcraft.apples.common.block;

import growthcraft.api.core.util.BlockFlags;
import growthcraft.apples.GrowthCraftApples;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Random;

public class BlockAppleLeaves extends BlockLeaves implements IShearable, IGrowable {
    @SideOnly(Side.CLIENT)


    private final int growth = GrowthCraftApples.getConfig().appleLeavesGrowthRate;
    private int[] adjacentTreeBlocks;

    public BlockAppleLeaves() {
        //super(Material.LEAVES, false);
        this.setTickRandomly(true);
        this.setHardness(0.2F);
        this.setLightOpacity(1);
        //setStepSound(soundTypeGrass);
        this.setUnlocalizedName("grc.appleLeaves");
        this.setCreativeTab(GrowthCraftApples.creativeTab);
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

    /* Apply bonemeal effect */
    @Override
    public void func_149853_b(World world, Random random, BlockPos pos, Block block) {
        growApple(world, random, pos, block);
    }

    private void growApple(World world, Random rand, BlockPos pos, Block block) {
        if (world.isAirBlock(pos.down())) {
            world.setBlockState(pos.down(), block.getDefaultState());
        }
    }

    private void removeLeaves(World world, BlockPos pos) {
        this.dropBlockAsItem(world, pos, world.getBlockState(pos), 0);
        world.setBlockToAir(pos);
    }

    /************
     * TICK
     ************/
    @Override
    public void updateTick(World world, BlockPos pos, Random random) {
        if (!world.isRemote) {
            final int meta = world.getBlockState(pos);

            if ((meta & 4) == 0) {
                // Spawn Apple
                if (world.rand.nextInt(this.growth) == 0) {
                    growApple(world, random, pos);
                }
            }

            if ((meta & LeavesStage.DECAY_MASK) != 0 && (meta & 4) == 0) {
                final byte b0 = 4;
                final int i1 = b0 + 1;
                final byte b1 = 32;
                final int j1 = b1 * b1;
                final int k1 = b1 / 2;

                if (this.adjacentTreeBlocks == null) {
                    this.adjacentTreeBlocks = new int[b1 * b1 * b1];
                }

                int l1;

                if (world.checkChunksExist(x - i1, y - i1, z - i1, x + i1, y + i1, z + i1)) {
                    int i2;
                    int j2;

                    for (l1 = -b0; l1 <= b0; ++l1) {
                        for (i2 = -b0; i2 <= b0; ++i2) {
                            for (j2 = -b0; j2 <= b0; ++j2) {
                                final Block block = world.getBlockState(x + l1, y + i2, z + j2);

                                if (!block.canSustainLeaves(world, x + l1, y + i2, z + j2)) {
                                    if (block.isLeaves(world, x + l1, y + i2, z + j2)) {
                                        this.adjacentTreeBlocks[(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = -2;
                                    } else {
                                        this.adjacentTreeBlocks[(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = -1;
                                    }
                                } else {
                                    this.adjacentTreeBlocks[(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = 0;
                                }
                            }
                        }
                    }

                    for (l1 = 1; l1 <= 4; ++l1) {
                        for (i2 = -b0; i2 <= b0; ++i2) {
                            for (j2 = -b0; j2 <= b0; ++j2) {
                                for (int k2 = -b0; k2 <= b0; ++k2) {
                                    if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1] == l1 - 1) {
                                        if (this.adjacentTreeBlocks[(i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1] == -2) {
                                            this.adjacentTreeBlocks[(i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1] = l1;
                                        }

                                        if (this.adjacentTreeBlocks[(i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1] == -2) {
                                            this.adjacentTreeBlocks[(i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1] = l1;
                                        }

                                        if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1] == -2) {
                                            this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1] = l1;
                                        }

                                        if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1] == -2) {
                                            this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1] = l1;
                                        }

                                        if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + (k2 + k1 - 1)] == -2) {
                                            this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + (k2 + k1 - 1)] = l1;
                                        }

                                        if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1] == -2) {
                                            this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1] = l1;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                l1 = this.adjacentTreeBlocks[k1 * j1 + k1 * b1 + k1];

                if (l1 >= 0) {
                    world.setBlockState(x, y, z, meta & -9, BlockFlags.SUPRESS_RENDER);
                } else {
                    this.removeLeaves(world, x, y, z);
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, BlockPos pos, Random random, IBlockState state) {
        super.randomDisplayTick(state, world, pos, random);
        if (world.canLightningStrikeAt(x, y + 1, z) && !World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) && random.nextInt(15) == 1) {
            final double d0 = (double) ((float) x + random.nextFloat());
            final double d1 = (double) y - 0.05D;
            final double d2 = (double) ((float) z + random.nextFloat());
            world.spawnParticle("dripWater", d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }

    /************
     * TRIGGERS
     ************/
    @Override
    public void breakBlock(World world, BlockPos pos, Block block, int par6) {
        final byte b0 = 1;
        final int i1 = b0 + 1;

        if (world.checkChunksExist(x - i1, y - i1, z - i1, x + i1, y + i1, z + i1)) {
            for (int j1 = -b0; j1 <= b0; ++j1) {
                for (int k1 = -b0; k1 <= b0; ++k1) {
                    for (int l1 = -b0; l1 <= b0; ++l1) {
                        final Block block2 = world.getBlockState(x + j1, y + k1, z + l1);
                        if (block2.isLeaves(world, x + j1, y + k1, z + l1)) {
                            block2.beginLeavesDecay(world, x + j1, y + k1, z + l1);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, int par6, ItemStack stack, IBlockState state, TileEntity entity) {
        super.harvestBlock(world, player, pos, state, entity, stack);
    }

    /************
     * STUFF
     ************/
    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, BlockPos pos) {
        return Item.getItemFromBlock(Blocks.LEAVES);
    }

    @Override
    public void beginLeavesDecay(World world, BlockPos pos) {
        world.setBlockState(pos, world.getBlockState(pos) | LeavesStage.DECAY_MASK, BlockFlags.SUPRESS_RENDER);
    }

    @Override
    public boolean isLeaves(IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean canSilkHarvest(World world, EntityPlayer player, BlockPos pos, int metadata) {
        return false;
    }

    /************
     * DROPS
     ************/
    @Override
    public Item getItemDropped(int meta, Random random, int par3) {
        return GrowthCraftApples.blocks.appleSapling.getItem();
    }

    @Override
    public int quantityDropped(Random random) {
        return random.nextInt(20) == 0 ? 1 : 0;
    }

    @Override
    public BlockPlanks.EnumType getWoodType(int meta) {
        return null;
    }

    @Override
    public void dropBlockAsItemWithChance(World world, BlockPos pos, int meta, float par6, int fortune, IBlockState state) {
        if (!world.isRemote) {
            int random = 20;

            if (fortune > 0) {
                random -= 2 << fortune;

                if (random < 10) {
                    random = 10;
                }
            }

            if (world.rand.nextInt(random) == 0) {
                this.dropBlockAsItem(world, pos, state, 1);
            }
        }
    }

    /************
     * RENDERS
     ************/
    @Override
    public boolean isOpaqueCube() {
        return Blocks.LEAVES.isOpaqueCube();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, BlockPos pos, int side) {
        return true;
    }

    /************
     * COLORS
     ************/
    @Override
    @SideOnly(Side.CLIENT)
    public int getBlockColor() {
        final double d0 = 0.5D;
        final double d1 = 1.0D;
        return ColorizerFoliage.getFoliageColor(d0, d1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta) {
        return ColorizerFoliage.getFoliageColorBasic();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, BlockPos pos) {
        final int meta = world.getBlockState(x, y, z);

        int r = 0;
        int g = 0;
        int b = 0;

        for (int l1 = -1; l1 <= 1; ++l1) {
            for (int i2 = -1; i2 <= 1; ++i2) {
                final int j2 = world.getBiome(x + i2, z + l1).getBiomeFoliageColor(x + i2, y, z + l1);
                r += (j2 & 16711680) >> 16;
                g += (j2 & 65280) >> 8;
                b += j2 & 255;
            }
        }

        return (r / 9 & 255) << 16 | (g / 9 & 255) << 8 | b / 9 & 255;
    }

    /************
     * SHEARS
     ************/
    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        final ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        ret.add(new ItemStack(Blocks.LEAVES, 1, world.getBlockState(pos) & 3));
        return ret;
    }

    public static class LeavesStage {
        public static final int DECAY_MASK = 8;

        private LeavesStage() {
        }
    }
}
