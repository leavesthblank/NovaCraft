package com.NovaCraftBlocks.plants;

import com.NovaCraftBlocks.NovaCraftBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockMushroom;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class BlockCaveFlower extends BlockMushroom {

    public BlockCaveFlower() {
        super();
        this.setHardness(0.1F);
        this.setResistance(0.1F);
        this.setStepSound(soundTypeGrass);
        this.setHarvestLevel("axe", 0);
        this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 0.3F, 0.7F);
    }

    public int getRenderType() {
        return 1;
    }

    protected boolean canPlaceBlockOn(Block p_149854_1_)
    {
        return (p_149854_1_ == NovaCraftBlocks.moss_block || p_149854_1_ == NovaCraftBlocks.arctic_moss_block || p_149854_1_ == Blocks.dirt);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
        return null;
    }

}
