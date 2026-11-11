package com.smartcity360.partB_composite;

/**
 * Leaf node representing a metered power consumer facility.
 * Candidate: Hashen Liyanaarachchi | JIAT/OODP II /EX/01
 */
public class FacilityNode implements PowerGridComponent {
    private final String facilityName;
    private final double basePowerDemandKw;
    private boolean energized = true;

    public FacilityNode(String facilityName, double basePowerDemandKw) {
        this.facilityName = facilityName;
        this.basePowerDemandKw = basePowerDemandKw;
    }

    @Override
    public String getTitle() {
        return facilityName;
    }

    public double getBasePowerDemandKw() {
        return basePowerDemandKw;
    }

    @Override
    public double computePowerLoad() {
        return energized ? basePowerDemandKw : 0.0;
    }

    @Override
    public void cutPower() {
        this.energized = false;
        System.out.println("      [FacilityNode: " + facilityName + "] Load shed complete. Power: DE-ENERGIZED (0.0 kW).");
    }

    @Override
    public void energizePower() {
        this.energized = true;
        System.out.println("      [FacilityNode: " + facilityName + "] Grid connection active. Power: ENERGIZED (" + basePowerDemandKw + " kW).");
    }

    @Override
    public boolean isEnergized() {
        return energized;
    }

    @Override
    public void displayLayout(String indent) {
        String status = energized ? "ONLINE (" + basePowerDemandKw + " kW)" : "OFFLINE (0.0 kW)";
        System.out.println(indent + "- Facility: " + facilityName + " -> " + status);
    }
}
