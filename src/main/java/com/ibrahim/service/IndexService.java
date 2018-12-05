package com.ibrahim.service;

import org.json.simple.JSONObject;

import com.ibrahim.model.RequestData;

public interface IndexService {

	JSONObject getInquiry(RequestData data);

}
