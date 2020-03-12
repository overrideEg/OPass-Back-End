/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.overrideeg.apps.opass.ui.sys;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author sergeykargopolov
 */
@XmlRootElement
public class ErrorMessage {


    private String errorMessage;
    private String errorMessageKey;
    private String href;

    public ErrorMessage() {
    }

    public ErrorMessage(String errorMessage, String errorMessageKey, String href) {
        this.errorMessage = errorMessage;
        this.errorMessageKey = errorMessageKey;
        this.href = href;
    }

    public ErrorMessage(String message, String name, String httpbasartech, StackTraceElement[] stackTrace) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    /**
     * @return the errorMessageKey
     */
    public String getErrorMessageKey() {
        return errorMessageKey;
    }

    /**
     * @param errorMessageKey the errorMessageKey to set
     */
    public void setErrorMessageKey(String errorMessageKey) {
        this.errorMessageKey = errorMessageKey;
    }

    /**
     * @return the href
     */
    public String getHref() {
        return href;
    }

    /**
     * @param href the href to set
     */
    public void setHref(String href) {
        this.href = href;
    }

}
