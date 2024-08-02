Let's create a simple Spring Boot application for the Sporty Shoes e-commerce portal. I will cover the basic functionalities such as user registration and login, product management by the admin, and product listing. We'll use MySQL for the database and Thymeleaf for the front-end templates.

### Step 1: Set Up Spring Boot Project

1. **Create a new Spring Boot project** using Spring Initializr (https://start.spring.io/):
   - Project: Maven
   - Language: Java
   - Spring Boot: 2.7.x (latest stable)
   - Dependencies:
     - Spring Web
     - Spring Data JPA
     - Thymeleaf
     - Spring Security
     - MySQL Driver

2. **Download and unzip the project**, then open it in your IDE (e.g., Eclipse, IntelliJ).

### Step 2: Configure MySQL Database

In `src/main/resources/application.properties`, configure the database connection:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sportyshoes
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
```

### Step 3: Define the Entity Classes

#### `Product.java`
```java
package com.example.sportyshoes.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private double price;

    // Getters and Setters
}
```

#### `User.java`
```java
package com.example.sportyshoes.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String role; // ADMIN or USER

    // Getters and Setters
}
```

### Step 4: Define Repositories

#### `ProductRepository.java`
```java
package com.example.sportyshoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.sportyshoes.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
```

#### `UserRepository.java`
```java
package com.example.sportyshoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.sportyshoes.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
```

### Step 5: Implement Services

#### `ProductService.java`
```java
package com.example.sportyshoes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sportyshoes.model.Product;
import com.example.sportyshoes.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
```

#### `UserService.java`
```java
package com.example.sportyshoes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.sportyshoes.model.User;
import com.example.sportyshoes.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
```

### Step 6: Configure Security

#### `WebSecurityConfig.java`
```java
package com.example.sportyshoes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/**").permitAll()
            .and()
            .formLogin()
            .loginPage("/login")
            .permitAll()
            .and()
            .logout()
            .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
```

### Step 7: Create Controllers

#### `AdminController.java`
```java
package com.example.sportyshoes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.sportyshoes.model.Product;
import com.example.sportyshoes.service.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/products";
    }

    @GetMapping("/products/new")
    public String newProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin/new_product";
    }

    @PostMapping("/products")
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.saveProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "admin/edit_product";
    }

    @PostMapping("/products/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute("product") Product product) {
        productService.saveProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }
}
```

#### `UserController.java`
```java
package com.example.sportyshoes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.sportyshoes.model.User;
import com.example.sportyshoes.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        user.setRole("USER");
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/admin/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "admin/users";
    }
}
```

### Step 8: Create Thymeleaf Templates

#### `src/main/resources/templates/register.html`
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Register</title>
</head>
<body>
    <h2>Register</h2>
    <form th:action="@{/register}" th:object="${user}" method="post">
        <div>
            <label>Username:</label>
            <input type="text" th:field="*{username}" />
        </div>
        <div>
            <label>Password:</label>
            <input type="password" th:field="*{password}" />
        </div>
        <div>
            <button type="submit">Register</button>
        </div>
    </form>
</body>
</html>
```

#### `src/main/resources/templates/login.html`
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Login</title>
</head>
<body>
    <h2>Login</h2>
    <form th:action="@{/login}" method="post">
        <div>
            <label>Username:</label>
            <input type="text"

 name="username" />
        </div>
        <div>
            <label>Password:</label>
            <input type="password" name="password" />
        </div>
        <div>
            <button type="submit">Login</button>
        </div>
    </form>
</body>
</html>
```

#### `src/main/resources/templates/admin/products.html`
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Products</title>
</head>
<body>
    <h2>Product List</h2>
    <a th:href="@{/admin/products/new}">Add New Product</a>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Category</th>
                <th>Price</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <tr th:each="product : ${products}">
                <td th:text="${product.id}"></td>
                <td th:text="${product.name}"></td>
                <td th:text="${product.category}"></td>
                <td th:text="${product.price}"></td>
                <td>
                    <a th:href="@{/admin/products/edit/{id}(id=${product.id})}">Edit</a>
                    <a th:href="@{/admin/products/delete/{id}(id=${product.id})}">Delete</a>
                </td>
            </tr>
        </tbody>
    </table>
</body>
</html>
```

### Step 9: Run the Application

1. **Create the MySQL Database**:
   ```sql
   CREATE DATABASE sportyshoes;
   ```

2. **Run the Spring Boot Application** from your IDE or using the command line:
   ```bash
   mvn spring-boot:run
   ```

3. **Access the Application**:
   - Register a new user: `http://localhost:8080/register`
   - Login: `http://localhost:8080/login`
   - Admin product management: `http://localhost:8080/admin/products`
