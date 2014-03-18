package com.oflaherty.webservice.builders;

import com.oflaherty.webservice.json.UserParams;
import org.dom4j.Element;

/**
 * Builder Interface
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 03/03/14.
 * @project RESTWebService
 */
public interface IBuilder {

    public Element executeBuilder(Element element);
    public Element executeBuilder(UserParams params);

}
