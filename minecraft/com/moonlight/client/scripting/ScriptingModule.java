package com.moonlight.client.scripting;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.player.SlowDownEvent;
import com.moonlight.client.event.impl.render.Render2DEvent;
import com.moonlight.client.event.impl.update.PostMotionEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.world.TickEvent;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.scripting.bindings.MinecraftBindingsExtends;
import com.moonlight.client.scripting.bindings.PacketBindingsExtends;
import com.moonlight.client.scripting.bindings.PlayerBindingsExtends;
import com.moonlight.client.scripting.bindings.RenderBindingsExtends;
import com.moonlight.client.scripting.bindings.WorldBindingsExtends;
import com.moonlight.client.scripting.objects.packet.client.*;
import com.moonlight.client.scripting.objects.packet.server.*;
import com.moonlight.client.scripting.utils.BlockPos;
import com.moonlight.client.value.Mode;
import com.moonlight.client.value.Value;
import com.moonlight.client.value.impl.BooleanValue;
import com.moonlight.client.value.impl.ModeValue;
import com.moonlight.client.value.impl.NumberValue;

import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;

public class ScriptingModule extends Module {

	private final com.moonlight.client.scripting.module.Module scriptingModule;
	private ArrayList<com.moonlight.client.scripting.module.value.Value> scriptingList = new ArrayList();
	
	public ScriptingModule(com.moonlight.client.scripting.module.Module scriptingModule) {
    	super(scriptingModule.getName(), scriptingModule.getDescription(), getActualCategory(scriptingModule.getCategory()), scriptingModule.getKey(), false);
    	
    	this.scriptingModule = scriptingModule;
    	
    	this.scriptingModule.mc = new MinecraftBindingsExtends();
    	this.scriptingModule.player = new PlayerBindingsExtends();
    	this.scriptingModule.sendQueue = new PacketBindingsExtends();
    	this.scriptingModule.world = new WorldBindingsExtends();
    	this.scriptingModule.render = new RenderBindingsExtends();
    	
    	values.addAll(getValuesScript());
	}
	
	public void updateValue() {
		for(com.moonlight.client.scripting.module.value.Value v : scriptingList) {
			for(Value v1 : values) {
				if(v.getName().equals(v1.getName())) {
					if(v.getClass() == com.moonlight.client.scripting.module.value.impl.BooleanValue.class) {
						((com.moonlight.client.scripting.module.value.impl.BooleanValue)v).setState(((BooleanValue)v1).getState());
					}
					
					if(v.getClass() == com.moonlight.client.scripting.module.value.impl.NumberValue.class) {
						((com.moonlight.client.scripting.module.value.impl.NumberValue)v).setValue(((NumberValue)v1).getValue());
					}
					
					if(v.getClass() == com.moonlight.client.scripting.module.value.impl.ModeValue.class) {
						((com.moonlight.client.scripting.module.value.impl.ModeValue)v).setMode(((ModeValue)v1).getModes().indexOf(((ModeValue)v1).getMode()));
					}
					
					break;
				}
			}
		}
	}
	
	public List<Value> getValuesScript() {
        final CopyOnWriteArrayList<Value> values = new CopyOnWriteArrayList<>();

        for (final Field field : scriptingModule.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);

                final Object o = field.get(scriptingModule);

                if (o instanceof com.moonlight.client.scripting.module.value.Value) {
                    scriptingList.add((com.moonlight.client.scripting.module.value.Value) o);
                	if(o instanceof com.moonlight.client.scripting.module.value.impl.BooleanValue) {
                		values.add(new BooleanValue(((com.moonlight.client.scripting.module.value.impl.BooleanValue) o)
                				.getName(), ((com.moonlight.client.scripting.module.value.impl.BooleanValue) o).getState()));
                	}
                	
                	if(o instanceof com.moonlight.client.scripting.module.value.impl.NumberValue) {
                		com.moonlight.client.scripting.module.value.impl.NumberValue v = (com.moonlight.client.scripting.module.value.impl.NumberValue)o;
                		values.add(new NumberValue(v.getName(), v.getValue(), v.getMin(), v.getMax()));
                	}
                	
                	if(o instanceof com.moonlight.client.scripting.module.value.impl.ModeValue) {
                		com.moonlight.client.scripting.module.value.impl.ModeValue v = (com.moonlight.client.scripting.module.value.impl.ModeValue)o;
                		ModeValue mv = new ModeValue(v.getName());
                		
                		for(String m : v.getModes()) {
                			Mode mode = new Mode(this, m);
                			mv.getModes().add(mode);
                		}
        				mv.setMode(0);
                		
                		values.add(mv);
                	}
                	
                }
            } catch (IllegalAccessException e) {
                //Do nothing
            }
        }
        
        return values;
    }
	
	@Override
	public void onEnabled() {
		if(scriptingModule == null) return;
		new ProtectedRunnable() {
			
			@Override
			public void protectedRun() {
				scriptingModule.onEnabled();
			}
		}.run();
	}
	
	@Override
	public void onDisable() {
		if(scriptingModule == null) return;
		new ProtectedRunnable() {
			
			@Override
			public void protectedRun() {
				scriptingModule.onDisabled();
			}
		}.run();
	}
	
	@EventLink
	public void onTick(TickEvent event) {
		updateValue();
	}
	
	@EventLink
	public void onSlowDown(SlowDownEvent event) {
		com.moonlight.client.scripting.event.impl.SlowDownEvent slowDownEvent = new com.moonlight.client.scripting.event.impl.SlowDownEvent(event.getForward(), event.getStrafe());
		
		new ProtectedRunnable() {
			@Override
			public void protectedRun() {
				scriptingModule.onSlowDown(slowDownEvent);
			}
		}.run();
		
		event.setForward(slowDownEvent.getForward());
		event.setStrafe(slowDownEvent.getStrafe());
		
		event.setCancelled(slowDownEvent.isCancelled());
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		com.moonlight.client.scripting.event.impl.PreMotionEvent motionEvent = new com.moonlight.client.scripting.event.impl.PreMotionEvent(
				event.getX(), event.getY(), event.getZ(), event.getPitch(), event.getYaw(), event.isOnGround());
		
		new ProtectedRunnable() {
			@Override
			public void protectedRun() {
				scriptingModule.onPreMotion(motionEvent);
			}
		}.run();
		
		event.setCancelled(motionEvent.isCancelled());
		
		event.setX(motionEvent.getX());
		event.setY(motionEvent.getY());
		event.setZ(motionEvent.getZ());
		
		event.setYaw(motionEvent.getYaw());
		event.setPitch(motionEvent.getPitch());
		
		event.setOnGround(motionEvent.isOnGround());
	}
	
	@EventLink
	public void onPostMotion(PostMotionEvent event) {
		new ProtectedRunnable() {
			@Override
			public void protectedRun() {
				scriptingModule.onPostMotion(new com.moonlight.client.scripting.event.impl.PostMotionEvent());
			}
		}.run();
	}
	
	@EventLink
	public void onPacketReceive(PacketReceiveEvent event) {
		com.moonlight.client.scripting.objects.packet.Packet portedPacket = null;
		
		if(event.getPacket() instanceof S02PacketChat) {
			S02PacketChat wrapper = (S02PacketChat) event.getPacket();
			
			portedPacket = new ChatPacket(wrapper.getChatComponent().getUnformattedText());
		}
		
		if(event.getPacket() instanceof S12PacketEntityVelocity) {
			S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) event.getPacket();
			
			portedPacket = new VelocityPacket(wrapper.getEntityID(), wrapper.getMotionX(), wrapper.getMotionY(), wrapper.getMotionZ());
		}
		
		if(event.getPacket() instanceof S08PacketPlayerPosLook) {
			S08PacketPlayerPosLook wrapper = (S08PacketPlayerPosLook) event.getPacket();
			
			portedPacket = new TeleportPacket(wrapper.getX(), wrapper.getY(), wrapper.getZ(), wrapper.getYaw(), wrapper.getPitch());
		}
		
		if(portedPacket != null) {
			com.moonlight.client.scripting.event.impl.PacketReceiveEvent packetEvent = new com.moonlight.client.scripting.event.impl.PacketReceiveEvent(portedPacket);
			new ProtectedRunnable() {
				@Override
				public void protectedRun() {
					scriptingModule.onPacketReceive(packetEvent);
				}
			}.run();
			
			if(packetEvent.isCancelled()) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventLink
	public void onRender2D(Render2DEvent event) {
		com.moonlight.client.scripting.event.impl.Render2DEvent renderEvent = new com.moonlight.client.scripting.event.impl.Render2DEvent();
		new ProtectedRunnable() {
			@Override
			public void protectedRun() {
				scriptingModule.onRender2D(renderEvent);
			}
		}.run();
	}
	
	@EventLink
	public void onPacketSend(PacketSendEvent event) {
		com.moonlight.client.scripting.objects.packet.Packet portedPacket = null;
		
		if(event.getPacket() instanceof C03PacketPlayer) {
			if(event.getPacket() instanceof C03PacketPlayer.C04PacketPlayerPosition) {
				C03PacketPlayer.C04PacketPlayerPosition wrapper = (C04PacketPlayerPosition) event.getPacket();
				portedPacket = new PlayerPacket.PlayerMovePacket(wrapper.getPositionX(), wrapper.getPositionY(), wrapper.getPositionZ(), wrapper.isOnGround());
			}else if(event.getPacket() instanceof C03PacketPlayer.C05PacketPlayerLook) {
				C03PacketPlayer.C05PacketPlayerLook wrapper = (C03PacketPlayer.C05PacketPlayerLook) event.getPacket();
				
				portedPacket = new PlayerPacket.PlayerLookPacket(wrapper.yaw, wrapper.pitch, wrapper.isOnGround());
			}else if(event.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook) {
				C03PacketPlayer.C06PacketPlayerPosLook wrapper = (C03PacketPlayer.C06PacketPlayerPosLook) event.getPacket();
				
				portedPacket = new PlayerPacket.PlayerMoveAndLookPacket(wrapper.getPositionX()
						, wrapper.getPositionY(), wrapper.getPositionZ(), wrapper.yaw, wrapper.pitch, wrapper.isOnGround());
			}else {
				portedPacket = new PlayerPacket();
			}
		}
		
		if(event.getPacket() instanceof C00PacketKeepAlive) {
			C00PacketKeepAlive wrapper = (C00PacketKeepAlive) event.getPacket();
			portedPacket = new KeepAlivePacket(wrapper.getKey());
		}
		
		if(event.getPacket() instanceof C07PacketPlayerDigging) {
			C07PacketPlayerDigging wrapper = (C07PacketPlayerDigging) event.getPacket();

			portedPacket = new DiggingPacket(convertDigging(wrapper.getStatus()), 
					new BlockPos(wrapper.getPosition().getX(), wrapper.getPosition().getY(), wrapper.getPosition().getZ()), convertFacing(wrapper.getFacing()));
			
			if(wrapper.getStatus() == C07PacketPlayerDigging.Action.SWAP_HELD_ITEMS) portedPacket = null;
		}
		
		if(event.getPacket() instanceof C0APacketAnimation) {
			portedPacket = new SwingPacket();
		}
		
		if(event.getPacket() instanceof C02PacketUseEntity) {
			C02PacketUseEntity wrapper = (C02PacketUseEntity) event.getPacket();
			
			portedPacket = new InteractPacket(wrapper.entityId, convertInteract(wrapper.getAction()));
		}
		
		if(portedPacket != null) {
			com.moonlight.client.scripting.event.impl.PacketSendEvent packetEvent = new com.moonlight.client.scripting.event.impl.PacketSendEvent(portedPacket);
			new ProtectedRunnable() {
				@Override
				public void protectedRun() {
					scriptingModule.onPacketSend(packetEvent);
				}
			}.run();
			
			if(packetEvent.isCancelled()) {
				event.setCancelled(true);
			}
		}
	}
	
	private com.moonlight.client.scripting.objects.packet.client.DiggingPacket.Action convertDigging(C07PacketPlayerDigging.Action action) {
		for(com.moonlight.client.scripting.objects.packet.client.DiggingPacket.Action a : com.moonlight.client.scripting.objects.packet.client.DiggingPacket.Action.values()) {
			if(a.name().equalsIgnoreCase(action.name())) {
				return a;
			}
		}
		
		return null;
	}
	
	private com.moonlight.client.scripting.utils.EnumFacing convertFacing(EnumFacing action) {
		for(com.moonlight.client.scripting.utils.EnumFacing a : com.moonlight.client.scripting.utils.EnumFacing.values()) {
			if(a.name().equalsIgnoreCase(action.name())) {
				return a;
			}
		}
		
		return null;
	}
	
	private com.moonlight.client.scripting.objects.packet.client.InteractPacket.Action convertInteract(C02PacketUseEntity.Action action) {
		for(com.moonlight.client.scripting.objects.packet.client.InteractPacket.Action a : com.moonlight.client.scripting.objects.packet.client.InteractPacket.Action.values()) {
			if(a.name().equalsIgnoreCase(action.name())) {
				return a;
			}
		}
		
		return null;
	}
	
	private static Category getActualCategory(com.moonlight.client.scripting.module.Module.Category category) {
		Category actualCategory = Category.COMBAT;
		if(category == com.moonlight.client.scripting.module.Module.Category.EXPLOIT) actualCategory = Category.EXPLOIT;
		if(category == com.moonlight.client.scripting.module.Module.Category.MOVEMENT) actualCategory = Category.MOVEMENT;
		if(category == com.moonlight.client.scripting.module.Module.Category.PLAYER) actualCategory = Category.PLAYER;
		if(category == com.moonlight.client.scripting.module.Module.Category.OTHERS) actualCategory = Category.OTHERS;
		if(category == com.moonlight.client.scripting.module.Module.Category.VISUALS) actualCategory = Category.VISUALS;
		
		return actualCategory;
	}
	
}
