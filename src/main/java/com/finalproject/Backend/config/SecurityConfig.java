package com.finalproject.Backend.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import com.finalproject.Backend.model.Tag;
import com.finalproject.Backend.model.Category;
import com.finalproject.Backend.repository.TagRepository;
import com.finalproject.Backend.repository.CategoryRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

 
    private JwtRequestFilter jwtRequestFilter;
    private CorsFilter corsFilter;

    public SecurityConfig(  JwtRequestFilter jwtRequestFilter,
                           CorsFilter corsFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.corsFilter = corsFilter; 
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> {
                })
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/health").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/countries/all").permitAll()
                        .requestMatchers("/api/images/**").permitAll() // Permitir todos los métodos HTTP para imágenes
                        .requestMatchers(HttpMethod.GET, "/api/bookmarks").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/bookmarks/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/bookmarks/search").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/experiences/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/categories/all").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/tags/all").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
        
    }


    @Bean
    public CommandLineRunner initData(CategoryRepository categoryRepository, TagRepository tagRepository) {
        return args -> {
            System.out.println("========================================");
            System.out.println("INICIALIZANDO DATOS EN LA BASE DE DATOS");
            System.out.println("========================================");
            
            // Inicializar Categorías
            System.out.println("\n📁 CATEGORÍAS:");
            saveOrUpdateCategory(categoryRepository, 1L, "Conflictos");
            saveOrUpdateCategory(categoryRepository, 2L, "Propuestas");
            saveOrUpdateCategory(categoryRepository, 3L, "Iniciativas");
            System.out.println("✓ Total categorías: " + categoryRepository.count());
            
            // Inicializar Tags
            System.out.println("\n🏷️  TAGS:");
            saveOrUpdateTag(tagRepository, 1L, "Medio Ambiente");
            saveOrUpdateTag(tagRepository, 2L, "Feminismos");
            saveOrUpdateTag(tagRepository, 3L, "Servicios Públicos");
            saveOrUpdateTag(tagRepository, 4L, "Vivienda");
            saveOrUpdateTag(tagRepository, 5L, "Urbanismo");
            saveOrUpdateTag(tagRepository, 6L, "Movilidad");
            saveOrUpdateTag(tagRepository, 7L, "Cultura");
            saveOrUpdateTag(tagRepository, 8L, "Economía y empleo");
            saveOrUpdateTag(tagRepository, 9L, "Deporte");
            saveOrUpdateTag(tagRepository, 10L, "Memoria democrática");
            System.out.println("✓ Total tags: " + tagRepository.count());
            
            System.out.println("\n========================================");
            System.out.println("✅ DATOS INICIALIZADOS CORRECTAMENTE");
            System.out.println("========================================\n");
        };
    }

    private void saveOrUpdateCategory(CategoryRepository repository, Long id, String name) {
        Category category = repository.findById(id).orElse(new Category());
        category.setId(id);
        category.setName(name);
        repository.save(category);
        System.out.println("  ✓ ID " + id + ": " + name);
    }

    private void saveOrUpdateTag(TagRepository repository, Long id, String name) {
        Tag tag = repository.findById(id).orElse(new Tag());
        tag.setId(id);
        tag.setName(name);
        repository.save(tag);
        System.out.println("  ✓ ID " + id + ": " + name);
    }

}
