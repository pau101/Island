package com.pau101.island;

import com.pau101.island.layer.GenLayerIsland;
import com.pau101.island.layer.GenLayerIsland.GenLayers;

import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.storage.WorldInfo;

public class IslandBiomeProvider extends BiomeProvider {
	private final GenLayer terrainBiomes;

	private final GenLayer exactBiomes;

	public IslandBiomeProvider(long seed, WorldInfo info) {
		super(info);
		GenLayers layers = GenLayerIsland.create(seed);
		terrainBiomes = layers.getTerrainBiomes();
		exactBiomes = layers.getExactBiomes();
	}
}
