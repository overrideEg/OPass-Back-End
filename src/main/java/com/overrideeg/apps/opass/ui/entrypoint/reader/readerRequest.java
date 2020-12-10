/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.reader;

import com.fasterxml.jackson.annotation.JsonProperty;

public class readerRequest {
    @JsonProperty(required = true)
    private Long company_id;
    private Long employee_id;
    private String qr;
    private Long scan_time;
    private String lat;
    private String lng;
    private String employeeImage;

    public Long getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Long employee_id) {
        this.employee_id = employee_id;
    }

    public Long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Long company_id) {
        this.company_id = company_id;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public Long getScan_time() {
        return scan_time;
    }

    public void setScan_time(Long scan_time) {
        this.scan_time = scan_time;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getEmployeeImage () {
        return employeeImage;
    }

    public void setEmployeeImage ( String employeeImage ) {
        this.employeeImage = employeeImage;
    }
}
