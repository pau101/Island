package com.pau101.island;

import com.pau101.island.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Island.ID, name = Island.NAME, version = Island.VERSION, acceptableRemoteVersions = "*")
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

	/*public static void main(String[] args) {
		Random rng = new Random();
		Random seeder = new Random();
		long bestIterDiscoverTime = System.currentTimeMillis();
		int bestDist = Integer.MAX_VALUE;
		end: while (true) {
			long seed = seeder.nextLong();
			rng.setSeed(seed);
			int iter = 0, x = 0, z = 0;
			while (true) {
				x += rng.nextInt(64) - rng.nextInt(64);
				z += rng.nextInt(64) - rng.nextInt(64);
				if (++iter == 1000) {
					int d = x * x + z * z;
					if (d < bestDist) {
						long t = System.currentTimeMillis();
						System.out.printf("best dist: %d %d (%d), seed: %d, time since last: %dms%n", x, z, (int) Math.sqrt(d), seed, t - bestIterDiscoverTime);
						bestDist = d;
						bestIterDiscoverTime = t;
						if (x == 0 && z == 0) {
							break end;
						}
					}
					break;
				}
			}
		}
	}*/
}
