// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.server;

import java.io.*;
import java.util.*;
import javax.swing.JFrame;

// Referenced classes of package net.minecraft.server:
//            RedstoneEtherNode, World, Chunk, RedstoneEtherFrequency

public class RedstoneEther
    implements Serializable
{

    private RedstoneEther()
    {
        currentWorldHash = "";
        ether = new HashMap();
    }

    public static RedstoneEther getInstance()
    {
        if(instance == null)
        {
            instance = new RedstoneEther();
        }
        return instance;
    }

    public void assGui(JFrame jframe)
    {
        gui = jframe;
    }

    public void addTransmitter(World world, int i, int j, int k, Serializable serializable)
    {
        checkWorldHash(world);
        if(!freqIsset(serializable))
        {
            createFreq(serializable);
        }
        RedstoneEtherNode redstoneethernode = new RedstoneEtherNode(i, j, k);
        Chunk chunk = world.getChunkAtWorldCoords(i, k);
        redstoneethernode.setChunkChords(chunk.x, chunk.z);
        redstoneethernode.freq = serializable;
        ((RedstoneEtherFrequency)ether.get(serializable)).addTransmitter(redstoneethernode);
        saveEther();
        if(gui != null)
        {
            gui.repaint();
        }
    }

    public void remTransmitter(World world, int i, int j, int k, Serializable serializable)
    {
        checkWorldHash(world);
        if(freqIsset(serializable))
        {
            ((RedstoneEtherFrequency)ether.get(serializable)).remTransmitter(world, i, j, k);
            if(((RedstoneEtherFrequency)ether.get(serializable)).count() == 0)
            {
                ether.remove(serializable);
            }
        }
        saveEther();
        if(gui != null)
        {
            gui.repaint();
        }
    }

    public void addReceiver(World world, int i, int j, int k, Serializable serializable)
    {
        checkWorldHash(world);
        if(!freqIsset(serializable))
        {
            createFreq(serializable);
        }
        RedstoneEtherNode redstoneethernode = new RedstoneEtherNode(i, j, k);
        Chunk chunk = world.getChunkAtWorldCoords(i, k);
        redstoneethernode.setChunkChords(chunk.x, chunk.z);
        redstoneethernode.freq = serializable;
        ((RedstoneEtherFrequency)ether.get(serializable)).addReceiver(redstoneethernode);
        saveEther();
        if(gui != null)
        {
            gui.repaint();
        }
    }

    public void remReceiver(World world, int i, int j, int k, Serializable serializable)
    {
        checkWorldHash(world);
        if(freqIsset(serializable))
        {
            ((RedstoneEtherFrequency)ether.get(serializable)).remReceiver(i, j, k);
            if(((RedstoneEtherFrequency)ether.get(serializable)).count() == 0)
            {
                ether.remove(serializable);
            }
        }
        saveEther();
        if(gui != null)
        {
            gui.repaint();
        }
    }

    private void checkWorldHash(World world)
    {
        if(world != null && !getWorldName().equals(currentWorldHash))
        {
            ether = new HashMap();
            currentWorldHash = getWorldName();
            loadEther(world);
        }
    }

    private void createFreq(Serializable serializable)
    {
        ether.put(serializable, new RedstoneEtherFrequency());
    }

    private boolean freqIsset(Serializable serializable)
    {
        return ether.containsKey(serializable);
    }

    public boolean getFreqState(Serializable serializable)
    {
        if(!freqIsset(serializable))
        {
            return false;
        } else
        {
            return ((RedstoneEtherFrequency)ether.get(serializable)).getState();
        }
    }

    public void setTransmitterState(World world, int i, int j, int k, Serializable serializable, boolean flag)
    {
        if(freqIsset(serializable))
        {
            ((RedstoneEtherFrequency)ether.get(serializable)).setTransmitterState(world, i, j, k, flag);
        }
        saveEther();
        if(gui != null)
        {
            gui.repaint();
        }
    }

    public int[] getClosestActiveTransmitter(int i, int j, Serializable serializable)
    {
        if(freqIsset(serializable))
        {
            return ((RedstoneEtherFrequency)ether.get(serializable)).getClosestActiveTransmitter(i, j);
        } else
        {
            return null;
        }
    }

    public static float pythagoras(int ai[], int ai1[])
    {
        double d = 0.0D;
        if(ai.length <= ai1.length)
        {
            for(int i = 0; i < ai.length; i++)
            {
                d += Math.pow(ai[i] - ai1[i], 2D);
            }

        } else
        {
            for(int j = 0; j < ai1.length; j++)
            {
                d += Math.pow(ai[j] - ai1[j], 2D);
            }

        }
        return (float)Math.sqrt(d);
    }

    public List getRXNodes()
    {
        LinkedList linkedlist = new LinkedList();
        HashMap hashmap = (HashMap)((HashMap)ether).clone();
        RedstoneEtherFrequency redstoneetherfrequency;
        for(Iterator iterator = hashmap.values().iterator(); iterator.hasNext(); linkedlist.addAll(redstoneetherfrequency.rxs.keySet()))
        {
            redstoneetherfrequency = (RedstoneEtherFrequency)iterator.next();
        }

        return linkedlist;
    }

    public List getTXNodes()
    {
        LinkedList linkedlist = new LinkedList();
        HashMap hashmap = (HashMap)((HashMap)ether).clone();
        RedstoneEtherFrequency redstoneetherfrequency;
        for(Iterator iterator = hashmap.values().iterator(); iterator.hasNext(); linkedlist.addAll(redstoneetherfrequency.txs.keySet()))
        {
            redstoneetherfrequency = (RedstoneEtherFrequency)iterator.next();
        }

        return linkedlist;
    }

    public Map getLoadedFrequencies()
    {
        HashMap hashmap = new HashMap();
        HashMap hashmap1 = (HashMap)((HashMap)ether).clone();
        Serializable serializable;
        for(Iterator iterator = hashmap1.keySet().iterator(); iterator.hasNext(); hashmap.put(serializable, Integer.valueOf(((RedstoneEtherFrequency)hashmap1.get(serializable)).count())))
        {
            serializable = (Serializable)iterator.next();
        }

        return hashmap;
    }

    private String getWorldName()
    {
        String s = "";
        File file = new File("server.properties");
        Properties properties = new Properties();
        try
        {
            if(file.canRead())
            {
                properties.load(new FileInputStream(file));
                s = properties.getProperty("level-name", "");
            } else
            {
                throw new IOException("WirelessRedstone: Unable to handle Properties file!");
            }
        }
        catch(FileNotFoundException filenotfoundexception)
        {
            filenotfoundexception.printStackTrace();
        }
        catch(IOException ioexception)
        {
            ioexception.printStackTrace();
        }
        catch(NumberFormatException numberformatexception)
        {
            numberformatexception.printStackTrace();
        }
        return s;
    }

    public void saveEther()
    {
        String s = getWorldName();
        try
        {
            FileOutputStream fileoutputstream = new FileOutputStream((new StringBuilder()).append(s).append(".redstoneEther.ser").toString());
            ObjectOutputStream objectoutputstream = new ObjectOutputStream(fileoutputstream);
            objectoutputstream.writeObject(ether);
            objectoutputstream.close();
            fileoutputstream.close();
        }
        catch(FileNotFoundException filenotfoundexception)
        {
            filenotfoundexception.printStackTrace();
        }
        catch(IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    public void loadEther(World world)
    {
        String s = getWorldName();
        try
        {
            FileInputStream fileinputstream = new FileInputStream((new StringBuilder()).append(s).append(".redstoneEther.ser").toString());
            ObjectInputStream objectinputstream = new ObjectInputStream(fileinputstream);
            ether = (Map)objectinputstream.readObject();
            objectinputstream.close();
            fileinputstream.close();
        }
        catch(FileNotFoundException filenotfoundexception)
        {
            saveEther();
        }
        catch(IOException ioexception)
        {
            ioexception.printStackTrace();
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            classnotfoundexception.printStackTrace();
        }
    }

    private static final long serialVersionUID = 0x8713914749e6fa90L;
    private Map ether;
    private String currentWorldHash;
    private static RedstoneEther instance;
    private JFrame gui;
}
