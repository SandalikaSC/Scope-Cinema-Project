//package com.cinema.apigateway.filter;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpHeaders;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Objects;
//
//@Component
//public class GatewayRoutingFilter extends AbstractGatewayFilterFactory<GatewayRoutingFilter.Config> {
//    public GatewayRoutingFilter() {
//        super(Config.class);
//    }
//    @Autowired
//    private RouteValidator validator;
//
//    @Override
//    public GatewayFilter apply(Config config) {
//        return ((exchange, chain) -> {
////            if (validator.isSecured.test(exchange.getRequest())) {
//                //header contains token or not
//               //System.out.println(exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION));
////                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
////                    throw new RuntimeException("missing authorization header");
////                }
//
////                String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
////                if (authHeader != null && authHeader.startsWith("Bearer ")) {
////                    authHeader = authHeader.substring(7);
////                }
////                try {
////                    //REST call to AUTH service
////                    template.getForObject("http://auth-service" + authHeader, String.class);
////                    jwtUtil.validateToken(authHeader);
//
////                } catch (Exception e) {
////                    System.out.println("invalid access...!");
////                    throw new RuntimeException("un authorized access to application");
////                }
////            }
//            return chain.filter(exchange);
//        });
//    }
//
//
//    public static class Config {
//
//    }
//}