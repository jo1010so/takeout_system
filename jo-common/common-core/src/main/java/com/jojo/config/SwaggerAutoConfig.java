package com.jojo.config;

import com.jojo.condition.IsDevEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.HashSet;

/**
 * Swagger 自动装配
 */
@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerAutoConfig {

    @Autowired
    private SwaggerProperties swaggerProperties;

    @Autowired
    private Environment environment;

    /*
    只有在开发环境才要去生成文档
     */
    @Bean
    @Conditional(IsDevEnvironment.class)
    public Docket docket(){
        /*boolean flag = true;
        String[] activeProfiles = environment.getActiveProfiles();
        for (String activeProfile:activeProfiles) {
            if (("pro").equals(activeProfile)){
                flag = false;
                break;
            }
        }*/
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(getApiInfo())
                /*.enable(flag)*/
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage())).build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                swaggerProperties.getTitle(),
                swaggerProperties.getDescription(),
                swaggerProperties.getVersion(),
                swaggerProperties.getTermsOfServiceUrl(),
                new Contact(
                        swaggerProperties.getName(),
                        swaggerProperties.getUrl(),
                        swaggerProperties.getEmail()
                ),
                swaggerProperties.getLicence(),
                swaggerProperties.getLicenceUrl(),
                new HashSet<>()
        );
    }
}
