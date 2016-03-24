package com.phoenixkahlo.messaging.utils;

import java.util.HashMap;
import java.util.Map;

import com.phoenixkahlo.messaging.messagetypes.Resource;

public class ResourceRepository<E extends Resource> {

	private Map<String, E> map = new HashMap<String, E>();
	
}
