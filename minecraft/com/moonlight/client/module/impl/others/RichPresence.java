package com.moonlight.client.module.impl.others;

import com.moonlight.client.Client;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.world.InvalidTickEvent;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

@ModuleInfo(name = "Rich Presence", description = "rpc for discord" , category = Category.OTHERS, key = 0, autoEnabled = true)
public class RichPresence extends Module {

    private boolean isStarted;

    @EventLink
    public void onInvalidTick(InvalidTickEvent event) {
        if(!isStarted) {
            DiscordRichPresence.Builder builder = new DiscordRichPresence.Builder(""){
                {
                    this.setDetails(Client.VERSION + " " + Client.BRANCH);
                    this.setBigImage("boykisser", "");
                    this.setStartTimestamps(System.currentTimeMillis());
                }
            };
            DiscordRPC.discordUpdatePresence(builder.build());
            DiscordEventHandlers discordEventHandlers = new DiscordEventHandlers();
            DiscordRPC.discordInitialize("1168254477049004142", discordEventHandlers, true);
            new Thread(() -> {
                while (this.isEnabled()) {
                    DiscordRPC.discordRunCallbacks();
                }
            }, "Callback").start();
            this.isStarted = true;
        }
    }

    @Override
    public void onDisable() {
        DiscordRPC.discordShutdown();
        this.isStarted = false;
    }
}