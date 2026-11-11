package com.smartcity360.partA_decorator;

/**
 * Concrete hardware telemetry stream for city energy meters.
 * Candidate: Hashen Liyanaarachchi | JIAT/OODP II /EX/01
 */
public class GridMeterStream implements TelemetryStream {
    private String telemetryBuffer = "";

    @Override
    public void transmit(String payload) {
        this.telemetryBuffer = payload;
        System.out.println("[GridMeterStream] Telemetry persisted to hardware buffer: " + payload);
    }

    @Override
    public String receive() {
        System.out.println("[GridMeterStream] Reading raw telemetry from hardware buffer: " + telemetryBuffer);
        return telemetryBuffer;
    }
}
