package com.pau101.island.proxy;

import com.pau101.island.Island;
import com.pau101.island.IslandConfig;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public final class ClientProxy extends CommonProxy {
	@Override
	public void init() {
		super.init();
		MinecraftForge.EVENT_BUS.register(this);
		/*
		ClientCommandHandler.instance.registerCommand(new CommandBase() {
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
		});//*/
	}

	@SubscribeEvent
	public void guiOpen(GuiOpenEvent event) {
		GuiScreen gui = event.getGui();
		if (IslandConfig.islandIsDefaultWorldType && gui instanceof GuiCreateWorld) {
			ReflectionHelper.setPrivateValue(GuiCreateWorld.class, (GuiCreateWorld) gui, island.getWorldTypeID(), "field_146331_K", "selectedIndex");
		}
	}

	@SubscribeEvent
	public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (Island.ID.equals(event.getModID())) {
			ConfigManager.sync(Island.ID, Config.Type.INSTANCE);
		}
	}
}
