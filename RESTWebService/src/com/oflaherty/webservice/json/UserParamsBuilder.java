package com.oflaherty.webservice.json;

import com.oflaherty.webservice.exceptions.ZeroValueException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import static com.oflaherty.webservice.common.ApplicationConstants.*;

/**
 * User Parameter Builder
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 27/02/14.
 * @project RESTWebService
 */
@Component
public class UserParamsBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserParamsBuilder.class);

    /**
     * Build a userParams object from JSON data entered by the user
     *
     * @param jsonString json entered by to the webservice by the user
     * @return a userParams object
     */
    public UserParams buildUserParamObject(String jsonString) throws ZeroValueException {

        UserParams params = new UserParams();

        try {

            JSONObject json = new JSONObject(jsonString);

            params.setIncome(convertToBigDecimalAndValidate(INCOME, json.getString(INCOME)));
            params.setRent(convertToBigDecimalAndValidate(RENT, json.getString(RENT)));
            params.setCouncilTax(convertToBigDecimalAndValidate(COUNCIL_TAX, json.getString(COUNCIL_TAX)));
            params.setGym(convertToBigDecimalAndValidate(GYM, json.getString(GYM)));
            params.setInternet(convertToBigDecimalAndValidate(INTERNET, json.getString(INTERNET)));
            params.setElectricity(convertToBigDecimalAndValidate(ELECTRICITY, json.getString(ELECTRICITY)));
            params.setHonmePhone(convertToBigDecimalAndValidate(HOME_PHONE, json.getString(HOME_PHONE)));
            params.setMobilePhone(convertToBigDecimalAndValidate(MOBILE_PHONE, json.getString(MOBILE_PHONE)));
            params.setCreditCard(convertToBigDecimalAndValidate(CREDIT_CARD, json.getString(CREDIT_CARD)));
            params.setSavings(convertToBigDecimalAndValidate(SAVINGS, json.getString(SAVINGS)));
            params.setPensionContrib(convertToBigDecimalAndValidate(PENSION_CONTRIB, json.getString(PENSION_CONTRIB)));

            JSONObject additionalPurchases = json.optJSONObject(ADDITIONAL_PURCHASES);

            Map<String, BigDecimal> addPurchases = extractAdditionalPurchases(additionalPurchases);
            params.setAddPurchases(addPurchases);

        } catch(JSONException e) {
            LOGGER.error(".buildUserParamObject()", e.toString());
            throw new InvalidParameterException();
        }

        return params;

    }

    private Map<String, BigDecimal> extractAdditionalPurchases(JSONObject additionalPurchases) {

        Map<String, BigDecimal> addPurchases = new HashMap<>();

        if(additionalPurchases != null) {

            for (Object fieldValue : additionalPurchases.keySet()) {
                String value = additionalPurchases.getString(fieldValue.toString());
                addPurchases.put(fieldValue.toString(), new BigDecimal(value));
            }
        }

        return addPurchases;
    }

    private BigDecimal convertToBigDecimalAndValidate(String parameter, String jsonKey) throws ZeroValueException {

        BigDecimal value = new BigDecimal(jsonKey);

        if(value.equals(BigDecimal.ZERO)) {
            throw new ZeroValueException(parameter + " is equal to ZERO. Please add a value greater than ZERO.");
        }

        return value;
    }


    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    void responseCode() {}
}
