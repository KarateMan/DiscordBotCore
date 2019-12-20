package com.karateman2400.discordbotcore.internal.modules;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * All modules should extend Module.
 */
public abstract class Module extends ListenerAdapter {

    /**
     * Runs prior to Configs or JDA being initialized.
     * @return Success or Failure.
     */
    public abstract boolean preInitialization();

    /**
     * Initializes the Configs for the Module.
     * @return Success or Failure.
     */
    public abstract boolean initializeConfiguration();

    /**
     * Runs prior to JDA being initialized, after Configs.
     * @return Success or Failure.
     */
    public abstract boolean initialization();

    /**
     * Runs after JDA and Configs initialize.
     * @return Success or Failure.
     */
    public abstract boolean postInitialization();

}
