// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.server;

import java.io.Serializable;
import java.util.*;

// Referenced classes of package net.minecraft.server:
//            RedstoneEtherNode, mod_WirelessRedstone, Block, RedstoneEther, 
//            World

public class RedstoneEtherFrequency
    implements Serializable
{

    public RedstoneEtherFrequency()
    {
        txs = new TreeMap();
        rxs = new TreeMap();
        state = false;
    }

    public boolean getState()
    {
        return state;
    }

    private void updateState(World world)
    {
        state = false;
        TreeMap treemap = (TreeMap)((TreeMap)txs).clone();
        Iterator iterator = treemap.keySet().iterator();
        do
        {
            if(!iterator.hasNext())
            {
                break;
            }
            RedstoneEtherNode redstoneethernode = (RedstoneEtherNode)iterator.next();
            if(!redstoneethernode.state)
            {
                continue;
            }
            state = true;
            break;
        } while(true);
    }

    public void setTransmitterState(World world, int i, int j, int k, boolean flag)
    {
        if(!txs.containsKey(new RedstoneEtherNode(i, j, k)))
        {
            return;
        } else
        {
            ((RedstoneEtherNode)txs.get(new RedstoneEtherNode(i, j, k))).state = flag;
            updateState(world);
            updateReceivers(world);
            return;
        }
    }

    public void addTransmitter(RedstoneEtherNode redstoneethernode)
    {
        txs.put(redstoneethernode, redstoneethernode);
    }

    public void addReceiver(RedstoneEtherNode redstoneethernode)
    {
        rxs.put(redstoneethernode, redstoneethernode);
    }

    public void remTransmitter(World world, int i, int j, int k)
    {
        if(!txs.containsKey(new RedstoneEtherNode(i, j, k)))
        {
            return;
        } else
        {
            txs.remove(new RedstoneEtherNode(i, j, k));
            updateState(world);
            updateReceivers(world);
            return;
        }
    }

    public void remReceiver(int i, int j, int k)
    {
        if(!rxs.containsKey(new RedstoneEtherNode(i, j, k)))
        {
            return;
        } else
        {
            rxs.remove(new RedstoneEtherNode(i, j, k));
            return;
        }
    }

    private void updateReceivers(World world)
    {
        RedstoneEtherNode redstoneethernode;
        for(Iterator iterator = rxs.keySet().iterator(); iterator.hasNext(); mod_WirelessRedstone.blockWirelessR.a(world, redstoneethernode.i, redstoneethernode.j, redstoneethernode.k, (Random)null))
        {
            redstoneethernode = (RedstoneEtherNode)iterator.next();
        }

    }

    public int count()
    {
        return rxs.size() + txs.size();
    }

    public int[] getClosestActiveTransmitter(int i, int j)
    {
        int ai[] = new int[2];
        int ai1[] = {
            i, j
        };
        int ai2[] = new int[2];
        boolean flag = true;
        float f = 0.0F;
        TreeMap treemap = (TreeMap)((TreeMap)txs).clone();
        Iterator iterator = treemap.keySet().iterator();
        do
        {
            if(!iterator.hasNext())
            {
                break;
            }
            RedstoneEtherNode redstoneethernode = (RedstoneEtherNode)iterator.next();
            if(redstoneethernode.state)
            {
                if(flag)
                {
                    ai = new int[2];
                    ai[0] = redstoneethernode.i;
                    ai[1] = redstoneethernode.k;
                    f = RedstoneEther.pythagoras(ai1, ai);
                    flag = false;
                } else
                {
                    ai2[0] = redstoneethernode.i;
                    ai2[1] = redstoneethernode.k;
                    if(f > RedstoneEther.pythagoras(ai1, ai2))
                    {
                        ai[0] = redstoneethernode.i;
                        ai[1] = redstoneethernode.k;
                    }
                }
            }
        } while(true);
        if(flag)
        {
            return null;
        } else
        {
            return ai;
        }
    }

    private static final long serialVersionUID = 0x291c1dfb786c525dL;
    public Map txs;
    public Map rxs;
    public boolean state;
}
