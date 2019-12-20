package com.karateman2400.discordbotcore.internal.commands;

import com.karateman2400.discordbotcore.Bot;
import com.karateman2400.discordbotcore.internal.modules.Module;
import com.karateman2400.discordbotcore.internal.modules.ModuleClass;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CommandRegistry {

    private List<Command> commands;
    private HashMap<List<String>, Command> commandsAliases;

    public CommandRegistry() {
        commands = new ArrayList<>();
        commandsAliases = new HashMap<>();

        for(Module module : Bot.getModuleRegistry().getModules()) {
            String commandModulePath = module.getClass().getName().replaceAll(module.getClass().getSimpleName(), "commands");
            try {
                Reflections reflections = new Reflections(commandModulePath);
                Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(CommandClass.class);

                for (Class<?> command : annotatedClasses) {
                    commands.add((Command) command.cast(command.newInstance()));
                }
            } catch (Exception failed) {
                Bot.getThreadManager().endAllProcesses("Failed while loading CommandRegistry class.");
            }
        }

    }

    public boolean registerCommands() {
        if(Bot.getConfigManager().getCoreConfig().isDebug()) System.out.println("[BotCore] Registering Commands");
        for(Command command : commands) {
            if(Bot.getConfigManager().getCoreConfig().isDebug()) System.out.println("[BotCore] " + command.getClass().getAnnotation(CommandClass.class).module().getSimpleName() +": " + command.getClass().getSimpleName());
            boolean duplicate = false;
            for(List<String> list : commandsAliases.keySet()) {
                for(String alias : command.aliases()) {
                    if(list.contains(alias)) duplicate = true;
                }
            }
            if(duplicate) {
                if(Bot.getConfigManager().getCoreConfig().isDebug()) System.out.println("[BotCore] Skipping Command: " + command.getClass().getSimpleName() + " with reason alias found in another command.");
                duplicate = false;
                continue;
            }
            commandsAliases.put(command.aliases(), command);
        }
        if(Bot.getConfigManager().getCoreConfig().isDebug()) System.out.println("[BotCore] Command Registration Success");
        return true;
    }

    public HashMap<List<String>, Command> getCommandsAliases() {
        return commandsAliases;
    }
}
