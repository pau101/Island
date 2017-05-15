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
		if (areaX > -areaWidth && areaX <= 0 && areaY > -areaHeight && areaY <= 0) {
			values[-areaX + -areaY * areaWidth] = 1;
		}
		return values;
	}

	public static GenLayers create(long seed) {
		GenLayer gen = new GenLayerIsland(93);
        gen = magnify(300, gen, 2);
		gen = magnifyFuzzy(2000, gen);
		gen = magnify(400, gen, 2);
		gen =  magnifyFuzzy(3000, gen);
        gen = new GenLayerIslandBiome(500, gen);
        gen = new GenLayerShore(25, gen);
		GenLayer exact = new GenLayerVoronoiZoom(10, gen);
		gen.initWorldGenSeed(seed);
		exact.initWorldGenSeed(seed);
		return new GenLayers(gen, exact);
	}

	private static GenLayer magnify(long seed, GenLayer gen, int amount) {
		return GenLayerZoom.magnify(seed, gen, amount);
	}

	private static GenLayer magnifyFuzzy(long seed, GenLayer gen) {
		return new GenLayerFuzzyZoom(seed, gen);
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
