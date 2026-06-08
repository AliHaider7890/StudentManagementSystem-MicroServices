//package com.example.apiGatewayy.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//@Component
//public class JwtAuthenticationFilter implements GlobalFilter {
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//
//        String path = exchange.getRequest().getURI().getPath();
//
//        // 🔓 AUTH endpoints bypass
//        if (path.startsWith("/auth")
//              //  && path.startsWith("/users")
//        ) {
//            return chain.filter(exchange);
//        }
//
//        // 🔐 Get Authorization header
//        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            return unauthorized(exchange);
//        }
//
//        String token = authHeader.substring(7);
//
//        // ❌ Invalid token
//        if (!jwtUtil.isValid(token)) {
//            return unauthorized(exchange);
//        }
//
//        // 🔥 Extract role
//        String role = jwtUtil.extractRole(token);
//
//        // 🎯 ROLE BASED ACCESS
//        if (path.startsWith("/course") && !role.equals("ADMIN") )  {
//            return forbidden(exchange);
//        }
//
//        if (path.startsWith("/teacher") &&
//                !(role.equals("ADMIN") || role.equals("TEACHER")) ||
//                role.equals("ROLE_ADMIN") || role.equals("ROLE_TEACHER")) {
//            return forbidden(exchange);
//        }
//
//        if (path.startsWith("/student") && !role.equals("STUDENT")) {
//            return forbidden(exchange);
//        }
//
//        return chain.filter(exchange);
//    }
//
//    private Mono<Void> unauthorized(ServerWebExchange exchange) {
//        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//        return exchange.getResponse().setComplete();
//    }
//
//    private Mono<Void> forbidden(ServerWebExchange exchange) {
//        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
//        return exchange.getResponse().setComplete();
//    }
//}

package com.example.apiGatewayy.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        System.out.println("🔍 Gateway Filter - Path: " + path);

        // 🔓 AUTH endpoints bypass (no token required)
        if (path.startsWith("/auth")) {
            System.out.println("✅ Auth endpoint, bypassing JWT");
            return chain.filter(exchange);
        }

        // 🔐 Get Authorization header
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("❌ No valid Authorization header");
            return unauthorized(exchange);
        }

        String token = authHeader.substring(7);

        // ❌ Invalid token
        if (!jwtUtil.isValid(token)) {
            System.out.println("❌ Invalid JWT token");
            return unauthorized(exchange);
        }

        // 🔥 Extract role from token
        String role = jwtUtil.extractRole(token);
        System.out.println("✅ Token valid - Role: " + role);

        // 🎯 ROLE BASED ACCESS CONTROL (Fixed)

        // Case 1: Course endpoints - Only ADMIN
        if (path.startsWith("/courses")) {
            if (!role.equals("ADMIN")) {
                System.out.println("❌ Forbidden: " + role + " cannot access /course");
                return forbidden(exchange);
            }
            System.out.println("✅ ADMIN access granted to /course");
        }

      //  else if(path.startsWith("/"))

        // Case 2: Teacher endpoints - ADMIN or TEACHER
        else if (path.startsWith("/teacher")) {
            if (!(role.equals("ADMIN") || role.equals("TEACHER"))) {
                System.out.println("❌ Forbidden: " + role + " cannot access /teacher");
                return forbidden(exchange);
            }
            System.out.println("✅ " + role + " access granted to /teacher");
        }

        // Case 3: Student endpoints - Only STUDENT
//        else if (path.startsWith("/api/students")) {
//            if (!role.equals("STUDENT") || !role.equals("ADMIN")) {
//                System.out.println("❌ Forbidden: " + role + " cannot access /student");
//                return forbidden(exchange);
//            }
//            System.out.println("✅ STUDENT access granted to /student");
//        }

        else if (path.startsWith("/api/students")) {
            if (!role.equals("STUDENT") && !role.equals("ADMIN")) {
                // TEACHER ya koi aur role aayega toh yahan enter karega
                System.out.println("❌ Forbidden: " + role + " cannot access /students");
                return forbidden(exchange);
            }
            // SIRF STUDENT ya ADMIN yahan tak pohoch payega
            System.out.println("✅ Access granted to /students for role: " + role);
        }

        // Case 4: Users endpoints - Only ADMIN
        else if (path.startsWith("/users")) {
            if (!role.equals("ADMIN")) {
                System.out.println("❌ Forbidden: " + role + " cannot access /users");
                return forbidden(exchange);
            }
            System.out.println("✅ ADMIN access granted to /users");
        }
        // /api/timetable
        else if (path.startsWith("/timetable/GetMyTimeTable")) {

            if (!(role.equals("TEACHER") || role.equals("ADMIN"))) {
                System.out.println("❌ Forbidden: " + role);
                return forbidden(exchange);
            }

            System.out.println("✅ Access granted to GetMyTimeTable for: " + role);
        }

        else if (path.startsWith("/timetable")) {
            if (!role.equals("ADMIN")) {
                System.out.println("❌ Forbidden: " + role + " cannot access /users");
                return forbidden(exchange);
            }
            System.out.println("✅ ADMIN access granted to /users");
        }

//        else if (path.startsWith("/api/students")) {
//            if (!role.equals("ADMIN") || !role.equals("STUDENTS")) {
//                System.out.println("❌ Forbidden: " + role + " cannot access /users");
//                return forbidden(exchange);
//            }
//            System.out.println("✅ ADMIN access granted to /users");
//        }



        // Forward request with original headers
        return chain.filter(exchange);
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    private Mono<Void> forbidden(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -1; // Highest priority
    }
}