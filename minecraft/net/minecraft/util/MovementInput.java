package net.minecraft.util;

import com.moonlight.client.baritone.utils.InputOverrideHandler;

public class MovementInput
{
    /**
     * The speed at which the player is strafing. Postive numbers to the left and negative to the right.
     */
    public float moveStrafe, realStrafe;

    /**
     * The speed at which the player is moving forward. Negative numbers will move backwards.
     */
    public float moveForward, realForward;
    public boolean jump;
    public boolean sneak;
    
    public void updatePlayerMoveState()
    {
    }
}
