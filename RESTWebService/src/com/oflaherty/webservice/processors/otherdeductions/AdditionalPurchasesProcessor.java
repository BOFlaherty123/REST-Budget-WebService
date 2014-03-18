package com.oflaherty.webservice.processors.otherdeductions;

import com.oflaherty.webservice.processors.AbstractProcessor;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static com.oflaherty.webservice.common.ApplicationConstants.*;
import static java.lang.String.format;

/**
 * Calculates and totals any additional purchases the user may have entered
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 05/03/14.
 * @project RESTWebService
 */
@SuppressWarnings("ALL")
@Component
public class AdditionalPurchasesProcessor extends AbstractProcessor {

    @Override
    protected Element executeProcessor(Element response, Element request) {

        LOGGER.info(format(GENERIC_LOG_FORMAT, this.getClass(), METHOD_NAME, METHOD_ENTERED));

        if(doesElementExist(request, ADDITIONAL_PURCHASES)) {

            List<Element> purchaseEls = request.element(ADDITIONAL_PURCHASES).elements();

            Element additionalPurchaseEl = response.addElement(ADDITIONAL_PURCHASES);

            List<BigDecimal> totals = new ArrayList<>();

            for(Element purchase : purchaseEls) {

                String description = purchase.element(DESCRIPTION).getText();
                String value = purchase.element(VALUE).getText();

                Element purchaseEl = additionalPurchaseEl.addElement(PURCHASE);
                purchaseEl.addElement(DESCRIPTION).addText(description);
                purchaseEl.addElement(VALUE).addText(value);

                LOGGER.info(format("purchase description: %s and value: %s", description, value));

                totals.add(new BigDecimal(value));

            }

            if(!additionalPurchaseEl.elements().isEmpty())  {
                BigDecimal total = calculateAdditionalPurchasesTotal(totals);
                additionalPurchaseEl.addElement("total").addText(total.toString());
            }

        }

        LOGGER.info(format(GENERIC_LOG_FORMAT, this.getClass(), METHOD_NAME, METHOD_EXIT));

        return response;
    }

    public BigDecimal calculateAdditionalPurchasesTotal(List<BigDecimal> purchaseValues) {

        BigDecimal total = new BigDecimal(BigInteger.ZERO);

        for(BigDecimal value : purchaseValues) {
            total = total.add(value);
        }

        LOGGER.info(format("total value of additional purchases is £%s", total));

        return total;

    }
}
