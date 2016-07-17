package com.cout970.magneticraft.client.render.tileentity

import com.cout970.magneticraft.api.energy.IWireConnector
import com.cout970.magneticraft.tileentity.electric.TileElectricConnector
import net.minecraft.client.renderer.GlStateManager.*
import org.lwjgl.opengl.GL11

/**
 * Created by cout970 on 29/06/2016.
 */
object TileElectricConnectorRenderer : TileEntityRenderer<TileElectricConnector>() {

    override fun renderTileEntityAt(te: TileElectricConnector, x: Double, y: Double, z: Double, partialTicks: Float, destroyStage: Int) {

        if (te.renderCache == -1) {
            te.renderCache = glGenLists(1)
            glNewList(te.renderCache, GL11.GL_COMPILE)
            for (i in te.connections) {
                if (i.firstNode != te.node) continue
                if (i.secondNode !is IWireConnector) continue
                renderConnection(i, i.firstNode as IWireConnector, i.secondNode as IWireConnector)
            }
            glEndList()
        }

        pushMatrix()
        translate(x, y, z)
        bindTexture(WIRE_TEXTURE)
        callList(te.renderCache)
        popMatrix()
    }

    override fun isGlobalRenderer(te: TileElectricConnector?): Boolean = true
}