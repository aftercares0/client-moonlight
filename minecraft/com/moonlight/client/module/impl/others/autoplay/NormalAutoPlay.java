package com.moonlight.client.module.impl.others.autoplay;

import java.util.Comparator;

import com.moonlight.client.Client;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.world.WorldChangeEvent;
import com.moonlight.client.event.impl.world.packet.PacketReceiveEvent;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.impl.combat.Aura;
import com.moonlight.client.module.impl.player.AutoTool;
import com.moonlight.client.value.Mode;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.BlockPressurePlateWeighted;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;

public class NormalAutoPlay extends Mode {

	private boolean needGap, canStart, running, sended, canAutoTool;
	
	private EntityPlayer target, lastTarget;
	
	public NormalAutoPlay(Module parent, String name) {
		super(parent, name);
	}

	@Override
	public void onDisabled() {
		if(mc.thePlayer == null) return;
		reset();
	}
	
	@EventLink
	public void onPreMotion(PreMotionEvent event) {
		canStart = true;
		if(canStart) {
			if(getSortedDistance().length < 1) {
				if(running) {
					if(mc.thePlayer != null) mc.thePlayer.sendChatMessage("#stop");
					running = false;
				}
				return;
			}
			target = (EntityPlayer) getSortedDistance()[0];
			
			
			if(lastTarget != target) {
				mc.thePlayer.sendChatMessage("#stop");
				mc.thePlayer.sendChatMessage("#follow " + target.getName());
				
				lastTarget = target;
			}
			
			
	    	Aura aura = Client.INSTANCE.moduleManager.get(Aura.class);
	    	AutoTool autotool = Client.INSTANCE.moduleManager.get(AutoTool.class);

			if(!aura.isEnabled()) {
				aura.setEnabled(true);
			}
	    	
	    	if(!needGap) {
	    		if(!autotool.isEnabled()) {
	    			autotool.setEnabled(true);
	    		}
	    	}
	    	
//	    	if(mc.thePlayer.getHealth() < 10 && hasGapple()) {
//	    		needGap = true;
//		    	Client.INSTANCE.moduleManager.get(AutoTool.class).setEnabled(false);
//	    	}
//
//	    	if(needGap && mc.thePlayer.getHealth() < 10) {
//	    		if(mc.thePlayer.getHeldItem() == null || !(mc.thePlayer.getHeldItem().getItem() instanceof ItemAppleGold)) switchGapple();
//
//	    		mc.gameSettings.keyBindDrop.setPressed(true);
//	    	}
//
//	    	if(needGap && mc.thePlayer.getHealth() >= 12) {
//	    		mc.gameSettings.keyBindDrop.setPressed(false);
//	    		needGap = false;
//	    	}
		}
	}
	
	private BlockPos scanForPlateBoost() {
		for(int x = (int) (mc.thePlayer.posX - 60); x < (int) (mc.thePlayer.posX + 60); x++) {
			for(int y = (int) (mc.thePlayer.posY - 10); y < (int) (mc.thePlayer.posX + 10); y++) {
				for(int z = (int) (mc.thePlayer.posZ - 60); z < (int) (mc.thePlayer.posZ + 60); z++) {
					Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
					if(block.getClass() == BlockPressurePlate.class ||
							block.getClass() == BlockPressurePlateWeighted.class) {
						return new BlockPos(x, y, z);
					}
				}
			}
		}
		
		return new BlockPos(mc.thePlayer);
	}
	
	@EventLink
    public void onWorldChange(WorldChangeEvent event) {
		reset();
    }
	
	public void onPacketReceive(PacketReceiveEvent event) {
		if(event.getPacket() instanceof S08PacketPlayerPosLook) {
			reset();
		}
	}
	
	private void reset() {
		if(mc.thePlayer != null) mc.thePlayer.sendChatMessage("#stop");
    	target = null;
    	lastTarget = null;
    	canStart = false;
    	needGap = false;
    	running = false;
    	sended = false;
    	
    	Client.INSTANCE.moduleManager.get(Aura.class).setEnabled(false);
    	Client.INSTANCE.moduleManager.get(AutoTool.class).setEnabled(false);
    	
		mc.gameSettings.keyBindDrop.setPressed(false);
	}
	
	private Object[] getSortedDistance() {
		return mc.theWorld.loadedEntityList.stream().filter(
				entity -> entity instanceof EntityPlayer && 
				(((EntityPlayer) entity).deathTime == 0 && !entity.isDead)
				&& entity != mc.thePlayer && entity.fallDistance < 3  && canAttack((EntityLivingBase) entity)).sorted(Comparator.comparingDouble(entity -> entity.getDistanceToEntity(mc.thePlayer))).toArray();		
	}
    
    public boolean canAttack(EntityLivingBase e) {
        if(e == null) return false;
        if(e == mc.thePlayer) return false;
        if(e.getHealth() <= 0) return false;
        if(e.isDead) return false;
        if(e.isInvisible()) return false;
        if(!e.canAttackWithItem()) return false;
        return true;
    }
    
    private boolean hasGapple() {
        for (int i = 0; i < 9; i++) {
            ItemStack is = mc.thePlayer.inventory.mainInventory[i];
            if (is != null && is.getItem() instanceof ItemAppleGold) {
                return true;
            }
        }
        
        return false;
    }
    
    private void switchGapple() {
        for (int i = 0; i < 9; i++) {
            ItemStack is = mc.thePlayer.inventory.mainInventory[i];
            if (is != null && is.getItem() instanceof ItemAppleGold) {
            	mc.thePlayer.inventory.currentItem = i;
                break;
            }
        }
    }
	
}
