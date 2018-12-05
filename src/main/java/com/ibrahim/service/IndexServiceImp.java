package com.ibrahim.service;

import org.json.simple.JSONObject;
import com.ibrahim.model.RequestData;
import com.ibrahim.model.ResponseISO;
import com.ibrahim.utils.Helper;

public class IndexServiceImp implements IndexService {

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getInquiry(RequestData data) {
		JSONObject json = new JSONObject();
		Helper helper = new Helper();
		try {
			ResponseISO res = helper.sendIso8583(data);
			json.put("message", res.getMessage());
			json.put("status", res.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

}
