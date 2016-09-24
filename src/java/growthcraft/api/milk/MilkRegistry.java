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

import growthcraft.api.core.log.ILoggable;
import growthcraft.api.core.log.ILogger;
import growthcraft.api.core.log.NullLogger;
import growthcraft.api.milk.cheesepress.CheesePressRegistry;
import growthcraft.api.milk.cheesepress.ICheesePressRegistry;
import growthcraft.api.milk.cheesevat.CheeseVatRegistry;
import growthcraft.api.milk.cheesevat.ICheeseVatRegistry;
import growthcraft.api.milk.churn.ChurnRegistry;
import growthcraft.api.milk.churn.IChurnRegistry;
import growthcraft.api.milk.pancheon.IPancheonRegistry;
import growthcraft.api.milk.pancheon.PancheonRegistry;

import javax.annotation.Nonnull;

public class MilkRegistry implements ILoggable
{
	private static final MilkRegistry INSTANCE = new MilkRegistry();

	private final ICheesePressRegistry cheesePressRegistry = new CheesePressRegistry();
	private final ICheeseVatRegistry cheeseVatRegistry = new CheeseVatRegistry();
	private final IChurnRegistry churnRegistry = new ChurnRegistry();
	private final IPancheonRegistry pancheonRegistry = new PancheonRegistry();
	private ILogger logger = NullLogger.INSTANCE;

	@Override
	public void setLogger(@Nonnull ILogger l)
	{
		this.logger = l;
		cheesePressRegistry.setLogger(logger);
		cheeseVatRegistry.setLogger(logger);
		churnRegistry.setLogger(logger);
		pancheonRegistry.setLogger(logger);
	}

	/**
	 * If you'd like to log something related to the Milk API, use this logger
	 * Otherwise, MAKE YOUR OWN.
	 */
	public ILogger getLogger()
	{
		return logger;
	}

	/**
	 * @return current instrance of the MilkRegistry
	 */
	public static final MilkRegistry instance()
	{
		return INSTANCE;
	}

	/**
	 * @return instance of the CheesePressRegistry
	 */
	public ICheesePressRegistry cheesePress()
	{
		return cheesePressRegistry;
	}

	/**
	 * @return instance of the CheeseVatRegistry
	 */
	public ICheeseVatRegistry cheeseVat()
	{
		return cheeseVatRegistry;
	}

	/**
	 * @return instance of the ChurnRegistry
	 */
	public IChurnRegistry churn()
	{
		return churnRegistry;
	}

	/**
	 * @return instance of the PancheonRegistry
	 */
	public IPancheonRegistry pancheon()
	{
		return pancheonRegistry;
	}
}
