package com.karateman2400.discordbotcore.internal.threads;

public class ThreadManager {

    public static volatile boolean endAll = false;

    public ThreadManager() {

    }

    public void endAllProcesses(String reason) {
        System.out.println("[BotCore] A fatal error has occurred with reason: " + reason);
        System.out.println("[BotCore] Bot Terminated");
        System.exit(0);
    }

}
