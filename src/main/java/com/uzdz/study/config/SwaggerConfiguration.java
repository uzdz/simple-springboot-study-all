package com.uzdz.study.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger增强插件
 * @author ：uzdz
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {


    /**
     * 创建连接的包信息
     * <p>
     * 配置统一返回的controller路径RequestHandlerSelectors.basePackage
     *
     * @return 返回创建状况
     */
    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //分组名称
                .groupName("2.X版本")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.qiyee.job.module.user.api"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }


    /**
     * 设置文档信息主页的内容说明
     *
     * @return 文档信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Project textBook API ")
                .description("服务接口")
                .termsOfServiceUrl("http://localhost:8001/")
                .contact(new Contact("Mr Liu", "http://localhost:8999/", "liuyang@synway.cn"))
                .license("what")
                .version("1.0")
                .build();
    }
}