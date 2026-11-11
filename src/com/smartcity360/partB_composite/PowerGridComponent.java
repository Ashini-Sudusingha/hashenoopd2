package com.smartcity360.partB_composite;

/**
 * Component interface for hierarchical smart power distribution nodes.
 * Treats individual facilities and composite zones uniformly.
 * Candidate: Hashen Liyanaarachchi | JIAT/OODP II /EX/01
 */
public interface PowerGridComponent {
    String getTitle();
    double computePowerLoad();
    void cutPower();
    void energizePower();
    boolean isEnergized();
    void displayLayout(String indent);

    default void attach(PowerGridComponent child) {
        throw new UnsupportedOperationException("Terminal facility nodes cannot hold child components.");
    }

    default void detach(PowerGridComponent child) {
        throw new UnsupportedOperationException("Terminal facility nodes cannot remove child components.");
    }
}
