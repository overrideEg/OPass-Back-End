package com.overrideeg.apps.opass.io.entity.System;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Date;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestLog extends OEntity {
    private String name;
    private String requestName;
    private String requestMethod;
    private String fromIpAddress;
    private String requestBody;
    private String requestHeaders;
    @Lob
    private String response;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:sss", shape = JsonFormat.Shape.STRING, timezone = "Africa/Cairo")
    private Date date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getFromIpAddress() {
        return fromIpAddress;
    }

    public void setFromIpAddress(String fromIpAddress) {
        this.fromIpAddress = fromIpAddress;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(String requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
