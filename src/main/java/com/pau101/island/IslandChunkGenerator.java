package com.pau101.island;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkProviderOverworld;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class IslandChunkGenerator extends ChunkProviderOverworld {
	private static final IBlockState BEDROCK = Blocks.BEDROCK.getDefaultState();

	private static final IBlockState OCEAN = Blocks.WATER.getDefaultState();

	private static final int RADIUS = 8;

	private static final int CHUNK_SIZE = 16;

	private static final int CHECKER_SIZE = 2;

	private final World world;

	private final int seaLevel;

	private final Random rng;

	private final ImmutableList<BiomeEntry> biomes;

	private final int biomeTotalWeight;

	public IslandChunkGenerator(World world, String generatorOptions) {
		super(world, world.getWorldInfo().getSeed(), world.getWorldInfo().isMapFeaturesEnabled(), generatorOptions);
		this.world = world;
		seaLevel = world.getSeaLevel();
		rng = new Random(world.getWorldInfo().getSeed());
		ImmutableList.Builder<BiomeEntry> biomes = ImmutableList.builder();
		for (BiomeManager.BiomeType type : BiomeManager.BiomeType.values()) {
			List<BiomeEntry> typeBiomes = BiomeManager.getBiomes(type);
			if (typeBiomes != null) {
				biomes.addAll(typeBiomes);
			}
		}
		this.biomes = biomes.build();
		biomeTotalWeight = WeightedRandom.getTotalWeight(this.biomes);
	}

	@Override
	public Chunk provideChunk(int chunkX, int chunkZ) {
		if (isLayerChunk(chunkX, chunkZ)) {
			ChunkPrimer primer = new ChunkPrimer();
			fillLayer(primer, 0, BEDROCK);
			for (int y = 1; y < seaLevel; y++) {
				fillLayer(primer, y, OCEAN);
			}
			Chunk chunk = new Chunk(world, primer, chunkX, chunkZ);
			rng.setSeed((long) (chunkX / CHECKER_SIZE) * 341873128712L + (long) (chunkZ / CHECKER_SIZE) * 132897987541L);
			Biome biome = WeightedRandom.getRandomItem(biomes, rng.nextInt(biomeTotalWeight)).biome;
			Arrays.fill(chunk.getBiomeArray(), (byte) Biome.getIdForBiome(biome));
			chunk.generateSkylightMap();
			return chunk;
		}
		return super.provideChunk(chunkX, chunkZ);
	}

	@Override
	public void populate(int chunkX, int chunkZ) {
		if (!isLayerChunk(chunkX, chunkZ) &&
			!isLayerChunk(chunkX + 1, chunkZ) &&
			!isLayerChunk(chunkX + 1, chunkZ + 1) &&
			!isLayerChunk(chunkX, chunkZ + 1)
		) {
			super.populate(chunkX, chunkZ);
		}
	}

	private boolean isLayerChunk(int chunkX, int chunkZ) {
		return Math.abs(chunkX) > RADIUS || Math.abs(chunkZ) > RADIUS;
	}

	private void fillLayer(ChunkPrimer primer, int y, IBlockState state) {
		for (int i = 0; i < CHUNK_SIZE * CHUNK_SIZE; i++) {
			primer.setBlockState(i / CHUNK_SIZE, y, i % CHUNK_SIZE, state);
		}
	}
}
