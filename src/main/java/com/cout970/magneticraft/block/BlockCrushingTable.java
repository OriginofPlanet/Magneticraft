package com.cout970.magneticraft.block;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.access.RecipeRegister;
import com.cout970.magneticraft.api.tool.IHammer;
import com.cout970.magneticraft.client.model.ModelConstants;
import com.cout970.magneticraft.tileentity.TileCrushingTable;
import net.darkaqua.blacksmith.api.common.block.IBlockContainerDefinition;
import net.darkaqua.blacksmith.api.common.block.blockdata.IBlockData;
import net.darkaqua.blacksmith.api.common.block.methods.BlockMethod;
import net.darkaqua.blacksmith.api.common.entity.IPlayer;
import net.darkaqua.blacksmith.api.common.inventory.IInventoryHandler;
import net.darkaqua.blacksmith.api.common.inventory.IItemStack;
import net.darkaqua.blacksmith.api.client.render.block.IBlockModelProvider;
import net.darkaqua.blacksmith.api.client.render.block.defaults.SimpleBlockModelProvider;
import net.darkaqua.blacksmith.api.common.tileentity.ITileEntity;
import net.darkaqua.blacksmith.api.common.tileentity.ITileEntityDefinition;
import net.darkaqua.blacksmith.api.common.util.*;
import net.darkaqua.blacksmith.api.common.util.raytrace.Cube;
import net.darkaqua.blacksmith.api.common.util.vectors.Vect3d;
import net.darkaqua.blacksmith.api.common.world.IWorld;

/**
 * Created by cout970 on 16/12/2015.
 */
public class BlockCrushingTable extends BlockModeled implements IBlockContainerDefinition, BlockMethod.OnActivated {

    @Override
    public String getBlockName() {
        return "crushing_table";
    }

    @Override
    public Cube getBounds(WorldRef ref) {
        return new Cube(0, 0, 0, 1, 0.875f, 1);
    }

    @Override
    public ITileEntityDefinition createTileEntity(IWorld world, IBlockData state) {
        return new TileCrushingTable();
    }

    @Override
    public boolean onActivated(WorldRef ref, IBlockData state, IPlayer p, Direction side, Vect3d vector3d) {
        if (p != null) {
            ITileEntity t = ref.getTileEntity();
            if (!(t.getTileEntityDefinition() instanceof TileCrushingTable)) {
                return true;
            }
            TileCrushingTable tile = (TileCrushingTable) t.getTileEntityDefinition();

            IItemStack i = p.getSelectedItemStack();
            IInventoryHandler playerInv = p.getInventory();
            if (i != null) {
                IHammer hammer = ObjectScanner.findInItem(i.getItem(), IHammer.IDENTIFIER, null);
                if (hammer != null) {
                    if (tile.canWork()) {
                        if (hammer.canHammer(i, ref)) {
                            tile.tick(hammer.getMaxHits(i, ref));
                            IItemStack stack = hammer.tick(i, ref);
                            p.setSelectedItemStack(stack);
                            return true;
                        }
                    } else {
                        if (tile.getContent() == null) {
                            for (int j = 0; j < playerInv.getSlots(); j++) {
                                IItemStack stack = playerInv.getStackInSlot(j);
                                if (stack != null && stack.getAmount() > 0 && RecipeRegister.getCrushingTableRecipe(stack) != null) {
                                    tile.setContent(playerInv.extractItemStack(j, 1, false));
                                    t.setModified();
                                    return true;
                                }
                            }
                        } else {
                            boolean inserted = false;
                            for (int j = 0; j < playerInv.getSlots(); j++) {
                                if (playerInv.insertItemStack(j, tile.getContent(), true) == null) {
                                    playerInv.insertItemStack(j, tile.getContent(), false);
                                    inserted = true;
                                    break;
                                }
                            }
                            if (inserted) {
                                tile.setContent(null);
                            }
                        }
                        t.setModified();

                        return true;
                    }

                } else if ((tile.getContent() == null) && (i.getAmount() > 0)) {
                    IItemStack split = i.split(1);
                    p.setSelectedItemStack((i.getAmount() > 0) ? i : null);
                    tile.setContent(split);

                    t.setModified();

                    return true;
                }
            }
            if (tile.getContent() != null) {
                boolean inserted = false;
                for (int j = 0; j < playerInv.getSlots(); j++) {
                    if (playerInv.insertItemStack(j, tile.getContent(), true) == null) {
                        playerInv.insertItemStack(j, tile.getContent(), false);
                        inserted = true;
                        break;
                    }
                }
                if (inserted) {
                    tile.setContent(null);
                }
            }
            t.setModified();
        }
        return true;
    }


    public IBlockModelProvider getModelProvider() {
        return new SimpleBlockModelProvider(iModelRegistry -> new SimpleBlockModelProvider.BlockModel(
                iModelRegistry.registerModelPart(Magneticraft.IDENTIFIER, ModelConstants.CRUSHING_TABLE)));
    }
}
