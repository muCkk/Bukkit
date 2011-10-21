// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.server;


// Referenced classes of package net.minecraft.server:
//            Container, TileEntityRedstoneWireless, IInventory, EntityHuman

public abstract class ContainerRedstoneWireless extends Container
{

    public ContainerRedstoneWireless(IInventory iinventory, TileEntityRedstoneWireless tileentityredstonewireless)
    {
        field_21149_a = tileentityredstonewireless;
    }

    public boolean b(EntityHuman entityhuman)
    {
        return field_21149_a.a(entityhuman);
    }

    protected TileEntityRedstoneWireless field_21149_a;
}
