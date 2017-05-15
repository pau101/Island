package com.pau101.island.layer;

import java.util.Arrays;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraft.world.gen.layer.IntCache;

public final class GenLayerIsland extends GenLayer {
	public GenLayerIsland() {
		super(93);
	}

	@Override
	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
		int[] values = IntCache.getIntCache(areaWidth * areaHeight);
		Arrays.fill(values, 0);
		if (areaX > -areaWidth && areaX <= 0 && areaY > -areaHeight && areaY <= 0) {
			values[-areaX + -areaY * areaWidth] = 1;
		}
		return values;
	}

	public static GenLayers create(long seed) {
		GenLayer gen = new GenLayerIsland();
        gen = new GenLayerFuzzyZoom(2000, gen);
        gen = GenLayerZoom.magnify(300, gen, 2);
		GenLayer exact = new GenLayerVoronoiZoom(10, gen);
		gen.initWorldGenSeed(seed);
		exact.initWorldGenSeed(seed);
		return new GenLayers(gen, exact);
	}

	public static final class GenLayers {
		private final GenLayer terrainBiomes;

		private final GenLayer exactBiomes;

		public GenLayers(GenLayer terrainBiomes, GenLayer exactBiomes) {
			this.terrainBiomes = terrainBiomes;
			this.exactBiomes = exactBiomes;
		}

		public GenLayer getTerrainBiomes() {
			return terrainBiomes;
		}

		public GenLayer getExactBiomes() {
			return exactBiomes;
		}
	}
}
