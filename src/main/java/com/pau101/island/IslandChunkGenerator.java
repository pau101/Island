package com.pau101.island;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkProviderOverworld;

public final class IslandChunkGenerator extends ChunkProviderOverworld {
	private static final IBlockState BEDROCK = Blocks.BEDROCK.getDefaultState();

	private static final IBlockState OCEAN = Blocks.WATER.getDefaultState();

	private static final int RADIUS = 8;

	private static final int CHUNK_SIZE = 16;

	private final World world;

	private final int seaLevel;

	public IslandChunkGenerator(World world, String generatorOptions) {
		super(world, world.getWorldInfo().getSeed(), world.getWorldInfo().isMapFeaturesEnabled(), generatorOptions);
		this.world = world;
		seaLevel = world.getSeaLevel();
	}

	@Override
	public Chunk provideChunk(int chunkX, int chunkZ) {
		if (Math.abs(chunkX) > RADIUS || Math.abs(chunkZ) > RADIUS) {
			ChunkPrimer primer = new ChunkPrimer();
			fillLayer(primer, 0, BEDROCK);
			for (int y = 1; y < seaLevel; y++) {
				fillLayer(primer, y, OCEAN);
			}
			Chunk chunk = new Chunk(world, primer, chunkX, chunkZ);
			Biome[] biomes = world.getBiomeProvider().getBiomes(null, chunkX * CHUNK_SIZE, chunkZ * CHUNK_SIZE, CHUNK_SIZE, CHUNK_SIZE);
			byte[] chunkBiomes = chunk.getBiomeArray();
			for (int i = 0; i < chunkBiomes.length; ++i) {
				chunkBiomes[i] = (byte) Biome.getIdForBiome(biomes[i]);
			}
			chunk.generateSkylightMap();
			return chunk;
		}
		return super.provideChunk(chunkX, chunkZ);
	}

	private void fillLayer(ChunkPrimer primer, int y, IBlockState state) {
		for (int i = 0; i < CHUNK_SIZE * CHUNK_SIZE; i++) {
			primer.setBlockState(i / CHUNK_SIZE, y, i % CHUNK_SIZE, state);
		}
	}
}
