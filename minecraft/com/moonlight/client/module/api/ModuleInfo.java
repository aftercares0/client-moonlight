package com.moonlight.client.module.api;

import org.lwjgl.input.Keyboard;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {

    String name();
	String description();
    Category category();
    boolean autoEnabled() default false;
    int key() default Keyboard.KEY_NONE;
    boolean isDev() default false;

}