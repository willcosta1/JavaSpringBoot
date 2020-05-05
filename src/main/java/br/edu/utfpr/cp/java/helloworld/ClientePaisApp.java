package br.edu.utfpr.cp.java.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import br.edu.utfpr.cp.java.helloworld.usuario.UsuarioPrincipal;

@SpringBootApplication
@EnableWebSecurity
public class ClientePaisApp extends WebSecurityConfigurerAdapter{

	@Bean
	public BCryptPasswordEncoder encoder(){
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/admin").hasAuthority("admin") //hasRole memoria, hasAuthority banco
		.antMatchers("/user").hasAuthority("user")
		.antMatchers("/private").fullyAuthenticated()
		.antMatchers("/public").permitAll()
		.antMatchers("/login*").permitAll()
		.antMatchers("/logout").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/login.html")
		.defaultSuccessUrl("/pais", false)
		.and()
		.logout();
	}

	public static void main(String[] args) {
		SpringApplication.run(ClientePaisApp.class, args);
	}
	//metodo para gerar senhas ao startar aplicação para trabalharmos com o banco h2
	//que é em memoria, sendo assim, criamos o data.sql em resources (spring entende)
	//@EventListener(ApplicationReadyEvent.class)
	//public void init() {
	//	System.out.println(new BCryptPasswordEncoder().encode("doe"));
	//	System.out.println(new BCryptPasswordEncoder().encode("doedoe"));
	//}
	@EventListener (InteractiveAuthenticationSuccessEvent.class)
	public void usuarioNaMemoria(InteractiveAuthenticationSuccessEvent event){
		var usuario = (UsuarioPrincipal) event.getAuthentication().getPrincipal();
        var request = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        request.getRequest().getSession().setAttribute("usuarioAtual", usuario);
	}
}