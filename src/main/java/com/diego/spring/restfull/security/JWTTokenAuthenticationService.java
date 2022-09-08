package com.diego.spring.restfull.security;

import com.diego.spring.restfull.ApplicationContextLoad;
import com.diego.spring.restfull.model.Usuario;
import com.diego.spring.restfull.repository.UsuarioRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Service
@Component
public class JWTTokenAuthenticationService {

    private static final long EXPIRATION_TIME = 172800000; // tempo de validade do token em ms (2 dias)
    private static final String SECRET = "SenhaExtremamenteSecreta"; // auxiliar
    private static final String TOKEN_PREFIX = "Bearer"; // prefixo padrão token
    private static final String HEADER_STRING = "Authorization";

    // gera o token de autenticação
    public void addAuthentication(HttpServletResponse response, String username) throws IOException {
        String JWT = Jwts.builder() // chama o gerador de token
                .setSubject(username) // adiciona o usuário recebido no método
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // tempo de expiração
                .signWith(SignatureAlgorithm.HS512, SECRET).compact(); // gera assinatura e faz a compactação

        String token = TOKEN_PREFIX + " " + JWT; // Bearer ...

        response.addHeader(HEADER_STRING, token); // Authorization: Bearer ...

        ApplicationContextLoad.getApplicationContext().getBean(UsuarioRepository.class).atualizaTokenUser(JWT, username);

        liberacaoCors(response); // liberando consulta externa à API

        response.getWriter().write("{\"Authorization\": \"" + token + "\"}"); // resposta no corpo http
    }

    // retorna o usuário validado com token ou nulo
    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {

        String token = request.getHeader(HEADER_STRING);

        try {
            if (token != null) {

                String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim();

                // descompacta o token e retorna o usuário pai
                String user = Jwts
                        .parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(tokenLimpo)
                        .getBody().getSubject();

                if (user != null) {
                    Usuario usuario = ApplicationContextLoad
                            .getApplicationContext()
                            .getBean(UsuarioRepository.class)
                            .findUserByLogin(user);

                    if (usuario != null) {

                        if (usuario.getToken().equalsIgnoreCase(tokenLimpo)) {

                            return new UsernamePasswordAuthenticationToken(
                                    usuario.getLogin(),
                                    usuario.getSenha(),
                                    usuario.getAuthorities());
                        }
                    }
                }
            }
        } catch (ExpiredJwtException e) {
            try {
                response.getOutputStream().println("Token expirado: faça login ou informe um novo token");
            } catch (IOException ex) { }
        }
        liberacaoCors(response);
        return null; // Não autorizado
    }

    private void liberacaoCors(HttpServletResponse response) {
        if (response.getHeader("Access-Control-Allow-Origin") == null) {
            response.addHeader("Access-Control-Allow-Origin", "*");
        }
        if (response.getHeader("Access-Control-Allow-Headers") == null) {
            response.addHeader("Access-Control-Allow-Headers", "*");
        }
        if (response.getHeader("Access-Control-Request-Headers") == null) {
            response.addHeader("Access-Control-Request-Headers", "*");
        }
        if (response.getHeader("Access-Control-Allow-Methods") == null) {
            response.addHeader("Access-Control-Allow-Methods", "*");
        }
    }
}