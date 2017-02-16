package growthcraft.cellar.common.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionCellar extends Potion {
    private static final ResourceLocation res = new ResourceLocation("grccellar", "textures/guis/potion_tipsy.png");

    public PotionCellar(int par1, boolean par2, int par3) {
        super(par1, par2, par3);
    }

    @Override
    public Potion setIconIndex(int par1, int par2) {
        super.setIconIndex(par1, par2);
        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasStatusIcon() {
        Minecraft.getMinecraft().renderEngine.bindTexture(res);
        return true;
    }
}
