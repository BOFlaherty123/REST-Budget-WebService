package client

import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient

/**
 * Description Here
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 13/03/14
 * @project RESTWebService
 */
public class MultipleRestfulClientTest {

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
        // define command line arguments
        def cli = new CliBuilder(usage: 'MultipleRestfulClientTest -[n]')
        cli.with {
            n longOpt: 'numberOfResponses', args: 1, 'How many test messages do you wish to send to the service?'
        }

        // set up command line arguments
        def options = cli.parse(args)
        if (!options) {
            return
        }

        // run the script
        new MultipleRestfulClientTest().run(options)

    }

    void run(OptionAccessor options) {

        def client = new RESTClient( 'http://localhost:8080' )
        int numberOfResponses = 1

        if(options.n) {
            numberOfResponses = options.n as int
        }

        for(i in 1..numberOfResponses) {

            def values = [
                    (INCOME) : generateRandomNumber(3000, 2000),
                    (RENT) : generateRandomNumber(800, 600),
                    (COUNCIL_TAX) : generateRandomNumber(100, 60),
                    (GYM) : generateRandomNumber(60, 40),
                    (INTERNET) : generateRandomNumber(20, 5),
                    (ELECTRICITY) : generateRandomNumber(80, 30),
                    (HOME_PHONE) : generateRandomNumber(20, 10),
                    (MOBILE_PHONE) : generateRandomNumber(50, 20),
                    (CREDIT_CARD) : generateRandomNumber(80, 20),
                    (SAVINGS) : generateRandomNumber(300, 100),
                    (PENSION_CONTRIB) : '2'
            ]

            def resp = triggerRestClient(client, values)
            println "Test: ${i} - Status: ${resp.status}"
            assert resp.status == 200
        }

    }

    static String generateRandomNumber(int max, int min) {
        def random = new Random()

        return random.nextInt((max-min)+1)+min
    }

    static def triggerRestClient(def client, def values) {

        def resp = client.post(
                path : '/RESTWebService/budget/calculate',
                body :  [income: values[INCOME],
                        rent: values[RENT],
                        councilTax: values[COUNCIL_TAX],
                        gym: values[GYM],
                        internet: values[INTERNET],
                        electricity: values[ELECTRICITY],
                        homePhone: values[HOME_PHONE],
                        mobilePhone: values[MOBILE_PHONE],
                        creditCard: values[CREDIT_CARD],
                        savings: values[SAVINGS],
                        pensionContrib: values[PENSION_CONTRIB]
                ],
                requestContentType : ContentType.JSON )

        return resp
    }

}
