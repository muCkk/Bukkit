// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.server;

import java.io.Serializable;
import java.util.Random;

// Referenced classes of package net.minecraft.server:
//            BlockContainer, PacketHandlerRedstoneWireless, Material, World, 
//            RedstoneEther, TileEntity, TileEntityRedstoneWirelessT, Block, 
//            mod_WirelessRedstone, EntityPlayer, TileEntityRedstoneWireless, IBlockAccess, 
//            EntityHuman

public class BlockRedstoneWirelessT extends BlockContainer
{

    protected BlockRedstoneWirelessT(int i)
    {
        super(i, Material.ORIENTABLE);
    }

    public void setState(World world, int i, int j, int k, boolean flag)
    {
        int l = world.getData(i, j, k);
        l &= 0xe;
        Serializable serializable = getFreq(world, i, j, k);
        if(flag)
        {
            l++;
        }
        RedstoneEther.getInstance().setTransmitterState(world, i, j, k, serializable, flag);
        world.setData(i, j, k, l);
        world.notify(i, j, k);
    }

    private boolean getState(World world, int i, int j, int k)
    {
        if(world == null)
        {
            return false;
        } else
        {
            int l = world.getData(i, j, k);
            return getState(l);
        }
    }

    private boolean getState(int i)
    {
        return (i & 1) == 1;
    }

    public Serializable getFreq(World world, int i, int j, int k)
    {
        TileEntity tileentity = world.getTileEntity(i, j, k);
        if(tileentity == null)
        {
            return Integer.valueOf(-1);
        }
        if(tileentity instanceof TileEntityRedstoneWirelessT)
        {
            return ((TileEntityRedstoneWirelessT)tileentity).getFreq();
        } else
        {
            return Integer.valueOf(0);
        }
    }

    public void a(World world, int i, int j, int k)
    {
        if(world.isStatic)
        {
            return;
        } else
        {
            TileEntityRedstoneWirelessT tileentityredstonewirelesst = (TileEntityRedstoneWirelessT)a_();
            tileentityredstonewirelesst.assBlock(i, j, k);
            world.setTileEntity(i, j, k, tileentityredstonewirelesst);
            RedstoneEther.getInstance().addTransmitter(world, i, j, k, getFreq(world, i, j, k));
            doPhysics(world, i, j, k, Block.REDSTONE_WIRE.id);
            return;
        }
    }

    public void remove(World world, int i, int j, int k)
    {
        if(world.isStatic)
        {
            world.n(i, j, k);
            return;
        } else
        {
            RedstoneEther.getInstance().remTransmitter(world, i, j, k, getFreq(world, i, j, k));
            super.remove(world, i, j, k);
            return;
        }
    }

    public void doPhysics(World world, int i, int j, int k, int l)
    {
        if(world.isStatic)
        {
            return;
        }
        if(l == id)
        {
            return;
        }
        if(l > 0 && (world.isBlockPowered(i, j, k) || world.isBlockIndirectlyPowered(i, j, k)))
        {
            setState(world, i, j, k, true);
        } else
        if(!world.isBlockPowered(i, j, k) && !world.isBlockIndirectlyPowered(i, j, k))
        {
            setState(world, i, j, k, false);
        }
    }

    public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
    }

    public int a(int i, Random random)
    {
        return mod_WirelessRedstone.txID;
    }

    public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman)
    {
        if(world.isStatic)
        {
            return true;
        }
        TileEntity tileentity = world.getTileEntity(i, j, k);
        if(tileentity instanceof TileEntityRedstoneWirelessT)
        {
            PacketHandlerRedstoneWireless.PacketHandlerOutput.sendGuiPacketTo((EntityPlayer)entityhuman, (TileEntityRedstoneWirelessT)tileentity);
        }
        return true;
    }

    public void changeFreq(TileEntityRedstoneWireless tileentityredstonewireless, int i, int j, int k, Serializable serializable, Serializable serializable1)
    {
        i = tileentityredstonewireless.x;
        j = tileentityredstonewireless.y;
        k = tileentityredstonewireless.z;
        RedstoneEther.getInstance().remTransmitter(tileentityredstonewireless.world, i, j, k, serializable);
        RedstoneEther.getInstance().addTransmitter(tileentityredstonewireless.world, i, j, k, serializable1);
        RedstoneEther.getInstance().setTransmitterState(tileentityredstonewireless.world, i, j, k, serializable1, getState(tileentityredstonewireless.world, i, j, k));
    }

    public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        if(getState(iblockaccess.getData(i, j, k)))
        {
            if(l == 0 || l == 1)
            {
                return mod_WirelessRedstone.spriteTopOn;
            } else
            {
                return mod_WirelessRedstone.spriteTOn;
            }
        } else
        {
            return a(l);
        }
    }

    public int a(int i)
    {
        if(i == 0 || i == 1)
        {
            return mod_WirelessRedstone.spriteTopOff;
        } else
        {
            return mod_WirelessRedstone.spriteTOff;
        }
    }

    public TileEntity a_()
    {
        return new TileEntityRedstoneWirelessT();
    }

    public boolean a()
    {
        return false;
    }
}
