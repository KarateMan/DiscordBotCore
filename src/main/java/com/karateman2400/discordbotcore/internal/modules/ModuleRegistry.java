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
            Bot.getThreadManager().endAllProcesses("Failed while loading ModuleRegistry class.", false);
        }
    }

    public boolean preInitialization() {
        System.out.println("[BotCore] Running Pre-Init");
        for(Module module : modules) {
            System.out.println("[BotCore] Pre-Init Status: " + module.getClass().getSimpleName());
            if(!module.preInitialization()) return false;
        }
        System.out.println("[BotCore] Pre-Init Success");
        return true;
    }

    public boolean configInitialization() {
        System.out.println("[BotCore] Running Config-Init");
        for(Module module : modules) {
            System.out.println("[BotCore] Config-Init Status: " + module.getClass().getSimpleName());
            if(module.getClass().getAnnotation(ModuleClass.class).needsConfig())
                if(!module.initializeConfiguration()) return false;
        }
        System.out.println("[BotCore] Config-Init Success");
        return true;
    }

    public boolean initialization() {
        System.out.println("[BotCore] Running Init");
        for(Module module : modules) {
            System.out.println("[BotCore] Init Status: " + module.getClass().getSimpleName());
            if(!module.initialization()) return false;
        }
        System.out.println("[BotCore] Init Success");
        return true;
    }
    public boolean registerCommands() {
        System.out.println("[BotCore] Registering Commands");
        return true;
    }

    public boolean postInitialization() {
        System.out.println("[BotCore] Running Post-Init");
        for(Module module : modules) {
            System.out.println("[BotCore] Post-Init Status: " + module.getClass().getSimpleName());
            if(!module.postInitialization()) return false;
            if(module.getClass().getAnnotation(ModuleClass.class).eventSubscriber()) Bot.getJda().addEventListener(module);
        }
        System.out.println("[BotCore] Post-Init Success");
        return true;
    }
}
