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
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Bean
	CsrfFilter csrfFilter() {
	    CsrfFilter csrfFilter = new CsrfFilter(new CookieCsrfTokenRepository());
	    return csrfFilter;
	}

	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        String[] allRoles = { "USER", "ADMIN" };

        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()
        .addFilterAfter(new CsrfFilter(CookieCsrfTokenRepository.withHttpOnlyFalse()), CsrfFilter.class)
        .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST).permitAll()
                .requestMatchers("/photos/edit", "/photos/edit/**", "/photo/create", "/photo/create/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/photos/**").hasAuthority("ADMIN")
                .requestMatchers("/categories", "/categories/**").hasAuthority("ADMIN")
                .requestMatchers("/photos", "/photos/**").hasAuthority("ADMIN")
                .anyRequest().permitAll()
                .and().formLogin().and().logout().and().exceptionHandling();
        
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
