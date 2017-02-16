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
package growthcraft.api.core.util;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class BiomeUtils {
    private BiomeUtils() {
    }

    /**
     * The default implementation of the BiomeDictionary.Type doesn't have
     * a non-mutative version of the getType method.
     * Growthcraft will NOT attempt to add any new biomes, if you f*ck up,
     * this method will slap you in the face for it.
     *
     * @param name - name of the biome to look for, this will be upcased
     * @return type - found biome type
     */
    public static BiomeDictionary.Type fetchBiomeType(String name) throws BiomeTypeNotFound {
        final String upcasedName = name.toUpperCase();

        // I really shouldn't be doing this, but what choice do you have :(
        for (BiomeDictionary.Type type : BiomeDictionary.Type.values()) {
            if (type.name().equals(upcasedName)) return type;
        }
        throw new BiomeTypeNotFound("Biome type '" + name + "' not found.");
    }

    public static boolean testBiomeTypeTags(Biome biome, TagParser.Tag[] tags) {
        if (tags.length == 0) {
            return false;
        }
        boolean hasMatching = false;
        for (TagParser.Tag tag : tags) {
            try {
                final boolean res = BiomeDictionary.isBiomeOfType(biome, fetchBiomeType(tag.value));
                if (tag.exclude && res) return false;
                if (tag.must && !res) return false;
                if (res) hasMatching = true;
            } catch (BiomeTypeNotFound ex) {
                ex.printStackTrace();
                return false;
            }
        }
        return hasMatching;
    }

    public static boolean testBiomeTypeTagsTable(Biome biome, TagParser.Tag[][] tagTable) {
        for (TagParser.Tag[] row : tagTable) {
            if (testBiomeTypeTags(biome, row)) return true;
        }
        return false;
    }

    public static boolean testBiomeIdTags(String biomeId, TagParser.Tag[] tags) {
        for (TagParser.Tag tag : tags) {
            if (tag.value.equals(biomeId)) {
                return true;
            }
        }
        return false;
    }

    public static class BiomeTypeNotFound extends Exception {
        public static final long serialVersionUID = 1L;

        public BiomeTypeNotFound(String msg) {
            super(msg);
        }

        public BiomeTypeNotFound() {
        }
    }
}
