/**
 * FileName: SwaggerConfig
 * Author:   hua
 * Date:     2019/10/20 22:34
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package my.hua.swagger.swaggerdemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * Swagger2配置
 *
 * @author hua
 * @create 2019/10/20
 * @since 1.0.0
 */
@SpringBootConfiguration
@EnableSwagger2 //开启Swagger2
public class SwaggerConfig {

    //配置Swagger信息
    private ApiInfo apiInfo() {
        Contact contact = new Contact(
                "HUA",
                "http://localhost",
                "hua@test.com");
        return new ApiInfo(
                "Swagger-Demo Api 文档",
                "Api Documentation 描述",
                "V1.0",//版本
                "urn:tos",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<VendorExtension>());
    }

    //配置Swagger的Docket的Bean实例
    @Bean
    public Docket userDocket(Environment environment) {

        //获取项目运行环境，在非生产环境中启动用Swagger
        Profiles profiles = Profiles.of("dev", "test");
        boolean flag =  environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(flag) //配置是否启用Swagger
                .groupName("用户管理API")
                .select()
                //RequestHandlerSelectors.any()  扫描所有
                //RequestHandlerSelectors.none() 不扫描
                //RequestHandlerSelectors.basePackage() 基于包扫描
                //RequestHandlerSelectors.withClassAnnotation(RestController.class) 扫描类上的注解
                //RequestHandlerSelectors.withMethodAnnotation(GetMapping.class) 扫描方法上的注解
                .apis(RequestHandlerSelectors.basePackage("my.hua.swagger"))
                //paths 根据请求路径过滤
                //PathSelectors.any()
                //PathSelectors.none()
                //PathSelectors.ant("/user/**") 匹配url中有 /user 的请求
                //PathSelectors.regex() 正则匹配路径
                .paths(PathSelectors.ant("/user/**"))
                .build();
    }

    @Bean
    public Docket goodsDocket(Environment environment) {
        //获取项目运行环境，在非生产环境中启动用Swagger
        Profiles profiles = Profiles.of("dev", "test");
        boolean flag =  environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(flag) //配置是否启用Swagger
                .groupName("商品管理API")//配置分组名称
                .select()
                .apis(RequestHandlerSelectors.basePackage("my.hua.swagger"))
                .paths(PathSelectors.ant("/goods/**"))
                .build();
    }

    @Bean
    public Docket aDocket(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("A");
    }

    @Bean
    public Docket bDocket(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("B");
    }

    @Bean
    public Docket cDocket(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("C");
    }





}
