package growthcraft.rice;

import growthcraft.api.core.log.GrcLogger;
import growthcraft.api.core.log.ILogger;
import growthcraft.api.core.module.ModuleContainer;
import growthcraft.cellar.GrowthCraftCellar;
import growthcraft.core.GrowthCraftCore;
import growthcraft.core.eventhandler.PlayerInteractEventPaddy;
import growthcraft.core.integration.NEI;
import growthcraft.core.util.MapGenHelper;
import growthcraft.rice.common.CommonProxy;
import growthcraft.rice.common.village.ComponentVillageRiceField;
import growthcraft.rice.common.village.VillageHandlerRice;
import growthcraft.rice.event.BonemealEventRice;
import growthcraft.rice.init.GrcRiceBlocks;
import growthcraft.rice.init.GrcRiceFluids;
import growthcraft.rice.init.GrcRiceItems;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

@Mod(
	modid = GrowthCraftRice.MOD_ID,
	name = GrowthCraftRice.MOD_NAME,
	version = GrowthCraftRice.MOD_VERSION,
	dependencies = "required-after:Growthcraft@@VERSION@;required-after:Growthcraft|Cellar@@VERSION@"
)
public class GrowthCraftRice
{
	public static final String MOD_ID = "Growthcraft|Rice";
	public static final String MOD_NAME = "Growthcraft Rice";
	public static final String MOD_VERSION = "@VERSION@";

	@Instance(MOD_ID)
	public static GrowthCraftRice instance;
	public static final GrcRiceBlocks blocks = new GrcRiceBlocks();
	public static final GrcRiceItems items = new GrcRiceItems();
	public static final GrcRiceFluids fluids = new GrcRiceFluids();

	private final ILogger logger = new GrcLogger(MOD_ID);
	private final GrcRiceConfig config = new GrcRiceConfig();
	private final ModuleContainer modules = new ModuleContainer();

	public static GrcRiceConfig getConfig()
	{
		return instance.config;
	}

	@EventHandler
	public void preload(FMLPreInitializationEvent event)
	{
		config.setLogger(logger);
		config.load(event.getModConfigurationDirectory(), "growthcraft/rice.conf");
		modules.add(blocks);
		modules.add(items);
		modules.add(fluids);
		if (config.enableForestryIntegration) modules.add(new growthcraft.rice.integration.ForestryModule());
		//if (config.enableMFRIntegration) modules.add(new growthcraft.rice.integration.MFRModule());
		//if (config.enableThaumcraftIntegration) modules.add(new growthcraft.rice.integration.ThaumcraftModule());
		modules.add(CommonProxy.instance);
		if (config.debugEnabled) modules.setLogger(logger);
		modules.freeze();
		modules.preInit();
		register();
	}

	private void register()
	{
		modules.register();
		MinecraftForge.addGrassSeed(items.rice.asStack(), config.riceSeedDropRarity);

		MapGenHelper.registerVillageStructure(ComponentVillageRiceField.class, "grc.ricefield");

		//====================
		// ORE DICTIONARY
		//====================
		OreDictionary.registerOre("cropRice", items.rice.getItem());
		OreDictionary.registerOre("seedRice", items.rice.getItem());
		// For Pam's HarvestCraft
		// Uses the same OreDict. names as HarvestCraft
		OreDictionary.registerOre("listAllseed", items.rice.getItem());

		//====================
		// CRAFTING
		//====================
		GameRegistry.addRecipe(new ShapedOreRecipe(items.riceBall.asStack(1), "###", "###", '#', "cropRice"));

		NEI.hideItem(blocks.riceBlock.asStack());

		MinecraftForge.EVENT_BUS.register(this);
	}

	private void initVillageHandlers()
	{
		final VillageHandlerRice handler = new VillageHandlerRice();
		final int brewerID = GrowthCraftCellar.getConfig().villagerBrewerID;
		if (brewerID > 0)
			VillagerRegistry.instance().registerVillageTradeHandler(brewerID, handler);
		VillagerRegistry.instance().registerVillageCreationHandler(handler);
	}

	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		PlayerInteractEventPaddy.paddyBlocks.put(Blocks.FARMLAND, blocks.paddyField.getBlockState());
		if (config.enableVillageGen) initVillageHandlers();
		modules.init();
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onTextureStitchPost(TextureStitchEvent.Post event)
	{
		if (event.getMap().getTextureType() == 0)
		{
			for (int i = 0; i < fluids.riceSakeBooze.length; ++i)
			{
				fluids.riceSakeBooze[i].setIcons(GrowthCraftCore.liquidSmoothTexture);
			}
		}
	}

	@EventHandler
	public void postload(FMLPostInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new BonemealEventRice());

		modules.postInit();
	}
}
