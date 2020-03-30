package com.overrideeg.apps.opass.ui.entrypoint;


import com.overrideeg.apps.opass.io.entities.system.OEntity;
import com.overrideeg.apps.opass.service.AbstractService;
import com.overrideeg.apps.opass.service.system.RestLogService;
import com.overrideeg.apps.opass.system.Connection.TenantContext;
import com.overrideeg.apps.opass.system.Connection.TenantResolver;
import com.overrideeg.apps.opass.ui.sys.ResponseModel;
import com.overrideeg.apps.opass.utils.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

/**
 * Abstract base class for REST resources exposing operations on an entity type.
 * All operations will return HTTP status 500 with a plain text body containing an
 * error message if an error occurred during request processing.
 *
 * @param <E> Entity type.
 * @author Ivan Krizsan
 */

@CrossOrigin(origins = "*"
        , methods = {RequestMethod.POST,
        RequestMethod.DELETE, RequestMethod.GET,
        RequestMethod.PUT, RequestMethod.OPTIONS, RequestMethod.HEAD},
        allowCredentials = "true", allowedHeaders = "*")
@RestController
public abstract class RestEntryPoint<E extends OEntity> {
    /* Constant(s): */

    @Autowired
    private TenantResolver tenantResolver;
    @Autowired
    RestLogService restLogService;
    /* Instance variable(s): */
    protected AbstractService<E> mService;
    private final Class<E> entityClass;

    public RestEntryPoint() {
        this.entityClass = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @PostMapping
    public @ResponseBody
    E postOne(@RequestBody E req, @RequestHeader Long tenantId, HttpServletRequest request) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        validateFields(req);
        resolveTenant(tenantId, request);
        E resp = (E) mService.save(req);
        return resp;
    }

    public void validateFields(E req) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for (Field field : req.getClass().getDeclaredFields()) {
            Class<?> aClass = field.getType();
            String name = aClass.getSimpleName();
            switch (name) {
                case "String":
                case "Long":
                case "long":
                case "Boolean":
                case "boolean":
                case "translatedField":
                case "Date":
                case "List":
                case "Double":
                case "attType":
                case "employeeStatus":
                case "Integer":
                case "int":
                case "userType":
                case "shiftHours":
                case "attendanceRules":
                    continue;
                default:
                    Object result = EntityUtils.runGetter(field, req);
                    if (result != null) {
                        Boolean valid = (Boolean) aClass.getDeclaredMethod("isValid").invoke(result);
                        if (!valid) {
                            EntityUtils.runSetter(field, req, null);
                        }
                    }

            }
        }
    }

    public void resolveTenant(Long tenantId, HttpServletRequest request) {
        if (tenantId == 0) {
            TenantContext.setCurrentTenant(null);
        }
        if (tenantId != 0) {
            TenantContext.setCurrentTenant(tenantId);
        }
        restLogService.saveLog(request.getRequestURI(), request.getRemoteAddr(), request.getMethod());

    }


    @PostMapping("arr")
    public @ResponseBody
    List<E> postArray(@Valid @RequestBody List<E> inEntity, @RequestHeader Long tenantId, HttpServletRequest request) {
        inEntity.forEach(req -> {
            try {
                validateFields(req);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        resolveTenant(tenantId, request);
        List<E> resp = mService.saveArray(inEntity);
        return resp;
    }


    @GetMapping
    public @ResponseBody
    List<E> getAll(HttpServletRequest request, @RequestHeader Long tenantId,
                   @RequestParam(name = "start", required = false) Integer start,
                   @RequestParam(name = "limit", required = false) Integer limit) {
        resolveTenant(tenantId, request);

        if (start == null) start = 0;
        if (limit == null) limit = 25;
        List<E> resp = mService.findAll(start, limit);
        return resp;
    }

    @GetMapping("/{id}")
    public @ResponseBody
    Optional<E> getEntityById(@PathVariable(value = "id") Long inEntityId, @RequestHeader Long tenantId, HttpServletRequest request) {
        resolveTenant(tenantId, request);
        Optional<E> response = mService.find(inEntityId);
        return response;
    }

    @DeleteMapping("/{id}")
    public @ResponseBody
    ResponseModel deleteEntityById(@PathVariable(value = "id") Long inEntityId, @RequestHeader Long tenantId, HttpServletRequest request) {
        resolveTenant(tenantId, request);
        ResponseModel delete = mService.delete(inEntityId);
        return delete;
    }


    @PutMapping
    public @ResponseBody
    ResponseModel updateEntity(@RequestBody final E inEntity, @RequestHeader Long tenantId, HttpServletRequest request) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        validateFields(inEntity);
        resolveTenant(tenantId, request);
        ResponseModel update = mService.update(inEntity);
        return update;
    }


    @PutMapping("arr")
    public @ResponseBody
    List<ResponseModel> updateArray(@Valid @RequestBody List<E> inEntity, @RequestHeader Long tenantId, HttpServletRequest request) throws NoSuchMethodException {
        inEntity.forEach(req -> {
            try {
                validateFields(req);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        });
        resolveTenant(tenantId, request);
        List<ResponseModel> update = mService.update(inEntity);
        return update;
    }


    public AbstractService<E> getService() {
        return mService;
    }

    public void setService(final AbstractService<E> inService) {
        mService = inService;
    }
}
