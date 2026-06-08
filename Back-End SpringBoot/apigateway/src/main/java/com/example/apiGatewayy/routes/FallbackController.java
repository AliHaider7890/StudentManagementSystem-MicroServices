package com.example.apiGatewayy.routes;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @RequestMapping("/course") // handles GET, POST, PUT, DELETE
    public Mono<Map<String, Object>> courseFallback() {
        Map<String, Object> fallback = new HashMap<>();
        fallback.put("status", "SERVICE_UNAVAILABLE");
        fallback.put("message", "Course service is currently unavailable");
        fallback.put("fallback", true);
        fallback.put("timestamp", System.currentTimeMillis());
        return Mono.just(fallback);
    }

    @RequestMapping("/teacher") // handles ALL methods
    public Mono<Map<String, Object>> teacherFallback() {
        Map<String, Object> fallback = new HashMap<>();
        fallback.put("status", "SERVICE_UNAVAILABLE");
        fallback.put("message", "Teacher service is currently unavailable");
        fallback.put("fallback", true);
        fallback.put("timestamp", System.currentTimeMillis());
        return Mono.just(fallback);
    }
}