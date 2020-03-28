/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.overrideeg.apps.opass.exceptions.AuthenticationException;
import com.overrideeg.apps.opass.exceptions.CouldNotCreateRecordException;
import com.overrideeg.apps.opass.io.entities.attendance;
import com.overrideeg.apps.opass.ui.entrypoint.reader.qrData;
import com.overrideeg.apps.opass.ui.entrypoint.reader.readerRequest;
import com.overrideeg.apps.opass.utils.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class readerService {
    @Autowired
    attendanceService attendanceService;
    @Autowired
    attendanceRulesService rulesService;
    @Autowired
    qrMachineService qrMachineService;

    public attendance validate(readerRequest request) {
        qrData qr = handleQrBody(request.getQr());
        //todo bimbo work here
        return null;
    }

    /**
     * method that decode qr encoded from request and map it into object called qrData
     *
     * @param qr Contains encoded qr from request
     * @return qr data decoded
     */
    private qrData handleQrBody(String qr) {
        String encodedOverride = EntityUtils.encode("OVERRIDE");
        String override = qr.substring(0, qr.indexOf(","));
        if (!override.equalsIgnoreCase(encodedOverride))
            throw new AuthenticationException("Text Not Illegal Here");
        String qrBodyEncoded = qr.substring(qr.indexOf(",") + 1);
        String decodedQr;
        try {
            decodedQr = EntityUtils.decode(qrBodyEncoded);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CouldNotCreateRecordException("Error During Decode Qr Data");
        }
        ObjectMapper mapper = new ObjectMapper();
        qrData returnValue = null;
        try {
            returnValue = mapper.readValue(decodedQr, qrData.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new CouldNotCreateRecordException("Error During Read Qr Data Or Missing Required Field");
        }
        return returnValue;
    }
}
