package com.karateman2400.discordbotcore;

import com.karateman2400.discordbotcore.internal.commands.CommandHandler;
import com.karateman2400.discordbotcore.internal.commands.CommandRegistry;
import com.karateman2400.discordbotcore.internal.configs.ConfigManager;
import com.karateman2400.discordbotcore.internal.modules.ModuleRegistry;
import com.karateman2400.discordbotcore.internal.threads.ThreadManager;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class Bot {

    private static JDA jda;
    private static ModuleRegistry moduleRegistry;
    private static CommandRegistry commandRegistry;
    private static ThreadManager threadManager;
    private static ConfigManager configManager;

    public static void main(String[] args) throws IOException {

        //Initialize Static Classes
        configManager = new ConfigManager();
        if(!configManager.initializeCoreConfig()) threadManager.endAllProcesses("Failed to load core config"); // Must run before ModuleRegistry is loaded
        moduleRegistry = new ModuleRegistry();
        commandRegistry = new CommandRegistry();
        threadManager = new ThreadManager();

        //Run Startup Processes
        if(!moduleRegistry.preInitialization()) threadManager.endAllProcesses("One or more modules failed pre-init");
        if(!moduleRegistry.configInitialization()) threadManager.endAllProcesses("One or more modules failed config-init");
        if(!moduleRegistry.initialization()) threadManager.endAllProcesses("One or more modules failed init");

        //Register JDA.
        try {
            if(configManager.getCoreConfig().isDebug()) System.out.println("[" + configManager.getCoreConfig().getName() + "] Registering JDA");
            jda = new JDABuilder(AccountType.BOT)
                    .setToken(configManager.getCoreConfig().getToken())
                    .build()
                    .awaitReady();
        } catch (LoginException e) {
            threadManager.endAllProcesses("JDA registration failed");
        } catch (InterruptedException e) {
            threadManager.endAllProcesses("JDA registration failed");
        }
        if(configManager.getCoreConfig().isDebug()) System.out.println("[" + configManager.getCoreConfig().getName() + "] JDA Registration Success");

        jda.getPresence().setActivity(Activity.of(Activity.ActivityType.STREAMING, "Activating Bot"));

        //Run Post Startup Processes
        if(!commandRegistry.registerCommands()) threadManager.endAllProcesses("One or more modules failed to load commands");
        if(!moduleRegistry.postInitialization()) threadManager.endAllProcesses("One or more modules failed post-init");
        jda.addEventListener(new CommandHandler());


        jda.getPresence().setActivity(Activity.of(Activity.ActivityType.STREAMING, "Bot Running"));
        jda.getSelfUser().getManager().setName(configManager.getCoreConfig().getName()).queue();
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
