package com.karateman2400.discordbotcore.internal.modules;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModuleClass {
    boolean needsConfig() default true;
    boolean eventSubscriber() default true;
}
