package com.oflaherty.webservice.builders.response;

import com.oflaherty.webservice.builders.IBuilder;
import com.oflaherty.webservice.json.UserParams;
import com.oflaherty.webservice.processors.AbstractProcessor;
import org.dom4j.Element;

import java.util.Map;
import java.util.TreeMap;

import static org.dom4j.DocumentHelper.createElement;

/**
 * Response Builder, builds the xml response from the request
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 28/02/14.
 * @project RESTWebService
 */
public class ResponseBuilder implements IBuilder {

    private Map<Integer, AbstractProcessor> processors = new TreeMap<>();

    @Override
    public Element executeBuilder(Element request) {

        Element response = createElement("response");

        for(AbstractProcessor processor : processors.values()) {
            processor.execute(response, request);
        }

        return response;
    }

    @Override
    public Element executeBuilder(UserParams params) {
        throw new UnsupportedOperationException();
    }

    public void setProcessors(Map<Integer, AbstractProcessor> processors) {
        this.processors = processors;
    }

}
