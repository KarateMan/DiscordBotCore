package com.karateman2400.discordbotcore.internal.modules;

import com.karateman2400.discordbotcore.Bot;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModuleRegistry {

    private List<Module> modules;
    private String botName;

    public ModuleRegistry() {
        modules = new ArrayList<>();

        botName = Bot.getConfigManager().getCoreConfig().getName();

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
        if(Bot.getConfigManager().getCoreConfig().isDebug()) System.out.println("[" + botName +  "] Running Pre-Init");
        for(Module module : modules) {
            if(Bot.getConfigManager().getCoreConfig().isDebug()) System.out.println("[" + botName +  "] Pre-Init Status: " + module.getClass().getSimpleName());
            if(!module.preInitialization()) return false;
        }
        if(Bot.getConfigManager().getCoreConfig().isDebug()) System.out.println("[" + botName +  "] Pre-Init Success");
        return true;
    }

    public boolean configInitialization() {
        if(Bot.getConfigManager().getCoreConfig().isDebug()) System.out.println("[" + botName +  "] Running Config-Init");
        for(Module module : modules) {
            if(Bot.getConfigManager().getCoreConfig().isDebug()) System.out.println("[" + botName +  "] Config-Init Status: " + module.getClass().getSimpleName());
            if(module.getClass().getAnnotation(ModuleClass.class).needsConfig())
                if(!module.initializeConfiguration()) return false;
        }
        Bot.getConfigManager().initializeConfigs();
        if(Bot.getConfigManager().getCoreConfig().isDebug()) System.out.println("[" + botName +  "] Config-Init Success");
        return true;
    }

    public boolean initialization() {
        if(Bot.getConfigManager().getCoreConfig().isDebug()) System.out.println("[" + botName +  "] Running Init");
        for(Module module : modules) {
            if(Bot.getConfigManager().getCoreConfig().isDebug()) System.out.println("[" + botName +  "] Init Status: " + module.getClass().getSimpleName());
            if(!module.initialization()) return false;
        }
        if(Bot.getConfigManager().getCoreConfig().isDebug()) System.out.println("[" + botName +  "] Init Success");
        return true;
    }

    public boolean postInitialization() {
        if(Bot.getConfigManager().getCoreConfig().isDebug()) System.out.println("[" + botName +  "] Running Post-Init");
        for(Module module : modules) {
            if(Bot.getConfigManager().getCoreConfig().isDebug()) System.out.println("[" + botName +  "] Post-Init Status: " + module.getClass().getSimpleName());
            if(!module.postInitialization()) return false;
            if(module.getClass().getAnnotation(ModuleClass.class).eventSubscriber()) Bot.getJda().addEventListener(module);
        }
        if(Bot.getConfigManager().getCoreConfig().isDebug()) System.out.println("[" + botName +  "] Post-Init Success");
        return true;
    }

    public List<Module> getModules() {
        return modules;
    }
}
