package com.oflaherty.webservice.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Description Here
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 24/03/14
 * @project RESTWebService
 */
@Controller
public class LoginController {

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String displayLogin() {
        return "login";
    }

    @RequestMapping(value="accessDenied", method = RequestMethod.GET)
    public String displayAccessDenied() {
        return "accessDenied";
    }

}
