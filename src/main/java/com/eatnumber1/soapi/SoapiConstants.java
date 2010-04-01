package com.eatnumber1.soapi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Russell Harmon
 * @since Mar 30, 2010
 */
public class SoapiConstants {
    @NotNull
    public static final String SOAP_HOST_KEY = "soapi.soap.host";

    @NotNull
    public static final String SOAP_PORT_KEY = "soapi.soap.port";

    @NotNull
    public static final String SOAP_PATH_KEY = "soapi.soap.path";

    @NotNull
    public static final String LOCAL_HOST_KEY = "soapi.local.host";

    @NotNull
    public static final String LOCAL_PORT_KEY = "soapi.local.port";

    private SoapiConstants() {}

    @Nullable
    public static String getSoapPath() {
        return System.getProperty(SOAP_PATH_KEY, "/add");
    }

    @NotNull
    public static String getSoapHost() {
        return System.getProperty(SOAP_HOST_KEY, "soap.csh.rit.edu");
    }

    @Nullable
    public static String getLocalHost() {
        return System.getProperty(LOCAL_HOST_KEY);
    }

    public static int getSoapPort() {
        return Integer.getInteger(SOAP_PORT_KEY, 80);
    }

    public static int getLocalPort() {
        return Integer.getInteger(LOCAL_PORT_KEY, 32145);
    }
}
