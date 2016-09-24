/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 IceDragon200
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
package growthcraft.api.cellar.yeast.user;

import growthcraft.api.core.schema.ICommentable;
import growthcraft.api.core.schema.ItemKeySchema;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class UserYeastEntry implements ICommentable
{
	public String comment = "";
	public ItemKeySchema item;
	public int weight;
	public List<String> biome_types;
	public List<String> biome_names;

	public UserYeastEntry(@Nonnull ItemKeySchema i, int w, @Nonnull List<String> biomeTypes, @Nonnull List<String> biomeNames)
	{
		this.item = i;
		this.weight = w;
		this.biome_types = biomeTypes;
		this.biome_names = biomeNames;
	}

	public UserYeastEntry(@Nonnull ItemKeySchema i, int w, @Nonnull List<String> biomeTypes)
	{
		this(i, w, biomeTypes, new ArrayList<String>());
	}

	public UserYeastEntry() {}

	@Override
	public String toString()
	{
		return String.format("UserYeastEntry(item: `%s`, weight: %d, biome_types: %s, biome_names: %s)", item, weight, biome_types, biome_names);
	}

	@Override
	public void setComment(String comm)
	{
		this.comment = comm;
	}

	@Override
	public String getComment()
	{
		return comment;
	}
}
