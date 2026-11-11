package com.smartcity360.partB_composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Composite node representing administrative zones (City, District, Street).
 * Manages children and delegates operations recursively.
 * Candidate: Hashen Liyanaarachchi | JIAT/OODP II /EX/01
 */
public class GridZone implements PowerGridComponent {
    private final String zoneTitle;
    private final String zoneCategory;
    private final List<PowerGridComponent> subNodes = new ArrayList<>();

    public GridZone(String zoneTitle, String zoneCategory) {
        this.zoneTitle = zoneTitle;
        this.zoneCategory = zoneCategory;
    }

    @Override
    public String getTitle() {
        return zoneTitle;
    }

    public String getZoneCategory() {
        return zoneCategory;
    }

    public List<PowerGridComponent> getSubNodes() {
        return Collections.unmodifiableList(subNodes);
    }

    @Override
    public void attach(PowerGridComponent child) {
        subNodes.add(child);
    }

    @Override
    public void detach(PowerGridComponent child) {
        subNodes.remove(child);
    }

    @Override
    public void cutPower() {
        System.out.println("[GridZone: " + zoneCategory + " - " + zoneTitle + "] Propagating load cut to " + subNodes.size() + " sub-units...");
        for (PowerGridComponent child : subNodes) {
            child.cutPower();
        }
    }

    @Override
    public void energizePower() {
        System.out.println("[GridZone: " + zoneCategory + " - " + zoneTitle + "] Propagating power restoration to " + subNodes.size() + " sub-units...");
        for (PowerGridComponent child : subNodes) {
            child.energizePower();
        }
    }

    @Override
    public boolean isEnergized() {
        for (PowerGridComponent child : subNodes) {
            if (child.isEnergized()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public double computePowerLoad() {
        double totalLoad = 0.0;
        for (PowerGridComponent child : subNodes) {
            totalLoad += child.computePowerLoad();
        }
        return totalLoad;
    }

    @Override
    public void displayLayout(String indent) {
        System.out.println(indent + "+ [" + zoneCategory + "] " + zoneTitle + " (Total Demand: " + computePowerLoad() + " kW)");
        for (PowerGridComponent child : subNodes) {
            child.displayLayout(indent + "    ");
        }
    }
}
