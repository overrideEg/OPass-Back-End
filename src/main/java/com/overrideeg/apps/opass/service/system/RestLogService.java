package com.overrideeg.apps.opass.service.system;

import com.overrideeg.apps.opass.io.entities.system.RestLog;
import com.overrideeg.apps.opass.io.repositories.system.RestLogRepo;
import com.overrideeg.apps.opass.service.AbstractService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RestLogService extends AbstractService<RestLog> {

    public RestLogService(final RestLogRepo inRepository) {
        super(inRepository);
    }


    public void saveLog(String RequestURL, String remoteAddr, String method) {
        try {
            RestLog restLog = new RestLog();
            restLog.setDate(new Date());
            restLog.setFromIpAddress(remoteAddr);
            restLog.setRequestMethod(method);
            restLog.setName(method + ":  " + RequestURL);
            restLog.setRequestName(method);
            save(restLog);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}