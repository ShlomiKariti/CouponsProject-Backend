package com.shlomi.coupons.logic;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class CacheController {
	
	private Map<String, Object> dataMap;
	
	public CacheController() {
		this.dataMap = new HashMap<>();
	}

	public void put(String key, Object value) {
		this.dataMap.put(key, value);
	}
	
	public Object get(String key) {
		return this.dataMap.get(key);
	}
	
}
