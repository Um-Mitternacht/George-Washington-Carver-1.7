/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 IceDragon200
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

import growthcraft.api.core.util.TagParser;
import growthcraft.core.ConfigBase;

public class GrcMilkConfig extends ConfigBase {
    @ConfigOption(catergory = "Milk", name = "Enabled", desc = "Should we enable a fluid for Milk? (you may bork the mod if you don't have an alternative, eg. Forestry's Milk)")
    public boolean milkEnabled = true;

    @ConfigOption(catergory = "Milk", name = "Fantasy Milk Effects", desc = "Should Skim Milk, Butter Milk have extra effects?")
    public boolean fantasyMilkEffects;

    @ConfigOption(catergory = "Milk", name = "Color", desc = "What color is milk?")
    public int milkColor = 0xFFFFFF;


    @ConfigOption(catergory = "Booze/Kumis", name = "Color", desc = "What color is kumis?")
    public int kumisColor = 0xFFFFFF;

    @ConfigOption(catergory = "Booze/Poisoned Kumis", name = "Color", desc = "What color is poisoned kumis?")
    public int poisonedKumisColor = 0x7F9A65;


    @ConfigOption(catergory = "Item/Stomach", name = "Drop Rate", desc = "How often do baby calves drop their stomachs?")
    public float stomachDropRate = 0.25f;

    @ConfigOption(catergory = "Item/Stomach", name = "Min Dropped", desc = "What is the minimum number of stomachs dropped?")
    public int stomachMinDropped = 2;

    @ConfigOption(catergory = "Item/Stomach", name = "Max Dropped", desc = "What is the maximum number of stomachs dropped?")
    public int stomachMaxDropped = 4;


    @ConfigOption(catergory = "Device/Cheese Vat", name = "Primary Tank Capacity", desc = "How much fluid does the primary tank hold? (the tank with Milk)")
    public int cheeseVatPrimaryTankCapacity = 5000;

    @ConfigOption(catergory = "Device/Cheese Vat", name = "Rennet Tank Capacity", desc = "How much fluid does the rennet tank hold? (the tank with Rennet obviously)")
    public int cheeseVatRennetTankCapacity = 333;

    @ConfigOption(catergory = "Device/Cheese Vat", name = "Waste Tank Capacity", desc = "How much fluid does the waste tank hold? (the tank with whey)")
    public int cheeseVatWasteTankCapacity = 1000;

    @ConfigOption(catergory = "Device/Cheese Vat", name = "Recipe Tank Capacity", desc = "How much fluid does the recipe tank hold? (the tank with any recipe related fluid)")
    public int cheeseVatRecipeTankCapacity = 1000;

    @ConfigOption(catergory = "Device/Cheese Vat", name = "Whey Transition Time", desc = "How long does it take to change whey to ricotta?")
    public int cheeseVatWheyTime = 2400;

    @ConfigOption(catergory = "Device/Cheese Vat", name = "Curd Transition Time", desc = "How long does it take to change milk to curds?")
    public int cheeseVatCurdTime = 2400;

    @ConfigOption(catergory = "Device/Cheese Vat", name = "Cheese Transition Time", desc = "How long does it take to change curds to cheese?")
    public int cheeseVatCheeseTime = 2400;

    @ConfigOption(catergory = "Device/Cheese Vat", name = "Milk To Curds Whey Amount", desc = "How much Whey is produced when transitioning from Milk to Curds?")
    public int cheeseVatMilkToCurdsWheyAmount = 1000;

    @ConfigOption(catergory = "Device/Cheese Vat", name = "Whey To Ricotta Whey Amount", desc = "How much Whey is produced when transitioning from Whey to Ricotta?")
    public int cheeseVatWheyToRicottaWheyAmount = 1000;


    @ConfigOption(catergory = "Device/Cheese Press", name = "Redstone Operated", desc = "Can the press operate via redstone?")
    public boolean cheesePressRedstoneOperated = true;

    @ConfigOption(catergory = "Device/Cheese Press", name = "Hand Operated", desc = "Can the press operate via user interaction?")
    public boolean cheesePressHandOperated = true;


    @ConfigOption(catergory = "Cheese", name = "Aging Time", desc = "How long does it take for cheese to age?")
    public int cheeseMaxAge = 1200;

    @ConfigOption(catergory = "Cheese", name = "Slices", desc = "How many slices are present in a cheese block?")
    public int cheeseMaxSlices = 8;

    @ConfigOption(catergory = "Cheese", name = "Item per Block Slice", desc = "How many items are present in a cheese block slice?")
    public int cheeseItemPerBlockSlice = 8;

    @ConfigOption(catergory = "Cheese", name = "Ricotta Bowl Count", desc = "How many bowls are used in the ricotta recipe?")
    public int ricottaBowlCount = 4;


    @ConfigOption(catergory = "Thistle", name = "Enable Thistle?", desc = "Is thistle (the item, block, recipes etc) available? (If this is false, you may ignore everything in this section)")
    public boolean thistleEnabled = true;

    @ConfigOption(catergory = "Thistle", name = "Spread Chance", desc = "How quickly does thistle spread? [Higher -> Slower] (Setting to 0 will disable)")
    public int thistleSpreadChance = 20;

    @ConfigOption(catergory = "Thistle", name = "Growth Chance", desc = "Chance that thistle will advance a stage upon ticking? [Higher -> Less Likely] (Setting to 0 will disable, AppleCore will handle growth if available)")
    public int thistleGrowthChance = 16;

    @ConfigOption(catergory = "Thistle", name = "Enable Thistle Seeds?", desc = "Should thistle seeds be available?")
    public boolean thistleSeedEnabled = true;

    @ConfigOption(catergory = "Thistle", name = "Grass Seed Weight", desc = "How likely is it to find a Thistle Seed by breaking grass? [Higher -> More Likely] (Setting to 0 will disable seeds in grass)")
    public int thistleSeedWeight = 8;


    @ConfigOption(catergory = "Thistle/World Gen", name = "Enable Thistle World Gen?", desc = "Can thistle spawn in the world? (This will be disabled if Thistle was disabled in the Thistle section) (WARNING: This thing spreads like wild fire, enable at your own risk)")
    public boolean thistleWorldGenEnabled;

    @ConfigOption(catergory = "Thistle/World Gen", name = "Enable Biome Dictionary compatability? (Set to false to use Biome IDs instead)")
    public boolean thistleUseBiomeDict = true;

    // Extreme Hills, Extreme Hills Edge, Extreme Hills+
    @ConfigOption(catergory = "Thistle/World Gen", name = "Biome IDs", desc = "Separate the IDs with ';' (without the quote marks)", opt = "scsv", def = "3;20;34")
    public TagParser.Tag[] thistleBiomesIdList;

    @ConfigOption(catergory = "Thistle/World Gen", name = "Biome Types", desc = "Separate the TYPEs with ',' (without the quote marks)\nSeparate batches with ';' (without the quote marks)\n", def = "+MOUNTAIN;+HILLS,-SNOWY,-COLD")
    public TagParser.Tag[][] thistleBiomesTypeList;

    @ConfigOption(catergory = "Thistle/World Gen", name = "Generate Amount", desc = "What is the maximum number of thistle spawned in a chunk?")
    public int thistleGenAmount = 10;

    @ConfigOption(catergory = "Thistle/World Gen", name = "WorldGen Chance", desc = "1/N chance of spawning thistle into a chunk, where N is the value set here. (Set to 0 to spawn maximum number)")
    public int thistleGenChance = 10;


    @ConfigOption(catergory = "Integration", name = "Enable Waila Integration", desc = "Should we integrate with Waila (if available)?")
    public boolean enableWailaIntegration = true;

    @ConfigOption(catergory = "Integration", name = "Enable MFR Integration", desc = "Should we integrate with Mine Factory Reloaded (if available)?")
    public boolean enableMFRIntegration = true;

    @ConfigOption(catergory = "Integration", name = "Enable Thaumcraft Integration", desc = "Should we integrate with Thaumcraft (if available)?")
    public boolean enableThaumcraftIntegration = true;

    /**
     * @return true, thistle is enabled, and allows world gen
     */
    public boolean canThistleGenerate() {
        return thistleEnabled && thistleWorldGenEnabled;
    }
}
