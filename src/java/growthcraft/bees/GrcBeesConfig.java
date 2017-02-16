package growthcraft.bees;

import growthcraft.core.ConfigBase;

public class GrcBeesConfig extends ConfigBase {
    @ConfigOption(catergory = "World Gen", name = "Biomes (IDs) That Generate Beehives", desc = "Separate the IDs with ';' (without the quote marks)")
    public String beeBiomesList = "1;4;18;27;28;129;132;155;156";

    @ConfigOption(catergory = "World Gen", name = "Enable Biome Dictionary compatability?", desc = "Default : true  || false = Disable")
    public boolean useBiomeDict = true;

    @ConfigOption(catergory = "World Gen", name = "Bee Hive WorldGen density", desc = "[Higher -> Denser]")
    public int beeWorldGenDensity = 2;


    @ConfigOption(catergory = "Bee Box", name = "Honeycomb spawn rate", desc = "[Higher -> Slower]")
    public float beeBoxHoneyCombSpawnRate = 18.75f;

    @ConfigOption(catergory = "Bee Box", name = "Honey spawn rate", desc = "[Higher -> Slower]")
    public float beeBoxHoneySpawnRate = 6.25f;

    @ConfigOption(catergory = "Bee Box", name = "Bee spawn rate", desc = "[Higher -> Slower]")
    public float beeBoxBeeSpawnRate = 6.25f;

    @ConfigOption(catergory = "Bee Box", name = "Flower spawn rate", desc = "[Higher -> Slower]")
    public float beeBoxFlowerSpawnRate = 6.25f;

    @ConfigOption(catergory = "Bee Box", name = "Bonus Multiplier", desc = "When the bee box has a 'bonus', what is the multiplier?")
    public float beeBoxBonusMultiplier = 2.5f;

    @ConfigOption(catergory = "Bee Box", name = "Flower spawn radius", desc = "[Higher -> Larger]")
    public int beeBoxFlowerRadius = 5;


    @ConfigOption(catergory = "Fluid/Honey", name = "Enabled", desc = "Should we enable honey fluid and its corresponding items?")
    public boolean honeyEnabled = true;


    @ConfigOption(catergory = "Booze", name = "Honey Mead Color", desc = "What color should honey mead be?")
    public int honeyMeadColor = 0xA3610C;


    @ConfigOption(catergory = "Village", name = "Enabled", desc = "Should we register Village Generation, and Villager Trades?")
    public boolean enableVillageGen = true;

    @ConfigOption(catergory = "Village", name = "Apiarist ID")
    public int villagerApiaristID = 7766;

    @ConfigOption(catergory = "Village", name = "Spawn Village Apiarist Structure", desc = "Should the apiarist structure be generated in villages?")
    public boolean generateApiaristStructure;


    @ConfigOption(catergory = "Integration", name = "Enable Growthcraft Bamboo Integration", desc = "Should we integrate with Growthcraft Bamboo (if available)?")
    public boolean enableGrcBambooIntegration = true;

    @ConfigOption(catergory = "Integration", name = "Enable Growthcraft Nether Integration", desc = "Should we integrate with Growthcraft Nether (if available)?")
    public boolean enableGrcNetherIntegration = true;

    @ConfigOption(catergory = "Integration", name = "Enable Waila Integration", desc = "Should we integrate with Waila (if available)?")
    public boolean enableWailaIntegration = true;

    @ConfigOption(catergory = "Integration", name = "Enable BiomesOPlenty Integration", desc = "Should we integrate with BiomesOPlenty (if available)?")
    public boolean enableBoPIntegration = true;

    @ConfigOption(catergory = "Integration", name = "Enable Natura Integration", desc = "Should we integrate with Natura (if available)?")
    public boolean enableNaturaIntegration = true;

    @ConfigOption(catergory = "Integration", name = "Enable Botania Integration", desc = "Should we integrate with Botania (if available)?")
    public boolean enableBotaniaIntegration = true;

    @ConfigOption(catergory = "Integration", name = "Enable Forestry Integration", desc = "Should we integrate with Forestry (if available)?")
    public boolean enableForestryIntegration = true;

    @ConfigOption(catergory = "Integration", name = "Enable Thaumcraft Integration", desc = "Should we integrate with Thaumcraft (if available)?")
    public boolean enableThaumcraftIntegration = true;

    @ConfigOption(catergory = "Integration", name = "Enable Fossils and Archaeology Integration", desc = "Should we integrate with Fossils and Archaeology (if available)?")
    public boolean enableFAIntegration = true;

    @ConfigOption(catergory = "Integration", name = "Enable Ars Magica 2 Integration", desc = "Should we integrate with Ars Magica 2 (if available)?")
    public boolean enableAM2Integration = true;

    @ConfigOption(catergory = "Integration", name = "Enable Totemic Integration", desc = "Should we integrate with Totemic (if available)?")
    public boolean enableTotemicIntegration = true;

    @ConfigOption(catergory = "Integration", name = "Enable ExtraBiomesXL Integration", desc = "Should we integrate with ExtraBiomesXL (if available)?")
    public boolean enableEBXLIntegration = true;

    @ConfigOption(catergory = "Integration", name = "Enable Highlands Integration", desc = "Should we integrate with Highlands (if available)?")
    public boolean enableHighlandsIntegration = true;
}
