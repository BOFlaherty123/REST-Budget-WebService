package audit

/**
 * Retrieves all saved responses.xml files and builds a snapshot of the key data to be sent back to AuditController
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 12/03/14
 * @project RESTWebService
 */
class ProcessBudgetServiceAudits {

    /*
        - Check that the Audit directory is not empty
        - Loop through the Audit Directory
        - For each audit found, iterate over the xml and build a response object (BudgetResponse)
        - Add objects to a List<BudgetResponse> and return to a Spring MVC Controller

        - Look into 'Embedding Groovy' to identify the best way to call the Groovy script from a Java controller and what
          .jar files are required.

     */
    private static List<Map<String, String>> processAuditFileList() {

        def directory = new File("D:\\RAD8\\Personal\\RESTAudit")
        def audits = [];

        directory.eachFile { File auditFile ->

            def response = new XmlSlurper().parse(auditFile)

            String income = response.yearly.income.net.income
            String incomeTax = response.tax.incomeTax.yearly.value
            String niTax = response.tax.nationalInsuranceTax.yearly.value

            assert income != null && incomeTax != null && niTax != null

            def map = [
                    'fileName' : auditFile.getName().trim(),
                    'income' :  income,
                    'incomeTax' : incomeTax,
                    'niTax' :  niTax,
                    'path' :   auditFile.getAbsolutePath().trim()
                ]

            audits.add(map)

        }

        assert audits.size() != 0 && audits != null

        return audits;

    }

}
