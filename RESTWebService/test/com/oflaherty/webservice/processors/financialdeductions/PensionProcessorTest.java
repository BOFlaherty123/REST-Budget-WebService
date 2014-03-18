package com.oflaherty.webservice.processors.financialdeductions;

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
 * @date Created on: 10/03/14
 * @project RESTWebService
 */
public class PensionProcessorTest {

    @InjectMocks
    private PensionProcessor processor;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void isPensionsContributionResponseCorrectGivenIncomeIs35000AndContributionPercentIs2() {

        Element requestEl = createElement(REQUEST);
        requestEl.addElement(INCOME).addText("3000");
        requestEl.addElement(PENSION_CONTRIB).addText("2");

        Element response = processor.executeProcessor(createElement(RESPONSE), requestEl);

        Element pensionEl = response.element(PENSION_CONTRIB);
        assertThat(pensionEl.elements().size(), is(2));
        assertThat(pensionEl.element(YEARLY).getText(), is("720.00"));
        assertThat(pensionEl.element(MONTHLY).getText(), is("60.00"));

    }

    @Test
    public void isPensionContributionResponseCorrectGivenIncomeIs50000AndContributionPercentIs4() {

        Element requestEl = createElement(REQUEST);
        requestEl.addElement(INCOME).addText("5000");
        requestEl.addElement(PENSION_CONTRIB).addText("4");

        Element response = processor.executeProcessor(createElement(RESPONSE), requestEl);

        Element pensionEl = response.element(PENSION_CONTRIB);
        assertThat(pensionEl.elements().size(), is(2));
        assertThat(pensionEl.element(YEARLY).getText(), is("2400.00"));
        assertThat(pensionEl.element(MONTHLY).getText(), is("200.00"));

    }

    @Test
    public void isPensionContributionResponseCorrectGivenIncomeIs40000AndContributionPercentIs6() {

        Element requestEl = createElement(REQUEST);
        requestEl.addElement(INCOME).addText("4000");
        requestEl.addElement(PENSION_CONTRIB).addText("6");

        Element response = processor.executeProcessor(createElement(RESPONSE), requestEl);

        Element pensionEl = response.element(PENSION_CONTRIB);
        assertThat(pensionEl.elements().size(), is(2));
        assertThat(pensionEl.element(YEARLY).getText(), is("2880.00"));
        assertThat(pensionEl.element(MONTHLY).getText(), is("240.00"));

    }

    @Test
    public void isPensionContributionResponseCorrectGivenIncomeIs40000AndContributionPercentIs8() {

        Element requestEl = createElement(REQUEST);
        requestEl.addElement(INCOME).addText("6000");
        requestEl.addElement(PENSION_CONTRIB).addText("8");

        Element response = processor.executeProcessor(createElement(RESPONSE), requestEl);

        Element pensionEl = response.element(PENSION_CONTRIB);
        assertThat(pensionEl.elements().size(), is(2));
        assertThat(pensionEl.element(YEARLY).getText(), is("5760.00"));
        assertThat(pensionEl.element(MONTHLY).getText(), is("480.00"));

    }

    @Test
    public void isExceptionThrownIfPensionContributionEnteredNotValid() {

        Element requestEl = createElement(REQUEST);
        requestEl.addElement(INCOME).addText("40000");
        requestEl.addElement(PENSION_CONTRIB).addText("7");

        try {
            processor.executeProcessor(createElement(RESPONSE), requestEl);
        } catch(IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Invalid pension contribution, please enter either 2,4,6 or 8%."));
        }

    }

}
