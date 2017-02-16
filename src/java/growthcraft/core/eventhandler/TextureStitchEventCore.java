package growthcraft.core.eventhandler;

import growthcraft.core.GrowthCraftCore;
import net.minecraft.block.BlockLiquid;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TextureStitchEventCore {
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onTextureStitchPre(TextureStitchEvent.Pre event) {
        if (event.map.getTextureType() == 0) {
            GrowthCraftCore.liquidSmoothTexture = event.map.registerIcon("grccore:liquidsmooth");
            GrowthCraftCore.liquidBlobsTexture = event.map.registerIcon("grccore:liquidblob");

            GrowthCraftCore.fluids.saltWater.getFluid().setIcons(BlockLiquid.getLiquidIcon("water_still"), BlockLiquid.getLiquidIcon("water_flow"));
        }
    }
}
