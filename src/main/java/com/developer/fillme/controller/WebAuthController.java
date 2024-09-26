package com.developer.fillme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web")
public class WebAuthController {
	
	@GetMapping("/login")
    public String index() {
		
        return "login"; 
    }
	@GetMapping("/auth")
	public String home() {
		
		return "web/home"; 
	}
}
