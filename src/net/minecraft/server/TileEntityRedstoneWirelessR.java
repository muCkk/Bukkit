// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.server;

import java.io.Serializable;
import java.util.Random;

// Referenced classes of package net.minecraft.server:
//            TileEntityRedstoneWireless, mod_WirelessRedstone, BlockRedstoneWirelessR, Block

public class TileEntityRedstoneWirelessR extends TileEntityRedstoneWireless
{

    public TileEntityRedstoneWirelessR()
    {
    }

    public void h_()
    {
        Serializable serializable = getFreq();
        int i = block[0];
        int j = block[1];
        int k = block[2];
        if(!oldFreq.equals(serializable) || firstTick)
        {
            ((BlockRedstoneWirelessR)mod_WirelessRedstone.blockWirelessR).changeFreq(world, i, j, k, oldFreq, serializable);
            oldFreq = serializable;
            if(firstTick)
            {
                firstTick = false;
            }
        }
        if(!((BlockRedstoneWirelessR)mod_WirelessRedstone.blockWirelessR).hasTicked())
        {
            mod_WirelessRedstone.blockWirelessR.a(world, i, j, k, (Random)null);
        }
    }

    public String getName()
    {
        return "Wireless Receiver";
    }

	@Override
	public ItemStack[] getContents() {
		// TODO Auto-generated method stub
		return null;
	}
}
