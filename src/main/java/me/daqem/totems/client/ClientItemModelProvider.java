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

        itemBuilder(itemGenerated, "health_totem");

        itemBuilder(itemGenerated, "totem_gem");
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
