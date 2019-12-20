package com.karateman2400.discordbotcore;

import com.karateman2400.discordbotcore.internal.commands.CommandRegistry;
import com.karateman2400.discordbotcore.internal.configs.ConfigManager;
import com.karateman2400.discordbotcore.internal.modules.ModuleRegistry;
import com.karateman2400.discordbotcore.internal.threads.ThreadManager;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Bot {

    private static JDA jda;
    private static ModuleRegistry moduleRegistry;
    private static CommandRegistry commandRegistry;
    private static ThreadManager threadManager;
    private static ConfigManager configManager;

    public static void main(String[] args){

        //Initialize Static Classes
        moduleRegistry = new ModuleRegistry();
        commandRegistry = new CommandRegistry();
        threadManager = new ThreadManager();
        configManager = new ConfigManager();

        //Run Startup Processes
        if(!moduleRegistry.preInitialization()) threadManager.endAllProcesses("One or more modules failed pre-init", false);
        if(!moduleRegistry.configInitialization()) threadManager.endAllProcesses("One or more modules failed config-init", false);
        if(!moduleRegistry.initialization()) threadManager.endAllProcesses("One or more modules failed init", false);

        //Register JDA.
        try {
            jda = new JDABuilder(AccountType.BOT)
                    .setToken("MzM4ODU1NDkzNTU4NTk5Njgw.Xf0IvQ.h00NvYGEJYsC68aDlqARpZb-ZCQ")
                    .build()
                    .awaitReady();
        } catch (LoginException e) {
            threadManager.endAllProcesses("JDA registration failed", true);
        } catch (InterruptedException e) {
            threadManager.endAllProcesses("JDA registration failed", true);
        }
        jda.getPresence().setActivity(Activity.of(Activity.ActivityType.STREAMING, "Activating Bot"));

        //Run Post Startup Processes
        if(!moduleRegistry.registerCommands()) threadManager.endAllProcesses("One or more modules failed to load commands", false);
        if(!moduleRegistry.postInitialization()) threadManager.endAllProcesses("One or more modules failed post-init", false);


        jda.getPresence().setActivity(Activity.of(Activity.ActivityType.STREAMING, "Bot Running"));
    }

    public static JDA getJda() {
        return jda;
    }

    public static ModuleRegistry getModuleRegistry() {
        return moduleRegistry;
    }

    public static CommandRegistry getCommandRegistry() {
        return commandRegistry;
    }

    public static ThreadManager getThreadManager() {
        return threadManager;
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }
}
