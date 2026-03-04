package com.moonlight.client.module.impl.visuals;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.util.MathHelper;

import com.moonlight.client.module.api.Category;
import com.moonlight.client.module.api.Module;
import com.moonlight.client.module.api.ModuleInfo;
import com.moonlight.client.value.impl.ModeValue;
import com.moonlight.client.value.impl.NumberValue;

@ModuleInfo(name = "Animations", description =  "Custom Animations, yes", category = Category.VISUALS, key = 0, autoEnabled = true)
public class Animations extends Module {

    public ModeValue modes = new ModeValue("Mode", this, "Serenity" , "1.7" , "Exhibition", "Swong", "Spin", "None");
    public NumberValue<Float> speed = new NumberValue("Swing Speed", 0.4f, 0.1f, 2f);

    public void renderAnimation(float f, float f1 , EntityPlayerSP entityplayersp, float partialTicks) {
        String mode = modes.getMode().getName();

        final ItemRenderer itemRenderer = mc.getItemRenderer();

        final float convertedProgress = MathHelper.sin(MathHelper.sqrt_float(f1) * (float) Math.PI);
                
        switch (mode) {
        	case "None":
        		itemRenderer.transformFirstPersonItem(f, 0.0F);
                itemRenderer.doBlockTransformations();
        		break;
        	case "1.7":
        		itemRenderer.transformFirstPersonItem(0.0F, f1);
                itemRenderer.doBlockTransformations();
                break;
        	case "Serenity":
                GlStateManager.translate(0.0F, 0.2F, -0.0F);
                itemRenderer.transformFirstPersonItem(0.0F, f1);
                itemRenderer.doBlockTransformations();
                break;
        	case "Exhibition":
        		itemRenderer.transformFirstPersonItem(f1 / 2.0F, 0.0F);
                GlStateManager.translate(0.0F, 0.3F, -0.0F);
                GlStateManager.rotate(-convertedProgress * 31.0F, 1.0F, 0.0F, 2.0F);
                GlStateManager.rotate(-convertedProgress * 33.0F, 1.5F, (convertedProgress / 1.1F), 0.0F);
                itemRenderer.doBlockTransformations();
                break;
        	case "Swong":
       		 	itemRenderer.transformFirstPersonItem(-0.30f / 2f, 8f);
       		 	GlStateManager.rotate(convertedProgress * 30.0F / 2.0F, -convertedProgress, -0.0F, 9.0F);
       			GlStateManager.rotate(convertedProgress * 40.0F, 1.0F, -convertedProgress / 2.0F, -0.0F);
       			GlStateManager.translate(0.0F, 0.2F, 0.0F);
       			itemRenderer.doBlockTransformations();
       			break;
        	case "Spin":
               itemRenderer.transformFirstPersonItem(convertedProgress, 0.0F);
               GlStateManager.translate(0.0F, 0.2F, -1.0F);
               GlStateManager.rotate(-59.0F, -1.0F, 0.0F, 3.0F);
               GlStateManager.rotate((float)-(System.currentTimeMillis() / 2L % 360L), 1.0F, 0.0F, 0.0F);
               GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
       			break;
        }
    }



}
