package com.overrideeg.apps.opass.ui.entrypoint;


import com.overrideeg.apps.opass.io.entities.system.OEntity;
import com.overrideeg.apps.opass.service.AbstractService;
import com.overrideeg.apps.opass.ui.sys.ResponseModel;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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


public abstract class RestEntryPoint<E extends OEntity> {
    /* Constant(s): */

    /* Instance variable(s): */
    protected AbstractService<E> mService;
    private final Class<E> entityClass;

    public RestEntryPoint() {
        this.entityClass = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];

    }


    @PostMapping
    public @ResponseBody
    E postOne(@Valid @RequestBody E req,
              @RequestHeader(name = "lang") String lang, HttpServletRequest request) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        E resp = (E) mService.save(req,lang);
        return resp;
    }


    @PostMapping("arr")
    public @ResponseBody
    List<E> postArray(@Valid @RequestBody List<E> inEntity,@RequestHeader(name = "lang") String lang, HttpServletRequest request) {
        List<E> resp = mService.saveArray(inEntity);
        return resp;
    }

    @GetMapping
    public @ResponseBody
    List<E> getAll(HttpServletRequest request,
                   @RequestParam(name = "start", required = false) Integer start,
                   @RequestParam(name = "limit", required = false) Integer limit) {
        if (start == null) start = 0;
        if (limit == null) limit = 25;
        List<E> resp = mService.findAll(start, limit);
        return resp;
    }

    @GetMapping("/{id}")
    public Optional<E> getEntityById(@PathVariable(value = "id") Long inEntityId, HttpServletRequest request) {
        Optional<E> response = mService.find(inEntityId);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseModel deleteEntityById(@PathVariable(value = "id") Long inEntityId, HttpServletRequest request) {
        ResponseModel delete = mService.delete(inEntityId);
        return delete;
    }



    @PutMapping
    public @ResponseBody
    ResponseModel updateEntity(@RequestBody final E inEntity, HttpServletRequest request) throws NoSuchMethodException {

        ResponseModel update = mService.update(inEntity);
        return update;
    }


    @PutMapping("arr")
    public @ResponseBody
    List<ResponseModel> updateArray(@Valid @RequestBody List<E> inEntity, HttpServletRequest request) throws NoSuchMethodException {
        List<ResponseModel> update = mService.update(inEntity);
        return update;
    }


    /**
     * Creates an array containing the entities in the supplied list.
     *
     * @param inEntityList List of entities.
     * @return Array containing the entities from the list.
     */
    protected abstract E[] entityListToArray(List<E> inEntityList);

    public AbstractService<E> getService() {
        return mService;
    }

    public void setService(final AbstractService<E> inService) {
        mService = inService;
    }
}
