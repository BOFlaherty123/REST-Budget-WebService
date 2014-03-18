package com.oflaherty.webservice;

import com.oflaherty.webservice.builders.request.RequestBuilder;
import com.oflaherty.webservice.builders.response.ResponseBuilder;
import com.oflaherty.webservice.json.UserParams;
import com.oflaherty.webservice.json.UserParamsBuilder;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import static com.oflaherty.webservice.common.ApplicationConstants.*;
import static java.lang.String.format;

/**
 * The REST Service Controller (EndPoint)
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 26/02/14.
 * @project RESTWebService
 */
@SuppressWarnings("ALL")
@Controller
@RequestMapping("/budget")
public class BudgetController {

    @Autowired
    private RequestBuilder requestBuilder;
    @Autowired
    private UserParamsBuilder userParamsBuilder;
    @Autowired
    private ResponseBuilder responseBuilder;

    private static final Logger LOGGER = LoggerFactory.getLogger(BudgetController.class);

    /**
     * Calculates and returns an xml response (Restful Webservice entry point)
     *
     * @param jsonString json string containing the relevant key/value pairs
     * @return an xml response document and a HTTP status code (201 if successful)
     */
    @RequestMapping(value="/calculate", method = RequestMethod.POST, produces = { MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity processCalculateBudgetResponse(@RequestBody String jsonString) throws Exception {

        final String METHOD_NAME = ".processCalculateBudgetResponse()";

        LOGGER.info(format(GENERIC_LOG_FORMAT, this.getClass(), METHOD_NAME, METHOD_ENTERED));

        // 1. Build entered user paramters (jsonString) into an object (JSONOjbject) and return object to this controller
        LOGGER.info(format(GENERIC_LOG_FORMAT, this.getClass(), METHOD_NAME, "Build userParams..."));
        UserParams userParams = userParamsBuilder.buildUserParamObject(jsonString);

        // 2. Build JSONObject to an XML document (Request)
        LOGGER.info(format(GENERIC_LOG_FORMAT, this.getClass(), METHOD_NAME, "Build request xml from userParams..."));
        Element request = requestBuilder.executeBuilder(userParams);

        // 3. Pass object to the calculation processors and Build Response XML message
        LOGGER.info(format(GENERIC_LOG_FORMAT, this.getClass(), METHOD_NAME, "Build response xml from the request..."));
        Element response = responseBuilder.executeBuilder(request);

        // Log XML Response Audit
        logXMLResponseAudit(response);

        LOGGER.info(format(GENERIC_LOG_FORMAT, this.getClass(), METHOD_NAME, METHOD_EXIT));

        return  new ResponseEntity(response.asXML(), HttpStatus.OK);
    }

    /**
     * Create audit file containing the response xml
     *
     * @param response dom4j element
     * @throws IOException
     */
    private void logXMLResponseAudit(Element response) throws IOException {

        String auditId = Long.toString(UUID.randomUUID().getLeastSignificantBits());

        XMLWriter writer = new XMLWriter(new FileWriter( "D:\\RAD8\\Personal\\RESTAudit\\response" + auditId + ".xml" ));
        writer.write( response );
        writer.close();

        // 4. On Success & Unique ID
        response.addElement("id").addText("bs" + auditId);
        response.addElement("statusCode").addText(HttpStatus.OK.toString());

    }

}
