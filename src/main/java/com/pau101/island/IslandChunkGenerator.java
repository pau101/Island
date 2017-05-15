package com.pau101.island;

import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkProviderOverworld;

public class IslandChunkGenerator extends ChunkProviderOverworld {
	public IslandChunkGenerator(World world, String generatorOptions) {
		super(world, world.getWorldInfo().getSeed(), world.getWorldInfo().isMapFeaturesEnabled(), generatorOptions);
	}
}
