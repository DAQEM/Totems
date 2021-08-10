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
            return new ItemStack(ModItems.totemOfHealth);
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

//HOTBAR
//DONE: 1 Totem of Health              (1 more heart per level)                        (!Heart)
//TODO: 2 Totem of Armor                (1 more armour per level)                       (!Armour)
//TODO: 3 Totem of Hunger               (10% less hunger per level)                         (Food)

// POTION EFFECTS
//TODO: 4 Totem of Speed               (10% faster walking (and flying?) per level)    (!Boots)
//TODO: 5 Totem of Strength            (10% more strength per level)                       (Sword?)
//TODO: 6 Totem of Haste               (10% faster mining per level)                   (!Wither)

//SPECIAL ABILITIES
//TODO: 7 Totem of Vein Mining         (10% chance per level)                          (!Mining Helmet)
//TODO: 8 Totem of Flight              (hard to craft)                                 (!Wings)
//TODO: 9 Totem of Tree Felling        (with chances)                                      (Axe)



//NOPE: 10 Totem of Night Vision
//NOPE: 11 Totem of Water Breathing

//NOPE: 12 Totem of Blooming           (A)
//NOPE: 13 Totem of Scaring             (A)
//NOPE: 14 Totem of End Calming         (A)
//NOPE: 15 Totem of Nether Calming      (A)


