package com.oflaherty.webservice.audit;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

/**
 * Processes audit files in directory using groovy and returns a List of audit objects to the controller to be displayed in the jsp
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 12/03/14
 * @project RESTWebService
 */
@SuppressWarnings("ALL")
@Controller
@RequestMapping("/audit")
public class AuditController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditController.class);
    private static final String SCRIPT_URL = "D:\\RAD8\\Personal\\RESTWebService\\GroovyScripts\\src\\audit\\ProcessBudgetServiceAudits.groovy";
    private static final String CLEANUP_URL = "D:\\RAD8\\Personal\\RESTWebService\\GroovyScripts\\src\\audit\\AuditFolderCleanup.groovy";

    @RequestMapping(value="/show")
    public String processAudits(ModelMap model, HttpServletRequest request) throws IllegalAccessException, InstantiationException, IOException {

        Class clazz = setupGroovyClassLoader(SCRIPT_URL);

        GroovyObject groovyObject = (GroovyObject) clazz.newInstance();
        Object[] args = {};

        // Retrieves auditFiles from GroovyScript
        List<Map<String, String>> auditFiles = (List<Map<String, String>>) groovyObject.invokeMethod("processAuditFileList", args);

        List<Audit> audits = new ArrayList<>();

        for(Map<String, String> data : auditFiles) {

            Audit auditObj = new Audit(data.get("fileName"), data.get("income"), data.get("incomeTax"), data.get("niTax"), data.get("path"));

            // add audit to model list
            audits.add(auditObj);

            LOGGER.info(format("audit added... %s - £%s - £%s - £%s - path: %s",
                    auditObj.getFileName(),
                    auditObj.getYearlyIncome(),
                    auditObj.getIncomeTax(),
                    auditObj.getNatInsTax(),
                    auditObj.getPath()));
        }

        model.addAttribute("audits", audits);
        model.addAttribute("ipAddress", request.getRemoteAddr());
        model.addAttribute("sessionId", request.getSession().getId());
        model.addAttribute("browser", request.getHeader("User-Agent"));

        return "audit";

    }

    @RequestMapping(value="/deleteAudit/{id}")
    public ModelAndView deleteAuditFile(@PathVariable("id") String id) throws IOException, IllegalAccessException, InstantiationException {
        Class clazz = setupGroovyClassLoader(CLEANUP_URL);

        GroovyObject groovyObject = (GroovyObject) clazz.newInstance();
        Object[] args = {id};

        groovyObject.invokeMethod("deleteAuditFile", args);

        return new ModelAndView("redirect:/audit/show");
    }

    @RequestMapping(value="/deleteAll")
    public void processAudits() throws IOException, IllegalAccessException, InstantiationException {

        Class clazz = setupGroovyClassLoader(CLEANUP_URL);

        GroovyObject groovyObject = (GroovyObject) clazz.newInstance();
        Object[] args = {};

        groovyObject.invokeMethod("deleteAllAuditFiles", args);

    }

    private Class setupGroovyClassLoader(String fileLocation) throws IOException {
        GroovyClassLoader gcl = new GroovyClassLoader();

        return gcl.parseClass(new File(fileLocation));
    }


    @RequestMapping(value="/deleteAudit.htm")
    public String test() {
        System.out.println("deleteAudit.htm method hit");
        return "/deleteAudit";
    }

}
