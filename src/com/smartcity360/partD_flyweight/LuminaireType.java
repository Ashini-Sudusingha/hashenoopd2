package com.smartcity360.partD_flyweight;

/**
 * Intrinsic shared Flyweight representing immutable luminaire hardware specifications.
 * Candidate: Hashen Liyanaarachchi | JIAT/OODP II /EX/01
 */
public class LuminaireType {
    private final String modelCode;
    private final int wattageWatts;
    private final String colorKelvin;
    private final String brandVendor;

    public LuminaireType(String modelCode, int wattageWatts, String colorKelvin, String brandVendor) {
        this.modelCode = modelCode;
        this.wattageWatts = wattageWatts;
        this.colorKelvin = colorKelvin;
        this.brandVendor = brandVendor;
    }

    public String getModelCode() {
        return modelCode;
    }

    public int getWattageWatts() {
        return wattageWatts;
    }

    public String getColorKelvin() {
        return colorKelvin;
    }

    public String getBrandVendor() {
        return brandVendor;
    }

    public void renderLight(String poleNumber, double latitude, double longitude, boolean illuminated) {
        String stateText = illuminated ? "ILLUMINATED (" + wattageWatts + "W, " + colorKelvin + ")" : "STANDBY (OFF)";
        System.out.println("  Luminaire [" + poleNumber + "] @ (" + latitude + ", " + longitude + ") | " +
                modelCode + " by " + brandVendor + " -> " + stateText);
    }
}
