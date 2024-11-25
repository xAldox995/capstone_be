package aldovalzani.capstone_be.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private JWTCheckerFilter jwtCheckerFilter;

    public SecurityConfig(JWTCheckerFilter jwtCheckerFilter) {
        this.jwtCheckerFilter = jwtCheckerFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.disable()) // Disabilita form login
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable()) // Disabilita CSRF
                .cors(Customizer.withDefaults()) // Abilita CORS con impostazioni predefinite
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll() // Endpoints pubblici
                        .anyRequest().authenticated() // Tutti gli altri richiedono autenticazione
                )
                .addFilterBefore(jwtCheckerFilter, UsernamePasswordAuthenticationFilter.class); // Aggiungi il filtro JWT
        return httpSecurity.build();
    }
    @Bean
    PasswordEncoder getCrypt(){
        return new BCryptPasswordEncoder(12);
    }
}
