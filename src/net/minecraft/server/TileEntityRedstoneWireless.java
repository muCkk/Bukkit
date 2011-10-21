// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.server;

import java.io.Serializable;

// Referenced classes of package net.minecraft.server:
//            TileEntity, IInventory, NBTTagCompound, NBTTagList, 
//            World, EntityHuman, ItemStack

public abstract class TileEntityRedstoneWireless extends TileEntity
    implements IInventory
{

    public TileEntityRedstoneWireless()
    {
        firstTick = true;
        block = new int[3];
        firstTick = true;
        oldFreq = Integer.valueOf(0);
        currentFreq = Integer.valueOf(0);
        flushPowerRoute();
        flushIndirPower();
    }

    public void assBlock(int i, int j, int k)
    {
        block[0] = i;
        block[1] = j;
        block[2] = k;
        firstTick = false;
    }

    public int getSize()
    {
        return 0;
    }

    public ItemStack getItem(int i)
    {
        return null;
    }

    public Serializable getFreq()
    {
        return currentFreq;
    }

    public void setFreq(int i)
    {
        if(i < 0)
        {
            i = 0;
        }
        if(i > 9999)
        {
            i = 9999;
        }
        currentFreq = Integer.valueOf(i);
        h_();
    }

    public int getBlockCoord(int i)
    {
        if(i < 3)
        {
            return block[i];
        } else
        {
            return 0;
        }
    }

    public abstract void h_();

    public ItemStack splitStack(int i, int j)
    {
        return null;
    }

    public void setItem(int i, ItemStack itemstack)
    {
        update();
    }

    public abstract String getName();

    public boolean isPoweringDirection(int i)
    {
        if(i < 6)
        {
            return powerRoute[i];
        } else
        {
            return false;
        }
    }

    public void flipPowerDirection(int i)
    {
        if(isPoweringIndirectly(i) && powerRoute[i])
        {
            flipIndirectPower(i);
        }
        powerRoute[i] = !powerRoute[i];
    }

    public void flushPowerRoute()
    {
        powerRoute = new boolean[6];
        for(int i = 0; i < powerRoute.length; i++)
        {
            powerRoute[i] = true;
        }

    }

    public void flipIndirectPower(int i)
    {
        if(!isPoweringDirection(i) && !indirPower[i])
        {
            flipPowerDirection(i);
        }
        indirPower[i] = !indirPower[i];
    }

    public boolean isPoweringIndirectly(int i)
    {
        if(i < 6)
        {
            return indirPower[i];
        } else
        {
            return false;
        }
    }

    public void flushIndirPower()
    {
        indirPower = new boolean[6];
        for(int i = 0; i < indirPower.length; i++)
        {
            indirPower[i] = true;
        }

    }

    public void a(NBTTagCompound nbttagcompound)
    {
        super.a(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.l("Coordinate");
        block = new int[3];
        for(int i = 0; i < nbttaglist.c(); i++)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.a(i);
            block[i] = nbttagcompound1.e("n");
        }

        NBTTagList nbttaglist1 = nbttagcompound.l("Frequency");
        NBTTagCompound nbttagcompound2 = (NBTTagCompound)nbttaglist1.a(0);
        try
        {
            String s = nbttagcompound2.getString("freq");
            try
            {
                currentFreq = Integer.valueOf(Integer.parseInt(s));
            }
            catch(NumberFormatException numberformatexception)
            {
                currentFreq = s;
            }
        }
        catch(ClassCastException classcastexception)
        {
            currentFreq = Integer.valueOf(nbttagcompound2.e("freq"));
        }
        NBTTagList nbttaglist2 = nbttagcompound.l("PowerRoute");
        if(nbttaglist2.c() == 6)
        {
            for(int j = 0; j < nbttaglist2.c(); j++)
            {
                NBTTagCompound nbttagcompound3 = (NBTTagCompound)nbttaglist2.a(j);
                powerRoute[j] = nbttagcompound3.m("b");
            }

        } else
        {
            flushPowerRoute();
            b(nbttagcompound);
        }
        NBTTagList nbttaglist3 = nbttagcompound.l("IndirPower");
        if(nbttaglist3.c() == 6)
        {
            for(int k = 0; k < nbttaglist3.c(); k++)
            {
                NBTTagCompound nbttagcompound4 = (NBTTagCompound)nbttaglist3.a(k);
                indirPower[k] = nbttagcompound4.m("b");
            }

        } else
        {
            flushIndirPower();
            b(nbttagcompound);
        }
    }

    public void b(NBTTagCompound nbttagcompound)
    {
        super.b(nbttagcompound);
        NBTTagList nbttaglist = new NBTTagList();
        for(int i = 0; i < block.length; i++)
        {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            nbttagcompound1.a("n", block[i]);
            nbttaglist.a(nbttagcompound1);
        }

        nbttagcompound.a("Coordinate", nbttaglist);
        NBTTagList nbttaglist1 = new NBTTagList();
        NBTTagCompound nbttagcompound2 = new NBTTagCompound();
        nbttagcompound2.setString("freq", currentFreq.toString());
        nbttaglist1.a(nbttagcompound2);
        nbttagcompound.a("Frequency", nbttaglist1);
        NBTTagList nbttaglist2 = new NBTTagList();
        for(int j = 0; j < powerRoute.length; j++)
        {
            NBTTagCompound nbttagcompound3 = new NBTTagCompound();
            nbttagcompound3.a("b", powerRoute[j]);
            nbttaglist2.a(nbttagcompound3);
        }

        nbttagcompound.a("PowerRoute", nbttaglist2);
        NBTTagList nbttaglist3 = new NBTTagList();
        for(int k = 0; k < indirPower.length; k++)
        {
            NBTTagCompound nbttagcompound4 = new NBTTagCompound();
            nbttagcompound4.a("b", indirPower[k]);
            nbttaglist3.a(nbttagcompound4);
        }

        nbttagcompound.a("IndirPower", nbttaglist3);
    }

    public int getMaxStackSize()
    {
        return 64;
    }

    public boolean a(EntityHuman entityhuman)
    {
        if(world.getTileEntity(x, y, z) != this)
        {
            return false;
        } else
        {
            return entityhuman.e((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D) <= 64D;
        }
    }

    public void e()
    {
    }

    public void t_()
    {
    }

    protected int block[];
    public boolean firstTick;
    public Serializable oldFreq;
    public Serializable currentFreq;
    protected boolean powerRoute[];
    protected boolean indirPower[];
}
