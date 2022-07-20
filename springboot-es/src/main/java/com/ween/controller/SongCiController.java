package com.ween.controller;

import com.ween.service.SongCiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@RequestMapping("/songci")
public class SongCiController {

	@Resource
	private SongCiService songCiService;

	@PostMapping("/search")
	public ResponseEntity<Object> search(@RequestParam String keyword) throws Exception {
		return ResponseEntity.ok(songCiService.search(keyword));
	}
}
