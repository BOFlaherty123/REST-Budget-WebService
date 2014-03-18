package com.oflaherty.webservice.processors.calculate;

import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static com.oflaherty.webservice.common.ApplicationConstants.*;
import static org.dom4j.DocumentHelper.createElement;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Description Here
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 07/03/14
 * @project RESTWebService
 */
public class CalculateYearlyProcessorTest {

    @InjectMocks
    private CalculateYearlyProcessor processor;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doesTheResponseElementContainAYearlyElementAndFourChildrenNodes() {

        Element requestEl = createElement(REQUEST);
        requestEl.addElement(INCOME).addText("2000");
        requestEl.addElement(RENT).addText("600");
        requestEl.addElement(COUNCIL_TAX).addText("68");
        requestEl.addElement(GYM).addText("40");

        Element responseEl = processor.executeProcessor(createElement(RESPONSE), requestEl);

        Element yearlyEl = responseEl.element(YEARLY);
        assertThat(yearlyEl.elements().size(), is(4));

        Element incomeEl = yearlyEl.element(INCOME).element(NET).element(INCOME);
        assertThat(incomeEl.getText(), is("24000"));
        assertThat(incomeEl.getText().isEmpty(), is(false));

        Element rent =  yearlyEl.element(RENT);
        assertThat(rent.getText(), is("7200"));
        assertThat(rent.getText().isEmpty(), is(false));

    }

}
