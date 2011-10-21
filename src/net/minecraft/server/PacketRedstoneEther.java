// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.server;

import java.io.*;

// Referenced classes of package net.minecraft.server:
//            Packet230ModLoader, NetHandler

public class PacketRedstoneEther extends Packet230ModLoader
{

    public PacketRedstoneEther()
    {
        i = 0;
        timestamp = 0;
        k = 0;
        freq = Integer.valueOf(0);
        state = false;
    }

    public PacketRedstoneEther(String s)
    {
        this();
        command = s;
    }

    public void setPosition(int j, int k, int l)
    {
        i = j;
        timestamp = k;
        k = l;
    }

    public void setFreq(Object obj)
    {
        freq = obj;
    }

    public void setState(boolean flag)
    {
        state = flag;
    }

    public void a(DataInputStream datainputstream)
        throws IOException
    {
        super.a(datainputstream);
        i = datainputstream.readInt();
        timestamp = datainputstream.readInt();
        k = datainputstream.readInt();
        freq = a(datainputstream, 15000);
        state = datainputstream.readBoolean();
        command = a(datainputstream, 20);
    }

    public void a(DataOutputStream dataoutputstream)
        throws IOException
    {
        super.a(dataoutputstream);
        dataoutputstream.writeInt(i);
        dataoutputstream.writeInt(timestamp);
        dataoutputstream.writeInt(k);
        PacketRedstoneEther _tmp = this;
        a(freq.toString(), dataoutputstream);
        dataoutputstream.writeBoolean(state);
        PacketRedstoneEther _tmp1 = this;
        a(command, dataoutputstream);
    }

    public void a(NetHandler nethandler)
    {
        super.a(nethandler);
    }

    public int a()
    {
        return super.a() + 17 + command.length();
    }

    public int i;
    public int timestamp;
    public int k;
    public Object freq;
    public boolean state;
    public String command;
}
