package com.karateman2400.discordbotcore.internal.threads;

public class ThreadManager {

    public static volatile boolean endAll = false;

    public ThreadManager() {

    }

    public int threadCount() {
        return Thread.activeCount();
    }

    public void endAllProcesses(String reason, boolean force) {
        System.out.println("[BotCore] A fatal error has occurred with reason: " + reason);
        System.out.println("[BotCore] Terminating Processes...");
        if(force) System.exit(0);
        endAll = true;
        System.out.println(threadCount());
        while(threadCount() != 1) continue;
        System.out.println("[BotCore] Bot Terminated");
        System.exit(0);
    }

}
