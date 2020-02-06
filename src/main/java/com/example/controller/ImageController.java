package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ImageController {
	@GetMapping("/image")
	public String view(Model model) {
		return "image";
	}
}
