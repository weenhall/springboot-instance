package com.ween.controller;

import com.ween.service.DocumentsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@RequestMapping("/document")
public class DocumentSearchController {

	@Resource
	private DocumentsService documentsService;

	@PostMapping("/search")
	public ResponseEntity<Object> search(@RequestParam String keyword){
		return ResponseEntity.ok(documentsService.search(keyword));
	}
}
