package com.overrideeg.apps.opass.service.Admin.BasicData.Address;

import com.overrideeg.apps.opass.io.entity.Admin.BasicData.Address.city;
import com.overrideeg.apps.opass.io.entity.Admin.BasicData.Address.cityLocalized;
import com.overrideeg.apps.opass.io.repositories.Admin.BasicData.Address.cityRepo;
import com.overrideeg.apps.opass.service.AbstractService;
import org.springframework.stereotype.Service;
@Service
public class cityService extends AbstractService<city, cityLocalized> {

public cityService(final cityRepo inRepository) {
super(inRepository);
}

}