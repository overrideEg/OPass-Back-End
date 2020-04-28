package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.branch;
import com.overrideeg.apps.opass.io.entities.department;
import com.overrideeg.apps.opass.io.repositories.departmentRepo;
import com.overrideeg.apps.opass.io.repositories.impl.departmentRepoImpl;
import com.overrideeg.apps.opass.ui.sys.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class departmentService extends AbstractService<department> {

    @Autowired
    departmentRepoImpl departmentRepo;

    public departmentService ( final departmentRepo inRepository ) {
        super(inRepository);
    }

    @Override
    public department save ( department inEntity ) {
        inEntity.updateDateTime = new Date().getTime();
        return super.save(inEntity);
    }

    @Override
    public ResponseModel update ( department inEntity ) {
        inEntity.updateDateTime = new Date().getTime();
        return super.update(inEntity);
    }

    public department getDepartmentAtDate ( Long id, Date date ) {
        long time = date.getTime();
        List<department> departments = departmentRepo.auditDepartment(id);

        List<department> collect = departments
                .stream()
                .filter(department -> department.updateDateTime.compareTo(time) > 0).collect(Collectors.toList());
        branch returnValue = new branch();
        if (collect.size() > 0) {
            return collect.get(collect.size() - 1);
        } else {
            return find(id).get();
        }
    }
}