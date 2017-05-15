package com.pau101.island;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Island.ID, name = Island.NAME, version = Island.VERSION)
public class Island {
	public static final String ID = "island";

	public static final String VERSION = "1.0.0";

	public static final String NAME = "Island";

	private static WorldType island;

	@EventHandler
	public void init(FMLPreInitializationEvent event) {
		island = new WorldType(ID) {
			@Override
			public BiomeProvider getBiomeProvider(World world) {
				return super.getBiomeProvider(world);
			}

			@Override
			public IChunkGenerator getChunkGenerator(World world, String generatorOptions) {
				return super.getChunkGenerator(world, generatorOptions);
			}
		};
	}
}