package growthcraft.grapes.common.item;

import growthcraft.core.common.item.GrcItemBase;
import growthcraft.core.util.BlockCheck;
import growthcraft.grapes.common.block.BlockGrapeVine0;
import growthcraft.grapes.GrowthCraftGrapes;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemGrapeSeeds extends GrcItemBase implements IPlantable
{
	public ItemGrapeSeeds()
	{
		super();
		this.setUnlocalizedName("grc.grapeSeeds");
		this.setCreativeTab(GrowthCraftGrapes.creativeTab);
	}

	/************
	 * MAIN
	 ************/
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int dir, float par8, float par9, float par10)
	{
		if (dir != 1)
		{
			return false;
		}
		else if (player.canPlayerEdit(x, y, z, dir, stack) && player.canPlayerEdit(x, y + 1, z, dir, stack))
		{
			final BlockGrapeVine0 block = GrowthCraftGrapes.blocks.grapeVine0.getBlock();
			if (BlockCheck.canSustainPlant(world, x, y, z, ForgeDirection.UP, block) && world.isAirBlock(x, y + 1, z))
			{
				world.setBlock(x, y + 1, z, block);
				--stack.stackSize;
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	/************
	 * TEXTURES
	 ************/
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg)
	{
		this.itemIcon = reg.registerIcon("grcgrapes:grape_seeds");
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z)
	{
		return EnumPlantType.Crop;
	}

	@Override
	public Block getPlant(IBlockAccess world, int x, int y, int z)
	{
		return GrowthCraftGrapes.blocks.grapeVine0.getBlock();
	}

	@Override
	public int getPlantMetadata(IBlockAccess world, int x, int y, int z)
	{
		return 0;
	}
}
