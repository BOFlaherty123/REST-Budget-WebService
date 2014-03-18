package com.oflaherty.webservice.audit;

/**
 * Description Here
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 12/03/14
 * @project RESTWebService
 */
public class Audit {

    private final String fileName;
    private final String yearlyIncome;
    private final String incomeTax;
    private final String natInsTax;
    private final String path;

    public Audit(String fileName, String income, String incomeTax, String niTax, String path) {
        this.fileName = fileName;
        this.yearlyIncome = income;
        this.incomeTax = incomeTax;
        this.natInsTax = niTax;
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public String getYearlyIncome() {
        return yearlyIncome;
    }

    public String getIncomeTax() {
        return incomeTax;
    }

    public String getNatInsTax() {
        return natInsTax;
    }

    public String getPath() {
        return path;
    }
}
