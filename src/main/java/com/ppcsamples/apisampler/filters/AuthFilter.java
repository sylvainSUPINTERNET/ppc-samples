package com.ppcsamples.apisampler.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ppcsamples.apisampler.DTO.UserDataDTO;
import com.ppcsamples.apisampler.DTO.UserDetailsDTO;
import com.ppcsamples.apisampler.services.AuthCheckService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class AuthFilter implements Filter {


    @Value("${ppc.authCheckUrl}")
    String authCheckUrl;

    @Autowired
    AuthCheckService AuthCheckService;

    

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if ( req.getHeader("Authorization") == null ) {

            req.setAttribute("filterPpcToken", ""); 
            req.setAttribute("userDetails", null);

            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is not valid.");

        } else {
            
            String jwtToken = req.getHeader("Authorization").split(" ")[1];

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + jwtToken);
            headers.set("Content-type",MediaType.APPLICATION_JSON_VALUE);

            var entity = new HttpEntity(headers);
    
            ResponseEntity<UserDataDTO> respAuthCheck = this.AuthCheckService.getRestTemplate().exchange(this.authCheckUrl, HttpMethod.GET, entity, UserDataDTO.class);
            UserDetailsDTO userDetailsDTO = respAuthCheck.getBody().data;
            
            /*
            <200,{"data":{"sub":"114706333881848131803","name":"Sylvain Joly","given_name":"Sylvain","family_name":"Joly","picture":"https://lh5.googleusercontent.com/-a4ImIYU-7wU/AAAAAAAAAAI/AAAAAAAAAAA/AMZuucm-zwF2Nrbv6ke4xLMu-opErqbF7w/s96-c/photo.jpg","email":"sylvain.joly00@gmail.com","email_verified":true,"locale":"fr","iat":1609509965}},[X-Powered-By:"Express", Access-Control-Allow-Origin:"http://localhost:3000", Vary:"Origin", Access-Control-Allow-Credentials:"true", Content-Type:"application/json; charset=utf-8", Content-Length:"328", ETag:"W/"148-OQ1KfO2+eBvWJ1DV7Z7rcCTmqLs"", Date:"Sun, 07 Feb 2021 19:33:53 GMT", Connection:"keep-alive"]>
            */
                
            req.setAttribute("filterPpcToken", jwtToken); 
            req.setAttribute("userDetails", userDetailsDTO);

            chain.doFilter(request, response);
        }

        
    }
}
