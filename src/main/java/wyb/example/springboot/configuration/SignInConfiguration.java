package wyb.example.springboot.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wyb.example.springboot.interceptor.SignInHandlerInterceptor;

/**
 * @author William Wang
 */
@Configuration
public class SignInConfiguration implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //super.addInterceptors(registry);
        registry.addInterceptor(new SignInHandlerInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/","/signIn","/toSignIn","/signUp","/toSignUp","/userName/**");
    }
}
