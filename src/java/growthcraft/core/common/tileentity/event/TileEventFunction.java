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
package growthcraft.core.common.tileentity.event;

import growthcraft.api.core.stream.IStreamable;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TileEventFunction
{
	private Method method;

	public TileEventFunction(@Nonnull Method m)
	{
		this.method = m;
	}

	private Object invoke2(Object a, Object b)
	{
		try
		{
			return this.method.invoke(a, b);
		}
		catch (IllegalAccessException e)
		{
			throw new IllegalStateException(e);
		}
		catch (IllegalArgumentException e)
		{
			throw new IllegalStateException(e);
		}
		catch (InvocationTargetException e)
		{
			throw new IllegalStateException(e);
		}
	}

	public void readFromNBT(Object tile, NBTTagCompound nbt)
	{
		invoke2(tile, nbt);
	}

	public void writeToNBT(Object tile, NBTTagCompound nbt)
	{
		invoke2(tile, nbt);
	}

	public boolean writeToStream(IStreamable tile, ByteBuf data)
	{
		return (Boolean)invoke2(tile, data);
	}

	public boolean readFromStream(IStreamable tile, ByteBuf data)
	{
		return (Boolean)invoke2(tile, data);
	}
}
