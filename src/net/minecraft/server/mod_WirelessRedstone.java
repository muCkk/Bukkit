// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.server;

import java.awt.GraphicsEnvironment;
import java.io.*;
import java.util.Properties;

// Referenced classes of package net.minecraft.server:
//            BaseModMp, ModLoader, TileEntityRedstoneWirelessR, TileEntityRedstoneWirelessT, 
//            RedstoneEtherGui, RedstoneEther, PacketRedstoneEther, Packet, 
//            PacketOpenWindowRedstoneWireless, PacketHandlerRedstoneWireless, ItemStack, Item, 
//            Block, BlockRedstoneWirelessR, BlockRedstoneWirelessT, EntityPlayer, 
//            Packet230ModLoader

public class mod_WirelessRedstone extends BaseModMp
{

    public mod_WirelessRedstone()
    {
    	
        ModLoader.RegisterTileEntity(net.minecraft.server.TileEntityRedstoneWirelessR.class, "Wireless Receiver");
        
        ModLoader.RegisterTileEntity(net.minecraft.server.TileEntityRedstoneWirelessT.class, "Wireless Transmitter");
        instance = this;
        if(!GraphicsEnvironment.isHeadless() && guiOn)
        {
            RedstoneEtherGui redstoneethergui = new RedstoneEtherGui();
            RedstoneEther.getInstance().assGui(redstoneethergui);
            redstoneethergui.setVisible(true);
        }
        Packet.a(etherPackID, true, true, net.minecraft.server.PacketRedstoneEther.class);
        Packet.a(guiPackID, true, true, net.minecraft.server.PacketOpenWindowRedstoneWireless.class);
    }

    public String Version()
    {
        return "1.4_1.8.1";
    }

    public String toString()
    {
        return (new StringBuilder()).append("WirelessRedstone ").append(Version()).toString();
    }

    public void HandleLogin(EntityPlayer entityplayer)
    {
        Thread thread = new Thread((new Runnable() {

            public Runnable setPlayer(EntityPlayer entityplayer1)
            {
                entityplayermp = entityplayer1;
                return this;
            }

            public void run()
            {
                try
                {
                    Thread.sleep(10000L);
                }
                catch(InterruptedException interruptedexception)
                {
                    interruptedexception.printStackTrace();
                }
                PacketHandlerRedstoneWireless.PacketHandlerOutput.sendEtherTilesTo(entityplayermp);
            }

            public EntityPlayer entityplayermp;

        }
).setPlayer(entityplayer));
        thread.start();
    }

    public void HandlePacket(Packet230ModLoader packet230modloader, EntityPlayer entityplayer)
    {
        PacketHandlerRedstoneWireless.handlePacket(packet230modloader, entityplayer);
    }

    public static void AddRecipes()
    {
        ModLoader.AddRecipe(new ItemStack(blockWirelessR, 1), new Object[] {
            "IRI", "RLR", "IRI", Character.valueOf('I'), Item.IRON_INGOT, Character.valueOf('R'), Item.REDSTONE, Character.valueOf('L'), Block.LEVER
        });
        ModLoader.AddRecipe(new ItemStack(blockWirelessT, 1), new Object[] {
            "IRI", "RTR", "IRI", Character.valueOf('I'), Item.IRON_INGOT, Character.valueOf('R'), Item.REDSTONE, Character.valueOf('T'), Block.REDSTONE_TORCH_ON
        });
    }

    private static void loadProperties(File file)
    {
        Properties properties = new Properties();
        System.out.println("WirelessRedstone: Loading Properties.");
        try
        {
            if(file.canRead())
            {
                properties.load(new FileInputStream(file));
                rxID = Integer.parseInt(properties.getProperty("WirelessRedstone.ReceiverID", "-1"));
                txID = Integer.parseInt(properties.getProperty("WirelessRedstone.TransmitterID", "-1"));
                int i = Integer.parseInt(properties.getProperty("WirelessRedstone.GuiEnabled", "-1"));
                guiPackID = Integer.parseInt(properties.getProperty("WirelessRedstone.Packet.Gui", "-1"));
                etherPackID = Integer.parseInt(properties.getProperty("WirelessRedstone.Packet.Ether", "-1"));
                if(rxID == -1 || txID == -1 || i == -1)
                {
                    if(rxID == -1)
                    {
                        System.out.println("WirelessRedstone: ReceiverID not found, restoring to default");
                        rxID = 113;
                    }
                    if(txID == -1)
                    {
                        System.out.println("WirelessRedstone: ReceiverID not found, restoring to default");
                        txID = 115;
                    }
                    if(i == -1)
                    {
                        System.out.println("WirelessRedstone: GuiEnabled not found, restoring to default");
                        guiOn = false;
                    }
                    if(guiPackID == -1)
                    {
                        System.out.println("WirelessRedstone: Packet.Gui not found, restoring to default");
                        guiPackID = 236;
                    }
                    if(etherPackID == -1)
                    {
                        System.out.println("WirelessRedstone: Packet.Ether not found, restoring to default");
                        etherPackID = 235;
                    }
                    saveProperties(file);
                }
                if(i == 1)
                {
                    guiOn = true;
                }
                System.out.println((new StringBuilder()).append("WirelessRedstone: ReceiverID=").append(rxID).toString());
                System.out.println((new StringBuilder()).append("WirelessRedstone: TransmitterID=").append(txID).toString());
                System.out.println((new StringBuilder()).append("WirelessRedstone: GuiEnabled=").append(guiOn).toString());
                System.out.println((new StringBuilder()).append("WirelessRedstone: Packet.Gui=").append(guiPackID).toString());
                System.out.println((new StringBuilder()).append("WirelessRedstone: Packet.Ether=").append(etherPackID).toString());
            } else
            if(!file.exists())
            {
                System.out.println("WirelessRedstone: Properties file not found, creating.");
                saveProperties(file);
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
            txID = 115;
            rxID = 113;
            guiOn = false;
            etherPackID = 235;
            guiPackID = 236;
            saveProperties(file);
        }
    }

    private static void saveProperties(File file)
    {
        Properties properties = new Properties();
        try
        {
            if(!file.exists())
            {
                file.createNewFile();
            }
            if(file.canWrite())
            {
                properties.load(new FileInputStream(file));
                System.out.println("WirelessRedstone: Saving Properties.");
                properties.setProperty("WirelessRedstone.ReceiverID", (new StringBuilder()).append(rxID).append("").toString());
                properties.setProperty("WirelessRedstone.TransmitterID", (new StringBuilder()).append(txID).append("").toString());
                if(guiOn)
                {
                    properties.setProperty("WirelessRedstone.GuiEnabled", "1");
                } else
                {
                    properties.setProperty("WirelessRedstone.GuiEnabled", "0");
                }
                properties.setProperty("WirelessRedstone.Packet.Gui", (new StringBuilder()).append(guiPackID).append("").toString());
                properties.setProperty("WirelessRedstone.Packet.Ether", (new StringBuilder()).append(etherPackID).append("").toString());
                properties.store(new FileOutputStream(file), "WirelessRedstone Properties");
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
    }

    public static void loadBlockTextures()
    {
        spriteTopOn = ModLoader.addOverride("/terrain.png", "/WirelessSprites/topOn.png");
        spriteTopOff = ModLoader.addOverride("/terrain.png", "/WirelessSprites/topOff.png");
        spriteROn = ModLoader.addOverride("/terrain.png", "/WirelessSprites/rxOn.png");
        spriteROff = ModLoader.addOverride("/terrain.png", "/WirelessSprites/rxOff.png");
        spriteTOn = ModLoader.addOverride("/terrain.png", "/WirelessSprites/txOn.png");
        spriteTOff = ModLoader.addOverride("/terrain.png", "/WirelessSprites/txOff.png");
    }

    public static Block blockWirelessR;
    public static Block blockWirelessT;
    public static int rxID;
    public static int txID;
    public static int etherPackID = 235;
    public static int guiPackID = 236;
    public static boolean guiOn = false;
    public static int spriteTopOn;
    public static int spriteTopOff;
    public static int spriteROn;
    public static int spriteROff;
    public static int spriteTOn;
    public static int spriteTOff;
    public static BaseModMp instance;

    static 
    {
        rxID = 113;
        txID = 115;
        loadProperties(new File("wirelessRedstone.properties"));
        loadBlockTextures();
        blockWirelessR = (new BlockRedstoneWirelessR(rxID)).c(1.0F).a(Block.i).a("wifir");
        ModLoader.RegisterBlock(blockWirelessR);
        blockWirelessT = (new BlockRedstoneWirelessT(txID)).c(1.0F).a(Block.i).a("wifit");
        ModLoader.RegisterBlock(blockWirelessT);
        AddRecipes();
    }
}
