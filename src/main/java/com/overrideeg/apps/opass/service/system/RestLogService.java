package com.overrideeg.apps.opass.service.system;

import com.google.gson.Gson;
import com.overrideeg.apps.opass.io.entities.system.RestLog;
import com.overrideeg.apps.opass.io.repositories.system.RestLogRepo;
import com.overrideeg.apps.opass.service.AbstractService;
import com.overrideeg.apps.opass.utils.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RestLogService extends AbstractService<RestLog> {

    public RestLogService(final RestLogRepo inRepository) {
        super(inRepository);
    }


    public void saveLog(String RequestURL, String remoteAddr, String method, Object response) {
        try {
            RestLog restLog = new RestLog();
            restLog.setDate(new Date());
            restLog.setFromIpAddress(remoteAddr);
            restLog.setRequestMethod(method);
            restLog.setName(method + ":  " + RequestURL);
            restLog.setRequestName(method);
            String json = EntityUtils.encode(new Gson().toJson(response));
            restLog.setResponse(json);
            save(restLog);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}