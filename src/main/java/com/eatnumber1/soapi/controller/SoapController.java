package com.eatnumber1.soapi.controller;

import com.eatnumber1.util.MessageBundle;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.htmlparser.util.ParserException;
import org.jetbrains.annotations.NotNull;

/**
 * @author Russell Harmon
 * @since Mar 29, 2010
 */
public class SoapController {
    public static final String SOAP_URI_KEY = "soapi.uri";
    public static final String LOCAL_HOST_KEY = "soapi.local.host";
    public static final String LOCAL_PORT_KEY = "soapi.local.port";

    @NotNull
    private static MessageBundle messages = MessageBundle.getMessageBundle(SoapController.class);

    @NotNull
    private HttpClient httpClient = new HttpClient();
    private URI soapUri, localUri;
    private SoapLocation location;

    public SoapController() throws IOException {
        soapUri = URI.create(System.getProperty(SOAP_URI_KEY, "http://soap.csh.rit.edu/add"));
        Integer port = Integer.getInteger(LOCAL_PORT_KEY);
        if( port == null ) {
            Random random = new Random();
            while( port == null || port < 1024 ) port = random.nextInt(65536);
        }
        String localHost = System.getProperty(LOCAL_HOST_KEY);
        if( localHost == null ) {
            // TODO
            throw new UnsupportedOperationException(messages.getMessage("com.eatnumber1.soapi.controller.error.localhost-autodiscovery-unsupported"));
        } else {
            try {
                localUri = new URI("http", null, localHost, port, null, null, null);
            } catch( URISyntaxException e ) {
                throw new RuntimeException(e);
            }
        }
    }

    public SoapController( URI soapUri, URI localUri, SoapLocation location ) {
        this.soapUri = soapUri;
        this.localUri = localUri;
        this.location = location;
    }

    public URI getLocalUri() {
        return localUri;
    }

    public void setLocalUri( URI localUri ) {
        this.localUri = localUri;
    }

    public URI getSoapUri() {
        return soapUri;
    }

    public void setSoapUri( URI soapUri ) {
        this.soapUri = soapUri;
    }

    public SoapLocation getLocation() {
        return location;
    }

    public void setLocation( SoapLocation location ) {
        this.location = location;
    }

    public void play() throws IOException, HttpException, ParserException, SoapServerException {
        assert soapUri != null && localUri != null && location != null;
        PostMethod method = new PostMethod(soapUri.toString());
        method.addParameter(new NameValuePair("bathroom", location.getKey()));
        method.addParameter(new NameValuePair("stream", localUri.toString()));
        try {
            int statusCode = httpClient.executeMethod(method);
            if( statusCode != HttpStatus.SC_OK ) throw new HttpException(statusCode);
            SoapResponse response = new SoapResponseParser(method.getResponseBodyAsStream()).parse();
            if( !SoapResponse.OK.equals(response) ) throw new SoapServerException(response);
        } finally {
            method.releaseConnection();
        }
    }

    public static void main( String[] args ) throws IOException, HttpException, ParserException, SoapServerException {
        SoapController ctl = new SoapController();
        ctl.setLocation(SoapLocation.SOUTH_VADER);
        ctl.play();

    }
}
