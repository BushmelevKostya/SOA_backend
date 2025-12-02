# SOA Backend Project

---

## Описание API

### Аутентификация

#### Регистрация нового пользователя

```
POST /auth/register
Content-Type: application/json

{
  "login": "username",
  "password": "password123"
}
```

**Ответ:**

```
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "username",
  "message": "Login successful"
}
```

#### Вход пользователя

```
POST /auth/login
Content-Type: application/json

{
  "login": "username",
  "password": "password123"
}
```

**Ответ:**

```
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "username",
  "message": "Login successful"
}
```

---

### Данные городов (требует аутентификации)

#### Получение списка городов

```
GET /api/data
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Ответ:**

```
{
  "cities": [
    {
      "id": 1,
      "name": "Москва",
      "population": 13000000
    },
    {
      "id": 2,
      "name": "Санкт-Петербург",
      "population": 5000000
    }
  ]
}
```

---

## Реализованные меры защиты

### Защита от SQL Injection (SQLi)

**Реализация:**

* Использование Spring Data JPA и Hibernate ORM
* JPA репозитории автоматически экранируют пользовательский ввод

**Пример кода:**

```java
public interface UserRepository extends JpaRepository<User, Long> {
    // Hibernate автоматически создает параметризованный запрос
    Optional<User> findByUsername(String username);
}
```

---

### Защита от XSS (Cross-Site Scripting)

**Реализация:**

* Использование `Spring HtmlUtils.htmlEscape()`

**Пример кода:**

```java
public record CityResponseDTO(List<City> cities) {
    public List<City> getSafeCities() {
        return cities.stream()
                .map(city -> {
                    City safeCity = new City();
                    safeCity.setId(city.getId());
                    safeCity.setName(HtmlUtils.htmlEscape(city.getName()));
                    safeCity.setPopulation(city.getPopulation());
                    return safeCity;
                })
                .collect(Collectors.toList());
    }
}
```

---

### Аутентификация и авторизация

#### JWT-аутентификация

**Реализация:**

* Генерация JWT токенов при успешном входе/регистрации
* Время жизни токена: **24 часа**
* Алгоритм подписи: **HS256**

**Пример кода:**

```java
@Service
public class JwtAuthService {
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
```

---

#### Защита эндпоинтов

**Реализация:**

* Все эндпоинты `/api/**` требуют аутентификации
* Эндпоинты `/auth/**` публичные

**Конфигурация безопасности:**

```java
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().authenticated()
            )
            .addFilterBefore(authenticationJwtTokenFilter(), 
                           UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}
```

---

### Хранение паролей

**Реализация:**

* Хэширование паролей с использованием **BCrypt**
* Соль генерируется автоматически

**Пример кода:**

```java
@Service
public class LoginService {
    public LoginResponseDTO createUser(LoginRequestDTO loginRequestDTO) {
        String encodedPassword = passwordEncoder.encode(loginRequestDTO.password());
        User user = new User(loginRequestDTO.login(), encodedPassword);
        userRepository.save(user);
        
        String token = jwtAuthService.generateToken(user.getUsername());
        return LoginResponseDTO.success(token, user.getUsername());
    }
}
```

## SCA - Software Composition Analysis

### Найденные уязвимости

| CVE | Северность | Пакет | Текущая версия | Исправленная версия | Описание |
|-----|------------|-------|----------------|-------------------|----------|
| [CVE-2025-11226](https://avd.aquasec.com/nvd/cve-2025-11226) | MEDIUM | ch.qos.logback:logback-core | 1.5.18 | 1.5.19, 1.3.16 | Условное выполнение произвольного кода |
| [CVE-2025-61795](https://avd.aquasec.com/nvd/cve-2025-61795) | LOW | org.apache.tomcat.embed:tomcat-embed-core | 10.1.46 | 11.0.12, 10.1.47, 9.0.110 | Отказ в обслуживании |

### Исправление
1. Обновить `logback-core` до версии `1.5.19`
2. Обновить `tomcat-embed-core` до версии `10.1.47`
