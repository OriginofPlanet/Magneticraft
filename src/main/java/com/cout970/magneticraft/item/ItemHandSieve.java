package com.cout970.magneticraft.item;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.model.ModelConstants;
import net.darkaqua.blacksmith.api.client.render.model.RenderPlace;
import net.darkaqua.blacksmith.api.client.render.model.RenderTransformation;
import net.darkaqua.blacksmith.api.client.render.item.IItemModelProvider;
import net.darkaqua.blacksmith.api.client.render.item.defaults.SimpleItemModelProvider;
import net.darkaqua.blacksmith.api.common.util.vectors.Vect3d;

/**
 * Created by cout970 on 28/12/2015.
 */
public class ItemHandSieve extends ItemBase {

    public ItemHandSieve() {
        maxStackSize = 1;
    }

    public IItemModelProvider getModelProvider() {
        return new SimpleItemModelProvider(iModelRegistry -> new SimpleItemModelProvider.ItemModel(
                iModelRegistry.registerModelPart(Magneticraft.IDENTIFIER, ModelConstants.HAND_SIEVE),
                place -> {
                    if (place == RenderPlace.THIRD_PERSON) {
                        return new RenderTransformation(new Vect3d(0, 5.5, -1).multiply(1 / 16d), new Vect3d(20, 0, 0), new Vect3d(1, 1, 1));
                    } else if (place == RenderPlace.FIRST_PERSON) {
                        return new RenderTransformation(new Vect3d(0, 3, -7).multiply(1 / 16d), new Vect3d(90, -120, 15), new Vect3d(2.5, 2.5, 2.5));
                    } else if (place == RenderPlace.GUI) {
                        return new RenderTransformation(new Vect3d(0, 0, 0).multiply(1 / 16d), new Vect3d(90, 0, 0), new Vect3d(1, 1, 1));
                    } else if (place == RenderPlace.NONE) {
                        return new RenderTransformation(new Vect3d(0, 0, 4.5).multiply(1 / 16d), new Vect3d(90, 0, 0), new Vect3d(1.2, 1.2, 1.2));
                    }
                    return null;
                }, false));

    }

    @Override
    public String getItemName() {
        return "hand_sieve";
    }
}
