package com.example.controller;

import com.example.model.Payload;
import com.example.model.Response;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class TextController {
	@GetMapping("/text")
	public String view(Model model) {
		model.addAttribute("payload", new Payload());
		return "text";
	}

	@PostMapping("/text")
	public String submitApiCall(@ModelAttribute Payload payload, Model model) {
        String crURL = "https://api.censorreact.intygrate.com/v1/text";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-api-key", payload.getApiKey());

        payload.setProfile(payload.getProfile() == "" ? "default" : payload.getProfile().trim());
        payload.setText(payload.getText() == "" ? "Specify your payload here" : payload.getText().trim());

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Payload> request = new HttpEntity<>(payload, headers);

        Response response = new Response();
        try {
            Object result = restTemplate.postForObject(crURL, request, Object.class);
            response.setData(result.toString());
        } catch (Exception ex) {
            response.setData(ex.getMessage());
        }

        model.addAttribute("response", response);
		return "text";
	}
}
