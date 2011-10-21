// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.server;

import java.io.Serializable;

public class RedstoneEtherNode
    implements Comparable, Serializable
{

    public RedstoneEtherNode(int l, int i1, int j1)
    {
        i = l;
        j = i1;
        k = j1;
        state = false;
        freq = Integer.valueOf(0);
        time = System.currentTimeMillis();
    }

    public void setChunkChords(int l, int i1)
    {
        ci = l;
        ck = i1;
    }

    public int compareTo(RedstoneEtherNode redstoneethernode)
    {
        if(redstoneethernode.i == i)
        {
            if(redstoneethernode.j == j)
            {
                if(redstoneethernode.k == k)
                {
                    return 0;
                } else
                {
                    return k - redstoneethernode.k;
                }
            } else
            {
                return j - redstoneethernode.j;
            }
        } else
        {
            return i - redstoneethernode.i;
        }
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof RedstoneEtherNode)
        {
            return ((RedstoneEtherNode)obj).i == i && ((RedstoneEtherNode)obj).j == j && ((RedstoneEtherNode)obj).k == k;
        } else
        {
            return false;
        }
    }

    public String toString()
    {
        String s = "";
        s = (new StringBuilder()).append(s).append("(").append(i).append(",").append(j).append(",").append(k).append(") ").toString();
        s = (new StringBuilder()).append(s).append("(").append(ci).append(",").append(ck).append(") ").toString();
        s = (new StringBuilder()).append(s).append("[").append(freq).append("] ").toString();
        return s;
    }

    public int compareTo(Object obj)
    {
        return compareTo((RedstoneEtherNode)obj);
    }

    private static final long serialVersionUID = 0x1fe3d3567e8169aaL;
    public int i;
    public int j;
    public int k;
    public int ci;
    public int ck;
    public boolean state;
    public Serializable freq;
    public long time;
}
