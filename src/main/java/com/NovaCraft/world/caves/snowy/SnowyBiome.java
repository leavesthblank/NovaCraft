package com.NovaCraft.world.caves.snowy;

import com.NovaCraft.world.caves.cold.BasicUndergroundBiomeNearSurface;
import com.NovaCraftBlocks.NovaCraftBlocks;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Locale;

public class SnowyBiome extends BasicUndergroundBiomeNearSurface {

    public static boolean useArcticMoss;
    public static double SnowLayerChance;
    public static double FloraChance;
    public static double OtherBlocksChance;

    public SnowyBiome() {
        super(Blocks.snow, null, null, 0, 0, 0, true);
    }

    private static boolean canReplace(Block block) {
        if (block == null) return false;
        String name = block.getUnlocalizedName();
        return name != null && (name.toLowerCase(Locale.ROOT).contains("stone") || name.toLowerCase(Locale.ROOT).contains("dirt") || name.toLowerCase(Locale.ROOT).contains("tuff")
                || name.toLowerCase(Locale.ROOT).contains("andesite") || name.toLowerCase(Locale.ROOT).contains("granite") || name.toLowerCase(Locale.ROOT).contains("diorite")) && !(name.toLowerCase(Locale.ROOT).contains("ore"));
    }

    @Override
    public void fillCeiling(World world, int x, int y, int z, Block block) {
        if (world.canBlockSeeTheSky(x, y, z)) return;
        if (!canReplace(block)) return;

        world.setBlock(x, y, z, Blocks.snow, 0, 2);
    }

    @Override
    public void fillWall(World world, int x, int y, int z, Block block) {
        if (world.canBlockSeeTheSky(x, y, z)) return;
        if (!canReplace(block)) return;

        fillCeiling(world, x, y, z, block);
    }

    private static boolean canSeeSky(World world, int x, int y, int z) {
        return world.canBlockSeeTheSky(x, y, z);
    }

    @Override
    public void fillFloor(World world, int x, int y, int z, Block block) {
        if (!canReplace(block)) return;

        world.setBlock(x, y, z, Blocks.snow, 0, 2);

        if (canSeeSky(world, x, y, z) || canSeeSky(world, x, y + 1, z) || canSeeSky(world, x, y - 1, z)) {
            return;
        }

        Block placeSnowLayer;
        placeSnowLayer = Blocks.snow_layer;

        if (SnowLayerChance > 0 && world.rand.nextDouble() < SnowLayerChance) {
            int height = 3 + world.rand.nextInt(4);
            for (int i = 0; i < height; i++) {
                int yy = y + 1 + i;
                if (world.getBlock(x, y + 1, z).isAir(world, x, y + 1, z) && world.getBlock(x,y,z) != Blocks.air && !canSeeSky(world, x, yy + 1, z))
                    world.setBlock(x, y + 1, z, placeSnowLayer, 0, 2);
                else break;
            }
        }

        Block placeBlockFloor;
        int chance = 1 + world.rand.nextInt(10);

        switch (chance) {
            case 1:
                placeBlockFloor = NovaCraftBlocks.arctic_moss_carpet;
                break;
            case 2:
                placeBlockFloor = NovaCraftBlocks.glow_lichen;
                break;
            case 3:
                placeBlockFloor = NovaCraftBlocks.cyan_rose;
                break;
            case 4:
                placeBlockFloor = NovaCraftBlocks.cave_bloom;
                break;
            default:
                placeBlockFloor = Blocks.snow_layer;
                break;
        }

        if (FloraChance > 0 && world.rand.nextDouble() < FloraChance) {
            int height = 3 + world.rand.nextInt(3);
            for (int i = 0; i < height; i++) {
                int yy = y + 1 + i;
                if (world.getBlock(x, y + 1, z).isAir(world, x, y + 1, z) && !canSeeSky(world, x, yy + 1, z))
                    world.setBlock(x, y + 1, z, placeBlockFloor, 1, 2);
                else break;
            }
        }

        Block placeBlockCeiling;
        int chanceCeiling = 1 + world.rand.nextInt(6);

        switch (chanceCeiling) {
            case 1:
                placeBlockCeiling = NovaCraftBlocks.glow_lichen;
                break;
            case 2:
                placeBlockCeiling = NovaCraftBlocks.large_stone_stalactite;
                break;
            default:
                placeBlockCeiling = NovaCraftBlocks.icicle;
                break;
        }

        if (FloraChance > 0 && world.rand.nextDouble() < FloraChance) {
            int height = 3 + world.rand.nextInt(3);
            for (int i = 0; i < height; i++) {
                int yy = y + 1 + i;
                if (world.getBlock(x, y - 1, z).isAir(world, x, y - 1, z) && !canSeeSky(world, x, yy - 1, z))
                    world.setBlock(x, y - 1, z, placeBlockCeiling, 0, 2);
                else break;
            }
        }

        Block placeBlockAlternate = Blocks.gravel;
        int chance_alternate = 1 + world.rand.nextInt(20);

        switch (chance_alternate) {
            case 1:
                placeBlockAlternate = Blocks.gravel;
                break;
            case 2:
                if (Loader.isModLoaded("etfuturum")) {
                    try {
                        Block coarse_dirt = GameRegistry.findBlock("etfuturum", "coarse_dirt");
                        placeBlockAlternate = coarse_dirt != null ? coarse_dirt : Blocks.dirt;
                    } catch (Exception ignored) {
                        placeBlockAlternate = Blocks.dirt;
                    }
                } else {
                    placeBlockAlternate = Blocks.dirt;
                }
                break;
            default:
                placeBlockAlternate = Blocks.packed_ice;
                break;
        }

        if (OtherBlocksChance > 0 && world.rand.nextDouble() < OtherBlocksChance) {
            int height = 3 + world.rand.nextInt(4);
            for (int i = 0; i < height; i++) {
                int yy = y + 1 + i;
                if (world.getBlock(x, y + 1, z).isAir(world, x, y + 1, z) && !canSeeSky(world, x, yy + 1, z))
                    world.setBlock(x, y, z, placeBlockAlternate, 0, 2);
                else break;
            }
        }
    }

    static {
        useArcticMoss = true;
        SnowLayerChance = 0.65;
        FloraChance = 0.55;
        OtherBlocksChance = 0.12;
    }

}
