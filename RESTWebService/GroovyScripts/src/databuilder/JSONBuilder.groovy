package databuilder

import groovy.json.JsonBuilder

/**
 * Created by BO034731 on 06/03/14.
 */
class JSONBuilder {

    static final String INCOME = 'Income'
    static final String RENT = 'Rent'
    static final String COUNCIL_TAX = 'CouncilTax'
    static final String GYM = 'Gym'
    static final String INTERNET = 'Internet'
    static final String ELECTRICITY = 'Electricity'
    static final String HOME_PHONE = 'HomePhone'
    static final String MOBILE_PHONE = 'MobilePhone'
    static final String CREDIT_CARD = 'CreditCard'
    static final String SAVINGS = 'Savings'
    static final String PENSION_CONTRIB = 'PensionContrib'

    public static void main(String[] args) {

        def jsonBuilder = new JsonBuilder()
        def json = jsonBuilder
        def reader = System.in.newReader();

        def inputMap = [
                (INCOME) : '0',
                (RENT) : '0',
                (COUNCIL_TAX) : '0',
                (GYM) : '0',
                (INTERNET) : '0',
                (ELECTRICITY) : '0',
                (HOME_PHONE) : '0',
                (MOBILE_PHONE) : '0',
                (CREDIT_CARD) : '0',
                (SAVINGS) : '0',
                (PENSION_CONTRIB) : '0'
        ]

        assert !inputMap.isEmpty()

        inputMap.each { key, value ->
            println key
            value = reader.readLine()
            assert value

            inputMap[key] = value
            assert inputMap[key]
        }

        println inputMap

        buildJSON(json, inputMap)

    }

    private static void buildJSON(JsonBuilder json, inputMap) {

        json {
            income inputMap["${INCOME}"]
            rent inputMap["${RENT}"]
            councilTax inputMap["${COUNCIL_TAX}"]
            gym inputMap["${GYM}"]
            internet inputMap["${INTERNET}"]
            electricity inputMap["${ELECTRICITY}"]
            homePhone inputMap["${HOME_PHONE}"]
            mobilePhone inputMap["${MOBILE_PHONE}"]
            creditCard inputMap["${CREDIT_CARD}"]
            savings inputMap["${SAVINGS}"]
            pensionContrib inputMap["${PENSION_CONTRIB}"]
            additionalPurchases ({
                'Television' '500'
                'Laptop' '400'
                'Watch' '50'
            })

        }

        assert json != null

        println json.toPrettyString()
    }

}
