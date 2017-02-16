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
package growthcraft.milk.integration;

import growthcraft.core.integration.WailaIntegrationBase;
import growthcraft.milk.GrowthCraftMilk;
import growthcraft.milk.common.block.*;
import growthcraft.milk.integration.waila.GrcMilkDataProvider;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraftforge.fml.common.Optional;

public class Waila extends WailaIntegrationBase {
    public Waila() {
        super(GrowthCraftMilk.MOD_ID);
    }

    @Optional.Method(modid = "Waila")
    public static void register(IWailaRegistrar reg) {
        final IWailaDataProvider provider = new GrcMilkDataProvider();

        reg.registerBodyProvider(provider, BlockCheesePress.class);
        reg.registerNBTProvider(provider, BlockCheesePress.class);

        reg.registerBodyProvider(provider, BlockButterChurn.class);
        reg.registerNBTProvider(provider, BlockButterChurn.class);

        reg.registerBodyProvider(provider, BlockCheeseVat.class);
        reg.registerNBTProvider(provider, BlockCheeseVat.class);

        reg.registerStackProvider(provider, BlockCheeseBlock.class);
        reg.registerBodyProvider(provider, BlockCheeseBlock.class);
        reg.registerNBTProvider(provider, BlockCheeseBlock.class);

        reg.registerStackProvider(provider, BlockHangingCurds.class);
        reg.registerBodyProvider(provider, BlockHangingCurds.class);
        reg.registerNBTProvider(provider, BlockHangingCurds.class);
    }
}
