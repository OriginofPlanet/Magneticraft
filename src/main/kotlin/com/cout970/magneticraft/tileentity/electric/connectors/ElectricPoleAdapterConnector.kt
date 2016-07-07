package com.cout970.magneticraft.tileentity.electric.connectors

import coffee.cypher.mcextlib.extensions.vectors.times
import com.cout970.magneticraft.api.energy.IElectricNode
import com.cout970.magneticraft.api.energy.IWireConnector
import com.cout970.magneticraft.api.energy.impl.ElectricNode
import com.cout970.magneticraft.block.BlockElectricPoleAdapter
import com.cout970.magneticraft.block.ELECTRIC_POLE_PLACE
import com.cout970.magneticraft.util.get
import com.google.common.collect.ImmutableList
import net.minecraft.util.math.Vec3d

/**
 * Created by cout970 on 06/07/2016.
 */
class ElectricPoleAdapterConnector(node: ElectricNode): IElectricNode by node, IWireConnector {

        override fun getConnectors(): ImmutableList<Vec3d> {
            val state = world.getBlockState(pos)
            if (state.block != BlockElectricPoleAdapter)
                return ImmutableList.of()
            val offset = ELECTRIC_POLE_PLACE[state].offset.rotateYaw(Math.toRadians(-90.0).toFloat())
            val vec = Vec3d(0.5, 1.0 - 0.0625 * 6.5, 0.5).add(offset * 0.5)
            return ImmutableList.of(vec)
        }

        override fun getConnectorsSize(): Int = 1
}