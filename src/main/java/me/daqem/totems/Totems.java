package me.daqem.totems;

import mcp.MethodsReturnNonnullByDefault;
import me.daqem.totems.init.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Totems.MODID)
public class Totems {

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    // The value here should match an entry in the META-INF/mods.toml file
    public static final String MODID = "totems";

    public Totems() {
        DistExecutor.runForDist(
                () -> SideProxy.Client::new,
                () -> SideProxy.Server::new
        );
    }

    @MethodsReturnNonnullByDefault
    public static final ItemGroup TAB = new ItemGroup("totemstab") {
        public ItemStack createIcon() {
            return new ItemStack(ModItems.healthTotem);
        }
    };

    public static ResourceLocation getId(String path) {
        return new ResourceLocation(MODID, path);
    }
}

//TODO: Change display name and lore color of totem
//TODO: Add glow to totem when max level
//TODO: Different text color per level.
