package growthcraft.cellar.client.gui;

import java.util.List;

import growthcraft.api.cellar.CellarRegistry;
import growthcraft.api.core.i18n.GrcI18n;
import growthcraft.cellar.common.tileentity.TileEntityCellarDevice;
import growthcraft.cellar.common.inventory.CellarContainer;
import growthcraft.core.client.gui.GrcGuiContainer;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiCellar<C extends CellarContainer, T extends TileEntityCellarDevice> extends GrcGuiContainer<C, T>
{
	public GuiCellar(ResourceLocation res, C container, T cd)
	{
		super(res, container, cd);
	}

	protected void addFermentTooltips(FluidStack fluid, List<String> tooltip)
	{
		if (fluid == null) return;
		if (fluid.amount <= 0) return;

		addFluidTooltips(fluid, tooltip);
		if (!CellarRegistry.instance().fermenting().canFerment(fluid))
		{
			tooltip.add("");
			tooltip.add(TextFormatting.RED + GrcI18n.translate("gui.grc.cantferment"));
		}
	}
}
