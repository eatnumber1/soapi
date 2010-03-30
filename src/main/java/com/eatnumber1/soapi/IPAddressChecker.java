package com.eatnumber1.soapi;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.NetworkInterface;

/**
 * @author Russell Harmon
 * @since Mar 29, 2010
 */
public class IPAddressChecker {
    public static final String PING_ADDRESS_KEY = "soapi.ping.address";
    public static final String PING_TTL = "soapi.ping.ttl";
    public static final String PING_TIMEOUT = "soapi.ping.timeout";

    @NotNull
    public static NetworkInterface getPublicInterface() throws IOException {
        /*int ttl = Integer.getInteger(PING_TTL, 255), timeout = Integer.getInteger(PING_TIMEOUT, 1000);
        InetAddress pingAddr = InetAddress.getByName(System.getProperty(PING_ADDRESS_KEY, "www.google.com"));
        Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
        while( ifaces.hasMoreElements() ) {
            NetworkInterface iface = ifaces.nextElement();
            Socket socket = new Socket(URI.create(System.getProperty(SoapController.SOAP_URI_KEY, "http://soap.csh.rit.edu/add"));)
            if( pingAddr.isReachable(iface, ttl, timeout) ) return iface;
        }
        throw new NoRouteToHostException("Cannot reach host " + pingAddr);*/
        return NetworkInterface.getByName("wlan0");
    }
}
