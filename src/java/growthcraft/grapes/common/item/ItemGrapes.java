package growthcraft.grapes.common.item;

import growthcraft.core.common.item.GrcItemFoodBase;
import growthcraft.grapes.GrowthCraftGrapes;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemGrapes extends GrcItemFoodBase
{
	@SideOnly(Side.CLIENT)


	public ItemGrapes()
	{
		super(2, 0.3F, false);
		setHasSubtypes(true);
		setMaxDamage(0);
		setUnlocalizedName("grc.grapes");
		setCreativeTab(GrowthCraftGrapes.creativeTab);
	}

	public EnumGrapes getEnumGrapes(ItemStack stack)
	{
		return EnumGrapes.get(stack.getItemDamage());
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		final EnumGrapes en = getEnumGrapes(stack);
		return super.getUnlocalizedName(stack) + "." + en.name;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg)
	{
		this.icons = new IIcon[EnumGrapes.VALUES.length];
		for (EnumGrapes grape : EnumGrapes.VALUES)
		{
			this.icons[grape.meta] = reg.registerIcon(String.format("grcgrapes:grapes/%s", grape.name));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta)
	{
		return icons[MathHelper.clamp_int(meta, 0, icons.length - 1)];
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void getSubItems(Item item, CreativeTabs ct, List list)
	{
		for (EnumGrapes en : EnumGrapes.VALUES)
		{
			list.add(en.asStack());
		}
	}
}
