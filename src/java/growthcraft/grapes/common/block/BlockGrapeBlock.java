package growthcraft.grapes.common.block;

import growthcraft.cellar.common.item.EnumYeast;
import growthcraft.core.common.block.GrcBlockBase;
import growthcraft.grapes.GrowthCraftGrapes;
import growthcraft.grapes.client.renderer.RenderGrape;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Random;

public class BlockGrapeBlock extends GrcBlockBase {
    protected int bayanusDropRarity = GrowthCraftGrapes.getConfig().bayanusDropRarity;
    protected int grapesDropMin = GrowthCraftGrapes.getConfig().grapesDropMin;
    protected int grapesDropMax = GrowthCraftGrapes.getConfig().grapesDropMax;

    public BlockGrapeBlock() {
        super(Material.PLANTS);
        //setBlockTextureName("grcgrapes:grape");
        setHardness(0.0F);
        //setStepSound(soundTypeGrass);
        setUnlocalizedName("grc.grapeBlock");
        getBoundingBox(0.1875F, 0.5F, 0.1875F, 0.8125F, 1.0F, 0.8125F);
        setCreativeTab(null);
    }

    /************
     * TICK
     ************/
    @Override
    public void updateTick(World world, BlockPos pos, Random random) {
        if (!this.canBlockStay(world, pos)) {
            fellBlockAsItem(world, pos);
        }
    }

    /************
     * TRIGGERS
     ************/
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, EntityPlayer player, int EnumFacing, float par7, float par8, float par9) {
        if (!world.isRemote) {
            fellBlockAsItem(world, pos);
        }
        return true;
    }

    @Override
    public void onNeighborChange(World world, BlockPos pos, Block par5) {
        if (!this.canBlockStay(world, pos)) {
            fellBlockAsItem(world, pos);
        }
    }

    /************
     * CONDITIONS
     ************/
    @Override
    public boolean canBlockStay(World world, BlockPos pos) {
        return GrowthCraftGrapes.blocks.grapeLeaves.getBlockState() == world.getBlockState(x, y + 1, z);
    }

    /************
     * STUFF
     ************/
    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, BlockPos pos) {
        return GrowthCraftGrapes.items.grapes.getItem();
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
        return GrowthCraftGrapes.items.grapes.getItem();
    }

    @Override
    public int quantityDropped(Random random) {
        return grapesDropMin + random.nextInt(grapesDropMax - grapesDropMin);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, BlockPos pos, int metadata, int fortune, IBlockState state) {
        final ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        final int count = quantityDropped(state, fortune, world.rand);
        for (int i = 0; i < count; ++i) {
            final Item item = getItemDropped(metadata, world.rand, fortune);
            if (item != null) {
                ret.add(new ItemStack(item, 1, damageDropped(metadata)));
            }
            if (world.rand.nextInt(bayanusDropRarity) == 0) {
                ret.add(EnumYeast.BAYANUS.asStack(1));
            }
        }
        return ret;
    }

    /************
     * RENDER
     ************/
    @Override
    public int getRenderType() {
        return RenderGrape.id;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    /************
     * BOXES
     ************/
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, BlockPos pos) {
        return null;
    }
}
