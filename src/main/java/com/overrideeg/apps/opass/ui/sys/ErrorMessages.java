/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.overrideeg.apps.opass.ui.sys;

/**
 * @author sergeykargopolov
 */
public enum ErrorMessages {


    DIMENSIONS_ERROR("Error In Dimensions Please Try Again"),
    MISSING_Company_Inv_Setting("Missing Company inventory Setting Please Add it"),
    Can_Not_Add_2_Inv_Settings_In_One_Company("Can Not Add Two Inventory Settings To One Company"),
    REQUEST_TIME_OUT("Request time out"),
    UnitsMustBeInSameGroup("Conditional Units Must Be In Same Group"),
    warehouse_Must_Be_In_His_Branch("warehouse must be in his branch"),
    Could_Not_Create_Transaction("Transaction Could Not Created"),

    WRONG_FIELD_ID("Wrong field id"),
    INCORRECT_CREDIT_WEIGHT("Selected credit weight is incorrect"),
    MISSING_REQUIRED_FIELD("Missing required fields "),
    RECORD_ALREADY_EXISTS("Record already exists"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    NO_RECORD_FOUND("Records not found"),
    AUTHENTICATION_FAILED("Authentication failed"),
    COULD_NOT_UPDATE_RECORD("Could not update record"),
    COULD_NOT_CREATE_RECORD("Could not create record "),
    COULD_NOT_DELETE_RECORD("Could not delete record"),
    EMAIL_ADDRESS_NOT_VERIFIED("Email address could not be verified"),
    MAC_ADDRESS_ILLEGAL("Mac Address Not Registered"),
    Time_EXCEDDED(" Time is Exceeded"),
    DATE_WRONG_EXCEPTION("Date Period Is Wrong"),
    DEPARTMENT_is_invalid("Department IS Invalid for Selected Machine"),
    Branch_is_invalid("Branch IS Invalid for Selected Machine");


    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
