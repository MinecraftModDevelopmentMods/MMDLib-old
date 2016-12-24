package mmd.lib.util;

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


public class TileEntityBase extends TileEntity {

    public int x() {
        return pos.getX();
    }

    public int y() {
        return pos.getY();
    }

    public int z() {
        return pos.getZ();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
    }

    @Override
    public void onChunkUnload() {
        if (!this.isInvalid()) this.invalidate();
    }

    public void onChunkLoad() {
        if (this.isInvalid()) this.validate();
        markForUpdate();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return super.writeToNBT(compound);
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound data = new NBTTagCompound();
        writeToNBT(data);
        return new SPacketUpdateTileEntity(this.pos, 1, data);
    }

    public void markForFullUpdate() {
        this.markForUpdate();
        this.markForLightUpdate();
        this.markDirty();
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity s35PacketUpdateTileEntity) {
        readFromNBT(s35PacketUpdateTileEntity.getNbtCompound());
        getWorld().markBlockRangeForRenderUpdate(this.pos, this.pos);
        markForUpdate();
    }

    public void markForUpdate() {
        if (this.getWorld() != null) {
            Block block = world.getBlockState(this.pos).getBlock();
            this.getWorld().notifyBlockUpdate(this.pos, getWorld().getBlockState(this.pos), getWorld().getBlockState(this.pos), 3);
            int xCoord = this.pos.getX();
            int yCoord = this.pos.getY();
            int zCoord = this.pos.getZ();
            this.getWorld().notifyBlockOfStateChange(new BlockPos(xCoord, yCoord - 1, zCoord), block);
            this.getWorld().notifyBlockOfStateChange(new BlockPos(xCoord, yCoord + 1, zCoord), block);
            this.getWorld().notifyBlockOfStateChange(new BlockPos(xCoord - 1, yCoord, zCoord), block);
            this.getWorld().notifyBlockOfStateChange(new BlockPos(xCoord + 1, yCoord, zCoord), block);
            this.getWorld().notifyBlockOfStateChange(new BlockPos(xCoord, yCoord, zCoord - 1), block);
            this.getWorld().notifyBlockOfStateChange(new BlockPos(xCoord, yCoord, zCoord + 1), block);
        }
    }

    public void markForLightUpdate() {
        if (this.getWorld().isRemote)
            this.getWorld().notifyBlockUpdate(this.pos, getWorld().getBlockState(this.pos), getWorld().getBlockState(this.pos), 3);
        this.getWorld().checkLightFor(EnumSkyBlock.BLOCK, this.pos);
    }
}