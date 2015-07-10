package tud.tk3.splitscreen.network;

import java.net.InetAddress;

public interface DiscoveryHandler {
    // interface for the discovery handler
    void onFound(InetAddress address, String nickname);
}
