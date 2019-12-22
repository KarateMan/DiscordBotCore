package com.karateman2400.discordbotcore.internal.threads;

import com.karateman2400.discordbotcore.Bot;

public class ThreadManager {

    public static volatile boolean endAll = false;
    private String botName;

    public ThreadManager() {
        botName = Bot.getConfigManager().getCoreConfig().getName();
    }

    public void endAllProcesses(String reason) {
        System.out.println("[" + botName +  "] A fatal error has occurred with reason: " + reason);
        System.out.println("[" + botName +  "] Bot Terminated");
        System.exit(0);
    }

}
