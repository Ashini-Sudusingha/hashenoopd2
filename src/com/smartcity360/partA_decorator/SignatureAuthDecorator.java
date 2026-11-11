package com.smartcity360.partA_decorator;

/**
 * Decorator enforcing cryptographic token validation on telemetry frames.
 * Candidate: Hashen Liyanaarachchi | JIAT/OODP II /EX/01
 */
public class SignatureAuthDecorator extends SecurityStreamDecorator {
    private final String expectedToken;

    public SignatureAuthDecorator(TelemetryStream stream, String token) {
        super(stream);
        this.expectedToken = token;
    }

    @Override
    public void transmit(String payload) {
        String authenticatedFrame = "SIG_VERIFIED[" + expectedToken + "]::" + payload;
        System.out.println("[SignatureAuthDecorator] Layer applied: Injected device authorization signature.");
        super.transmit(authenticatedFrame);
    }

    @Override
    public String receive() {
        String frame = super.receive();
        String expectedPrefix = "SIG_VERIFIED[" + expectedToken + "]::";
        if (frame.startsWith(expectedPrefix)) {
            System.out.println("[SignatureAuthDecorator] Verification SUCCESS: Valid token [" + expectedToken + "].");
            return frame.substring(expectedPrefix.length());
        }
        System.out.println("[SignatureAuthDecorator] Verification FAILED: Invalid or missing token.");
        return frame;
    }
}
