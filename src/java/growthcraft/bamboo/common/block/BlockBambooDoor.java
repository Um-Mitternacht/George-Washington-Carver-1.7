package growthcraft.bamboo.common.block;

import growthcraft.bamboo.GrowthCraftBamboo;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockBambooDoor extends BlockDoor
{
	private static final String[] doorIconNames = new String[] {"grcbamboo:door_lower", "grcbamboo:door_upper"};

	@SideOnly(Side.CLIENT)
	private IIcon[] tex;

	public BlockBambooDoor()
	{
		super(Material.WOOD);
		setStepSound(soundTypeWood);
		setHardness(3.0F);
		disableStats();
		setCreativeTab(null);
		setBlockName("grc.bambooDoor");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World par1World, int par2, int par3, int par4)
	{
		return GrowthCraftBamboo.items.bambooDoorItem.getItem();
	}

	@Override
	public Item getItemDropped(int meta, Random par2Random, int par3)
	{
		return (meta & 8) != 0 ? null : GrowthCraftBamboo.items.bambooDoorItem.getItem();
	}

	/************
	 * TEXTURES
	 ************/
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		this.tex = new IIcon[doorIconNames.length * 2];

		for (int i = 0; i < doorIconNames.length; ++i)
		{
			this.tex[i] = reg.registerIcon(doorIconNames[i]);
			this.tex[i + doorIconNames.length] = new IconFlipped(this.tex[i], true, false);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2)
	{
		return this.tex[0];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, BlockPos pos, IBlockState state, int side)
	{
		if (side != 1 && side != 0)
		{
			final int meta = this.getMetaFromState(state);
			final int j1 = meta & 3;
			final boolean flag = (meta & 4) != 0;
			final boolean flag2 = (meta & 8) != 0;
			boolean flag1 = false;

			if (flag)
			{
				if (j1 == 0 && side == 2)
				{
					flag1 = !flag1;
				}
				else if (j1 == 1 && side == 5)
				{
					flag1 = !flag1;
				}
				else if (j1 == 2 && side == 3)
				{
					flag1 = !flag1;
				}
				else if (j1 == 3 && side == 4)
				{
					flag1 = !flag1;
				}
			}
			else
			{
				if (j1 == 0 && side == 5)
				{
					flag1 = !flag1;
				}
				else if (j1 == 1 && side == 3)
				{
					flag1 = !flag1;
				}
				else if (j1 == 2 && side == 4)
				{
					flag1 = !flag1;
				}
				else if (j1 == 3 && side == 2)
				{
					flag1 = !flag1;
				}

				if ((meta & 16) != 0)
				{
					flag1 = !flag1;
				}
			}

			return this.tex[0 + (flag1 ? doorIconNames.length : 0) + (flag2 ? 1 : 0)];
		}
		else
		{
			return this.tex[0];
		}
	}
}
