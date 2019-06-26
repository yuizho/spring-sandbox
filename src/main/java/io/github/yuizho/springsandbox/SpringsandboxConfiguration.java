package io.github.yuizho.springsandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class SpringsandboxConfiguration {
    @Bean
    @Scope("prototype")
    public Logger logger(InjectionPoint injectionPoint) {
        Class<?> injectedClazz = injectionPoint.getMember().getDeclaringClass();
        return LoggerFactory.getLogger(injectedClazz);
    }
}
