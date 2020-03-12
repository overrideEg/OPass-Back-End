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
public class ResponseModel<E> {
    private E entity;
    private RequestOperation requestOperation;
    private ResponseStatus responseStatus;

    public ResponseModel(E entity, RequestOperation requestOperation, ResponseStatus responseStatus) {
        this.entity = entity;
        this.requestOperation = requestOperation;
        this.responseStatus = responseStatus;
    }

    public E getEntity() {
        return entity;
    }

    public void setEntity(E entity) {
        this.entity = entity;
    }

    /**
     * @return the requestOperation
     */
    public RequestOperation getRequestOperation() {
        return requestOperation;
    }

    /**
     * @param requestOperation the requestOperation to set
     */
    public void setRequestOperation(RequestOperation requestOperation) {
        this.requestOperation = requestOperation;
    }

    /**
     * @return the responseStatus
     */
    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    /**
     * @param responseStatus the responseStatus to set
     */
    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

}