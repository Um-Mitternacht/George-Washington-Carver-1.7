package growthcraft.bees;

import growthcraft.api.bees.BeesFluidTag;
import growthcraft.api.bees.BeesRegistry;
import growthcraft.api.bees.user.UserBeesConfig;
import growthcraft.api.bees.user.UserFlowerEntry;
import growthcraft.api.bees.user.UserFlowersConfig;
import growthcraft.api.core.fluids.TaggedFluidStacks;
import growthcraft.api.core.item.recipes.ShapelessMultiRecipe;
import growthcraft.api.core.log.GrcLogger;
import growthcraft.api.core.log.ILogger;
import growthcraft.api.core.module.ModuleContainer;
import growthcraft.bees.client.eventhandler.GrcBeesHandleTextureStitch;
import growthcraft.bees.common.CommonProxy;
import growthcraft.bees.common.tileentity.TileEntityBeeBox;
import growthcraft.bees.common.village.ComponentVillageApiarist;
import growthcraft.bees.common.village.VillageHandlerBees;
import growthcraft.bees.common.village.VillageHandlerBeesApiarist;
import growthcraft.bees.common.world.WorldGeneratorBees;
import growthcraft.bees.creativetab.CreativeTabsGrowthcraftBees;
import growthcraft.bees.init.GrcBeesBlocks;
import growthcraft.bees.init.GrcBeesFluids;
import growthcraft.bees.init.GrcBeesItems;
import growthcraft.bees.init.GrcBeesRecipes;
import growthcraft.cellar.GrowthCraftCellar;
import growthcraft.core.GrcGuiProvider;
import growthcraft.core.common.definition.BlockDefinition;
import growthcraft.core.integration.bop.BopPlatform;
import growthcraft.core.util.MapGenHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

@Mod(
	modid = GrowthCraftBees.MOD_ID,
	name = GrowthCraftBees.MOD_NAME,
	version = GrowthCraftBees.MOD_VERSION,
	dependencies = "required-after:Growthcraft@@VERSION@;required-after:Growthcraft|Cellar@@VERSION@;after:Forestry"
)
public class GrowthCraftBees
{
	public static final String MOD_ID = "Growthcraft|Bees";
	public static final String MOD_NAME = "Growthcraft Bees";
	public static final String MOD_VERSION = "@VERSION@";

	@Instance(MOD_ID)
	public static GrowthCraftBees instance;
	public static CreativeTabs tab;
	public static final GrcBeesBlocks blocks = new GrcBeesBlocks();
	public static final GrcBeesItems items = new GrcBeesItems();
	public static final GrcBeesFluids fluids = new GrcBeesFluids();
	public static final GrcGuiProvider guiProvider = new GrcGuiProvider(new GrcLogger(MOD_ID + ":GuiProvider"));

	private final ILogger logger = new GrcLogger(MOD_ID);
	private final GrcBeesConfig config = new GrcBeesConfig();
	private final ModuleContainer modules = new ModuleContainer();
	private final UserBeesConfig userBeesConfig = new UserBeesConfig();
	private final UserFlowersConfig userFlowersConfig = new UserFlowersConfig();
	private final GrcBeesRecipes recipes = new GrcBeesRecipes();
	//private UserHoneyConfig userHoneyConfig = new UserHoneyConfig();

	public static UserBeesConfig getUserBeesConfig()
	{
		return instance.userBeesConfig;
	}

	/**
	 * Only use this logger for logging GrowthCraftBees related items
	 */
	public static ILogger getLogger()
	{
		return instance.logger;
	}

	public static GrcBeesConfig getConfig()
	{
		return instance.config;
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		config.setLogger(logger);
		config.load(event.getModConfigurationDirectory(), "growthcraft/bees.conf");
		modules.add(blocks);
		modules.add(items);
		modules.add(fluids);
		modules.add(recipes);

		userBeesConfig.setConfigFile(event.getModConfigurationDirectory(), "growthcraft/bees/bees.json");
		modules.add(userBeesConfig);

		userFlowersConfig.setConfigFile(event.getModConfigurationDirectory(), "growthcraft/bees/flowers.json");
		modules.add(userFlowersConfig);

		//userHoneyConfig.setConfigFile(event.getModConfigurationDirectory(), "growthcraft/bees/honey.json");
		//modules.add(userHoneyConfig);

		if (config.enableGrcBambooIntegration) modules.add(new growthcraft.bees.integration.GrcBambooModule());
		if (config.enableGrcNetherIntegration) modules.add(new growthcraft.bees.integration.GrcNetherModule());
		if (config.enableWailaIntegration) modules.add(new growthcraft.bees.integration.Waila());
		if (config.enableBoPIntegration) modules.add(new growthcraft.bees.integration.BoPModule());
		if (config.enableNaturaIntegration) modules.add(new growthcraft.bees.integration.NaturaModule());
		if (config.enableBotaniaIntegration) modules.add(new growthcraft.bees.integration.BotaniaModule());
		if (config.enableForestryIntegration) modules.add(new growthcraft.bees.integration.ForestryModule());
		//if (config.enableThaumcraftIntegration) modules.add(new growthcraft.bees.integration.ThaumcraftModule());
		if (config.enableFAIntegration) modules.add(new growthcraft.bees.integration.FAModule());
		//if (config.enableAM2Integration) modules.add(new growthcraft.bees.integration.AM2Module());
		if (config.enableTotemicIntegration) modules.add(new growthcraft.bees.integration.TotemicModule());
		if (config.enableEBXLIntegration) modules.add(new growthcraft.bees.integration.EBXLModule());
		if (config.enableHighlandsIntegration) modules.add(new growthcraft.bees.integration.HighlandsModule());

		if (config.debugEnabled)
		{
			BeesRegistry.instance().setLogger(logger);
			modules.setLogger(logger);
		}

		modules.add(CommonProxy.instance);
		if (config.debugEnabled)
		{
			BeesRegistry.instance().setLogger(logger);
			modules.setLogger(logger);
		}
		modules.freeze();
		tab = new CreativeTabsGrowthcraftBees("creative_tab_grcbees");

		MinecraftForge.EVENT_BUS.register(new GrcBeesHandleTextureStitch());

		modules.preInit();
		register();
	}

	private void register()
	{
		// TileEntities
		GameRegistry.registerTileEntity(TileEntityBeeBox.class, "grc.tileentity.beeBox");
		GameRegistry.registerWorldGenerator(new WorldGeneratorBees(), 0);
		MapGenHelper.registerVillageStructure(ComponentVillageApiarist.class, "grc.apiarist");
		modules.register();
		registerRecipes();
		userBeesConfig.addDefault(items.bee.asStack()).setComment("Growthcraft's default bee");
		BeesRegistry.instance().addHoneyComb(items.honeyCombEmpty.asStack(), items.honeyCombFilled.asStack());
		userFlowersConfig.addDefault(Blocks.RED_FLOWER);
		userFlowersConfig.addDefault(Blocks.YELLOW_FLOWER);
		if (BopPlatform.isLoaded())
		{
			userFlowersConfig.addDefault(
				new UserFlowerEntry("BiomesOPlenty", "flowers", OreDictionary.WILDCARD_VALUE)
					.setEntryType("forced"))
				.setComment("BiomesOPlenty flowers require a forced entry, in order for it to be placed by the bee box spawning.");
			userFlowersConfig.addDefault(
				new UserFlowerEntry("BiomesOPlenty", "flowers2", OreDictionary.WILDCARD_VALUE)
					.setEntryType("forced"))
				.setComment("BiomesOPlenty flowers require a forced entry, in order for it to be placed by the bee box spawning.");
		}
	}

	private void registerRecipes()
	{
		final BlockDefinition planks = new BlockDefinition(Blocks.PLANKS);
		for (int i = 0; i < 6; ++i)
		{
			GameRegistry.addRecipe(blocks.beeBox.asStack(1, i), new Object[] { " A ", "A A", "AAA", 'A', planks.asStack(1, i) });
		}

		final ItemStack honeyStack = items.honeyCombFilled.asStack();
		GameRegistry.addShapelessRecipe(items.honeyJar.asStack(),
			honeyStack, honeyStack, honeyStack, honeyStack, honeyStack, honeyStack, Items.FLOWER_POT);
	}

	private void postRegisterRecipes()
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(blocks.beeBox.asStack(), " A ", "A A", "AAA", 'A', "plankWood"));

		GameRegistry.addRecipe(new ShapelessMultiRecipe(
				items.honeyJar.asStack(),
				new TaggedFluidStacks(1000, BeesFluidTag.HONEY.getName()),
				Items.FLOWER_POT));
	}

	private void initVillageHandlers()
	{
		final VillageHandlerBeesApiarist handler = new VillageHandlerBeesApiarist();
		final int brewerID = GrowthCraftCellar.getConfig().villagerBrewerID;
		final int apiaristID = config.villagerApiaristID;
		if (apiaristID > 0)
		{
			VillagerRegistry.instance().registerVillagerId(apiaristID);
			VillagerRegistry.instance().registerVillageTradeHandler(apiaristID, handler);
		}
		VillagerRegistry.instance().registerVillageCreationHandler(handler);
		if (brewerID > 0)
		{
			VillagerRegistry.instance().registerVillageCreationHandler(brewerID, new VillageHandlerBees());
		}
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(this, guiProvider);
		if (config.enableVillageGen) initVillageHandlers();
		modules.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		userBeesConfig.loadUserConfig();
		userFlowersConfig.loadUserConfig();
		postRegisterRecipes();

		modules.postInit();
	}
}
