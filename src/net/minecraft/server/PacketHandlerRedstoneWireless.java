// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.server;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

// Referenced classes of package net.minecraft.server:
//            PacketRedstoneEther, EntityPlayer, Packet230ModLoader, World, 
//            TileEntityRedstoneWireless, TileEntityRedstoneWirelessT, TileEntity, mod_WirelessRedstone, 
//            ModLoaderMp, PacketOpenWindowRedstoneWireless, RedstoneEther, RedstoneEtherNode

public class PacketHandlerRedstoneWireless
{
    private static class PacketHandlerInput
    {

        public static void handleEther(PacketRedstoneEther packetredstoneether, World world, EntityPlayer entityplayer)
        {
            if(packetredstoneether.command.equals("changeFreq"))
            {
                TileEntity tileentity = world.getTileEntity(packetredstoneether.i, packetredstoneether.timestamp, packetredstoneether.k);
                System.out.println(tileentity);
                System.out.println((new StringBuilder()).append(packetredstoneether.i).append(",").append(packetredstoneether.timestamp).append(",").append(packetredstoneether.k).toString());
                if(tileentity instanceof TileEntityRedstoneWireless)
                {
                    int i = Integer.parseInt(packetredstoneether.freq.toString());
                    int j = Integer.parseInt(((TileEntityRedstoneWireless)tileentity).getFreq().toString());
                    ((TileEntityRedstoneWireless)tileentity).setFreq(j + i);
                    packetredstoneether.freq = ((TileEntityRedstoneWireless)tileentity).getFreq();
                    if(tileentity instanceof TileEntityRedstoneWirelessT)
                    {
                        packetredstoneether.command = "addTransmitter";
                    } else
                    {
                        packetredstoneether.command = "addReceiver";
                    }
                    ModLoaderMp.SendPacketToAll(mod_WirelessRedstone.instance, packetredstoneether);
                }
            } else
            {
                System.out.println(packetredstoneether.command);
            }
        }

        private PacketHandlerInput()
        {
        }
    }

    public static class PacketHandlerOutput
    {

        public static void sendGuiPacketTo(EntityPlayer entityplayer, TileEntityRedstoneWireless tileentityredstonewireless)
        {
            PacketOpenWindowRedstoneWireless packetopenwindowredstonewireless = new PacketOpenWindowRedstoneWireless(tileentityredstonewireless);
            ModLoaderMp.SendPacketTo(mod_WirelessRedstone.instance, entityplayer, packetopenwindowredstonewireless);
        }

        public static void sendEtherTilesTo(EntityPlayer entityplayer)
        {
            List list = RedstoneEther.getInstance().getRXNodes();
            PacketRedstoneEther packetredstoneether;
            for(Iterator iterator = list.iterator(); iterator.hasNext(); ModLoaderMp.SendPacketTo(mod_WirelessRedstone.instance, entityplayer, packetredstoneether))
            {
                RedstoneEtherNode redstoneethernode = (RedstoneEtherNode)iterator.next();
                packetredstoneether = new PacketRedstoneEther("addReceiver");
                packetredstoneether.setFreq(redstoneethernode.freq);
                packetredstoneether.setPosition(redstoneethernode.i, redstoneethernode.j, redstoneethernode.k);
                packetredstoneether.setState(redstoneethernode.state);
            }

            list = RedstoneEther.getInstance().getTXNodes();
            PacketRedstoneEther packetredstoneether1;
            for(Iterator iterator1 = list.iterator(); iterator1.hasNext(); ModLoaderMp.SendPacketTo(mod_WirelessRedstone.instance, entityplayer, packetredstoneether1))
            {
                RedstoneEtherNode redstoneethernode1 = (RedstoneEtherNode)iterator1.next();
                packetredstoneether1 = new PacketRedstoneEther("addTransmitter");
                packetredstoneether1.setFreq(redstoneethernode1.freq);
                packetredstoneether1.setPosition(redstoneethernode1.i, redstoneethernode1.j, redstoneethernode1.k);
                packetredstoneether1.setState(redstoneethernode1.state);
            }

        }

        public PacketHandlerOutput()
        {
        }
    }


    public PacketHandlerRedstoneWireless()
    {
    }

    public static void handlePacket(Packet230ModLoader packet230modloader, EntityPlayer entityplayer)
    {
        if(packet230modloader instanceof PacketRedstoneEther)
        {
            PacketHandlerInput.handleEther((PacketRedstoneEther)packet230modloader, entityplayer.world, entityplayer);
        }
    }
}
