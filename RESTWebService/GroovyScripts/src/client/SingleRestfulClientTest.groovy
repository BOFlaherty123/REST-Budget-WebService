package client

import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient

/**
 * Fires a single Restful webservice message and evaluates the response xml
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 06/03/14
 * @project RESTWebService
 */
@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7' )
class SingleRestfulClientTest {

    public static void main(String[] args) {

        def client = new RESTClient( 'http://localhost:8080' )
        def resp = client.post(
                path : '/RESTWebService/budget/calculate',
                body :         [income: '1500',
                        rent: '500',
                        councilTax: '50',
                        gym: '40',
                        internet: '60',
                        electricity: '80',
                        homePhone: '5',
                        mobilePhone: '5',
                        creditCard: '10',
                        savings: '250',
                        pensionContrib: '2'
                ],
                requestContentType : ContentType.JSON )

        println "Status: ${resp.status}"
        assert resp.status == 200

        def xml = resp.data

        assert xml.yearly.income.net.income == '18000'
        assert xml.yearly.rent == '6000'
        assert xml.yearly.gym == '480'
        assert xml.yearly.creditCard == '120'
        assert xml.yearly.utilities == '1800'
        assert xml.yearly.savings == '3000'

        assert xml.tax.incomeTax.monthly != null
        assert xml.tax.incomeTax.yearly != null
        assert xml.tax.nationalInsuranceTax.monthly != null
        assert xml.tax.nationalInsuranceTax.yearly != null

        assert xml.pensionContrib.monthly != null
        assert xml.pensionContrib.yearly != null

    }

}
