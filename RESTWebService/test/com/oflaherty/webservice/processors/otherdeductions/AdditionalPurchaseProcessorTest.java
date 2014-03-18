package com.oflaherty.webservice.processors.otherdeductions;

import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static com.oflaherty.webservice.common.ApplicationConstants.REQUEST;
import static com.oflaherty.webservice.common.ApplicationConstants.RESPONSE;
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
@SuppressWarnings("ALL")
public class AdditionalPurchaseProcessorTest {

    @InjectMocks
    private AdditionalPurchasesProcessor processor;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void withOneAdditionalPurchasesElementsTestCorrectTotalValueAndXmlLayout() {

        Element requestEl = createElement(REQUEST);

        Element additionalPurcahsesEl = requestEl.addElement("additionalPurchases");

        String values[] = {"300.00"};

        buildAdditionalPurchasesEls(1, additionalPurcahsesEl, values);

        Element responseEl = createElement(RESPONSE);
        responseEl = processor.executeProcessor(responseEl, requestEl);

        List<Element> purchaseEls = responseEl.element("additionalPurchases").elements("purchase");

        int i = 1;
        for(Element el : purchaseEls) {
            assertThat(el.element("description").getText(), is("item #" + i));
            assertThat(el.element("value").getText(), is(values[i-1]));
            i++;
        }

        assertThat(responseEl.element("additionalPurchases").element("total").getText(), is("300.00"));

    }

    @Test
    public void withTwoAdditionalPurchasesElementsTestCorrectTotalValueAndXmlLayout() {

        Element requestEl = createElement(REQUEST);

        Element additionalPurcahsesEl = requestEl.addElement("additionalPurchases");

        String values[] = {"800.00", "1500.00"};

        buildAdditionalPurchasesEls(2, additionalPurcahsesEl, values);

        Element responseEl = createElement(RESPONSE);
        responseEl = processor.executeProcessor(responseEl, requestEl);

        List<Element> purchaseEls = responseEl.element("additionalPurchases").elements("purchase");

        int i = 1;
        for(Element el : purchaseEls) {
            assertThat(el.element("description").getText(), is("item #" + i));
            assertThat(el.element("value").getText(), is(values[i-1]));
            i++;
        }

        assertThat(responseEl.element("additionalPurchases").element("total").getText(), is("2300.00"));

    }

    @Test
    public void withThreeAdditionalPurchasesElementsTestCorrectTotalValueAndXmlLayout() {

        Element requestEl = createElement(REQUEST);

        Element additionalPurcahsesEl = requestEl.addElement("additionalPurchases");

        String values[] = {"100.00", "200.00", "300.00"};

        buildAdditionalPurchasesEls(3, additionalPurcahsesEl, values);

        Element responseEl = createElement(RESPONSE);
        responseEl = processor.executeProcessor(responseEl, requestEl);

        List<Element> purchaseEls = responseEl.element("additionalPurchases").elements("purchase");

        int i = 1;
        for(Element el : purchaseEls) {
            assertThat(el.element("description").getText(), is("item #" + i));
            assertThat(el.element("value").getText(), is(values[i-1]));
            i++;
        }

        assertThat(responseEl.element("additionalPurchases").element("total").getText(), is("600.00"));
    }

    @Test
    public void withNoAdditionalPurchasesElementsTestCorrectTotalValueAndXmlLayout() {

        Element requestEl = createElement(REQUEST);

        Element additionalPurcahsesEl = requestEl.addElement("additionalPurchases");

        String values[] = {};

        buildAdditionalPurchasesEls(0, additionalPurcahsesEl, values);

        Element responseEl = createElement(RESPONSE);
        responseEl = processor.executeProcessor(responseEl, requestEl);

        List<Element> purchaseEls = responseEl.element("additionalPurchases").elements("purchase");

        int i = 1;
        for(Element el : purchaseEls) {
            assertThat(el.element("description").getText(), is("item #" + i));
            assertThat(el.element("value").getText(), is(values[i-1]));
            i++;
        }
    }

    private void buildAdditionalPurchasesEls(int size, Element additionalPurcahsesEl, String[] values) {

        for(int i = 1; i <= size; i++) {
            Element purchaseEl = additionalPurcahsesEl.addElement("purchase");
            purchaseEl.addElement("description").addText("item #" + i);
            purchaseEl.addElement("value").addText(values[i-1]);
        }
    }

}
