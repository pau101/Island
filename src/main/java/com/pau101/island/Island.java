package com.pau101.island;

import com.pau101.island.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Island.ID, name = Island.NAME, version = Island.VERSION)
public final class Island {
	public static final String ID = "island";

	public static final String VERSION = "1.0.0";

	public static final String NAME = "Island";

	@SidedProxy(
		clientSide = "com.pau101.island.proxy.ClientProxy",
		serverSide = "com.pau101.island.proxy.CommonProxy"
	)
	public static CommonProxy proxy;

	@EventHandler
	public void init(FMLPreInitializationEvent event) {
		proxy.init();
	}
}
