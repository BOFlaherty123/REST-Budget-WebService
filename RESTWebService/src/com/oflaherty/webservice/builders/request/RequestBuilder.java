package com.oflaherty.webservice.builders.request;

import com.oflaherty.webservice.builders.IBuilder;
import com.oflaherty.webservice.json.UserParams;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

import static com.oflaherty.webservice.common.ApplicationConstants.*;
import static org.dom4j.DocumentHelper.createElement;

/**
 * Request Builder, builds the xml elements from the entered JSON
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 27/02/14.
 * @project RESTWebService
 */
@Component
public class RequestBuilder implements IBuilder {

    /**
     *  Executes the request builder
     *
     * @param params userParams with the users initial values
     * @return a request element
     */
    @Override
    public Element executeBuilder(UserParams params) {

        Element requestEl = createElement(REQUEST);

        buildElement(requestEl, INCOME, params.getIncome());
        buildElement(requestEl, RENT, params.getRent());
        buildElement(requestEl, COUNCIL_TAX, params.getCouncilTax());
        buildElement(requestEl, GYM, params.getGym());
        buildElement(requestEl, INTERNET, params.getInternet());
        buildElement(requestEl, ELECTRICITY, params.getElectricity());
        buildElement(requestEl, HOME_PHONE, params.getHonmePhone());
        buildElement(requestEl, MOBILE_PHONE, params.getMobilePhone());
        buildElement(requestEl, CREDIT_CARD, params.getCreditCard());
        buildElement(requestEl, TV_LICENSE, params.getTvLicense());
        buildElement(requestEl, SAVINGS, params.getSavings());
        buildElement(requestEl, PENSION_CONTRIB, params.getPensionContrib());
        buildAdditionalPurchasesEl(requestEl, params.getAddPurchases());

        return requestEl;
    }

    /**
     * builds an individual request element
     *
     * @param requestEl request parent element
     * @param elementName name of the element to create
     * @param value value from userParams
     */
    private void buildElement(Element requestEl, String elementName, BigDecimal value) {
        requestEl.addElement(elementName).addText(convertBigDecimalToString(value));
    }

    /**
     * Builds the additionalPurcahses element tree (unlimited number of additional purchases)
     *
     * @param requestEl request parent element
     * @param additionalPurchases map containing key (string) and value (BigDecimal)
     */
    private void buildAdditionalPurchasesEl(Element requestEl, Map<String, BigDecimal> additionalPurchases) {

        Element additionalPurchasesEl = requestEl.addElement(ADDITIONAL_PURCHASES);

        for(Map.Entry<String, BigDecimal> entry : additionalPurchases.entrySet()) {

            Element purchase = additionalPurchasesEl.addElement("purchase");

            String description = entry.getKey();
            String value = entry.getValue().toString();

            purchase.addElement("description").addText(description);
            purchase.addElement("value").addText(value);
        }

    }

    @Override
    public Element executeBuilder(Element element) {
        throw new UnsupportedOperationException();
    }

    private String convertBigDecimalToString(BigDecimal value) {

        if(value == null) {
            value = new BigDecimal("10");
        }

        return value.toString();

    }

}