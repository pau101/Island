package com.pau101.island;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Island.ID, name = Island.NAME, version = Island.VERSION)
public final class Island {
	public static final String ID = "island";

	public static final String VERSION = "1.0.0";

	public static final String NAME = "Island";

	private static WorldType island;

	@EventHandler
	public void init(FMLPreInitializationEvent event) {
		island = new WorldType(ID) {
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
		/*ClientCommandHandler.instance.registerCommand(new CommandBase() {
			@Override
			public String getName() {
				return "regen";
			}

			@Override
			public String getUsage(ICommandSender sender) {
				return "/regen [fully [reseed]]";
			}

			@Override
			public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
				Minecraft mc = Minecraft.getMinecraft();
				WorldInfo worldInfo = server.getEntityWorld().getWorldInfo();
				mc.loadWorld(null);
				ISaveFormat saveLoader = mc.getSaveLoader();
				saveLoader.flushCache();
				boolean fully = false;
				boolean reseed = false;
				if (args.length > 0 && "fully".equalsIgnoreCase(args[0])) {
					fully = true;
					if (args.length > 1 && "reseed".equalsIgnoreCase(args[1])) {
						reseed = true;
					}
				}
				if (fully) {
					saveLoader.deleteWorldDirectory("Debug");
				} else {
					saveLoader.deleteWorldDirectory("Debug/region");
				}
				long seed = worldInfo.getSeed();
				if (reseed) {
					seed++;
				}
				WorldSettings worldSettings = new WorldSettings(seed, GameType.SPECTATOR, true, false, island);
				worldSettings.enableCommands();
				mc.launchIntegratedServer("Debug", "Debug", worldSettings);
				if (fully) {
					World world = mc.getIntegratedServer().getEntityWorld();
					world.getGameRules().setOrCreateGameRule("doDaylightCycle", "false");
					world.getGameRules().setOrCreateGameRule("doWeatherCycle", "false");
					world.setWorldTime(6000);
				}
			}
		});*/
	}
}
