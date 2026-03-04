package com.moonlight.client.command;

import java.util.ArrayList;
import java.util.List;

import com.moonlight.client.command.impl.*;

public class CommandManager extends ArrayList<Command> {
	
	public CommandManager() {
		add(new BindCommand());
		add(new FakeDamageCommand());
	}
	
	public void handle(String message) {
		for(Command command : this) {
			String args[] = message.replace(".", "").split(" ");
			if(command.name.equalsIgnoreCase(args[0])) {
				String newArgs[] = message.replace(args[0], "").split(" ");
				command.onCommand(newArgs);
				break;
			}
		}
	}
	
	public List<Command> getAll() {
        return this;
    }
	
	public <T extends Command> T get(final String name) {
        return (T) this.stream()
                .filter(module -> module.name.equalsIgnoreCase(name))
                .findAny().orElse(null);
    }

    public <T extends Command> T get(final Class<?> clazz) {
        return (T) this.stream()
                .filter(module -> module.getClass() == clazz)
                .findAny().orElse(null);
    }

}
