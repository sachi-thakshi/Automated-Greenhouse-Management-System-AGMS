package lk.ijse.agmsapigateway.filter;

import lk.ijse.agmsapigateway.util.JwtUtil;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RouteValidator validator;

    public AuthenticationFilter() {
        super(Config.class);
    }

    public static class Config {}

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();

            System.out.println("Gateway received request for path: " + path);

            if (path.toLowerCase().contains("auth")) {
                System.out.println("FORCED BYPASS TRIGGERED FOR: " + path);
                return chain.filter(exchange);
            }

            if (!request.getHeaders().containsKey(org.springframework.http.HttpHeaders.AUTHORIZATION)) {
                System.err.println("Blocking request: No Authorization header found for " + path);
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String authHeader = request.getHeaders().get(org.springframework.http.HttpHeaders.AUTHORIZATION).get(0);
            String token = null;

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7).trim();
            } else {
                System.err.println("Blocking request: Authorization header does not start with Bearer");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            try {
                if (!jwtUtil.validateToken(token)) {
                    System.err.println("Token Validation Failed for: " + path);
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
                System.out.println("Token Validated Successfully for: " + path);
            } catch (Exception e) {
                System.err.println("JWT Exception: " + e.getMessage());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return chain.filter(exchange);
        };
    }

}