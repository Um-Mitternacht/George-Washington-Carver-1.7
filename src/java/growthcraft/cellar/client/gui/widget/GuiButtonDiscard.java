package growthcraft.cellar.client.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiButtonDiscard extends GuiButton
{
	private final ResourceLocation res;
	private final int iconX;
	private final int iconY;

	public GuiButtonDiscard(ResourceLocation resl, int id, int xpos, int ypos)
	{
		super(id, xpos, ypos, 16, 16, "");
		this.res = resl;
		this.iconX = 0;
		this.iconY = 166;
	}

	@Override
	public void drawButton(Minecraft mc, int w, int h)
	{
		if (this.visible)
		{
			mc.getTextureManager().bindTexture(this.res);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.hovered = w >= this.xPosition && h >= this.yPosition && w < this.xPosition + this.width && h < this.yPosition + this.height;
			final short y = 182;
			int x = 0;

			if (!this.enabled)
			{
				x += this.width * 1;
			}
			else if (this.hovered)
			{
				x += this.width * 2;
			}

			this.drawTexturedModalRect(this.xPosition, this.yPosition, x, y, this.width, this.height);
			this.drawTexturedModalRect(this.xPosition, this.yPosition, this.iconX, this.iconY, 16, 16);
		}
	}
}
