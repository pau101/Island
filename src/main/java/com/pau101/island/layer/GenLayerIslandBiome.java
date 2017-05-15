package com.pau101.island.layer;

import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.*;

public final class GenLayerIslandBiome extends GenLayer {
	public GenLayerIslandBiome(long seed, GenLayer parent) {
		super(seed);
		this.parent = parent;
	}

	@Override
	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
		int[] land = parent.getInts(areaX, areaY, areaWidth, areaHeight);
		int[] biomes = IntCache.getIntCache(areaWidth * areaHeight);
		for (int i = 0; i < biomes.length; i++) {
			biomes[i] = Biome.getIdForBiome(land[i] == 0 ? Biomes.OCEAN : Biomes.PLAINS);
		}
		return biomes;
	}
}
