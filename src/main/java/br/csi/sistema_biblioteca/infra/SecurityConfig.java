package br.csi.sistema_biblioteca.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AutenticacaoFilter autenticacaoFilter;
    public SecurityConfig(AutenticacaoFilter filtro) {
        this.autenticacaoFilter = filtro;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf-> csrf.disable())
                .sessionManagement(sm-> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->
                        auth.requestMatchers(HttpMethod.POST, "/login").permitAll()
                                //operações referentes aos usuários
                                .requestMatchers(HttpMethod.POST, "/usuario").permitAll()
                                .requestMatchers(HttpMethod.GET, "/usuario/listar").
                                    hasAuthority("administrador")
                                .requestMatchers(HttpMethod.GET, "/usuario/listarDados").
                                    hasAuthority("administrador")
                                .requestMatchers(HttpMethod.GET, "usuario/listar/uuid/{uuid}").
                                    hasAnyAuthority("administrador", "cliente")
                                .requestMatchers(HttpMethod.DELETE, "/usuario").hasAnyAuthority(
                                        "administrador", "cliente")
                                .requestMatchers(HttpMethod.PUT, "/usuario").hasAnyAuthority(
                                        "administrador", "cliente")

                                //operações referentes aos livros
                                .requestMatchers(HttpMethod.POST, "/livro").hasAuthority("administrador")
                                .requestMatchers(HttpMethod.DELETE, "/livro").hasAuthority("administrador")
                                .requestMatchers(HttpMethod.PUT, "/livro").hasAuthority("administrador")
                                .requestMatchers(HttpMethod.GET, "/livro").permitAll()

                                //operações referentes aos autores
                                .requestMatchers(HttpMethod.POST, "/autor").hasAuthority("administrador")
                                .requestMatchers(HttpMethod.DELETE, "/autor").hasAuthority("administrador")
                                .requestMatchers(HttpMethod.PUT, "/autor").hasAuthority("administrador")
                                .requestMatchers(HttpMethod.GET, "/autor/").hasAnyAuthority(
                                        "administrador", "cliente")

                                //operações referentes às reservas
                                .requestMatchers(HttpMethod.POST, "/reserva").hasAnyAuthority(
                                        "administrador", "cliente")
                                .requestMatchers(HttpMethod.GET, "/reserva/listar").
                                    hasAuthority("administrador")
                                .requestMatchers(HttpMethod.GET, "/reserva/uuid/{uuid}").
                                    hasAnyAuthority("administrador", "cliente")
                                .requestMatchers(HttpMethod.DELETE, "/reserva/listar").
                                    hasAnyAuthority("administrador", "cliente")
                                .requestMatchers(HttpMethod.PUT, "/reserva/listar").
                                    hasAnyAuthority("administrador", "cliente")

                                .anyRequest().authenticated())
                .addFilterBefore(this.autenticacaoFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
