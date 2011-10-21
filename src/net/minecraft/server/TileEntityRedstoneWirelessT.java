// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.server;

import java.io.Serializable;

// Referenced classes of package net.minecraft.server:
//            TileEntityRedstoneWireless, mod_WirelessRedstone, BlockRedstoneWirelessT

public class TileEntityRedstoneWirelessT extends TileEntityRedstoneWireless
{

    public TileEntityRedstoneWirelessT()
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
            ((BlockRedstoneWirelessT)mod_WirelessRedstone.blockWirelessT).changeFreq(this, i, j, k, oldFreq, serializable);
            oldFreq = serializable;
            if(firstTick)
            {
                firstTick = false;
            }
        }
    }

    public String getName()
    {
        return "Wireless Transmitter";
    }

	@Override
	public ItemStack[] getContents() {
		// TODO Auto-generated method stub
		return null;
	}
}
