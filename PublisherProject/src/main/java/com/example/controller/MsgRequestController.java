package com.example.controller;

import com.example.model.RequestVo;
import com.example.model.Response;
import com.example.service.RequestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class MsgRequestController {

	private static final Logger log = LogManager.getLogger();

	@Autowired
	private RequestService requestService;

	@PostMapping("/msg")
	public ResponseEntity<Response> createRequest(@RequestBody RequestVo requestVo) {

		ThreadContext.put("request.token", requestVo.getTokenKey());
		log.info("Publisher receive request from client {}",requestVo.toString());

		Response response = requestService.updateMsgRequest(requestVo);
		response.setData(requestVo.getItem().get(0).getQrInfor());
		log.info("Publisher response to client {}", response.toString());

		ThreadContext.clearAll();
		return ResponseEntity.ok().body(response);
	}

}
