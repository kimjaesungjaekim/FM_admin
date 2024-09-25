package com.developer.fillme.controller;

import com.developer.fillme.constant.EPolicyType;
import com.developer.fillme.service.IPolicyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Tag(name = "POLICY", description = "API POLICY")
@RequiredArgsConstructor
@RequestMapping("/api/policy")
public class PolicyController {

	private final IPolicyService iPolicyService;
	@GetMapping()
	public String getTemplatesPolicy(@RequestParam EPolicyType type) {
		return iPolicyService.getTemplatesPolicy(type);
	}

}
