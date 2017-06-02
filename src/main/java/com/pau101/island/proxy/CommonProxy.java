package com.pau101.island.proxy;

import com.pau101.island.Island;
import com.pau101.island.IslandBiomeProvider;
import com.pau101.island.IslandChunkGenerator;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.IChunkGenerator;

public class CommonProxy {
	protected WorldType island;

	public void init() {
		island = new WorldType(Island.ID) {
			@Override
			public int getSpawnFuzz(WorldServer world, MinecraftServer server) {
				return 0;
			}

			@Override
			public BiomeProvider getBiomeProvider(World world) {
				return new IslandBiomeProvider(world.getWorldInfo());
			}

			@Override
			public IChunkGenerator getChunkGenerator(World world, String generatorOptions) {
				return new IslandChunkGenerator(world, generatorOptions);
			}
		};
	}
}
