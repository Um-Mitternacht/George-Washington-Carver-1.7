package growthcraft.bamboo;

import growthcraft.api.core.util.TagParser;
import growthcraft.core.ConfigBase;

public class GrcBambooConfig extends ConfigBase {
    @ConfigOption(catergory = "World Gen", name = "Generate Bamboo Forest biome?")
    public boolean generateBambooBiome = true;

    @ConfigOption(catergory = "World Gen", name = "Enable Biome Dictionary compatability?")
    public boolean useBiomeDict = true;

    @ConfigOption(catergory = "World Gen", name = "Bamboo Forest biome ID", desc = "What is the Biome ID for Bamboo Forest?")
    public int bambooBiomeID = 170;

    // Ocean, Plains, Forest, River, ForestHills, Jungle, JungleHills, JungleEdge,JungleM, JungleEdgeM
    @ConfigOption(catergory = "World Gen", name = "Biomes (IDs) That Generate Bamboos", desc = "Separate the IDs with ';' (without the quote marks)", opt = "scsv", def = "0;1;4;7;18;21;22;23;149;151")
    public TagParser.Tag[] bambooBiomesIdList;

    @ConfigOption(catergory = "World Gen", name = "Biome Types That Generate Bamboos", desc = "Separate the TYPES with ',' (without the quote marks)\nIf you would like to create a new batch, seperate batches with ';' (without the quote marks)\n", def = "FOREST,JUNGLE,WATER,PLAINS,-SNOWY")
    public TagParser.Tag[][] bambooBiomesTypeList;

    @ConfigOption(catergory = "World Gen", name = "Bamboo WorldGen density", desc = "How clustered should bamboo generate? [Higher -> Denser]")
    public int bambooWorldGenDensity = 64;

    @ConfigOption(catergory = "World Gen", name = "Bamboo WorldGen rarity", desc = "How rare is it for bamboo to spawn? [Higher -> Rarer]")
    public int bambooWorldGenRarity = 32;


    @ConfigOption(catergory = "Village", name = "Generate Village Bamboo Yard", desc = "Should we spawn Bamboo Yards in villages?")
    public boolean generateBambooYard;


    @ConfigOption(catergory = "Bamboo/Growth", name = "Bamboo Shoot growth rate", desc = "[Higher -> Slower]")
    public int bambooShootGrowthRate = 7;

    @ConfigOption(catergory = "Bamboo/Growth", name = "Bamboo Spread rate", desc = "[Higher -> Slower]")
    public int bambooStalkGrowthRate = 4;

    @ConfigOption(catergory = "Bamboo/Growth", name = "Bamboo Maximum Height", desc = "[Higher -> Taller]")
    public int bambooTreeMaxHeight = 256;

    @ConfigOption(catergory = "Bamboo/Growth", name = "Bamboo Minimum Height", desc = "[Higher -> Taller]")
    public int bambooTreeMinHeight = 12;


    @ConfigOption(catergory = "Integration", name = "Enable Forestry Integration", desc = "Should we integrate with Forestry (if available)?")
    public boolean enableForestryIntegration = true;

    @ConfigOption(catergory = "Integration", name = "Enable MFR Integration", desc = "Should we integrate with Mine Factory Reloaded (if available)?")
    public boolean enableMFRIntegration = true;

    @ConfigOption(catergory = "Integration", name = "Enable Thaumcraft Integration", desc = "Should we integrate with Thaumcraft (if available)?")
    public boolean enableThaumcraftIntegration = true;
}
