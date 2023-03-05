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

		http.csrf().disable().authorizeHttpRequests()
				// AUTH PER CREARE E MODIFICARE FOTO: ADMIN
				.requestMatchers("/photos/edit", "/photos/edit/**", "/photo/create", "/photo/create/**")
				.hasAuthority("ADMIN")

				// POST SU FOTO QUINDI IL DELETE: ADMIN
				.requestMatchers(HttpMethod.POST, "/photos/**").hasAuthority("ADMIN")

				// CONTROLLI SULLE CATEGORIE: ADMIN
				.requestMatchers("/categories", "/categories/**").hasAuthority("ADMIN")

				// ELENCO E DETTAGLIO FOTO: USER E ADMIN
				.requestMatchers("/photos", "/photos/**").hasAnyAuthority(allRoles)

				// ACCESSO ALLA HOME: USER E ADMIN
				.requestMatchers("/**").permitAll()

				.and().formLogin().and().logout().and().exceptionHandling()

				// VIEW HTML PERSONALIZZATA PER L'ACCESSO NEGATO
				.accessDeniedPage("/access-denied.html");

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