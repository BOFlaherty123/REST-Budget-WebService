package com.oflaherty.webservice.processors.financialdeductions;

import com.oflaherty.webservice.processors.AbstractProcessor;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.oflaherty.webservice.common.ApplicationConstants.*;
import static java.lang.String.format;

/**
 * Calculates the users tax contributions (income/national insurance)
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 28/02/14.
 * @project RESTWebService
 */
@Component
public class TaxProcessor extends AbstractProcessor {

    private final static BigDecimal PERSONAL_ALLOWANCE = new BigDecimal("10000");
    private static final BigDecimal TWELVE_MONTHS = new BigDecimal("12");
    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

    /*

        Personal allowance for 2014/2015 is 10,000

        Work Out Income Tax (20%) (with check to see if income qualifies for 40%)
        Work out National Insurance at 11%

        Get Income value (monthly) and multiply by 12 months to provide yearly salary
        Divide yearly salary by 100% and multiply by tax rate

        Deduct personal allowance from gross yearly salary and ..
        If entered income is under 40,000 (find out exact rate for 2014/2015) then you will be taxed 20% against this new value (salary - personal allowance)
        Over 40,000 and apply a higher rate tax rate

        Apply national insurance taxation at 9% to gross salary (per month)

        <tax>
             <incomeTax>
                <monthly>xxx</monthly>
                <yearly>xxxx</yearly>
             </incomeTax>
                <monthly>xxx</monthly>
                <yearly>xxxx</yearly>
             </nationalInsuranceTax>
        </tax>


     */
    @Override
    protected Element executeProcessor(Element response, Element request) {

        LOGGER.info(format(GENERIC_LOG_FORMAT, this.getClass(), METHOD_NAME, METHOD_ENTERED));

        if(doesElementExist(request, INCOME)) {

            BigDecimal income = new BigDecimal(request.element(INCOME).getText());

            if(!income.equals(BigDecimal.ZERO)) {

                Element taxEl = response.addElement(TAX);
                processIncomeTaxElement(income, taxEl);
                processNationalInsuranceTaxElement(income, taxEl);

            }

        }

        LOGGER.info(format(GENERIC_LOG_FORMAT, this.getClass(), METHOD_NAME, METHOD_EXIT));

        return response;

    }

    private void processNationalInsuranceTaxElement(BigDecimal income, Element taxEl) {

        Element natInsuranceTaxEl = taxEl.addElement(NI_TAX);

        BigDecimal niTax = calculateNationalInsurance(income);

        // Build monthly/yearly elements
        LOGGER.info(".processNationalInsuranceTaxElement()");
        buildTaxElement(natInsuranceTaxEl, niTax, MONTHLY, VALUE);
        buildTaxElement(natInsuranceTaxEl, niTax.multiply(TWELVE_MONTHS), YEARLY, VALUE);

    }

    private void processIncomeTaxElement(BigDecimal income, Element taxEl) {

        Element incomeTaxEl = taxEl.addElement(INCOME_TAX);

        BigDecimal taxableIncome = income.multiply(TWELVE_MONTHS).subtract(PERSONAL_ALLOWANCE).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal monthIncomeTax = taxableIncome.divide(TWELVE_MONTHS, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal incomeTax = calculateIncomeTax(monthIncomeTax);

        // Build monthly/yearly elements
        LOGGER.info(".processIncomeTaxElement()");
        buildTaxElement(incomeTaxEl, incomeTax, MONTHLY, VALUE);
        buildTaxElement(incomeTaxEl, incomeTax.multiply(TWELVE_MONTHS), YEARLY, VALUE);

    }


    private void buildTaxElement(Element element, BigDecimal taxValue, String timePeriodEl, String elementName) {
        Element taxEl = element.addElement(timePeriodEl);
        taxEl.addElement(elementName).addText(taxValue.toString());

        LOGGER.info(format("Tax: £%s to be paid %s", taxValue, timePeriodEl));
    }

    private BigDecimal calculateIncomeTax(BigDecimal monthIncomeTax) {
        return monthIncomeTax.divide(ONE_HUNDRED, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("20"));
    }

    private BigDecimal calculateNationalInsurance(BigDecimal income) {
        return income.divide(ONE_HUNDRED, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("9").setScale(2, BigDecimal.ROUND_HALF_UP));
    }

}

