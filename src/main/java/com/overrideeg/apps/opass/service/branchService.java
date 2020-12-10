package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.branch;
import com.overrideeg.apps.opass.io.repositories.branchRepo;
import com.overrideeg.apps.opass.io.repositories.impl.branchRepoImpl;
import com.overrideeg.apps.opass.ui.sys.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class branchService extends AbstractService<branch> {

    @Autowired
    branchRepoImpl branchRepo;

    public branchService ( final branchRepo inRepository ) {
        super(inRepository);
    }

    @Override
    public branch save ( branch inEntity ) {
        inEntity.updateDateTime = new Date().getTime();
        return super.save(inEntity);
    }

    @Override
    public ResponseModel update ( branch inEntity ) {
        inEntity.updateDateTime = new Date().getTime();
        return super.update(inEntity);
    }

    public branch getBranchAtDate ( Long id, Date date ) {
        long time = date.getTime();
        List<branch> branchs = branchRepo.auditBranch(id);

        List<branch> collect = branchs
                .stream()
                .filter(branch -> branch.updateDateTime.compareTo(time) > 0).collect(Collectors.toList());
        branch returnValue = new branch();
        if (collect.size() > 0) {
            return collect.get(collect.size() - 1);
        } else {
            return find(id).get();
        }
    }
}