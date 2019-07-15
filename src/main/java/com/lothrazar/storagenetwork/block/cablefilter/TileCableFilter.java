package com.lothrazar.storagenetwork.block.cablefilter;
import com.lothrazar.storagenetwork.block.TileCableWithFacing;
import com.lothrazar.storagenetwork.capabilities.CapabilityConnectableLink;
import com.lothrazar.storagenetwork.capabilities.StorageNetworkCapabilities;
import com.lothrazar.storagenetwork.registry.ModBlocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public class TileCableFilter extends TileCableWithFacing implements ITickableTileEntity {

  protected CapabilityConnectableLink itemStorage;

  public TileCableFilter() {
    super(ModBlocks.filterkabeltile);
    this.itemStorage = new CapabilityConnectableLink(this);
  }

  @Override
  public void read(CompoundNBT compound) {
    super.read(compound);
    this.itemStorage.deserializeNBT(compound.getCompound("itemStorage"));
  }

  @Override
  public CompoundNBT write(CompoundNBT compound) {
    CompoundNBT result = super.write(compound);
    result.put("itemStorage", itemStorage.serializeNBT());
    return result;
  }

  @Override
  public void setDirection(@Nullable Direction direction) {
    super.setDirection(direction);
    this.itemStorage.setInventoryFace(direction);
  }

  @Nullable
  @Override
  public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
    if (capability == StorageNetworkCapabilities.CONNECTABLE_ITEM_STORAGE_CAPABILITY) {
      LazyOptional<CapabilityConnectableLink> cap = LazyOptional.of(() -> itemStorage);
      return (LazyOptional<T>) cap;
    }
    return super.getCapability(capability, facing);
  }

  @Override public void tick() {
    super.refreshDirection();
  }
}