/*Copyright (c) 2015-2016 wavemaker.com. All Rights Reserved.

This software is the confidential and proprietary information of wavemaker.com. You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the source code license agreement you entered into with wavemaker.com*/

package com.feasthunt.feasthunt.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/


import com.feasthunt.feasthunt.service.RestaurantService;


import java.io.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.TypeMismatchException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.util.WMMultipartUtils;
import com.wavemaker.runtime.util.WMRuntimeUtils;
import com.wavemaker.runtime.file.model.DownloadResponse;
import com.wordnik.swagger.annotations.*;

import com.feasthunt.feasthunt.*;
import com.feasthunt.feasthunt.service.*;


/**
 * Controller object for domain model class Restaurant.
 * @see com.feasthunt.feasthunt.Restaurant
 */

@RestController(value = "Feasthunt.RestaurantController")
@Api(value = "/feasthunt/Restaurant", description = "Exposes APIs to work with Restaurant resource.")
@RequestMapping("/feasthunt/Restaurant")
public class RestaurantController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantController.class);

	@Autowired
	@Qualifier("feasthunt.RestaurantService")
	private RestaurantService restaurantService;


	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ApiOperation(value = "Returns the list of Restaurant instances matching the search criteria.")
	public Page<Restaurant> findRestaurants( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
		LOGGER.debug("Rendering Restaurants list");
		return restaurantService.findAll(queryFilters, pageable);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ApiOperation(value = "Returns the list of Restaurant instances.")
	public Page<Restaurant> getRestaurants(Pageable pageable) {
		LOGGER.debug("Rendering Restaurants list");
		return restaurantService.findAll(pageable);
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	@ApiOperation(value = "Returns the total count of Restaurant instances.")
	public Long countAllRestaurants() {
		LOGGER.debug("counting Restaurants");
		Long count = restaurantService.countAll();
		return count;
	}


    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @ApiOperation(value = "Returns the Restaurant instance associated with the given id.")
    public Restaurant getRestaurant(@PathVariable("id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Getting Restaurant with id: {}" , id);
        Restaurant instance = restaurantService.findById(id);
        LOGGER.debug("Restaurant details with id: {}" , instance);
        return instance;
    }
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Deletes the Restaurant instance associated with the given id.")
    public boolean deleteRestaurant(@PathVariable("id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Deleting Restaurant with id: {}" , id);
        Restaurant deleted = restaurantService.delete(id);
        return deleted != null;
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @ApiOperation(value = "Updates the Restaurant instance associated with the given id.")
    public Restaurant editRestaurant(@PathVariable("id") Integer id, @RequestBody Restaurant instance) throws EntityNotFoundException {
        LOGGER.debug("Editing Restaurant with id: {}" , instance.getId());
        instance.setId(id);
        instance = restaurantService.update(instance);
        LOGGER.debug("Restaurant details with id: {}" , instance);
        return instance;
    }




	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ApiOperation(value = "Creates a new Restaurant instance.")
	public Restaurant createRestaurant(@RequestBody Restaurant instance) {
		LOGGER.debug("Create Restaurant with information: {}" , instance);
		instance = restaurantService.create(instance);
		LOGGER.debug("Created Restaurant with information: {}" , instance);
	    return instance;
	}

	/**
	 * This setter method should only be used by unit tests
	 * 
	 * @param service
	 */
	protected void setRestaurantService(RestaurantService service) {
		this.restaurantService = service;
	}
}

