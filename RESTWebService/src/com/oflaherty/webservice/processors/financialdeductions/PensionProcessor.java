package com.oflaherty.webservice.processors.financialdeductions;

import com.oflaherty.webservice.processors.AbstractProcessor;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.oflaherty.webservice.common.ApplicationConstants.*;
import static java.lang.String.format;

/**
 * Calculates the users pension contributions (per month/year)
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 05/03/14
 * @project RESTWebService
 */
@Component
public class PensionProcessor extends AbstractProcessor {

    private static final BigDecimal ONE_HUNDRED_PERCENT = new BigDecimal("100");
    private static final BigDecimal TWELVE_MONTHS = new BigDecimal("12");
    private static final String PENSION_TWO = "2";
    private static final String PENSION_FOUR = "4";
    private static final String PENSION_SIX = "6";
    private static final String PENSION_EIGHT = "8";

    @Override
    protected Element executeProcessor(Element response, Element request) {

        LOGGER.info(format(GENERIC_LOG_FORMAT, this.getClass(), METHOD_NAME, METHOD_ENTERED));

        if(doesElementExist(request, "pensionContrib")) {

            // Perform action based on the users entered percentage value (i.e. 2%, 3%, 6% etc)
            String userIncome =  request.element(INCOME).getText();
            String pensionContrib = request.element(PENSION_CONTRIB).getText();

            BigDecimal totalContribution;

            switch (pensionContrib) {
                case PENSION_TWO:
                    totalContribution = calculatePensionContribution(new BigDecimal(userIncome), new BigDecimal(PENSION_TWO));
                    break;
                case PENSION_FOUR:
                    totalContribution = calculatePensionContribution(new BigDecimal(userIncome), new BigDecimal(PENSION_FOUR));
                    break;
                case PENSION_SIX:
                    totalContribution = calculatePensionContribution(new BigDecimal(userIncome), new BigDecimal(PENSION_SIX));
                    break;
                case PENSION_EIGHT:
                    totalContribution = calculatePensionContribution(new BigDecimal(userIncome), new BigDecimal(PENSION_EIGHT));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid pension contribution, please enter either 2,4,6 or 8%.");
            }

            String yearlyContribution = bigDecimalValueToString(totalContribution);
            String monthlyContribution = bigDecimalValueToString(totalContribution.divide(TWELVE_MONTHS, 2));

            Element pensionResponse = response.addElement(PENSION_CONTRIB);
            pensionResponse.addElement(YEARLY).addText(yearlyContribution);
            pensionResponse.addElement(MONTHLY).addText(monthlyContribution);

            LOGGER.info(format("The users pension contribution per year £%s, per month £%s", yearlyContribution, monthlyContribution));
        }

        LOGGER.info(format(GENERIC_LOG_FORMAT, this.getClass(), METHOD_NAME, METHOD_EXIT));

        return response;

    }

    /**
     * Calculations the users pension contribution in monetary terms based on their entered income
     *
     k* @param userIncome users income as entered by them
     * @param pensionPercentage users set percentage contribution %
     * @return monetary value that the user will contribute to their pension fund
     */
    private BigDecimal calculatePensionContribution(BigDecimal userIncome, BigDecimal pensionPercentage) {
        return userIncome.multiply(TWELVE_MONTHS).divide(ONE_HUNDRED_PERCENT).multiply(pensionPercentage).setScale(2, RoundingMode.CEILING);
    }

    /**
     * Converts BigDecimal value to String
     *
     * @param value bigDecimal value to convert
     * @return a string value of the bigDecimal
     */
    private String bigDecimalValueToString(BigDecimal value) {
        return value.toString();
    }

}
