package iser.apiOrion.auth.filter;

import iser.apiOrion.auth.dto.LoginDto;
import iser.apiOrion.auth.dto.TokenValidationResult;
import iser.apiOrion.auth.serviceImpl.JwtTokenProvider;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

@Component
public class CustomFilter implements Filter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Value("${valida.insertar-datos.requestURI.igual-noToken:total-lock}")
    private String insertarDatosRequestURI;

    @Value("${clave.valida.datos:total-lock}")
    private String claveValidaDatos;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        //res.setHeader("Access-Control-Allow-Origin", "*");
        //res.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        //res.setHeader("Access-Control-Max-Age", "3600");

        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Content-Length, X-Requested-With");

        System.out.println("---------------------------------------------------------");

        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println(headerName + ": " + req.getHeader(headerName));
        }
        System.out.println("---------------------------------------------------------");

        System.out.println("Request Method: " + ((HttpServletRequest) request).getHeader("Authorization"));
        System.out.println(" Request Headers: " + req.getHeader("Authorization"));
        System.out.println(" Response Headers: " + res.getHeader("Authorization"));
        System.out.println(" URI: " + req.getRequestURI());
        System.out.println(" Requieres token? " + this.jwtTokenProvider.requestURINoToken(req.getRequestURI()));

        if (this.jwtTokenProvider.requestURINoToken(req.getRequestURI()) || (req.getRequestURI().equals(insertarDatosRequestURI) && res.getHeader("clave").equals(claveValidaDatos))) {
            System.out.println("No requiere token");
            chain.doFilter(request, response);
        } else {
            System.out.println("Requiere token");

            String token = this.jwtTokenProvider.extractToken(req);

            TokenValidationResult validationResult = this.jwtTokenProvider.resolveToken(token);
            if (validationResult.isValid()) {
                System.out.println("Token valido");
                res.addHeader("Authorization", jwtTokenProvider.createToken(this.jwtTokenProvider.getSubject(token)));
                res.addHeader("Access-Control-Expose-Headers", "Authorization");
                chain.doFilter(request, response);
            } else {
                System.out.println("Token invalido: " + validationResult.getMessage());
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, validationResult.getMessage());
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Init Filter");
    }

    @Override
    public void destroy() {
        System.out.println("Destroy Filter");
    }
}
