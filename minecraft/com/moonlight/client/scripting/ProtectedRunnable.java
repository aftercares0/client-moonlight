package com.moonlight.client.scripting;

import java.security.AccessController;
import java.security.PrivilegedAction;

public abstract class ProtectedRunnable implements Runnable {

	public abstract void protectedRun();

	@Override
	public final void run() {
		//Idk, let's do this later
		protectedRun();
	}
	
}
