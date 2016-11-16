package growthcraft.cellar.common.item;

import growthcraft.core.common.item.GrcItemBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;



public class ItemChievDummy extends GrcItemBase
{
	private IIcon[] icon;

	public ItemChievDummy()
	{
		super();
		this.setCreativeTab(null);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setUnlocalizedName("grc.chievItemDummy");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg)
	{
		this.icon = new IIcon[1];
		this.icon[0] = reg.registerIcon("grccellar:chievicon_0");
	}

	@Override
	@SideOnly(Side.CLIENT)
	//public IIcon getIconFromDamage(int meta)
	{
		return this.icon[meta];
	}
}
