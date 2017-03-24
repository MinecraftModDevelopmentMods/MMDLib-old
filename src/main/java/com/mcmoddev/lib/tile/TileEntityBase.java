package com.mcmoddev.lib.tile;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public abstract class TileEntityBase extends TileEntity {

    public int x () {
        return this.pos.getX();
    }

    public int y () {
        return this.pos.getY();
    }

    public int z () {
        return this.pos.getZ();
    }

    @Override
    public abstract void readFromNBT (NBTTagCompound compound);

    @Override
    public void onChunkUnload () {
        if (!this.isInvalid())
            this.invalidate();
    }

    public void onChunkLoad () {
        if (this.isInvalid())
            this.validate();
        this.markForUpdate();
    }

    @Override
    public abstract NBTTagCompound writeToNBT (NBTTagCompound compound);

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket () {
        final NBTTagCompound data = new NBTTagCompound();
        this.writeToNBT(data);
        return new SPacketUpdateTileEntity(this.pos, 1, data);
    }

    public void markForFullUpdate () {
        this.markForUpdate();
        this.markForLightUpdate();
        this.markDirty();
    }

    @Override
    public NBTTagCompound getUpdateTag () {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public boolean shouldRefresh (World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onDataPacket (NetworkManager networkManager, SPacketUpdateTileEntity s35PacketUpdateTileEntity) {
        this.readFromNBT(s35PacketUpdateTileEntity.getNbtCompound());
        this.getWorld().markBlockRangeForRenderUpdate(this.pos, this.pos);
        this.markForUpdate();
    }

    public void markForUpdate () {
        final Block block = this.world.getBlockState(this.pos).getBlock();
        final int xCoord = this.pos.getX();
        final int yCoord = this.pos.getY();
        final int zCoord = this.pos.getZ();
        this.getWorld().notifyBlockUpdate(this.pos, this.getWorld().getBlockState(this.pos), this.getWorld().getBlockState(this.pos), 3);
    }

    public void markForLightUpdate () {
        if (this.getWorld().isRemote)
            this.getWorld().notifyBlockUpdate(this.pos, this.getWorld().getBlockState(this.pos), this.getWorld().getBlockState(this.pos), 3);
        this.getWorld().checkLightFor(EnumSkyBlock.BLOCK, this.pos);
    }
}