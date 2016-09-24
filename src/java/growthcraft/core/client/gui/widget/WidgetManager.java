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
package growthcraft.core.client.gui.widget;

import growthcraft.core.client.gui.GrcGuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

public class WidgetManager<C extends Container, T extends TileEntity>
{
	public final GrcGuiContainer<C, T> gui;
	protected List<Widget> widgets = new ArrayList<Widget>();

	public WidgetManager(GrcGuiContainer<C, T> g)
	{
		this.gui = g;
	}

	@SuppressWarnings({"rawtypes"})
	public WidgetManager add(Widget widget)
	{
		widgets.add(widget);
		return this;
	}

	public void draw(int mx, int my)
	{
		for (Widget widget : widgets)
		{
			widget.draw(mx, my);
		}
	}

	public void drawForeground(int mx, int my)
	{
		for (Widget widget : widgets)
		{
			widget.drawForeground(mx, my);
		}
	}
}
