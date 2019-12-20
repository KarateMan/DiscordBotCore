package com.karateman2400.discordbotcore.modules.example;

import com.karateman2400.discordbotcore.internal.modules.Module;
import com.karateman2400.discordbotcore.internal.modules.ModuleClass;

@ModuleClass
public class ExampleModule extends Module {

    @Override
    public boolean preInitialization() {
        return true;
    }

    @Override
    public boolean initializeConfiguration() {
        return true;
    }

    @Override
    public boolean initialization() {
        return true;
    }

    @Override
    public boolean postInitialization() {
        return true;
    }
}
