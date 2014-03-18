package com.oflaherty.webservice.processors.calculate;

import com.oflaherty.webservice.processors.AbstractProcessor;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import static com.oflaherty.webservice.common.ApplicationConstants.GENERIC_LOG_FORMAT;
import static com.oflaherty.webservice.common.ApplicationConstants.METHOD_ENTERED;
import static com.oflaherty.webservice.common.ApplicationConstants.METHOD_EXIT;
import static java.lang.String.format;

/**
 * Calculates the monthly totals for the given user input
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 27/02/14
 * @project RESTWebService
 */
@Component
public class CalculateMonthlyProcessor extends AbstractProcessor {

    @Override
    protected Element executeProcessor(Element response, Element request) {

        LOGGER.info(format(GENERIC_LOG_FORMAT, this.getClass(), METHOD_NAME, METHOD_ENTERED));

        Element monthlyEl = response.addElement("monthly");
        monthlyEl.addElement("income").addText(request.element("income").getText());

        LOGGER.info(format(GENERIC_LOG_FORMAT, this.getClass(), METHOD_NAME, METHOD_EXIT));

        return response;
    }

}
