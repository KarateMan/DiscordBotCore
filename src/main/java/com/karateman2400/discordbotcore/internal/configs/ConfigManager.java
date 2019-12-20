package com.karateman2400.discordbotcore.internal.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.karateman2400.discordbotcore.internal.modules.Module;

import java.io.*;
import java.util.HashMap;

public class ConfigManager {

    private Gson gson;
    private ConfigCore coreConfig;
    private HashMap<Module, ConfigClass> configs;

    public ConfigManager() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        gson = builder.create();
        configs = new HashMap<>();
    }

    public boolean initializeCoreConfig() throws FileNotFoundException {
        File dir = new File("configs");
        if(!dir.exists()) dir.mkdir();
        File file = new File(dir, "core.json");
        try {
            if(file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                coreConfig = gson.fromJson(reader, ConfigCore.class);
                return true;
            }
            coreConfig = new ConfigCore();
            coreConfig.setName("BotCore");
            coreConfig.setToken("Need Token");
            coreConfig.setThreaded(true);
            coreConfig.setDebug(true);
            FileWriter writer = new FileWriter(file);
            writer.write(gson.toJson(coreConfig));
            writer.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    public ConfigCore getCoreConfig() {
        return coreConfig;
    }

    public void registerConfig(Module module, ConfigClass config) {
        configs.put(module, config);
    }

    public void initializeConfigs() {
        for(Module module : configs.keySet()) {
            File file = new File("configs/" + configs.get(module).getClass().getSimpleName().toLowerCase() + ".json");
            try {
                if(file.exists()) {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    configs.replace(module, gson.fromJson(reader, configs.get(module).getClass()));
                } else {
                    System.out.println(1);
                    FileWriter writer = new FileWriter(file);
                    writer.write(gson.toJson(configs.get(module)));
                    writer.close();
                }
            } catch (FileNotFoundException e) {
            } catch (IOException e) {}
        }
    }
}
