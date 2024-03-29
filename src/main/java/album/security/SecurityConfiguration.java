package album.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	

	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        String[] allRoles = { "USER", "ADMIN" };

        http.csrf()
        .and()
        .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/apiPhotos/**").permitAll()
                .requestMatchers("/photos/edit", "/photos/edit/**", "/photo/create", "/photo/create/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/photos/**").hasAuthority("ADMIN")
                .requestMatchers("/categories", "/categories/**").hasAuthority("ADMIN")
                .requestMatchers("/photos", "/photos/**").hasAuthority("ADMIN")
                .anyRequest().permitAll()
                .and().formLogin().and().logout().logoutSuccessUrl("/").and().exceptionHandling();
        
        return http.build();
    }

	@Bean
	DatabaseUserDetailsService userDetailsService() {
		return new DatabaseUserDetailsService();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		System.out.println(passwordEncoder().encode("miao"));

		return authProvider;
	}
}
