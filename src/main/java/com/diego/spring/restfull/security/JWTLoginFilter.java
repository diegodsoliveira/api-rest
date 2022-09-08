package com.diego.spring.restfull.security;

import com.diego.spring.restfull.model.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// gerenciador de token
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
    protected JWTLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {

        super(new AntPathRequestMatcher(defaultFilterProcessesUrl)); // obriga a autenticar a url

        setAuthenticationManager(authenticationManager); // seta gerenciador de autenticação
    }

    // retorna o usuário ao processar a autenticação
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        Usuario user = new ObjectMapper()
                .readValue(request.getInputStream(), Usuario.class);

        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getSenha()));
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        new JWTTokenAuthenticationService().addAuthentication(response, authResult.getName());
    }
}
