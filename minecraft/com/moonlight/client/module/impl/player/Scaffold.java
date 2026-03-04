package com.moonlight.client.module.impl.player;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.util.vector.Vector2f;

import com.ibm.icu.text.Normalizer.Mode;
import com.moonlight.client.baritone.api.event.events.TickEvent;
import com.moonlight.client.component.impl.RotationComponent;
import com.moonlight.client.component.impl.rotation.MovementCorrection;
import com.moonlight.client.event.annotations.EventLink;
import com.moonlight.client.event.impl.strafe.JumpEvent;
import com.moonlight.client.event.impl.strafe.MoveInputEvent;
import com.moonlight.client.event.impl.update.PostMotionEvent;
import com.moonlight.client.event.impl.update.PreMotionEvent;
import com.moonlight.client.event.impl.update.PreUpdateEvent;
import com.moonlight.client.event.impl.world.PostBlockPlace;
import com.moonlight.client.event.impl.world.packet.PacketSendEvent;
import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.module.impl.movement.speed.*;
import com.moonlight.client.module.impl.player.scaffold.*;
import com.moonlight.client.module.impl.player.scaffold.sprint.*;
import com.moonlight.client.util.client.ChatUtil;
import com.moonlight.client.util.player.MoveUtil;
import com.moonlight.client.util.rotation.RayCastUtil;
import com.moonlight.client.util.rotation.RotationUtil;
import com.moonlight.client.util.timer.TimerHelper;
import com.moonlight.client.util.world.PacketUtil;
import com.moonlight.client.util.world.ScaffoldUtil;
import com.moonlight.client.util.world.ScaffoldUtil.BlockData;
import com.moonlight.client.value.impl.BooleanValue;
import com.moonlight.client.value.impl.ModeValue;
import com.moonlight.client.value.impl.NumberValue;

import net.minecraft.block.BlockAir;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.TextFormatting;
import net.minecraft.util.Vec3;

@ModuleInfo(name = "Scaffold", description = "block fly?!?@?!@?@", category = Category.PLAYER)
public class Scaffold extends Module {
	
	public ModeValue mode = new ModeValue("Mode", this, "Normal", "Telly");
	public ModeValue pick = new ModeValue("Pick", this, "Always", "Switch",
			"Spoof");
	public ModeValue sprint = new ModeValue("Sprint", this, new NoneSprint(this, "None") , new NormalSprint(this, "Normal"), new SpoofSprint(this, "Spoof"), new
			LegitSprint(this, "Legit"), new PacketSprint(this, "Packet"));
    public ModeValue rotation = new ModeValue("Rotation", this, "Normal", "Stable", "Simple", "Watchdog", "Bruteforce", "None");
    public ModeValue eagle = new ModeValue("Eagle", this, "None", "Sneak", "Silent");
    public NumberValue<Integer> delay = new NumberValue("Delay", 0, 0, 1000);
    public NumberValue<Integer> rotationSpeed = new NumberValue("Rotation Speed", 10, 10, 10);
    public BooleanValue moveFix = new BooleanValue("Movement Fix", false);
    public BooleanValue keepRot = new BooleanValue("Keep Rotation", true);
    public BooleanValue keepY = new BooleanValue("Keep Y", false);
    public BooleanValue strafe = new BooleanValue("Strafe", false);
    public BooleanValue swing = new BooleanValue("Swing", false);
    public NumberValue<Float> strafeSpeed = new NumberValue("Strafe Speed", 0.03f, 0.01f, 1f);
    public BooleanValue rayCast = new BooleanValue("Ray Cast", true);
    public BooleanValue smartSneak = new BooleanValue("Smart Sneak", true);
	
    public BooleanValue grimExploit = new BooleanValue("Grim 1.17", false);
    
    public TimerHelper timer = new TimerHelper();
    public ScaffoldUtil.BlockData blockData = null;
    public int slotId;
	public Vector2f rotations;
    public EnumFacing lastFacing;
    public boolean hasBlock;
    public int startY;
    
    public void onEnabled() {
        startY = -1;
        if(mc.thePlayer == null) return;
        startY = (int) (mc.thePlayer.posY - 1);

        hasBlock = false;
        slotId = mc.thePlayer.inventory.currentItem;

        lastFacing = EnumFacing.DOWN;
    }
    
    @EventLink
    public void onPacketSend(PacketSendEvent event) {
    	if(event.getPacket() instanceof C03PacketPlayer && grimExploit.getState()) {
//    		C03PacketPlayer wrapper = (C03PacketPlayer) event.getPacket();
//    		event.setPacket(new C03PacketPlayer.C06PacketPlayerPosLook(wrapper.getPositionX(), wrapper.getPositionY(), wrapper.getPositionZ(),
//    				wrapper.getYaw(), wrapper.getPitch(), wrapper.isOnGround()));
    	}
    	    	
    	if(pick.getMode().getName().equalsIgnoreCase("Spoof")) {
    		if(event.getPacket() instanceof C09PacketHeldItemChange) {
    			C09PacketHeldItemChange packet = (C09PacketHeldItemChange) event.getPacket();
                
                if(slotId == packet.getSlotId() || mc.thePlayer.inventory.getStackInSlot(packet.getSlotId()) == null || !(mc.thePlayer.inventory.getStackInSlot(packet.getSlotId()).getItem() instanceof ItemBlock)) {
                    event.setCancelled(true);
                }
                
                if(!event.isCancelled()) {
                	slotId = packet.getSlotId();
                }
            }
    	}
    }
    
    @EventLink
    public void onJump(JumpEvent event) {
        if(rotations != null) {
            if(moveFix.getState()) event.setYaw(rotations.x);
        }
    }
    
	@EventLink
    public void onPreUpdate(PreUpdateEvent event) {
		this.setDisplayName(this.getName() + " " + TextFormatting.GRAY + mode.getMode().getName());
		
		if(strafe.getState() && mc.thePlayer.onGround && MoveUtil.isMoving()) {
			MoveUtil.strafe(strafeSpeed.getValue());
		}
		
        if(!rotation.getMode().getName().equalsIgnoreCase("None")) {
        	try {
                BlockPos rayCastBlock = rotations == null ? null : RayCastUtil.rayTraceCustom(3, rotations.x, rotations.y);

                if(rayCastBlock == null) {
                    getRotation();
                }else {
                    if((rayCastBlock.getX() !=
                            blockData.position.getX() || rayCastBlock.getY() != blockData.position.getY() ||
                            rayCastBlock.getZ() != blockData.position.getZ())
                            && !(mc.theWorld.getBlockState(blockData.position).getBlock() instanceof BlockAir)) {
                        getRotation();
                    }
                }
            }catch (Exception e) {
                //Do nothing
            }
        	
        	if(grimExploit.getState()) {
        		if(rotations != null) {
        			PacketUtil.sendNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ,
            				rotations.x, rotations.y, mc.thePlayer.onGround));
        		}
        	}else {
        		int rotationSpeed = 360 * (this.rotationSpeed.getValue() / 10);
        		
//        		if(mode.getMode().getName().equalsIgnoreCase("Telly")) {
//        			if(mc.thePlayer.offGroundTicks < 1) {
//        				RotationComponent.stop();
//        				return;
//        			}
//        		}
//        		
        		if(!keepRot.getState()) {
        			if(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer).down()).getBlock() instanceof BlockAir) {
        				if(rotations != null) {
                            RotationComponent.setRotations(rotations, rotationSpeed, moveFix.getState() ? MovementCorrection.SILENT : MovementCorrection.OFF);
                    	}
        			}else {
        				RotationComponent.stop();
        			}
        		}else {
        			if(rotations != null) {
                        RotationComponent.setRotations(rotations, rotationSpeed, moveFix.getState() ? MovementCorrection.SILENT : MovementCorrection.OFF);
                	}
        		}
        	}
        }
                
        if(eagle.getMode().getName().equalsIgnoreCase("Sneak")) {
            mc.thePlayer.safeWalk = false;
            if(mc.thePlayer.onGround && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer).down()).getBlock() instanceof BlockAir) {
                mc.gameSettings.keyBindSprint.setPressed(true);
            }else {
                mc.gameSettings.keyBindSprint.setPressed(false);
            }
        }else if(eagle.getMode().getName().equalsIgnoreCase("Silent")) {
            mc.thePlayer.safeWalk = true;
        }
        
        int lastSlot = mc.thePlayer.inventory.currentItem;
        
        int BlockInInventory = ScaffoldUtil.findBlock(9, 36);
        int BlockInHotbar = ScaffoldUtil.findBlock(36, 45);

        if (BlockInInventory == -1 && BlockInHotbar == -1) {
            hasBlock = false;
            return;
        }

        hasBlock = true;

        lastSlot = mc.thePlayer.inventory.currentItem;

        if(BlockInHotbar != -1) mc.thePlayer.inventory.currentItem = BlockInHotbar - 36;
        else mc.playerController.windowClick(0, BlockInInventory, 0, 1, mc.thePlayer);

        mc.playerController.syncCurrentPlayItem();
        
        handleScaffold();
        
        if(grimExploit.getState()) {
        	PacketUtil.sendNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ,
    				mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, mc.thePlayer.onGround));
        }
        
        if(!pick.getMode().getName().equalsIgnoreCase("Always")) {
        	mc.thePlayer.inventory.currentItem = lastSlot;
        }
    }

	public void handleScaffold() {
		if(startY == -1) startY = (int) (mc.thePlayer.posY - 1);
        if(mc.thePlayer.onGround) startY = (int) (mc.thePlayer.posY - 1);
        
        List<BlockData> blockData = ScaffoldUtil.getPossiblePlace(keepY.getState(), startY);
                
        if(!blockData.isEmpty()) {
        	this.blockData = blockData.get(0);
        }
        
        if(!hasBlock || this.blockData == null) return;

        placeBlock();
	}
	
	@EventLink
	public void onPostMotion(PostMotionEvent event) {

	}
	
    @Override
    public void onDisable() {
    	if(mc.thePlayer == null) return;
    	mc.thePlayer.safeWalk = false;
        mc.gameSettings.keyBindSprint.setPressed(false);

        mc.timer.timerSpeed = 1.0F;
        blocks = 0;
    }
    
    private int blocks;
    
    @EventLink
    public void onMoveInput(MoveInputEvent event) {
    	if(smartSneak.getState()) {
    		if(blocks >= 5) {
        		if(mc.thePlayer.onGround && !(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer).down()).getBlock() instanceof BlockAir)) {
        			event.setSneak(true);
            		blocks = 0;
        		}
        	}
    	}
    	
    	if(mode.getMode().getName().equalsIgnoreCase("Telly") && MoveUtil.isMoving()) {
    		event.setJump(true);
    	}
    }
    
    @EventLink
    public void onPostPlace(PostBlockPlace event) {
    	blocks++;
    }
    
    public void getRotation() {
        if(blockData == null) return;

        Vector2f predictedRotations = RotationUtil.getBlockRotation(blockData.position, blockData.face);

        switch (rotation.getMode().getName()) {
            case "Normal":
                switch (blockData.face) {
                    case EAST:
                        if(predictedRotations.x > 136 || predictedRotations.x < 44) {
                            if(mc.thePlayer.getPosition().getZ() > blockData.position.getZ()) {
                                predictedRotations.x = 135;
                            } else {
                                predictedRotations.x = 45;
                            }
                        }
                        break;
                    case WEST:
                        if(predictedRotations.x < -136 || predictedRotations.x > -44) {
                            if(mc.thePlayer.getPosition().getZ() > blockData.position.getZ()) {
                                predictedRotations.x = -135;
                            } else {
                                predictedRotations.x = -45;
                            }
                        }
                        break;
                    case NORTH:
                        if(predictedRotations.x < -46 || predictedRotations.x > 46) {
                            if(mc.thePlayer.getPosition().getX() > blockData.position.getX()) {
                                predictedRotations.x = 45;
                            } else {
                                predictedRotations.x = -45;
                            }
                        }
                        break;
                    case SOUTH:
                        if(predictedRotations.x < 134 && predictedRotations.x > -134) {
                            if(mc.thePlayer.getPosition().getX() > blockData.position.getX()) {
                                predictedRotations.x = 135;
                            } else {
                                predictedRotations.x = -135;
                            }
                        }
                        break;
				default:
					break;
                }
                predictedRotations.y = 81.5f;
                break;
            case "Simple":
                float yaw = 0;

                switch (blockData.face) {
                    case SOUTH: {
                        if(mc.thePlayer.posX > blockData.position.getX()) {
                            yaw = 135;
                        }else {
                            yaw = -135;
                        }
                        break;
                    }

                    case EAST: {
                        if(mc.thePlayer.posZ > blockData.position.getZ()) {
                            yaw = 135;
                        }else {
                            yaw = 45;
                        }
                        break;
                    }

                    case WEST: {
                        if(mc.thePlayer.posZ > blockData.position.getZ()) {
                            yaw = -135;
                        }else {
                            yaw = -45;
                        }
                        break;
                    }

                    case NORTH: {
                        if(mc.thePlayer.posX > blockData.position.getX()) {
                            yaw = -45;
                        }else {
                            yaw = 45;
                        }
                        break;
                    }

                    default:
                        break;
                }

                predictedRotations.x = yaw;
                predictedRotations.y = 90;
                break;
                
            case "Watchdog":
            	boolean found1 = false;
            	float targetYaw1 = 0, targetPitch1 = 0;
                for (float y = mc.thePlayer.rotationYaw - 180; y <= mc.thePlayer.rotationYaw + 360 - 180 && !found1; y += 45) {
                    for (float pitch = 90; pitch > 30 && !found1; pitch -= 1) {
                        if (RayCastUtil.lookingAtBlock(blockData.position, y, pitch, blockData.face, false)) {
                            targetYaw1 = y;
                            targetPitch1 = pitch;
                            found1 = true;
                        }
                    }
                }

                if (!found1) {
                    targetYaw1 = (float) (rotations.x + (Math.random() - 0.5) * 4);
                    targetPitch1 = (float) (rotations.y + (Math.random() - 0.5) * 4);
                }
                
                final double randomAmount = 2;

                float randomYaw = 0, randomPitch = 0;
                if (randomAmount != 0) {
                    randomYaw += ((Math.random() - 0.5) * randomAmount) / 2;
                    randomYaw += ((Math.random() - 0.5) * randomAmount) / 2;
                    randomPitch += ((Math.random() - 0.5) * randomAmount) / 2;

                    if (mc.thePlayer.ticksExisted % 5 == 0) {
                        randomYaw = (float) (((Math.random() - 0.5) * randomAmount) / 2);
                        randomPitch = (float) (((Math.random() - 0.5) * randomAmount) / 2);
                    }

                    targetYaw1 += randomYaw;
                }
                
                predictedRotations = new Vector2f(targetYaw1, 81.5F);
            	break;
            	
            case "Bruteforce":
            	boolean found = false;
            	float targetYaw = 0, targetPitch = 0;
                for (float y = mc.thePlayer.rotationYaw - 180; y <= mc.thePlayer.rotationYaw + 360 - 180 && !found; y += 45) {
                    for (float pitch = 90; pitch > 30 && !found; pitch -= 1) {
                        if (RayCastUtil.lookingAtBlock(blockData.position, y, pitch, blockData.face, false)) {
                            targetYaw = y;
                            targetPitch = pitch;
                            found = true;
                        }
                    }
                }

                if (!found) {
                    targetYaw = (float) (rotations.x + (Math.random() - 0.5) * 4);
                    targetPitch = (float) (rotations.y + (Math.random() - 0.5) * 4);
                }
                
                predictedRotations = new Vector2f(targetYaw, targetPitch);
            	break;
            	
            case "Stable":
            	predictedRotations.x = MoveUtil.getPlayerDirection() - 180;
            	predictedRotations.y = 81.5f;
                
                break;
        }


        rotations = predictedRotations;
    }
    
    
    
    public void placeBlock() {
    	if(blockData == null) return;
    	if(delay.getValue() != 0 && !timer.hasTimeElapsed(delay.getValue())) return;
        if(rayCast.getState() && !RayCastUtil.compare(RayCastUtil.rayTraceCustom(3, mc.thePlayer.rotationYawHead, mc.thePlayer.rotationPitchHead),
                blockData.position)) return;
        if(rayCast.getState() && !RayCastUtil.lookingAtBlock(blockData.position,
        		mc.thePlayer.rotationYawHead, mc.thePlayer.rotationPitchHead, blockData.face, false)) return;
        if(!(mc.theWorld.getBlockState(blockData.position.offset(blockData.face)).getBlock() instanceof BlockAir)) return;

        ScaffoldUtil.placeBlock(swing.getState(), blockData.position, blockData.face, ScaffoldUtil.getVec3(blockData.position, blockData.face, false));
        
        timer.reset();
    }
}
