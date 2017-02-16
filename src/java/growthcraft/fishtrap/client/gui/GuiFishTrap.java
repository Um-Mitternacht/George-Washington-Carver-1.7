package growthcraft.fishtrap.client.gui;

import growthcraft.core.client.gui.GrcGuiContainer;
import growthcraft.fishtrap.common.inventory.ContainerFishTrap;
import growthcraft.fishtrap.common.tileentity.TileEntityFishTrap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiFishTrap extends GrcGuiContainer<ContainerFishTrap, TileEntityFishTrap> {
    private static final ResourceLocation res = new ResourceLocation("grcfishtrap", "textures/guis/fishtrap_gui.png");
    private TileEntityFishTrap te;

    public GuiFishTrap(InventoryPlayer inv, TileEntityFishTrap fishTrap) {
        super(res, new ContainerFishTrap(inv, fishTrap), fishTrap);
        this.ySize = 133;
    }
}
