// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.server;

import java.io.Serializable;
import java.util.Random;

// Referenced classes of package net.minecraft.server:
//            BlockContainer, PacketHandlerRedstoneWireless, Material, World, 
//            TileEntity, TileEntityRedstoneWirelessR, RedstoneEther, mod_WirelessRedstone, 
//            EntityPlayer, TileEntityRedstoneWireless, IBlockAccess, EntityHuman

public class BlockRedstoneWirelessR extends BlockContainer
{

    protected BlockRedstoneWirelessR(int i)
    {
        super(i, Material.ORIENTABLE);
        a(true);
        initialSchedule = true;
    }

    public void setState(World world, int i, int j, int k, boolean flag)
    {
        int l = world.getData(i, j, k);
        l &= 0xe;
        if(flag)
        {
            l++;
        }
        world.setData(i, j, k, l);
    }

    private boolean getState(World world, int i, int j, int k)
    {
        int l = world.getData(i, j, k);
        return getState(l);
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
        if(tileentity instanceof TileEntityRedstoneWirelessR)
        {
            return ((TileEntityRedstoneWirelessR)tileentity).getFreq();
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
            TileEntityRedstoneWirelessR tileentityredstonewirelessr = (TileEntityRedstoneWirelessR)a_();
            tileentityredstonewirelessr.assBlock(i, j, k);
            world.setTileEntity(i, j, k, tileentityredstonewirelessr);
            RedstoneEther.getInstance().addReceiver(world, i, j, k, getFreq(world, i, j, k));
            world.applyPhysics(i, j, k, id);
            a(world, i, j, k, (Random)null);
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
            RedstoneEther.getInstance().remReceiver(world, i, j, k, getFreq(world, i, j, k));
            world.applyPhysics(i, j, k, id);
            super.remove(world, i, j, k);
            return;
        }
    }

    public int c()
    {
        return 10;
    }

    public int a(int i, Random random)
    {
        return mod_WirelessRedstone.rxID;
    }

    public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman)
    {
        if(world.isStatic)
        {
            return true;
        }
        TileEntity tileentity = world.getTileEntity(i, j, k);
        if(tileentity instanceof TileEntityRedstoneWirelessR)
        {
            PacketHandlerRedstoneWireless.PacketHandlerOutput.sendGuiPacketTo((EntityPlayer)entityhuman, (TileEntityRedstoneWirelessR)tileentity);
        }
        return true;
    }

    public void changeFreq(World world, int i, int j, int k, Serializable serializable, Serializable serializable1)
    {
        RedstoneEther.getInstance().remReceiver(world, i, j, k, serializable);
        RedstoneEther.getInstance().addReceiver(world, i, j, k, serializable1);
        a(world, i, j, k, (Random)null);
    }

    public boolean hasTicked()
    {
        return !initialSchedule;
    }

    public void a(World world, int i, int j, int k, Random random)
    {
        if(initialSchedule)
        {
            initialSchedule = false;
        }
        if(world == null)
        {
            return;
        }
        Serializable serializable = getFreq(world, i, j, k);
        boolean flag = getState(world, i, j, k);
        boolean flag1 = RedstoneEther.getInstance().getFreqState(serializable);
        if(flag1 != flag)
        {
            setState(world, i, j, k, flag1);
            world.notify(i, j, k);
            notifyNeighbors(world, i, j, k);
        }
    }

    public void notifyNeighbors(World world, int i, int j, int k)
    {
        world.applyPhysics(i, j, k, 0);
        world.applyPhysics(i - 1, j, k, 0);
        world.applyPhysics(i + 1, j, k, 0);
        world.applyPhysics(i, j - 1, k, 0);
        world.applyPhysics(i, j + 1, k, 0);
        world.applyPhysics(i, j, k - 1, 0);
        world.applyPhysics(i, j, k + 1, 0);
    }

    public void doPhysics(World world, int i, int j, int k, int l)
    {
        if(world.isStatic)
        {
            return;
        } else
        {
            a(world, i, j, k, (Random)null);
            return;
        }
    }

    public boolean a(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        if(iblockaccess instanceof World)
        {
            TileEntity tileentity = ((World)iblockaccess).getTileEntity(i, j, k);
            if(tileentity instanceof TileEntityRedstoneWireless)
            {
                return ((TileEntityRedstoneWireless)tileentity).isPoweringDirection(l) && getState((World)iblockaccess, i, j, k);
            }
        }
        return false;
    }

    public boolean d(World world, int i, int j, int k, int l)
    {
        return a(world, i, j, k, l);
    }

    public boolean isPowerSource()
    {
        return true;
    }

    public boolean a()
    {
        return false;
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
                return mod_WirelessRedstone.spriteROn;
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
            return mod_WirelessRedstone.spriteROff;
        }
    }

    public TileEntity a_()
    {
        return new TileEntityRedstoneWirelessR();
    }

    private boolean initialSchedule;
}
