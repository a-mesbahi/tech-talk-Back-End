package com.techtalk.usersservice;

import com.techtalk.usersservice.persistence.Admin;
import com.techtalk.usersservice.persistence.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.util.Objects;

@SpringBootApplication
public class UsersServiceApplication {

    public static void main(String[] args) {
        /*
            ClassLoader classLoader = UsersServiceApplication.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("serviceAccountKey.json"))).getParentFile();
        */
        SpringApplication.run(UsersServiceApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    @Bean
    CommandLineRunner runner(AdminRepository adminRepository){
        return args->{
        /*
            Admin admin = new Admin();
            admin.setEmail("admin@techtalk.com");
            admin.setPassword(this.passwordEncoder().encode("admin123"));
            admin.setName("Amine Mesbahi");
            adminRepository.save(admin);
            adminRepository.findAll().forEach(el-> System.out.println(el));
        */
        };
    }

}
