package com.smartcity360.partD_flyweight;

/**
 * Context object holding extrinsic state (coordinates, pole ID, illumination status)
 * and referencing the shared LuminaireType flyweight.
 * Candidate: Hashen Liyanaarachchi | JIAT/OODP II /EX/01
 */
public class StreetLuminaire {
    private final String poleNumber;
    private final double gpsLatitude;
    private final double gpsLongitude;
    private boolean illuminated = false;
    private final LuminaireType specification;

    public StreetLuminaire(String poleNumber, double gpsLatitude, double gpsLongitude, LuminaireType specification) {
        this.poleNumber = poleNumber;
        this.gpsLatitude = gpsLatitude;
        this.gpsLongitude = gpsLongitude;
        this.specification = specification;
    }

    public String getPoleNumber() {
        return poleNumber;
    }

    public double getGpsLatitude() {
        return gpsLatitude;
    }

    public double getGpsLongitude() {
        return gpsLongitude;
    }

    public boolean isIlluminated() {
        return illuminated;
    }

    public LuminaireType getSpecification() {
        return specification;
    }

    public void turnOn() {
        this.illuminated = true;
    }

    public void turnOff() {
        this.illuminated = false;
    }

    public void displayStatus() {
        specification.renderLight(poleNumber, gpsLatitude, gpsLongitude, illuminated);
    }
}
