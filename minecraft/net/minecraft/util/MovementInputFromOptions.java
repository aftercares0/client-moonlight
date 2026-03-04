package net.minecraft.util;

import com.moonlight.client.baritone.api.utils.input.Input;
import com.moonlight.client.baritone.utils.InputOverrideHandler;
import com.moonlight.client.event.impl.strafe.MoveInputEvent;
import net.minecraft.client.settings.GameSettings;

public class MovementInputFromOptions extends MovementInput
{	
    private final GameSettings gameSettings;

    public MovementInputFromOptions(GameSettings gameSettingsIn)
    {
        this.gameSettings = gameSettingsIn;
    }

    public void updatePlayerMoveState()
    {
        this.moveStrafe = 0.0F;
        this.moveForward = 0.0F;

        if (this.gameSettings.keyBindLeft.isKeyDown())
        {
            ++this.moveForward;
        }

        if (this.gameSettings.keyBindRight.isKeyDown())
        {
            --this.moveForward;
        }

        if (this.gameSettings.keyBindBack.isKeyDown())
        {
            ++this.moveStrafe;
        }

        if (this.gameSettings.keyBindJump.isKeyDown())
        {
            --this.moveStrafe;
        }

        this.realForward = moveForward;
        this.realStrafe = moveStrafe;
        
        if (this.sneak)
        {
            this.realStrafe = (float)((double)this.realStrafe * 0.3D);
            this.realForward = (float)((double)this.realForward * 0.3D);
        }
        
        MoveInputEvent event = (MoveInputEvent) new MoveInputEvent(moveStrafe, moveForward, this.gameSettings.keyBindSneak.isKeyDown(), this.gameSettings.keyBindSprint.isKeyDown()).fire();

        jump = event.isJump();
        sneak = event.isSneak();

        moveForward = event.forward;
        moveStrafe = event.strafe;
        
        if (this.sneak)
        {
            this.moveStrafe = (float)((double)this.moveStrafe * 0.3D);
            this.moveForward = (float)((double)this.moveForward * 0.3D);
        }
    }
}
