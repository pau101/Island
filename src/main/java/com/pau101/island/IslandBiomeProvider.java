package com.pau101.island;

import com.pau101.island.layer.GenLayerIsland;
import com.pau101.island.layer.GenLayerIsland.GenLayers;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.init.Biomes;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraft.world.storage.WorldInfo;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class IslandBiomeProvider extends BiomeProvider {
	private final GenLayer terrainBiomes;

	private final GenLayer exactBiomes;

	private final BiomeCache biomeCache;

	private final List<Biome> spawnBiomes = Collections.singletonList(Biomes.PLAINS);

	public IslandBiomeProvider(WorldInfo info) {
		super(info);
		GenLayers layers = GenLayerIsland.create(info.getSeed());
		terrainBiomes = layers.getTerrainBiomes();
		exactBiomes = layers.getExactBiomes();
		biomeCache = new BiomeCache(this);
	}

	@Override
	public List<Biome> getBiomesToSpawnIn() {
		return spawnBiomes;
	}

	@Override
	public Biome getBiome(BlockPos pos) {
		return getBiome(pos, (Biome) null);
	}

	@Override
	public Biome getBiome(BlockPos pos, Biome defaultBiome) {
		return biomeCache.getBiome(pos.getX(), pos.getZ(), defaultBiome);
	}

	@Override
	public float getTemperatureAtHeight(float temperature, int height) {
		return temperature;
	}

	@Override
	public Biome[] getBiomesForGeneration(Biome[] outputArray, int x, int z, int width, int height) {
		IntCache.resetIntCache();
		if (outputArray == null || outputArray.length < width * height) {
			outputArray = new Biome[width * height];
		}
		int[] biomes = terrainBiomes.getInts(x, z, width, height);
		try {
			for (int i = 0; i < width * height; i++) {
				outputArray[i] = Biome.getBiome(biomes[i], Biomes.DEFAULT);
			}
			return outputArray;
		} catch (Throwable throwable) {
			CrashReport report = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
			CrashReportCategory cat = report.makeCategory("RawBiomeBlock");
			cat.addCrashSection("biomes[] size", outputArray.length);
			cat.addCrashSection("x", x);
			cat.addCrashSection("z", z);
			cat.addCrashSection("w", width);
			cat.addCrashSection("h", height);
			throw new ReportedException(report);
		}
	}

	@Override
	public Biome[] getBiomes(@Nullable Biome[] outputArray, int x, int z, int width, int depth) {
		return getBiomes(outputArray, x, z, width, depth, true);
	}

	@Override
	public Biome[] getBiomes(@Nullable Biome[] outputArray, int x, int z, int width, int length, boolean cacheFlag) {
		IntCache.resetIntCache();
		if (outputArray == null || outputArray.length < width * length) {
			outputArray = new Biome[width * length];
		}
		if (cacheFlag && width == 16 && length == 16 && (x & 15) == 0 && (z & 15) == 0) {
			Biome[] biomes = biomeCache.getCachedBiomes(x, z);
			System.arraycopy(biomes, 0, outputArray, 0, width * length);
			return outputArray;
		}
		int[] biomes = exactBiomes.getInts(x, z, width, length);
		for (int i = 0; i < width * length; i++) {
			outputArray[i] = Biome.getBiome(biomes[i], Biomes.DEFAULT);
		}
		return outputArray;
	}

	@Override
	public boolean areBiomesViable(int x, int z, int radius, List<Biome> allowed) {
		IntCache.resetIntCache();
		int minX = x - radius >> 2;
		int minZ = z - radius >> 2;
		int maxX = x + radius >> 2;
		int maxZ = z + radius >> 2;
		int width = maxX - minX + 1;
		int height = maxZ - minZ + 1;
		int[] biomes = terrainBiomes.getInts(minX, minZ, width, height);
		try {
			for (int idx = 0; idx < width * height; idx++) {
				Biome biome = Biome.getBiome(biomes[idx]);
				if (!allowed.contains(biome)) {
					return false;
				}
			}
			return true;
		} catch (Throwable throwable) {
			CrashReport report = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
			CrashReportCategory cat = report.makeCategory("Layer");
			cat.addCrashSection("Layer", terrainBiomes.toString());
			cat.addCrashSection("x", x);
			cat.addCrashSection("z", z);
			cat.addCrashSection("radius", radius);
			cat.addCrashSection("allowed", allowed);
			throw new ReportedException(report);
		}
	}

	@Nullable
	@Override
	public BlockPos findBiomePosition(int x, int z, int range, List<Biome> searchBiomes, Random random) {
		IntCache.resetIntCache();
		int minX = x - range >> 2;
		int minZ = z - range >> 2;
		int maxX = x + range >> 2;
		int maxZ = z + range >> 2;
		int width = maxX - minX + 1;
		int height = maxZ - minZ + 1;
		int[] biomes = terrainBiomes.getInts(minX, minZ, width, height);
		BlockPos pos = null;
		int invReplaceChance = 0;
		for (int idx = 0; idx < width * height; idx++) {
			int blockX = minX + idx % width << 2;
			int blockZ = minZ + idx / width << 2;
			Biome biome = Biome.getBiome(biomes[idx]);
			if (searchBiomes.contains(biome) && (pos == null || random.nextInt(invReplaceChance + 1) == 0)) {
				pos = new BlockPos(blockX, 0, blockZ);
				invReplaceChance++;
			}
		}
		return pos;
	}

	@Override
	public void cleanupCache() {
		biomeCache.cleanupCache();
	}

	@Override
	public GenLayer[] getModdedBiomeGenerators(WorldType worldType, long seed, GenLayer[] original) {
		return original;
	}

	@Override
	public boolean isFixedBiome() {
		return false;
	}

	@Override
	public Biome getFixedBiome() {
		return null;
	}
}
