package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.access.RecipeCrushingTable;
import com.cout970.magneticraft.api.access.RecipeRegister;
import com.cout970.magneticraft.util.MiscUtils;
import net.darkaqua.blacksmith.api.Game;
import net.darkaqua.blacksmith.api.client.particle.IParticle;
import net.darkaqua.blacksmith.api.client.registry.IParticleManager;
import net.darkaqua.blacksmith.api.client.registry.ISoundHandler;
import net.darkaqua.blacksmith.api.client.sound.SoundEffectFactory;
import net.darkaqua.blacksmith.api.common.intermod.IInterfaceIdentifier;
import net.darkaqua.blacksmith.api.common.intermod.IInterfaceProvider;
import net.darkaqua.blacksmith.api.common.intermod.InterModUtils;
import net.darkaqua.blacksmith.api.common.inventory.IInventoryHandler;
import net.darkaqua.blacksmith.api.common.inventory.IItemStack;
import net.darkaqua.blacksmith.api.common.inventory.defaults.SimpleInventoryHandler;
import net.darkaqua.blacksmith.api.common.item.IItemBlock;
import net.darkaqua.blacksmith.api.common.storage.IDataCompound;
import net.darkaqua.blacksmith.api.common.util.Direction;
import net.darkaqua.blacksmith.api.common.util.ResourceReference;
import net.darkaqua.blacksmith.api.common.util.vectors.Vect3d;

/**
 * Created by cout970 on 16/12/2015.
 */
public class TileCrushingTable extends TileBase implements IInterfaceProvider {

    private SimpleInventoryHandler inventory;
    private int progress;

    public TileCrushingTable() {
        inventory = new SimpleInventoryHandler(1) {

            @Override
            public void setStackInSlot(int slot, IItemStack stack) {
                super.setStackInSlot(slot, stack);
                sendUpdateToClient();
            }
        };
    }

    public IInventoryHandler getInventory() {
        return inventory;
    }

    @Override
    public void onDelete() {
        super.onDelete();
        if (Game.isServer()) {
            MiscUtils.dropItem(getParent().getWorldRef(), new Vect3d(0.5, 0.5, 0.5), inventory.getStackInSlot(0), true);
            inventory.setStackInSlot(0, null);
        }
    }

    @Override
    public void loadData(IDataCompound tag) {
        super.loadData(tag);
        inventory.load(tag, "inv");
    }

    @Override
    public void saveData(IDataCompound tag) {
        super.saveData(tag);
        inventory.save(tag, "inv");
    }

    public IItemStack getContent() {
        return inventory.getStackInSlot(0);
    }

    public void setContent(IItemStack input) {
        inventory.setStackInSlot(0, input);
    }

    public boolean canWork() {
        return (inventory.getStackInSlot(0) != null) &&
                (RecipeRegister.getCrushingTableRecipe(inventory.getStackInSlot(0)) != null);
    }

    public IItemStack getOutput() {
        RecipeCrushingTable rec = RecipeRegister.getCrushingTableRecipe(inventory.getStackInSlot(0));
        if (rec == null)
            return null;
        return rec.getOutput().copy();
    }

    public void tick(int maxHits) {
        progress++;
        if (Game.isClient()) {
            addParticles();
            if (progress != maxHits)
                addHitSound();
        }
        if (progress >= maxHits) {
            progress = 0;
            setContent(getOutput().copy());
            addFinalSound();
        }
    }

    private void addHitSound() {
        ISoundHandler sh = Game.getClientHandler().getSoundHandler();
        ResourceReference res = new ResourceReference(Magneticraft.ID, "sounds.crushing_hit");
        sh.playSound(SoundEffectFactory.createSoundEffect(res, getPosition().toVect3d()));
    }

    private void addFinalSound() {
        ISoundHandler sh = Game.getClientHandler().getSoundHandler();
        ResourceReference res = new ResourceReference(Magneticraft.ID, "sounds.crushing_final");
        sh.playSound(SoundEffectFactory.createSoundEffect(res, getPosition().toVect3d()));
    }

    private void addParticles() {
        if (getContent() == null) return;
        IParticleManager man = Game.getClientHandler().getParticleManager();
        IParticle part;
        if (getContent().getItem() instanceof IItemBlock) {
            part = man.getBlockCrackParticle(((IItemBlock) getContent().getItem()).getBlock().getDefaultBlockData());
        } else {
            part = man.getItemCrackParticle(getContent());
        }
        for (int i = 0; i < 20; i++) {
            man.addParticle(getWorld(), part, getPosition().toVect3d().add(0.5, 0.8, 0.5), Vect3d.randomVector().multiply(1 / 32d));
        }
    }

    @Override
    public boolean hasInterface(IInterfaceIdentifier id, Direction side) {
        return InterModUtils.matches(IInventoryHandler.IDENTIFIER, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getInterface(IInterfaceIdentifier<T> identifier, Direction side) {
        return (T) inventory;
    }
}
