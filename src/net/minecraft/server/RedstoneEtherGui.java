// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import net.minecraft.server.MinecraftServer;

// Referenced classes of package net.minecraft.server:
//            RedstoneEtherNode, RedstoneEther, ModLoader, WorldServer, 
//            mod_WirelessRedstone, Block

public class RedstoneEtherGui extends JFrame
{
    private class RedstoneEtherNodeGuiPanel extends JPanel
        implements MouseListener
    {

        public void setSelectedNode(RedstoneEtherNode redstoneethernode, int i)
        {
            selectedNode = redstoneethernode;
            selectedNodeType = i;
            repaint();
        }

        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D graphics2d = (Graphics2D)g;
            drawNode(graphics2d);
        }

        public void drawNode(Graphics2D graphics2d)
        {
            if(selectedNode != null)
            {
                graphics2d.drawString((new StringBuilder()).append("Node: (").append(selectedNode.i).append(",").append(selectedNode.j).append(",").append(selectedNode.k).append(")").toString(), 10, 20);
                if(selectedNodeType == 1)
                {
                    graphics2d.drawString("Type: Receiver", 20, 60);
                } else
                {
                    graphics2d.drawString("Type: Transmitter", 20, 60);
                }
                graphics2d.drawString((new StringBuilder()).append("Frequency: ").append(selectedNode.freq).toString(), 20, 80);
                SimpleDateFormat simpledateformat = new SimpleDateFormat("hh:mm:ss.SSS");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(selectedNode.time);
                graphics2d.drawString((new StringBuilder()).append("Timestamp: ").append(simpledateformat.format(calendar.getTime())).toString(), 20, 100);
                if(selectedNodeType == 2)
                {
                    graphics2d.drawString((new StringBuilder()).append("Node State: ").append(selectedNode.state).toString(), 20, 120);
                }
                graphics2d.drawString((new StringBuilder()).append("Frequency State: ").append(RedstoneEther.getInstance().getFreqState(selectedNode.freq)).toString(), 20, 140);
                graphics2d.drawString((new StringBuilder()).append("Block ID: ").append(ModLoader.getMinecraftServerInstance().worlds.get(0).getTypeId(selectedNode.i, selectedNode.j, selectedNode.k)).toString(), 20, 160);
                graphics2d.drawString((new StringBuilder()).append("Chunk Coord: ").append(selectedNode.ci).append(",").append(selectedNode.ck).toString(), 20, 180);
                graphics2d.setColor(Color.LIGHT_GRAY);
                graphics2d.fillRect(20, 220, 50, 20);
                graphics2d.setColor(Color.BLACK);
                graphics2d.drawString("Delete", 20, 235);
            }
        }

        public void mouseClicked(MouseEvent mouseevent)
        {
            if(mouseevent.getPoint().x > 20 && mouseevent.getPoint().x < 70 && mouseevent.getPoint().y > 200 && mouseevent.getPoint().y < 220)
            {
                if(selectedNodeType == 1)
                {
                    RedstoneEther.getInstance().remReceiver(ModLoader.getMinecraftServerInstance().worlds.get(0), selectedNode.i, selectedNode.j, selectedNode.k, selectedNode.freq);
                    mod_WirelessRedstone.blockWirelessR.g(ModLoader.getMinecraftServerInstance().worlds.get(0), selectedNode.i, selectedNode.j, selectedNode.k, ModLoader.getMinecraftServerInstance().worlds.get(0).getData(selectedNode.i, selectedNode.j, selectedNode.k));
                } else
                if(selectedNodeType == 2)
                {
                    RedstoneEther.getInstance().remTransmitter(ModLoader.getMinecraftServerInstance().worlds.get(0), selectedNode.i, selectedNode.j, selectedNode.k, selectedNode.freq);
                    mod_WirelessRedstone.blockWirelessT.g(ModLoader.getMinecraftServerInstance().worlds.get(0), selectedNode.i, selectedNode.j, selectedNode.k, ModLoader.getMinecraftServerInstance().worlds.get(0).getData(selectedNode.i, selectedNode.j, selectedNode.k));
                }
                ModLoader.getMinecraftServerInstance().worlds.get(0).setTypeId(selectedNode.i, selectedNode.j, selectedNode.k, 0);
            }
        }

        public void mouseEntered(MouseEvent mouseevent)
        {
        }

        public void mouseExited(MouseEvent mouseevent)
        {
        }

        public void mousePressed(MouseEvent mouseevent)
        {
        }

        public void mouseReleased(MouseEvent mouseevent)
        {
        }

        private static final long serialVersionUID = 1L;
        private RedstoneEtherNode selectedNode;
        private int selectedNodeType;

        public RedstoneEtherNodeGuiPanel()
        {
            setPreferredSize(new Dimension(500, 400));
            addMouseListener(this);
            setBackground(Color.white);
            setBorder(BorderFactory.createLineBorder(Color.black));
        }
    }

    private class RedstoneEtherGuiPanel extends JPanel
        implements MouseListener
    {

        public void assNodePanel(RedstoneEtherNodeGuiPanel redstoneethernodeguipanel)
        {
            nodePanel = redstoneethernodeguipanel;
        }

        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D graphics2d = (Graphics2D)g;
            drawActiveFrequencies(graphics2d);
            drawNodes(graphics2d);
        }

        private void drawActiveFrequencies(Graphics2D graphics2d)
        {
            Map map = RedstoneEther.getInstance().getLoadedFrequencies();
            graphics2d.drawString("Active Freqs:", 5, 20);
            int i = 3;
            for(Iterator iterator = map.keySet().iterator(); iterator.hasNext();)
            {
                Serializable serializable = (Serializable)iterator.next();
                if(RedstoneEther.getInstance().getFreqState(serializable))
                {
                    graphics2d.setColor(Color.GREEN);
                } else
                {
                    graphics2d.setColor(Color.RED);
                }
                graphics2d.drawString((new StringBuilder()).append(serializable).append(": ").append(map.get(serializable)).toString(), 5, i * 10);
                i++;
            }

            graphics2d.setColor(Color.BLACK);
        }

        private void drawNodes(Graphics2D graphics2d)
        {
            java.util.List list = RedstoneEther.getInstance().getRXNodes();
            java.util.List list1 = RedstoneEther.getInstance().getTXNodes();
            bounds = new HashMap();
            graphics2d.drawString("RXes:", 100, 20);
            int i = 3;
            for(Iterator iterator = list.iterator(); iterator.hasNext();)
            {
                RedstoneEtherNode redstoneethernode = (RedstoneEtherNode)iterator.next();
                if(RedstoneEther.getInstance().getFreqState(redstoneethernode.freq))
                {
                    graphics2d.setColor(Color.GREEN);
                } else
                {
                    graphics2d.setColor(Color.RED);
                }
                graphics2d.drawString((new StringBuilder()).append("(").append(redstoneethernode.i).append(",").append(redstoneethernode.j).append(",").append(redstoneethernode.k).append("):[").append(redstoneethernode.freq).append("]").toString(), 100, i * 10);
                Point apoint[] = {
                    new Point(100, (i - 1) * 10), new Point(299, (i - 1) * 10 + 9)
                };
                bounds.put(apoint, redstoneethernode);
                i++;
            }

            graphics2d.setColor(Color.BLACK);
            graphics2d.drawString("TXes:", 300, 20);
            i = 3;
            for(Iterator iterator1 = list1.iterator(); iterator1.hasNext();)
            {
                RedstoneEtherNode redstoneethernode1 = (RedstoneEtherNode)iterator1.next();
                if(redstoneethernode1.state)
                {
                    graphics2d.setColor(Color.GREEN);
                } else
                {
                    graphics2d.setColor(Color.RED);
                }
                graphics2d.drawString((new StringBuilder()).append("(").append(redstoneethernode1.i).append(",").append(redstoneethernode1.j).append(",").append(redstoneethernode1.k).append("):[").append(redstoneethernode1.freq).append("]").toString(), 300, i * 10);
                Point apoint1[] = {
                    new Point(300, (i - 1) * 10), new Point(500, (i - 1) * 10 + 9)
                };
                bounds.put(apoint1, redstoneethernode1);
                i++;
            }

            graphics2d.setColor(Color.BLACK);
        }

        public void mouseClicked(MouseEvent mouseevent)
        {
            repaint();
            if(nodePanel == null)
            {
                return;
            }
            for(Iterator iterator = bounds.keySet().iterator(); iterator.hasNext();)
            {
                Point apoint[] = (Point[])iterator.next();
                if(mouseevent.getPoint().x > apoint[0].x && mouseevent.getPoint().x < apoint[1].x && mouseevent.getPoint().y > apoint[0].y && mouseevent.getPoint().y < apoint[1].y)
                {
                    RedstoneEtherNode redstoneethernode = (RedstoneEtherNode)bounds.get(apoint);
                    if(RedstoneEther.getInstance().getRXNodes().indexOf(redstoneethernode) > -1)
                    {
                        nodePanel.setSelectedNode(redstoneethernode, 1);
                    } else
                    {
                        nodePanel.setSelectedNode(redstoneethernode, 2);
                    }
                    return;
                }
            }

            nodePanel.setSelectedNode(null, 0);
        }

        public void mouseEntered(MouseEvent mouseevent)
        {
        }

        public void mouseExited(MouseEvent mouseevent)
        {
        }

        public void mousePressed(MouseEvent mouseevent)
        {
        }

        public void mouseReleased(MouseEvent mouseevent)
        {
        }

        private static final long serialVersionUID = 1L;
        private Map bounds;
        private RedstoneEtherNodeGuiPanel nodePanel;

        public RedstoneEtherGuiPanel()
        {
            setPreferredSize(new Dimension(500, 200));
            addMouseListener(this);
            setBackground(Color.white);
            setBorder(BorderFactory.createLineBorder(Color.black));
            bounds = new HashMap();
        }
    }


    public RedstoneEtherGui()
    {
        setTitle("Ether GUI");
        setLocationRelativeTo(null);
        JPanel jpanel = new JPanel();
        jpanel.setPreferredSize(new Dimension(500, 600));
        etherPanel = new RedstoneEtherGuiPanel();
        jpanel.add(etherPanel);
        nodePanel = new RedstoneEtherNodeGuiPanel();
        jpanel.add(nodePanel);
        etherPanel.assNodePanel(nodePanel);
        add(jpanel);
        pack();
    }

    private static final long serialVersionUID = 1L;
    private RedstoneEtherGuiPanel etherPanel;
    private RedstoneEtherNodeGuiPanel nodePanel;
}
