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
package growthcraft.api.milk;

import growthcraft.api.core.CoreRegistry;
import growthcraft.api.core.fluids.FluidTag;

public class MilkFluidTags
{
	public static final FluidTag CHEESE = CoreRegistry.instance().fluidTags().createTag("cheese");
	public static final FluidTag CREAM = CoreRegistry.instance().fluidTags().createTag("cream");
	public static final FluidTag MILK = CoreRegistry.instance().fluidTags().createTag("milk");
	public static final FluidTag MILK_CURDS = CoreRegistry.instance().fluidTags().createTag("milk_curds");
	public static final FluidTag RENNET = CoreRegistry.instance().fluidTags().createTag("rennet");
	public static final FluidTag WHEY = CoreRegistry.instance().fluidTags().createTag("whey");

	private MilkFluidTags() {}
}
