package me.daqem.totems.client;

import me.daqem.totems.Totems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ClientItemModelProvider extends ItemModelProvider {

    public ClientItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Totems.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));

        itemBuilder(itemGenerated, "totem_of_health");
        itemBuilder(itemGenerated, "totem_of_armor");
        itemBuilder(itemGenerated, "totem_of_hunger");
        itemBuilder(itemGenerated, "totem_of_speed");
        itemBuilder(itemGenerated, "totem_of_strength");
        itemBuilder(itemGenerated, "totem_of_haste");
        itemBuilder(itemGenerated, "totem_of_tree_felling");
        itemBuilder(itemGenerated, "totem_of_flight");
        itemBuilder(itemGenerated, "totem_of_vein_mining");

        itemBuilder(itemGenerated, "totem_gem");
        itemBuilder(itemGenerated, "totem_scrap");
        itemBuilder(itemGenerated, "heart");
        doubleItemBuilder(itemGenerated, "totem_bag", "totem_bag_string");

        cubeAll("totem_gem_ore", modLoc("block/totem_gem_ore"));
    }

    private void itemBuilder(ModelFile itemGenerated, String name) {
        getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }

    private void doubleItemBuilder(ModelFile itemGenerated, String name, String secondName) {
        getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name).texture("layer1", "item/" + secondName);
    }
}
