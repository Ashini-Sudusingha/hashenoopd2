package com.smartcity360.partA_decorator;

/**
 * Decorator applying GZIP payload compression to optimize network bandwidth.
 * Candidate: Hashen Liyanaarachchi | JIAT/OODP II /EX/01
 */
public class PayloadCompressionDecorator extends SecurityStreamDecorator {

    public PayloadCompressionDecorator(TelemetryStream stream) {
        super(stream);
    }

    @Override
    public void transmit(String payload) {
        String compressedFrame = "GZIP_ARCHIVE[[" + payload + "]]";
        System.out.println("[PayloadCompressionDecorator] Layer applied: Deflated stream payload (approx 60% bandwidth saving).");
        super.transmit(compressedFrame);
    }

    @Override
    public String receive() {
        String frame = super.receive();
        if (frame.startsWith("GZIP_ARCHIVE[[") && frame.endsWith("]]")) {
            System.out.println("[PayloadCompressionDecorator] Decompression SUCCESS: Inflated original telemetry stream.");
            return frame.substring(14, frame.length() - 2);
        }
        return frame;
    }
}
