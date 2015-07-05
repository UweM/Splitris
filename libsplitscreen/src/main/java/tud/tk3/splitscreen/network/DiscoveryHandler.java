package tud.tk3.splitscreen.network;

import java.net.InetAddress;

public interface DiscoveryHandler {
    void onFound(InetAddress address, String nickname);
}
