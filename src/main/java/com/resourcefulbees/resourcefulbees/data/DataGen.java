package com.resourcefulbees.resourcefulbees.data;

import com.resourcefulbees.resourcefulbees.config.BeeSetup;
import com.resourcefulbees.resourcefulbees.config.Config;
import com.resourcefulbees.resourcefulbees.registry.BeeRegistry;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.resourcefulbees.resourcefulbees.ResourcefulBees.LOGGER;

public class DataGen {
    
    public static void generateClientData() {
        BeeRegistry.getBees().forEach(((s, customBeeData) -> {
            if (Config.GENERATE_ENGLISH_LANG.get()) GenerateEnglishLang(s);
            generateItemModels(s);
        }));
    }

    private static void writeFile(String path, String file, String data) throws IOException {
        Files.createDirectories(Paths.get(path));
        FileWriter writer = new FileWriter(Paths.get(path, file).toFile());
        writer.write(data);
        writer.close();
    }
    
    private static void GenerateEnglishLang(String name) {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        String displayName = StringUtils.replace(name, "_", " ");
        displayName = WordUtils.capitalizeFully(displayName);

        //block
        builder.append("\"block.resourcefulbees.");
        builder.append(name);
        builder.append("_honeycomb_block\" : \"");
        builder.append(displayName);
        builder.append(" Honeycomb Block\",\n");
        //comb
        builder.append("\"item.resourcefulbees.");
        builder.append(name);
        builder.append("_honeycomb\" : \"");
        builder.append(displayName);
        builder.append(" Honeycomb\",\n");
        //spawn egg
        builder.append("\"item.resourcefulbees.");
        builder.append(name);
        builder.append("_spawn_egg\" : \"");
        builder.append(displayName);
        builder.append(" Bee Spawn Egg\",\n");
        //entity
        builder.append("\"entity.resourcefulbees.");
        builder.append(name);
        builder.append("_bee\" : \"");
        builder.append(displayName);
        builder.append(" Bee\",\n");

        builder.deleteCharAt(builder.lastIndexOf(","));
        builder.append("}");

        String langPath = BeeSetup.RESOURCE_PATH.toString() + "/assets/resourcefulbees/lang/";
        String langFile = "en_us.json";
        try {
            writeFile(langPath, langFile, builder.toString());
            LOGGER.info("Language File Generated!");
        } catch (IOException e) {
            LOGGER.error("Could not generate language file!");
            //e.printStackTrace();
        }
    }
    
    private static void generateItemModels(String name) {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        builder.append("    \"parent\": \"item/generated\",\n");
        builder.append("    \"textures\": {\n");
        builder.append("        \"layer0\": \"resourcefulbees:item/honeycomb\"\n");
        builder.append("    }\n");
        builder.append("}");

        String itemModelPath = BeeSetup.RESOURCE_PATH.toString() + "/assets/resourcefulbees/models/item/";
        String modelFile = name + "_honeycomb.json";
        try {
            writeFile(itemModelPath, modelFile, builder.toString());
            LOGGER.info("Model File Generated!");
        } catch (IOException e) {
            LOGGER.error("Could not generate model file!");
            //e.printStackTrace();
        }

        StringBuilder builder1 = new StringBuilder();
        builder1.append(String.format("{\n    \"parent\": \"resourcefulbees:block/%1$s_honeycomb_block\"\n}", name));
        modelFile = name + "_honeycomb_block.json";

        try {
            writeFile(itemModelPath, modelFile, builder1.toString());
            LOGGER.info("Model File Generated!");
        } catch (IOException e) {
            LOGGER.error("Could not generate model file!");
            //e.printStackTrace();
        }
    }

    private static void generateBlockModels(String name) {

    }
}
