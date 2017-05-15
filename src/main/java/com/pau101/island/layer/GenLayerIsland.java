package com.pau101.island.layer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerShore;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraft.world.gen.layer.IntCache;

import java.util.Arrays;

public final class GenLayerIsland extends GenLayer {
	public GenLayerIsland(long seed) {
		super(seed);
	}

	@Override
	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
		int[] values = IntCache.getIntCache(areaWidth * areaHeight);
		Arrays.fill(values, 0);
		/*for (int dy = 0; dy < 2; dy++) {
			for (int dx = 0; dx < 2; dx++) {
				int x = dx - areaX, y = dy - areaY;
				if (x >= 0 && y >= 0 && x < areaWidth && y < areaHeight) {
					values[x + y * areaWidth] = 1;
				}
			}
		}*/
		if (areaX > -areaWidth && areaX <= 0 && areaY > -areaHeight && areaY <= 0) {
			values[-areaX + -areaY * areaWidth] = 1;
		}
		return values;
	}

	public static GenLayers create(long seed) {
		GenLayer gen = new GenLayerIsland(93);
        gen = GenLayerZoom.magnify(300, gen, 1);
		gen = new GenLayerFuzzyZoom(6000, gen);
		gen = GenLayerZoom.magnify(300, gen, 3);
        gen = new GenLayerIslandBiome(500, gen);
        gen = new GenLayerShore(25, gen);
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
