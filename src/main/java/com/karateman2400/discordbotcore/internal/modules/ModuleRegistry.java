package com.karateman2400.discordbotcore.internal.modules;

import com.karateman2400.discordbotcore.Bot;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModuleRegistry {

    private List<Module> modules;

    public ModuleRegistry() {
        modules = new ArrayList<>();

        try {
            Reflections reflections = new Reflections("com.karateman2400.discordbotcore.modules");
            Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(ModuleClass.class);

            for (Class<?> module : annotatedClasses) {
                modules.add((Module) module.cast(module.newInstance()));
            }
        } catch (Exception failed) {
            Bot.getThreadManager().endAllProcesses("Failed while loading ModuleRegistry class.");
        }
    }

    public boolean preInitialization() {
        if(Bot.getConfigManager().getCoreConfig().isDebug()) System.out.println("[BotCore] Running Pre-Init");
        for(Module module : modules) {
            if(Bot.getConfigManager().getCoreConfig().isDebug()) System.out.println("[BotCore] Pre-Init Status: " + module.getClass().getSimpleName());
            if(!module.preInitialization()) return false;
        }
        if(Bot.getConfigManager().getCoreConfig().isDebug()) System.out.println("[BotCore] Pre-Init Success");
        return true;
    }

    public boolean configInitialization() {
        if(Bot.getConfigManager().getCoreConfig().isDebug()) System.out.println("[BotCore] Running Config-Init");
        for(Module module : modules) {
            if(Bot.getConfigManager().getCoreConfig().isDebug()) System.out.println("[BotCore] Config-Init Status: " + module.getClass().getSimpleName());
            if(module.getClass().getAnnotation(ModuleClass.class).needsConfig())
                if(!module.initializeConfiguration()) return false;
        }
        Bot.getConfigManager().initializeConfigs();
        if(Bot.getConfigManager().getCoreConfig().isDebug()) System.out.println("[BotCore] Config-Init Success");
        return true;
    }

    public boolean initialization() {
        if(Bot.getConfigManager().getCoreConfig().isDebug()) System.out.println("[BotCore] Running Init");
        for(Module module : modules) {
            if(Bot.getConfigManager().getCoreConfig().isDebug()) System.out.println("[BotCore] Init Status: " + module.getClass().getSimpleName());
            if(!module.initialization()) return false;
        }
        if(Bot.getConfigManager().getCoreConfig().isDebug()) System.out.println("[BotCore] Init Success");
        return true;
    }

    public boolean postInitialization() {
        if(Bot.getConfigManager().getCoreConfig().isDebug()) System.out.println("[BotCore] Running Post-Init");
        for(Module module : modules) {
            if(Bot.getConfigManager().getCoreConfig().isDebug()) System.out.println("[BotCore] Post-Init Status: " + module.getClass().getSimpleName());
            if(!module.postInitialization()) return false;
            if(module.getClass().getAnnotation(ModuleClass.class).eventSubscriber()) Bot.getJda().addEventListener(module);
        }
        if(Bot.getConfigManager().getCoreConfig().isDebug()) System.out.println("[BotCore] Post-Init Success");
        return true;
    }

    public List<Module> getModules() {
        return modules;
    }
}
