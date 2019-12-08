package io.github.yuizho.springsandbox;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.aws.core.io.s3.PathMatchingSimpleStorageResourcePatternResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.support.ResourcePatternResolver;

@Configuration
public class SpringsandboxConfiguration {

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    @Scope("prototype")
    public Logger logger(InjectionPoint injectionPoint) {
        Class<?> injectedClazz = injectionPoint.getMember().getDeclaringClass();
        return LoggerFactory.getLogger(injectedClazz);
    }

    @Primary
    @Bean
    public AmazonS3 amazonS3Client() {
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                // これ効いてるのか？
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                "http://localhost:9090",
                                region
                        )
                )
                .build();
    }

    @Bean
    public ResourcePatternResolver resourcePatternResolver(
            ApplicationContext applicationContext) {
        return new PathMatchingSimpleStorageResourcePatternResolver(
                amazonS3Client(),
                applicationContext
        );
    }
}
