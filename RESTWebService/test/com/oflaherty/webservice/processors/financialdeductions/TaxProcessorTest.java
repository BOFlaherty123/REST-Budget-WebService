package com.oflaherty.webservice.processors.financialdeductions;

import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.dom4j.DocumentHelper.createElement;
import static com.oflaherty.webservice.common.ApplicationConstants.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * Description Here
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 11/03/14
 * @project RESTWebService
 */
public class TaxProcessorTest {

    @InjectMocks
    private TaxProcessor processor;

    @Before
    public void innit() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenIncomeElementNotEnteredTaxProcessorShouldNotFalloverWithNoChildrenElements() {

        Element requestEl = createElement("request");
        requestEl.addElement(GYM).addText("500");

        Element response = processor.executeProcessor(createElement("response"), requestEl);
        assertThat(response.elements().size(), is((0)));

    }

    @Test
    public void givenIncome0EnteredTaxProcessorShouldNotFalloverWithNoChildrenElements() {

        Element requestEl = createElement("request");
        requestEl.addElement(INCOME).addText("0");

        Element response = processor.executeProcessor(createElement("response"), requestEl);
        assertThat(response.elements().size(), is((0)));
    }

    @Test
    public void givenIncome3000TestToEnsureThatIncomeAndNationalInsuranceElementsExistWithCorrectValues() {

        Element requestEl = createElement("request");
        requestEl.addElement(INCOME).addText("3000");

        Element response = processor.executeProcessor(createElement("response"), requestEl);
        assertThat(response.elements().size(), is(not(0)));
        assertThat(response.element(TAX).elements().size(), is(2));

        Element incomeTax = response.element(TAX).element(INCOME_TAX);
        assertThat(incomeTax.elements().size(), is(not(0)));

        assertThat(incomeTax.element(MONTHLY).element(VALUE).getText(), is("433.40"));
        assertThat(incomeTax.element(YEARLY).element(VALUE).getText(), is("5200.80"));

        Element niTax =  response.element(TAX).element(NI_TAX);
        assertThat(incomeTax.elements().size(), is(not(0)));

        assertThat(niTax.element(MONTHLY).element(VALUE).getText(), is("270.00"));
        assertThat(niTax.element(YEARLY).element(VALUE).getText(), is("3240.00"));
    }

}
