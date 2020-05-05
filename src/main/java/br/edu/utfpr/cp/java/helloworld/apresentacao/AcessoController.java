package br.edu.utfpr.cp.java.helloworld.apresentacao;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import br.edu.utfpr.cp.java.helloworld.usuario.UsuarioPrincipal;

@Controller
public class AcessoController {

    @GetMapping("/admin")
    public String adminPage(){
        return "/adminPage.html";
    }

    @GetMapping("/private")
    public String privatePage(HttpServletResponse response){
        var usuarioPrincipal = (UsuarioPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        response.addCookie(new Cookie("usuarioAtual", usuarioPrincipal.getUsername()));

        return "/privatePage.html";
    }

    @GetMapping("/public")
    public String publicPage(){
        return "/publicPage.html";
    }
    
    @GetMapping("/user")
    public String userPage(Model memoria ,@CookieValue String usuarioAtual){
        
        memoria.addAttribute("usuario", usuarioAtual);

        return "/userPage";
    }
}