/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.overrideeg.apps.opass.system.Caching;


import java.util.*;

public class OCacheManager {

	private static OCacheManager instance;
	private static Object monitor = new Object();
	private Map<String, Object> cache = Collections.synchronizedMap(new HashMap<String, Object>());


	public OCacheManager() {
	}

	public void put(String cacheKey, Object value) {
		cache.put(cacheKey, value);
	}

	public void put(String cacheKey, Object value, Integer numberOfMinutes) {
		int minute = numberOfMinutes*60*1000 ;
		cache.put(cacheKey, value);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				clear(cacheKey);
			}
		},minute);
	}

	public Object get(String cacheKey) {
		return cache.get(cacheKey);
	}

	public void clear(String cacheKey) {
		cache.put(cacheKey, null);
	}

	public void clear() {
		cache.clear();
	}

	public static OCacheManager getInstance() {
		if (instance == null) {
			synchronized (monitor) {
				if (instance == null) {
					instance = new OCacheManager();
				}
			}
		}
		return instance;
	}

}