package com.NovaCraftBlocks.potion;

import java.util.Random;
import com.NovaCraft.Items.NovaCraftItems;
import com.NovaCraft.renderer.RenderIDs;
import com.NovaCraftBlocks.NovaCraftBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockCauldron;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMercuryVaniteCauldron extends BlockCauldron {

    @SideOnly(Side.CLIENT)
    public IIcon inner, top, bottom, side;

    public static IIcon Mercury;

    public BlockMercuryVaniteCauldron() {
        super();
        this.setHardness(12);
        this.setResistance(15);
        this.setTickRandomly(true);
        this.setCreativeTab((CreativeTabs)null);
    }

    protected boolean canSilkHarvest() {
        return false;
    }

    public Item getItemDropped(final int metadata, final Random rand, final int fortune) {
        return NovaCraftItems.vanite_cauldron_item;
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return NovaCraftItems.vanite_cauldron_item;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (side == 1) return this.top;
        if (side == 0) return this.bottom;
        return this.side;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.inner = register.registerIcon("nova_craft:vanite_cauldron_inner");
        this.top = register.registerIcon("nova_craft:vanite_cauldron_top");
        this.bottom = register.registerIcon("nova_craft:vanite_cauldron_bottom");
        this.side = register.registerIcon("nova_craft:vanite_cauldron_side");

        this.blockIcon = this.side;

        BlockMercuryVaniteCauldron.Mercury = register.registerIcon("nova_craft:mercury_still");
    }

    @SideOnly(Side.CLIENT)
    public static IIcon getVaniteCauldronIcon(String name) {
        BlockMoltenVaniteCauldron block = (BlockMoltenVaniteCauldron) NovaCraftBlocks.vanite_cauldron;

        if (name.equals("inner")) return block.inner;
        if (name.equals("bottom")) return block.bottom;

        return null;
    }

    @Override
    public int tickRate(World world)
    {
        return 0;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        int meta = world.getBlockMetadata(x, y, z);
        ItemStack held = player.getCurrentEquippedItem();
        if (held == null) return false;

        if (held.getItem() == (NovaCraftItems.vanite_bucket) && meta == 1) {

            if (!world.isRemote) {

                world.setBlock(x, y, z, NovaCraftBlocks.lava_vanite_cauldron, 0, 3);

                ItemStack potion = new ItemStack(NovaCraftItems.vanite_bucket_mercury, 1, 0);

                if (!player.inventory.addItemStackToInventory(potion)) {
                    player.dropPlayerItemWithRandomChoice(potion, false);
                }

                if (!player.capabilities.isCreativeMode) {
                    held.stackSize--;
                    if (held.stackSize <= 0) {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                    }
                }

                player.inventory.markDirty();
                player.inventoryContainer.detectAndSendChanges();
            }

            return true;
        }

        else if (held.getItem() == (Items.bucket) && meta == 1) {

            if (!world.isRemote) {

                world.setBlock(x, y, z, NovaCraftBlocks.lava_vanite_cauldron, 0, 3);

                ItemStack potion = new ItemStack(NovaCraftItems.bucket_mercury, 1, 0);

                if (!player.inventory.addItemStackToInventory(potion)) {
                    player.dropPlayerItemWithRandomChoice(potion, false);
                }

                if (!player.capabilities.isCreativeMode) {
                    held.stackSize--;
                    if (held.stackSize <= 0) {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                    }
                }

                player.inventory.markDirty();
                player.inventoryContainer.detectAndSendChanges();
            }

            return true;
        }

        return false;
    }


    @Override
    public int getRenderType()
    {
        return RenderIDs.MERCURY_VANITE_CAULDRON;
    }

    @SideOnly(Side.CLIENT)
    public static float getRenderLiquidLevel(int p_150025_0_)
    {
        int j = MathHelper.clamp_int(p_150025_0_, 0, 3);
        return (float)(6 + 3 * j) / 16.0F;
    }

}
