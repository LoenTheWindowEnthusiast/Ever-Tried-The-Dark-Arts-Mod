package net.loenk.evertrieddarkarts;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.loenk.evertrieddarkarts.block.ModBlocks;
import net.minecraft.client.render.RenderLayer;

public class ModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.HEX_BAG, RenderLayer.getCutout());
    }
}
