/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.auth;

import com.overrideeg.apps.opass.annotations.Secured;
import com.overrideeg.apps.opass.io.entities.User;
import com.overrideeg.apps.opass.service.UserService;
import com.overrideeg.apps.opass.service.system.RestLogService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.system.Connection.ResolveTenant;
import com.overrideeg.apps.opass.ui.sys.RequestOperation;
import com.overrideeg.apps.opass.ui.sys.ResponseModel;
import com.overrideeg.apps.opass.ui.sys.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping(ApiUrls.Users_EP)
@Secured(methodsToSecure = {RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.GET})
public class UserEntryPoint {
    @Autowired
    UserService userService;
    @Autowired
    RestLogService restLogService;
    @Autowired
    ResolveTenant resolveTenant;

    @PostMapping
    public @ResponseBody
    User addUser(@RequestBody User userRequest, @RequestHeader Long tenantId,HttpServletRequest request) {
        resolveTenant.resolve(tenantId,request);
        userRequest.setCompany_id(tenantId);
        return userService.save(userRequest);
    }

    @PutMapping
    public @ResponseBody
    ResponseModel updateUser(@RequestBody User userRequest, @RequestHeader Long tenantId,HttpServletRequest request) {
        resolveTenant.resolve(tenantId,request);
        userRequest.setCompany_id(tenantId);
        User updatedUser = userService.update(userRequest);
        ResponseModel update = new ResponseModel(updatedUser, RequestOperation.DELETE, ResponseStatus.SUCCESS);
        return update;
    }

    @GetMapping()
    public List<User> getUsers(@RequestHeader Long tenantId, HttpServletRequest request){
        resolveTenant.resolve(tenantId,request);
        return  this.userService.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@RequestHeader Long tenantId, HttpServletRequest request, @PathVariable Long id){
        resolveTenant.resolve(tenantId,request);
        return  this.userService.findById(id);
    }

    @PutMapping("/{id}/image")
    public @ResponseBody
    ResponseModel updateUserImage(@PathVariable(value = "id") Long inEntityId, @RequestBody User userRequest, @RequestHeader Long tenantId,HttpServletRequest request) {
//        userRequest.setCompany_id(tenantId);
        resolveTenant.resolve(tenantId,request);
        userRequest.setId(inEntityId);
        User updatedUser = userService.updateUserImage(userRequest);
        ResponseModel update = new ResponseModel(updatedUser, RequestOperation.DELETE, ResponseStatus.SUCCESS);
        return update;
    }


    @DeleteMapping("/{id}")
    public @ResponseBody
    ResponseModel deleteEntityById(@PathVariable(value = "id") Long inEntityId, @RequestHeader Long tenantId,HttpServletRequest request) {
        resolveTenant.resolve(tenantId,request);

        userService.delete(inEntityId);
        ResponseModel delete = new ResponseModel(null, RequestOperation.DELETE, ResponseStatus.SUCCESS);
        return delete;
    }
}