/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015, 2016 IceDragon200
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package growthcraft.milk;

import growthcraft.api.core.log.GrcLogger;
import growthcraft.api.core.log.ILogger;
import growthcraft.api.core.module.ModuleContainer;
import growthcraft.api.milk.MilkRegistry;
import growthcraft.milk.client.handler.GrcMilkHandleTextureStitch;
import growthcraft.milk.common.CommonProxy;
import growthcraft.milk.common.tileentity.*;
import growthcraft.milk.creativetab.GrcMilkCreativeTabs;
import growthcraft.milk.eventhandler.EventHandlerOnBabyCowDeath;
import growthcraft.milk.init.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(
        modid = GrowthCraftMilk.MOD_ID,
        name = GrowthCraftMilk.MOD_NAME,
        version = GrowthCraftMilk.MOD_VERSION,
        dependencies = GrowthCraftMilk.MOD_DEPENDENCIES
)
public class GrowthCraftMilk {
    public static final String MOD_ID = "Growthcraft|Milk";
    public static final String MOD_NAME = "Growthcraft Milk";
    public static final String MOD_VERSION = "@VERSION@";
    public static final String MOD_DEPENDENCIES = "required-after:Growthcraft;required-after:Growthcraft|Cellar";
    public static final GrcMilkBlocks blocks = new GrcMilkBlocks();
    public static final GrcMilkFluids fluids = new GrcMilkFluids();
    public static final GrcMilkItems items = new GrcMilkItems();
    public static final GrcMilkRecipes recipes = new GrcMilkRecipes();
    public static final GrcMilkUserApis userApis = new GrcMilkUserApis();
    // Events
    public static final EventBus MILK_BUS = new EventBus();
    @Instance(MOD_ID)
    public static GrowthCraftMilk instance;
    public static CreativeTabs creativeTab;
    private final ILogger logger = new GrcLogger(MOD_ID);
    private final GrcMilkConfig config = new GrcMilkConfig();
    private final ModuleContainer modules = new ModuleContainer();

    public static GrcMilkConfig getConfig() {
        return instance.config;
    }

    public static ILogger getLogger() {
        return instance.logger;
    }

    @EventHandler
    public void preload(FMLPreInitializationEvent event) {
        config.setLogger(logger);
        config.load(event.getModConfigurationDirectory(), "growthcraft/milk.conf");
        if (config.debugEnabled) {
            modules.setLogger(logger);
            MilkRegistry.instance().setLogger(logger);
        }
        modules.add(blocks);
        modules.add(items);
        modules.add(fluids);
        modules.add(recipes);
        //if (config.enableMFRIntegration) modules.add(new growthcraft.milk.integration.MFRModule());
        //if (config.enableThaumcraftIntegration) modules.add(new growthcraft.milk.integration.ThaumcraftModule());
        if (config.enableWailaIntegration) modules.add(new growthcraft.milk.integration.Waila());
        modules.add(userApis);
        modules.add(CommonProxy.instance);
        modules.freeze();
        GrcMilkEffects.init();

        userApis.setConfigDirectory(event.getModConfigurationDirectory());

        GrowthCraftMilk.creativeTab = new GrcMilkCreativeTabs("creative_tab_grcmilk");

        modules.preInit();
        MinecraftForge.EVENT_BUS.register(new GrcMilkHandleTextureStitch());
        MinecraftForge.EVENT_BUS.register(new EventHandlerOnBabyCowDeath());
        register();
    }

    private void register() {
        modules.register();

        if (items.seedThistle != null && config.thistleSeedWeight > 0) {
            MinecraftForge.addGrassSeed(items.seedThistle.asStack(), config.thistleSeedWeight);
        }

        GameRegistry.registerTileEntity(TileEntityButterChurn.class, "grcmilk.tileentity.ButterChurn");
        GameRegistry.registerTileEntity(TileEntityCheeseBlock.class, "grcmilk.tileentity.CheeseBlock");
        GameRegistry.registerTileEntity(TileEntityCheesePress.class, "grcmilk.tileentity.CheesePress");
        GameRegistry.registerTileEntity(TileEntityCheeseVat.class, "grcmilk.tileentity.CheeseVat");
        GameRegistry.registerTileEntity(TileEntityHangingCurds.class, "grcmilk.tileentity.HangingCurds");
        GameRegistry.registerTileEntity(TileEntityPancheon.class, "grcmilk.tileentity.Pancheon");
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
        modules.init();
        userApis.loadConfigs();
    }

    @EventHandler
    public void postload(FMLPostInitializationEvent event) {
        modules.postInit();
    }
}
