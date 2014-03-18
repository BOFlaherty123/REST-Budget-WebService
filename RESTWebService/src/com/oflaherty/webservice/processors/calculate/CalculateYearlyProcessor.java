package com.oflaherty.webservice.processors.calculate;

import com.oflaherty.webservice.processors.AbstractProcessor;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.oflaherty.webservice.common.ApplicationConstants.*;
import static java.lang.String.format;

/**
 * Calculates the yearly totals for the given user input
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 27/02/14
 * @project RESTWebService
 */
@Component
public class CalculateYearlyProcessor extends AbstractProcessor {

    private static final String TWELVE_MONTHS = "12";
    private static final String ELEMENT_YEARLY_TOTAL_LOG = "Element: %s  Yearly Total: %s";

    /**
     * execute the calculate yearly processor
     *
     * @param response response element
     * @param request request element
     * @return the calculated yearly responses, with twelve month totals
     */
    @Override
    protected Element executeProcessor(Element response, Element request) {

        final String METHOD_NAME = ".executeProcessor()";

        LOGGER.info(format(GENERIC_LOG_FORMAT, this.getClass(), METHOD_NAME, METHOD_ENTERED));

        Element yearlyEl = response.addElement(YEARLY);
        Element incomeEl = yearlyEl.addElement(INCOME);
        Element netIncomeEl = incomeEl.addElement(NET);

        // Yearly Income
        buildYearlyElementResponse(request, netIncomeEl, INCOME);

        // Yearly Rent
        buildYearlyElementResponse(request, yearlyEl, RENT);

        // Yearly Council Tax
        buildYearlyElementResponse(request, yearlyEl, COUNCIL_TAX);

        // Yearly Gym
        buildYearlyElementResponse(request, yearlyEl, GYM);

        // Yearly Utilities (home & mobile phones, electricity, internet)
        calculateYearlyUtilities(request, yearlyEl);

        // Yearly CreditCard
        buildYearlyElementResponse(request, yearlyEl, CREDIT_CARD);

        // Yearly Savings
        buildYearlyElementResponse(request, yearlyEl, SAVINGS);

        LOGGER.info(format(GENERIC_LOG_FORMAT, this.getClass(), METHOD_NAME, METHOD_EXIT));

        return response;
    }

    /**
     * build yearly element response
     *
     * @param request request element
     * @param yearlyEl yearly element (parent element)
     * @param elementName name of the child element to be appended to the parent (yearlyEl)
     */
    private void buildYearlyElementResponse(Element request, Element yearlyEl, String elementName) {
        final String METHOD_NAME = ".buildYearlyElementResponse() ";

        BigDecimal yearlyValue = convertStringElementValueToBigDecimal(request, elementName);

        if(yearlyValue != null) {
            addYearlyTotalToElement(yearlyEl, elementName, multiplyValueByTwelveToString(yearlyValue));
            LOGGER.info(format(GENERIC_LOG_FORMAT, this.getClass(), METHOD_NAME, format(ELEMENT_YEARLY_TOTAL_LOG, elementName, multiplyValueByTwelveToString(yearlyValue))));
        }
    }

    /**
     * Calculates all utility bill totals (internet, homePhone, mobilePhone and electricity)
     *
     * @param request
     * @param yearlyEl
     */
    private void calculateYearlyUtilities(Element request, Element yearlyEl) {

        BigDecimal yearlyInternet = convertStringElementValueToBigDecimal(request, INTERNET);
        BigDecimal yearlyHomePhone = convertStringElementValueToBigDecimal(request, HOME_PHONE);
        BigDecimal yearlyMobilePhone = convertStringElementValueToBigDecimal(request, MOBILE_PHONE);
        BigDecimal yearlyElectricity = convertStringElementValueToBigDecimal(request, ELECTRICITY);

        if(isNotNull(yearlyInternet) && isNotNull(yearlyHomePhone) && isNotNull(yearlyMobilePhone) && isNotNull(yearlyElectricity)) {

            BigDecimal utlityTotal = yearlyHomePhone
                    .add(yearlyMobilePhone)
                    .add(yearlyInternet)
                    .add(yearlyElectricity);

            addYearlyTotalToElement(yearlyEl, UTILITIES, multiplyValueByTwelveToString(utlityTotal));

        }

    }

    /**
     * converts a string value to a BigDecimal and returns the value
     *
     * @param request
     * @param elementName
     * @return
     */
    private BigDecimal convertStringElementValueToBigDecimal(Element request, String elementName) {

        BigDecimal value = null;

        if(request.element(elementName) != null) {
            value = new BigDecimal(request.element(elementName).getText());
        }

        return value;
    }

    /**
     * adds the yearly total value to the element
     *
     * @param yearlyEl
     * @param elementName
     * @param twelveMonthTotal
     */
    private void addYearlyTotalToElement(Element yearlyEl, String elementName, String twelveMonthTotal) {
        yearlyEl.addElement(elementName).addText(twelveMonthTotal);
    }

    /**
     * multiplies the initial value from the request by twelve (yearly total)
     *
     * @param yearlyIncome
     * @return
     */
    private String multiplyValueByTwelveToString(BigDecimal yearlyIncome) {
        return yearlyIncome.multiply(new BigDecimal(TWELVE_MONTHS)).toString();
    }

}
