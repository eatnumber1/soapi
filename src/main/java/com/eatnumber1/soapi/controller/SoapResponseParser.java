package com.eatnumber1.soapi.controller;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.TextExtractingVisitor;
import org.jetbrains.annotations.NotNull;

/**
 * @author Russell Harmon
 * @since Mar 29, 2010
 */
public class SoapResponseParser {
    public static final String SOAP_RESPONSE_ENCODING_KEY = "soapi.soap.encoding";

    private Parser parser;

    public SoapResponseParser() {
    }

    public SoapResponseParser( @NotNull InputStream in ) throws UnsupportedEncodingException {
        setParser(in);
    }

    public Parser getParser() {
        return parser;
    }

    public void setParser( @NotNull InputStream in ) throws UnsupportedEncodingException {
        parser = new Parser(new Lexer(new Page(in, System.getProperty(SOAP_RESPONSE_ENCODING_KEY, "US-ASCII"))));
    }

    @NotNull
    public SoapResponse parse() throws ParserException {
        NodeFilter filter = new AndFilter(new NodeClassFilter(ParagraphTag.class), new NodeFilter() {
            @Override
            public boolean accept( Node node ) {
                return !"Response from Server:".equals(node.getLastChild().getText());
            }
        });
        NodeList nodes = parser.extractAllNodesThatMatch(filter);
        TextExtractingVisitor visitor = new TextExtractingVisitor();
        nodes.visitAllNodesWith(visitor);
        return SoapResponse.create(visitor.getExtractedText());
    }
}
