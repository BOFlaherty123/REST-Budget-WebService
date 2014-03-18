package com.oflaherty.webservice.processors.otherdeductions;

import com.oflaherty.webservice.processors.AbstractProcessor;
import org.dom4j.Element;

import static com.oflaherty.webservice.common.ApplicationConstants.GENERIC_LOG_FORMAT;
import static com.oflaherty.webservice.common.ApplicationConstants.METHOD_ENTERED;
import static com.oflaherty.webservice.common.ApplicationConstants.METHOD_EXIT;
import static java.lang.String.format;

/**
 * Calculates and deducts socialising costs from the users budget
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 04/03/14.
 * @project RESTWebService
 */
public class SocialisingProcessor extends AbstractProcessor {

    @Override
    protected Element executeProcessor(Element response, Element request) {

        LOGGER.info(format(GENERIC_LOG_FORMAT, this.getClass(), METHOD_NAME, METHOD_ENTERED));

        if(doesElementExist(request, "social")) {

            System.out.println("process social elements");

        }

        LOGGER.info(format(GENERIC_LOG_FORMAT, this.getClass(), METHOD_NAME, METHOD_EXIT));

        return null;
    }

}
