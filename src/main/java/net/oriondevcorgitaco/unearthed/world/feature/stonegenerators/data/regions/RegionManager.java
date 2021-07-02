package net.oriondevcorgitaco.unearthed.world.feature.stonegenerators.data.regions;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.oriondevcorgitaco.unearthed.block.BlockGeneratorReference;
import net.oriondevcorgitaco.unearthed.world.feature.stonegenerators.data.Cell;
import net.oriondevcorgitaco.unearthed.world.feature.stonegenerators.data.State;
import net.oriondevcorgitaco.unearthed.world.feature.stonegenerators.data.Type;

import java.util.*;

import static net.oriondevcorgitaco.unearthed.world.feature.stonegenerators.data.Cells.*;

public class RegionManager {
    private static List<Region> regions = new ArrayList<>();
    private static Map<Biome.Category, Region> specialRegions = Collections.emptyMap();
    private static Map<ResourceLocation, Region> biomeRegion = Collections.emptyMap();
    private static List<BatolithicState> batolithicStates;
    private static List<State> tertiaries = new ArrayList<>();

    public static Region getRegion(Biome biome, float value) {
        if (specialRegions.containsKey(biome.getBiomeCategory())) {
            return specialRegions.get(biome.getBiomeCategory());
        } else if (biomeRegion.containsKey(biome.getRegistryName())) {
            return biomeRegion.get(biome.getRegistryName());
        } else {
            return regions.get(((int) (value * regions.size())));
        }
    }

    public static State getBatolithState(float qf, float ap) {
        return batolithicStates.stream().min(Comparator.comparing(state -> state.getDistance(qf, ap))).get().state;
    }

    public static State getTertiaryState(float level) {
        return tertiaries.get(((int) (tertiaries.size() * level)));
    }

    public static void addTertiary(Cell... cells) {
        for (Cell cell : cells){
            tertiaries.add(cell.getState(Type.TERTIARY));
        }
    }
    static class BatolithicState {
        public State state;
        public float qf;
        public float ap;

        public BatolithicState(Cell cell, float qf, float ap) {
            this.state = cell.getState(Type.BATHOLITH);
            this.qf = qf;
            this.ap = ap;
        }

        public float getDistance(float qf, float ap) {
            return (float) Math.sqrt(qf * this.qf + ap + this.ap);
        }
    }

    static {
        batolithicStates = new ArrayList<>();
        batolithicStates.add(new BatolithicState(GRANITE, 0.8f, 0.0f));
        batolithicStates.add(new BatolithicState(DIORITE, 0.0f, 0.9f));
        batolithicStates.add(new BatolithicState(GRANODIORITE, -0.1f, 0.9f));
        batolithicStates.add(new BatolithicState(ANDESITE, -0.5f, 0.0f));

        tertiaries = new ArrayList<>();
        addTertiary(MARBLE, DACITE, PUMICE, PILLOW_BASALT, DOLERITE);

        regions.add(Regions.DEFAULT);
        regions.add(Regions.LIMESTONE_REGION);
        regions.add(Regions.SEDIMENTARY);
    }
}
