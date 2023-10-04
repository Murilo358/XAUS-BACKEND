package com.XAUS.SecurityConfig;


import com.XAUS.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component

//Once per request (filtra apenas uma vez por request)

public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Override //Método obrigatorio a ser implementado
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);

        if(token != null){
            //Decripta o token e verifica se o subject (email) existe no banco através do user Repository
            var email  = tokenService.validateToken(token);
            UserDetails user = userRepository.findByEmail(email);

            //Cria uma autenticação (loga) colocando credencias  do usuário com suas roles e etc
            //(Lembrando que esse é um filterBefore) onde será executado antes de fazer todas as filtragens e verificações de cada endPoint

            if(user != null){
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                //Seta no contextHolder (segura essa informação)
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }

        //Continua a filtragem no securityConfig caso não seja encontrado nenhum token
        filterChain.doFilter(request, response);
    }
//Irá recuperar o token do request auth (parte que envia token de autenticação)
    public String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;

        //Remove o bearer para nada pois na requisições ele vem o Bearer (usado para padronização de tokens) + token
        return authHeader.replace("Bearer ", "");
    }


}
