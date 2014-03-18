package com.oflaherty.webservice.json;

import java.math.BigDecimal;
import java.util.Map;

/**
 * User Parameter Model Object
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 27/02/14.
 * @project RESTWebService
 */
public class UserParams {

    private BigDecimal income;
    private BigDecimal rent;
    private BigDecimal councilTax;
    private BigDecimal gym;
    private BigDecimal internet;
    private BigDecimal electricity;
    private BigDecimal honmePhone;
    private BigDecimal mobilePhone;
    private BigDecimal creditCard;
    private BigDecimal tvLicense;
    private BigDecimal savings;
    private BigDecimal pensionContrib;
    private Map<String, BigDecimal> addPurchases;

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getRent() {
        return rent;
    }

    public void setRent(BigDecimal rent) {
        this.rent = rent;
    }

    public BigDecimal getCouncilTax() {
        return councilTax;
    }

    public void setCouncilTax(BigDecimal councilTax) {
        this.councilTax = councilTax;
    }

    public BigDecimal getGym() {
        return gym;
    }

    public void setGym(BigDecimal gym) {
        this.gym = gym;
    }

    public BigDecimal getInternet() {
        return internet;
    }

    public void setInternet(BigDecimal internet) {
        this.internet = internet;
    }

    public BigDecimal getElectricity() {
        return electricity;
    }

    public void setElectricity(BigDecimal electricity) {
        this.electricity = electricity;
    }

    public BigDecimal getHonmePhone() {
        return honmePhone;
    }

    public void setHonmePhone(BigDecimal honmePhone) {
        this.honmePhone = honmePhone;
    }

    public BigDecimal getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(BigDecimal mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public BigDecimal getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(BigDecimal creditCard) {
        this.creditCard = creditCard;
    }

    public BigDecimal getTvLicense() {
        return tvLicense;
    }

    public void setTvLicense(BigDecimal tvLicense) {
        this.tvLicense = tvLicense;
    }

    public BigDecimal getSavings() {
        return savings;
    }

    public void setSavings(BigDecimal savings) {
        this.savings = savings;
    }

    public BigDecimal getPensionContrib() {
        return pensionContrib;
    }

    public void setPensionContrib(BigDecimal pensionContrib) {
        this.pensionContrib = pensionContrib;
    }

    public Map<String, BigDecimal> getAddPurchases() {
        return addPurchases;
    }

    public void setAddPurchases(Map<String, BigDecimal> addPurchases) {
        this.addPurchases = addPurchases;
    }
}
