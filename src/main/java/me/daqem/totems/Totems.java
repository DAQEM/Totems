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
//TODO: Different text color per level
//TODO: Generate custom structures
//TODO: Add the Heart to chests

//TODO: 1  Totem of Health
//TODO: 2  Totem of Strength
//TODO: 3  Totem of Flight
//TODO: 4  Totem of Night Vision
//TODO: 5  Totem of Water Breathing
//TODO: 6  Totem of Speed
//TODO: 7  Totem of Haste
//TODO: 8  Totem of Vein Mining
//TODO: 9  Totem of Blooming            (Amy)
//TODO: 10 Totem of Scaring             (Amy)
//TODO: 11 Totem of End Calming         (Amy)
//TODO: 12 Totem of
//TODO: 13 Totem of
//TODO: 14 Totem of
//TODO: 15 Totem of
//TODO: 16 Totem of
//TODO: 17 Totem of
//TODO: 18 Totem of
//TODO: 19 Totem of
//TODO: 20 Totem of
//TODO: 21 Totem of
//TODO: 22 Totem of
//TODO: 23 Totem of
//TODO: 24 Totem of
//TODO: 25 Totem of
//TODO: 26 Totem of
//TODO: 27 Totem of



