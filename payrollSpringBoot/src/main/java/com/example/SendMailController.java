package com.example;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.interfaceService.MailService;
import com.example.model.MailModel;

@RestController
@CrossOrigin(origins = "*")
public class SendMailController {
	
	@Autowired
	private  MailService mailService;

	@PostMapping("/sendAnEmail")
	public ResponseEntity<?> sendEmail(@RequestBody MailModel mailModel){
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			mailService.sendMail(mailModel);
			data.put("status", "Success");
			data.put("messege", "mail send successfully");
			return ResponseEntity.status(HttpStatus.OK).body(data);
		} catch (Exception e) {
			data.put("status", "Failed");
			data.put("messege", e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(data);
		}
	}
	
}