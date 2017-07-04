package com.cout970.magneticraft.multiblock

import com.cout970.magneticraft.block.MultiblockParts
import com.cout970.magneticraft.block.Multiblocks
import com.cout970.magneticraft.multiblock.components.MainBlockComponent
import com.cout970.magneticraft.multiblock.components.SingleBlockComponent
import com.cout970.magneticraft.multiblock.core.BlockData
import com.cout970.magneticraft.multiblock.core.IMultiblockComponent
import com.cout970.magneticraft.multiblock.core.Multiblock
import com.cout970.magneticraft.multiblock.core.MultiblockContext
import com.cout970.magneticraft.tilerenderer.core.PIXEL
import com.cout970.magneticraft.util.vector.times
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.util.text.ITextComponent

/**
 * Created by cout970 on 2017/07/03.
 */
object MultiblockSolarPanel : Multiblock() {

    override val name: String = "solar_panel"
    override val size: BlockPos = BlockPos(3, 1, 3)
    override val scheme: List<MultiblockLayer>
    override val center: BlockPos = BlockPos(1, 0, 0)

    init {
        val replacement = Multiblocks.gap.defaultState

        val pBlock = MultiblockParts.PartType.ELECTRIC.getBlockState(MultiblockParts.parts)
        val P: IMultiblockComponent = SingleBlockComponent(pBlock, replacement)

        val M: IMultiblockComponent = MainBlockComponent(Multiblocks.solarPanel) { context, activate ->
            Multiblocks.MultiblockOrientation.of(context.facing, activate).getBlockState(Multiblocks.solarPanel)
        }

        scheme = yLayers(
                zLayers(listOf(P, M, P),
                        listOf(P, P, P),
                        listOf(P, P, P))
        )
    }

    override fun getGlobalCollisionBox(): List<AxisAlignedBB> = listOf(
            Vec3d(-13.0, 12.0, 1.0) * PIXEL to Vec3d(5.0, 14.0, 15.0) * PIXEL,
            Vec3d(-12.0, 11.0, 2.0) * PIXEL to Vec3d(4.0, 12.0, 14.0) * PIXEL,
            Vec3d(-6.0, 10.0, 6.0) * PIXEL to Vec3d(-2.0, 11.0, 10.0) * PIXEL,
            Vec3d(-13.0, 12.0, 17.0) * PIXEL to Vec3d(5.0, 14.0, 31.0) * PIXEL,
            Vec3d(-12.0, 11.0, 18.0) * PIXEL to Vec3d(4.0, 12.0, 30.0) * PIXEL,
            Vec3d(-6.0, 10.0, 22.0) * PIXEL to Vec3d(-2.0, 11.0, 26.0) * PIXEL,
            Vec3d(-13.0, 12.0, 33.0) * PIXEL to Vec3d(5.0, 14.0, 47.0) * PIXEL,
            Vec3d(-12.0, 11.0, 34.0) * PIXEL to Vec3d(4.0, 12.0, 46.0) * PIXEL,
            Vec3d(-6.0, 10.0, 38.0) * PIXEL to Vec3d(-2.0, 11.0, 42.0) * PIXEL,
            Vec3d(-6.0, 0.0, 4.0) * PIXEL to Vec3d(-2.0, 2.0, 44.0) * PIXEL,
            Vec3d(-5.0, 4.0, 39.0) * PIXEL to Vec3d(-3.0, 12.0, 41.0) * PIXEL,
            Vec3d(-5.0, 4.0, 23.0) * PIXEL to Vec3d(-3.0, 12.0, 25.0) * PIXEL,
            Vec3d(-7.0, 0.0, 38.0) * PIXEL to Vec3d(23.0, 4.0, 42.0) * PIXEL,
            Vec3d(-7.0, 0.0, 22.0) * PIXEL to Vec3d(-1.0, 4.0, 26.0) * PIXEL,
            Vec3d(-5.0, 4.0, 7.0) * PIXEL to Vec3d(-3.0, 12.0, 9.0) * PIXEL,
            Vec3d(-7.0, 0.0, 6.0) * PIXEL to Vec3d(23.0, 4.0, 10.0) * PIXEL,
            Vec3d(11.0, 12.0, 1.0) * PIXEL to Vec3d(29.0, 14.0, 15.0) * PIXEL,
            Vec3d(12.0, 11.0, 2.0) * PIXEL to Vec3d(28.0, 12.0, 14.0) * PIXEL,
            Vec3d(11.0, 12.0, 17.0) * PIXEL to Vec3d(29.0, 14.0, 31.0) * PIXEL,
            Vec3d(12.0, 11.0, 18.0) * PIXEL to Vec3d(28.0, 12.0, 30.0) * PIXEL,
            Vec3d(11.0, 12.0, 33.0) * PIXEL to Vec3d(29.0, 14.0, 47.0) * PIXEL,
            Vec3d(12.0, 11.0, 34.0) * PIXEL to Vec3d(28.0, 12.0, 46.0) * PIXEL,
            Vec3d(18.0, 10.0, 6.0) * PIXEL to Vec3d(22.0, 11.0, 10.0) * PIXEL,
            Vec3d(18.0, 10.0, 22.0) * PIXEL to Vec3d(22.0, 11.0, 26.0) * PIXEL,
            Vec3d(18.0, 10.0, 38.0) * PIXEL to Vec3d(22.0, 11.0, 42.0) * PIXEL,
            Vec3d(19.0, 4.0, 7.0) * PIXEL to Vec3d(21.0, 12.0, 9.0) * PIXEL,
            Vec3d(19.0, 4.0, 23.0) * PIXEL to Vec3d(21.0, 12.0, 25.0) * PIXEL,
            Vec3d(19.0, 4.0, 39.0) * PIXEL to Vec3d(21.0, 12.0, 41.0) * PIXEL,
            Vec3d(17.0, 0.0, 22.0) * PIXEL to Vec3d(23.0, 4.0, 26.0) * PIXEL,
            Vec3d(18.0, 0.0, 4.0) * PIXEL to Vec3d(22.0, 2.0, 44.0) * PIXEL,
            Vec3d(5.0, 0.0, 1.0) * PIXEL to Vec3d(11.0, 11.0, 6.0) * PIXEL,
            Vec3d(6.0, 4.0, 6.0) * PIXEL to Vec3d(10.0, 10.0, 7.0) * PIXEL,
            Vec3d(6.0, 6.0, 0.0) * PIXEL to Vec3d(10.0, 10.0, 1.0) * PIXEL
    )


    override fun checkExtraRequirements(data: MutableList<BlockData>, context: MultiblockContext): List<ITextComponent> = listOf()
}