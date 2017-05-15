package com.pau101.island.layer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerScale extends GenLayer {
	public GenLayerScale(long seed, GenLayer parent) {
		super(seed);
		this.parent = parent;
	}

	@Override
	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
		int pX = areaX >> 1;
		int pY = areaY >> 1;
		int pWidth = (areaWidth >> 1) + 2;
		int pHeight = (areaHeight >> 1) + 2;
		int[] pInts = parent.getInts(pX, pY, pWidth, pHeight);
		int gWidth = pWidth - 1 << 1;
		int gHeight = pHeight - 1 << 1;
		int[] gInts = IntCache.getIntCache(gWidth * gHeight);
		for (int z = 0; z < pHeight - 1; ++z) {
			int idx = (z << 1) * gWidth;
			int x = 0;
			int val = pInts[x + z * pWidth];
			for (; x < pWidth - 1; x++) {
				gInts[idx] = val;
				gInts[idx++ + gWidth] = val;
				gInts[idx] = val;
				gInts[idx++ + gWidth] = val;
			}
		}
		int[] ints = IntCache.getIntCache(areaWidth * areaHeight);
		for (int y = 0; y < areaHeight; y++) {
			System.arraycopy(gInts, (y + (areaY & 1)) * gWidth + (areaX & 1), ints, y * areaWidth, areaWidth);
		}
		return ints;
	}

	public static GenLayer magnify(long seed, GenLayer root, int amount) {
		GenLayer gen = root;
		for (int i = 0; i < amount; i++) {
			gen = new GenLayerScale(seed + i, gen);
		}
		return gen;
	}
}
