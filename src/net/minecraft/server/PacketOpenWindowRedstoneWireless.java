// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.server;

import java.io.*;

// Referenced classes of package net.minecraft.server:
//            Packet230ModLoader, TileEntityRedstoneWireless, TileEntityRedstoneWirelessR, TileEntityRedstoneWirelessT, 
//            NetHandler

public class PacketOpenWindowRedstoneWireless extends Packet230ModLoader
{

    public PacketOpenWindowRedstoneWireless()
    {
        i = 0;
        timestamp = 0;
        k = 0;
        firstTick = true;
        oldFreq = Integer.valueOf(0);
        currentFreq = Integer.valueOf(0);
        type = 0;
    }

    public PacketOpenWindowRedstoneWireless(TileEntityRedstoneWireless tileentityredstonewireless)
    {
        this();
        i = tileentityredstonewireless.getBlockCoord(0);
        timestamp = tileentityredstonewireless.getBlockCoord(1);
        k = tileentityredstonewireless.getBlockCoord(2);
        firstTick = tileentityredstonewireless.firstTick;
        currentFreq = tileentityredstonewireless.currentFreq;
        currentFreq = tileentityredstonewireless.getFreq();
        if(tileentityredstonewireless instanceof TileEntityRedstoneWirelessR)
        {
            type = 0;
        } else
        if(tileentityredstonewireless instanceof TileEntityRedstoneWirelessT)
        {
            type = 1;
        }
    }

    public void a(DataInputStream datainputstream)
        throws IOException
    {
        super.a(datainputstream);
        i = datainputstream.readInt();
        timestamp = datainputstream.readInt();
        k = datainputstream.readInt();
        firstTick = datainputstream.readBoolean();
        currentFreq = a(datainputstream, 15000);
        oldFreq = a(datainputstream, 15000);
        type = datainputstream.readInt();
    }

    public void a(DataOutputStream dataoutputstream)
        throws IOException
    {
        super.a(dataoutputstream);
        dataoutputstream.writeInt(i);
        dataoutputstream.writeInt(timestamp);
        dataoutputstream.writeInt(k);
        dataoutputstream.writeBoolean(firstTick);
        PacketOpenWindowRedstoneWireless _tmp = this;
        a(currentFreq.toString(), dataoutputstream);
        PacketOpenWindowRedstoneWireless _tmp1 = this;
        a(oldFreq.toString(), dataoutputstream);
        dataoutputstream.writeInt(type);
    }

    public void a(NetHandler nethandler)
    {
        super.a(nethandler);
    }

    public int a()
    {
        return super.a() + 4 + 4 + 4 + 1 + 4 + 4 + 4;
    }

    public int i;
    public int timestamp;
    public int k;
    public boolean firstTick;
    public Object oldFreq;
    public Object currentFreq;
    public int type;
}
