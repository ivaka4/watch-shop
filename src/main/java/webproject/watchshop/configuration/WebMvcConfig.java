package webproject.watchshop.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import webproject.watchshop.web.interceptor.PageTitleInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final PageTitleInterceptor pageTitleInterceptor;

    public WebMvcConfig(PageTitleInterceptor pageTitleInterceptor) {
        this.pageTitleInterceptor = pageTitleInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(pageTitleInterceptor);
    }
}
