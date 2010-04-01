package com.eatnumber1.soapi.controller;

import com.eatnumber1.util.MessageBundle;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.htmlparser.util.ParserException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Russell Harmon
 * @since Mar 29, 2010
 */
public class SoapController {
    @NotNull
    private static MessageBundle messages = MessageBundle.getMessageBundle(SoapController.class);

    @NotNull
    private static Logger log = LoggerFactory.getLogger(SoapController.class);

    @NotNull
    private HttpClient httpClient = new HttpClient();
    private URI soapUri, localUri;
    private SoapLocation location;

    public SoapController() {
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
        log.debug(messages.getMessage("com.eatnumber1.soapi.controller.debug.request", Arrays.toString(method.getParameters())));
        try {
            int statusCode = httpClient.executeMethod(method);
            if( statusCode != HttpStatus.SC_OK ) throw new HttpException(statusCode);
            byte[] data = method.getResponseBody();
            log.debug(messages.getMessage("com.eatnumber1.soapi.controller.debug.response", new String(data)));
            SoapResponse response = new SoapResponseParser(new ByteArrayInputStream(data)).parse();
            if( !SoapResponse.OK.equals(response) ) throw new SoapServerException(response);
        } finally {
            method.releaseConnection();
        }
        log.info(messages.getMessage("com.eatnumber1.soapi.controller.request_sent", location.name(), localUri.toString()));
    }
}
