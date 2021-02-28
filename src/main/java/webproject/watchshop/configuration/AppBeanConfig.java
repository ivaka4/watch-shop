package webproject.watchshop.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;


import java.util.Random;
import java.util.concurrent.Executor;

//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import java.util.Properties;

@Configuration
@EnableAsync
@EnableScheduling
public class AppBeanConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder(){
        return new Pbkdf2PasswordEncoder();
    }
    
    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    //For spg
    @Bean
    public Random random() {
        return new Random();
    }

//    @Bean
//    public ListFactoryForPassword listFactoryForPassword() {
//        return new ListFactoryForPassword();
//    }

//    @Configuration
//    public class SpringFoxConfig {
//        @Bean
//        public Docket api() {
//            return new Docket(DocumentationType.SWAGGER_2)
//                    .select()
//                    .apis(RequestHandlerSelectors.any())
//                    .paths(PathSelectors.any())
//                    .build();
//        }
    }

