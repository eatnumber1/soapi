package com.eatnumber1.soapi.controller;

import com.eatnumber1.soapi.SoapiConstants;
import com.eatnumber1.util.MessageBundle;
import java.net.URI;
import java.net.URISyntaxException;
import org.jetbrains.annotations.NotNull;

/**
 * @author Russell Harmon
 * @since Mar 30, 2010
 */
public class SoapControllerFactory {
    @NotNull
    private static final MessageBundle messages = MessageBundle.getMessageBundle(SoapControllerFactory.class);

    private String localHost = SoapiConstants.getLocalHost(),
            soapHost = SoapiConstants.getSoapHost();
    private Integer localPort = SoapiConstants.getLocalPort(),
            soapPort = SoapiConstants.getSoapPort();
    private String soapPath = SoapiConstants.getSoapPath();
    private SoapLocation location;

    public String getLocalHost() {
        return localHost;
    }

    public void setLocalHost( String localHost ) {
        this.localHost = localHost;
    }

    public String getSoapHost() {
        return soapHost;
    }

    public void setSoapHost( String soapHost ) {
        this.soapHost = soapHost;
    }

    public Integer getLocalPort() {
        return localPort;
    }

    public void setLocalPort( Integer localPort ) {
        this.localPort = localPort;
    }

    public Integer getSoapPort() {
        return soapPort;
    }

    public void setSoapPort( Integer soapPort ) {
        this.soapPort = soapPort;
    }

    public SoapLocation getLocation() {
        return location;
    }

    public void setLocation( SoapLocation location ) {
        this.location = location;
    }

    public String getSoapPath() {
        return soapPath;
    }

    public void setSoapPath( String soapPath ) {
        this.soapPath = soapPath;
    }

    public SoapController produce() throws URISyntaxException {
        if( localHost == null ) {
            // TODO
            throw new UnsupportedOperationException(messages.getMessage("com.eatnumber1.soapi.controller.error.localhost-autodiscovery-unsupported"));
        }
        assert location != null;
        return new SoapController(new URI("http", null, soapHost, soapPort, soapPath, null, null), new URI("http", null, localHost, localPort, null, null, null), location);
    }
}
