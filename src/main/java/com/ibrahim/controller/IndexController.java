package com.ibrahim.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ibrahim.model.RequestData;
import com.ibrahim.service.IndexService;

@RestController
public class IndexController {
	
	@Autowired
	private IndexService indexService;

	@RequestMapping(value = "/inquiry", method = RequestMethod.POST)
    public ResponseEntity<?> inquiry(@RequestBody RequestData data) {
		JSONObject list = indexService.getInquiry(data);
    	return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
