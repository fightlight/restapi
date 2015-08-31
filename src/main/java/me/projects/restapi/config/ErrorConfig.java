package me.projects.restapi.config;

import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;

import java.util.Map;

@Configuration
public class ErrorConfig {
    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {

            @Override
            public Map<String, Object> getErrorAttributes(
                    RequestAttributes requestAttributes,
                    boolean includeStackTrace) {
                Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
                Throwable error = getError(requestAttributes);
                if (error instanceof Exception) {
                    errorAttributes.remove("exception");
                }
                return errorAttributes;
            }
        };
    }
}
