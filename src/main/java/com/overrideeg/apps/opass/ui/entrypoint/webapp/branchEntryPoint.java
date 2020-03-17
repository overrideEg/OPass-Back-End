package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.io.entities.branch;
import com.overrideeg.apps.opass.service.branchService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Component
@RequestMapping(ApiUrls.branch_EP)
public class branchEntryPoint extends RestEntryPoint<branch> {

    public branchEntryPoint(final branchService inService) {
        setService(inService);
    }

    @Override
    protected branch[] entityListToArray(List<branch> inEntityList) {
        return inEntityList.toArray(new branch[inEntityList.size()]);
    }

}