package com.oflaherty.webservice.processors;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract Processor
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 28/02/14.
 * @project RESTWebService
 */
public abstract class AbstractProcessor {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractProcessor.class);

    protected final String METHOD_NAME = ".executeProcessor()";

    /**
     * Abstract execute method
     *
     * @param response respone element
     * @param request request element
     * @return element value from a class that extends this abstract class
     */
    public Element execute(Element response, Element request) {
        return executeProcessor(response, request);
    }

    protected abstract Element executeProcessor(Element response, Element request);

    protected boolean doesElementExist(Element inputEl, String elementName) {
        return inputEl.element(elementName) != null;
    }

    protected boolean isNotNull(Object object) {
        return object != null;
    }

}
