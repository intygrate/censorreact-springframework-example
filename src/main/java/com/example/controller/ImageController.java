package com.example.controller;

import com.example.model.Payload;
import com.example.model.Response;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ImageController {
	@GetMapping("/image")
	public String view(Model model) {
		model.addAttribute("payload", new Payload());
		return "image";
	}

	@PostMapping("/image")
	public String submitApiCall(@RequestParam MultipartFile file, @ModelAttribute Payload payload, Model model) {
        String crURL = "https://api.censorreact.intygrate.com/v1/image";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-api-key", payload.getApiKey());

        try {
            if (!file.isEmpty()) {
                // Need to pass image base64 metadata
                StringBuilder imageBase64Meta = new StringBuilder("data:");
                imageBase64Meta.append(file.getContentType());
                imageBase64Meta.append(";base64,");

                payload.setImageBytes(imageBase64Meta.toString() + Base64Utils.encodeToString(file.getBytes()));
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        payload.setProfile(payload.getProfile() == "" ? "default" : payload.getProfile().trim());

        // censorREACT Image API expect 'ImageBytes' as the parameter name
        Map param = new HashMap<String, String>();
        param.put("ImageBytes", payload.getImageBytes());
        param.put("profile", payload.getProfile());

        HttpEntity<Map> request = new HttpEntity<>(param, headers);
        Response response = new Response();

        try {
            Object result = new RestTemplate().postForObject(crURL, request, Object.class);
            response.setData(result.toString());
        } catch (Exception ex) {
            response.setData(ex.getMessage());
        }

        model.addAttribute("response", response);
		return "image";
	}
}
