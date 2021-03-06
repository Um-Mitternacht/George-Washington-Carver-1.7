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
package growthcraft.api.cellar;

import growthcraft.api.cellar.booze.IBoozeRegistry;
import growthcraft.api.cellar.brewing.IBrewingRegistry;
import growthcraft.api.cellar.culturing.ICulturingRegistry;
import growthcraft.api.cellar.distilling.IDistilleryRegistry;
import growthcraft.api.cellar.fermenting.IFermentingRegistry;
import growthcraft.api.cellar.heatsource.IHeatSourceRegistry;
import growthcraft.api.cellar.pressing.IPressingRegistry;
import growthcraft.api.cellar.yeast.IYeastRegistry;
import growthcraft.api.core.log.ILoggable;

public interface ICellarRegistry extends ILoggable {
    IBoozeRegistry booze();

    IBrewingRegistry brewing();

    ICulturingRegistry culturing();

    IPressingRegistry pressing();

    IFermentingRegistry fermenting();

    IHeatSourceRegistry heatSource();

    IDistilleryRegistry distilling();

    IYeastRegistry yeast();
}
