package com.moonlight.client.module.impl.combat;

import java.util.Comparator;

import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import com.moonlight.client.Client;
import com.moonlight.client.component.impl.RotationComponent;
import com.moonlight.client.component.impl.rotation.MovementCorrection;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.KeyEvent;
import com.moonlight.client.event.impl.client.ForceUseStop;
import com.moonlight.client.event.impl.client.StartSprintEvent;
import com.moonlight.client.event.impl.player.PostAttackEvent;
import com.moonlight.client.event.impl.player.PreAttackEvent;
import com.moonlight.client.event.impl.render.RenderItemEvent;
import com.moonlight.client.event.impl.strafe.MoveInputEvent;
import com.moonlight.client.event.impl.update.PostMotionEvent;
import com.moonlight.client.event.impl.update.PostUpdateEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.update.PreUpdateEvent;
import com.moonlight.client.event.impl.world.TickEvent;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.module.impl.player.Scaffold;
import com.moonlight.client.util.player.MoveUtil;
import com.moonlight.client.util.rotation.RayCastUtil;
import com.moonlight.client.util.rotation.RotationUtil;
import com.moonlight.client.util.timer.TimerHelper;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.value.impl.BooleanValue;
import com.moonlight.client.value.impl.ModeValue;
import com.moonlight.client.value.impl.NumberValue;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;

import de.florianmichael.vialoadingbase.ViaLoadingBase;
import de.florianmichael.viamcp.fixes.AttackOrder;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity.*;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.TextFormatting;

@ModuleInfo(name = "Kill Aura", description = "A goddamn aura, kill aura", key = Keyboard.KEY_R, category = Category.COMBAT)
public class Aura extends Module {

	public EntityLivingBase target;
	private int targetIndex;
	
	private ModeValue attackMode = new ModeValue("Mode", this, "Single", "Switch", "Multi");
	private ModeValue sortMode = new ModeValue("Sort", this, "Distance", "Health");
	private ModeValue block = new ModeValue("Block", this, "None", "Fake", "Vanilla", "Legit");
	
	private ModeValue rotationMode = new ModeValue("Rotation", this, "Silent", "Lock", "None");
	
	private ModeValue movementCorrection = new ModeValue("MoveFix", this, "Off", "Silent", "Strafe");
	
	private NumberValue<Float> range = new NumberValue("Range", 3.8f, 1f, 6f);;
	private NumberValue<Integer> fov = new NumberValue("FOV", 360, 30, 360);;
	
	private NumberValue<Float> minCPS = new NumberValue("Min APS", 12f, 1f, 20f);
	private NumberValue<Float> maxCPS = new NumberValue("Max APS", 14f, 1f, 20f);
	
	private NumberValue<Integer> maxTargets = new NumberValue("Max Targets", 3, 1, 6);
	
	private NumberValue<Integer> switchDelay = new NumberValue("Switch Delay", 120, 0, 1000);
	
	private BooleanValue newHitDelay = new BooleanValue("1.9+ Hit", false);
	private BooleanValue jitter = new BooleanValue("Jittering", true);
	private BooleanValue rayCast = new BooleanValue("Ray Cast", true);
	private BooleanValue noSwing = new BooleanValue("No Swing", false);
	private BooleanValue keepSprint = new BooleanValue("Keep Sprint", false);
	private BooleanValue grimExploit = new BooleanValue("Grim 1.17", false);
	
	private TimerHelper timer = new TimerHelper();
	private TimerHelper switchTimer = new TimerHelper();
	private TimerHelper newHitTimer = new TimerHelper();
	
	private boolean blocking;
	
	private float aps;
	
	private Vector2f rotations;
	
	@EventLink
	public void onTickE(TickEvent event) {
		this.setDisplayName(this.getName() + " " + TextFormatting.GRAY + attackMode.getMode().getName());
	}
	
	@EventLink
	public void onKey(KeyEvent event) {
		
	}
	
	@Override
	public void onEnabled() {
		target = null;
		timer.reset();
		switchTimer.reset();
		newHitTimer.reset();
		aps = -2;
	}
	
	@Override
	public void onDisable() {
		mc.gameSettings.keyBindDrop.setPressed(GameSettings.isKeyDown(mc.gameSettings.keyBindDrop));
	}
	
	@EventLink
	public void onPostMotion(PostMotionEvent event) {
		
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		if(canAttack(target, false)) {
			rotations = RotationUtil.getEntityRotation(target, jitter.getState());
			
			MovementCorrection correction = MovementCorrection.OFF;
			
			if(movementCorrection.getMode().getName().equalsIgnoreCase("Silent")) {
				correction = MovementCorrection.SILENT;
			}else if(movementCorrection.getMode().getName().equalsIgnoreCase("Strafe")) {
				correction = MovementCorrection.STRAFE;
			}
					
			switch (rotationMode.getMode().getName()) {
				case "Lock":
					mc.thePlayer.rotationYaw = rotations.x;
					mc.thePlayer.rotationPitch = rotations.y;
					break;
			}
		}
	}
	
	@EventLink
	public void onPreUpdate(PreUpdateEvent event) {
		if(minCPS.getValue() > maxCPS.getValue()) minCPS.setValue(maxCPS.getValue());
	}
	
	@EventLink
	public void onUpdate(PreUpdateEvent event) {
		if(canAttack(target, false)) {
			rotations = RotationUtil.getEntityRotation(target, jitter.getState());
			
			MovementCorrection correction = MovementCorrection.OFF;
			
			if(movementCorrection.getMode().getName().equalsIgnoreCase("Silent")) {
				correction = MovementCorrection.SILENT;
			}else if(movementCorrection.getMode().getName().equalsIgnoreCase("Strafe")) {
				correction = MovementCorrection.STRAFE;
			}
					
			switch (rotationMode.getMode().getName()) {
				case "Silent":
					RotationComponent.setRotations(rotations, 80, correction);
					break;
				
				case "Lock":
					Vector2f fixed = RotationUtil.applySensitivityPatch(rotations, new Vector2f(mc.thePlayer.lastReportedYaw, mc.thePlayer.lastReportedPitch));
					mc.thePlayer.rotationYaw = fixed.x;
					mc.thePlayer.rotationPitch = fixed.y;
					break;
			}
		}
		
		Object[] targets = sortMode.getMode().getName().equalsIgnoreCase("Distance") ? getSortedDistance() : getSortedHealth();		
		
		if(block.getMode().getName().equalsIgnoreCase("Vanilla")) {
			if(!canAttack(target, false) && (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) {
				mc.gameSettings.keyBindDrop.setPressed(false);
			}else {
				if(canAttack(target, false) && (mc.thePlayer.getHeldItem() != null
						&& (mc.thePlayer.getHeldItem().getItem() instanceof ItemSword))) {
					mc.gameSettings.keyBindDrop.setPressed(true);
				}
			}
			
			if(!canAttack(target, false)) {
				mc.gameSettings.keyBindDrop.setPressed(GameSettings.isKeyDown(mc.gameSettings.keyBindDrop));
			}
		}
		
		if(aps == -2) {
			aps = RandomUtils.nextFloat(minCPS.getValue(), maxCPS.getValue());
		}
		
		if(targets.length >= 1 && timer.hasTimeElapsed(1000 / aps)) {
			switch (attackMode.getMode().getName()) {
				case "Single":
					target = (EntityLivingBase) targets[0];
					attack();
					break;
			
				case "Switch":
					if(switchTimer.hasTimeElapsed(switchDelay.getValue())) {
						targetIndex++;
						switchTimer.reset();
					}
        		
					if(targets.length - 1 < targetIndex) {
						targetIndex = 0;
					}

					target = (EntityLivingBase) targets[targetIndex];

					attack();
					break;
                
				case "Multi":
					for(Object o : targets) {
						target = (EntityLivingBase) o;
	                
						attack();
					}
				
					break;
			}
		
			timer.reset();
		
			aps = RandomUtils.nextFloat(minCPS.getValue(), maxCPS.getValue());
		}
		
		if(!rotationMode.getName().equalsIgnoreCase("None") && movementCorrection.getMode().getName().equalsIgnoreCase("Silent")) {
            float diff = MathHelper.wrapAngleTo180_float(MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYawHead) - MathHelper.wrapAngleTo180_float(MoveUtil.getPlayerDirection())) + 22.5F;

            if (diff < 0) {
                diff = 360 + diff;
            }

            int a = (int) (diff / 45.0);

            float value = mc.thePlayer.moveForward != 0 ? Math.abs(mc.thePlayer.moveForward) : Math.abs(mc.thePlayer.moveStrafing);

            float forward = value;
            float strafe = 0;

            for (int i = 0; i < 8 - a; i++) {
                float dirs[] = MoveUtil.incrementMoveDirection(forward, strafe);

                forward = dirs[0];
                strafe = dirs[1];
            }

            if(forward < 0.8F) {
                mc.gameSettings.keyBindInventory.setPressed(false);;
                mc.thePlayer.setSprinting(false);
            }
        }
	}
	
	@EventLink
	public void onRenderItem(RenderItemEvent event) {
		//Whatever, shit gonna get handle serverside anyway
		if(!block.getMode().getName().equalsIgnoreCase("None")
				&& mc.thePlayer.getHeldItem() != null
				&& mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && canAttack(target, false)) {
			event.setAction(EnumAction.BLOCK);
		}
	}
	
	private Object[] getSortedDistance() {
		return mc.theWorld.loadedEntityList.stream().filter(entity -> entity instanceof EntityPlayer && (((EntityPlayer) entity).deathTime == 0 && !entity.isDead) && entity != mc.thePlayer).sorted(Comparator.comparingDouble(entity -> entity.getDistanceToEntity(mc.thePlayer))).toArray();		
	}
	
	private Object[] getSortedHealth() {
		return mc.theWorld.loadedEntityList.stream().filter(entity -> entity instanceof EntityPlayer && (((EntityPlayer) entity).deathTime == 0 && !entity.isDead) && entity != mc.thePlayer).sorted(Comparator.comparingDouble(entity -> ((EntityLivingBase) entity).getHealth())).toArray();		
	}
	
	public boolean canAttack(EntityLivingBase e, boolean checkCast) {
        float range = this.range.getValue();
        if(e == null) return false;
        if(e == mc.thePlayer) return false;
        if(e.getDistanceToEntity(mc.thePlayer) > range) return false;
        if(e.getHealth() <= 0) return false;
        if(Client.INSTANCE.bots.contains(e.getEntityId())) return false;
        if(e.isDead) return false;
        if(e.isInvisible()) return false;
        if(!RayCastUtil.fov(e, fov.getValue())) return false;
        if(Client.INSTANCE.moduleManager.get(Scaffold.class).isEnabled()) return false;
        if(checkCast && rayCast.getState() && RayCastUtil.rayCastEntity(mc.thePlayer, range, mc.thePlayer.rotationYawHead, mc.thePlayer.rotationPitchHead) != e) return false;
        return true;
    }
	
	private void attack() { 
		if(!canAttack(target, rayCast.getState())) return;
				
		double speed = 4.0, delay = -1.0;
		
		if (mc.thePlayer.getHeldItem() != null) {
			Item item = mc.thePlayer.getHeldItem().getItem();
			if (item instanceof ItemSword) {
				speed = 1.6D;
			} else if (item instanceof ItemSpade) {
	            speed = 1.0D;
	        } else if (item instanceof ItemPickaxe) {
	            speed = 1.2D;
	        } else if (item instanceof ItemAxe) {
	        	switch (((ItemAxe)item).getToolMaterial()) {
	        		case STONE:
	        			speed = 0.8D;
	        			break;
	        		case IRON:
	        			speed = 0.9D;
	        			break;
	        		default:
	        			speed = 1.0D;
	        			break;
	        	}
	        }
		}
		delay = 1.0D / speed * 20.0D - 1.0D;
		
		if(newHitDelay.getState() && !newHitTimer.hasTimeElapsed((long) (delay * 50))) return;
		
		if(block.getMode().getName().equalsIgnoreCase("Legit")) {
			if(mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
				mc.gameSettings.keyBindDrop.setPressed(false);
			}else {
				mc.gameSettings.keyBindDrop.setPressed(GameSettings.isKeyDown(mc.gameSettings.keyBindDrop));
			}
		}
		
		if(grimExploit.getState()) {
			rotations = RotationUtil.getEntityRotation(target, jitter.getState());
			PacketUtil.sendNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ,
    				rotations.x, rotations.y, mc.thePlayer.onGround));
		}
		
		if (ViaLoadingBase.getInstance().getTargetVersion().isOlderThanOrEqualTo(ProtocolVersion.v1_8)) {
			if(!this.noSwing.getState()) mc.thePlayer.swingItem();
	        else PacketUtil.send(new C0APacketAnimation());
        }

        if(keepSprint.getState()) {
    		new PreAttackEvent(target).fire();
        	
            mc.playerController.syncCurrentPlayItem();
            PacketUtil.send(new C02PacketUseEntity(target, Action.ATTACK));
            
        	new PostAttackEvent(target).fire();
        }else {
        	mc.playerController.attackEntity(mc.thePlayer, target); 
        }

        if (mc.thePlayer.fallDistance > 0) mc.thePlayer.onCriticalHit(target);
            	
        if (!ViaLoadingBase.getInstance().getTargetVersion().isOlderThanOrEqualTo(ProtocolVersion.v1_8)) {
			if(!this.noSwing.getState()) mc.thePlayer.swingItem();
	        else PacketUtil.send(new C0APacketAnimation());
        }
        
    	if(block.getMode().getName().equalsIgnoreCase("Legit")) {
			if(mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
				mc.gameSettings.keyBindDrop.setPressed(true);
			}else {
				mc.gameSettings.keyBindDrop.setPressed(GameSettings.isKeyDown(mc.gameSettings.keyBindDrop));
			}
    	}
    	
    	newHitTimer.reset();
	}
	
	@EventLink
	public void onPreAttack(PreAttackEvent event) {
	}
	
	@EventLink
	public void onPostAttack(PostAttackEvent event) {
		
	}
	
	@EventLink
	public void onForceUse(ForceUseStop event) {
		if(mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword
				&& canAttack(target, false)) {
			if(block.getMode().getName().equalsIgnoreCase("None") || block.getMode().getName().equalsIgnoreCase("Fake")) {
				event.setCancelled(true);
			}
		}
	}
	
	
}
