# Spring Boot集成Swagger2

## 集成Swagger2

- 引入依赖

```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.9.2</version>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
</dependency>
```

- 集成Swagger：增加Swagger配置并启用Swagger

```java
@SpringBootConfiguration
@EnableSwagger2 //开启Swagger2
public class SwaggerConfig {

}
```

此时Swagger已集成，启动项目可以通过`http://localhost:8080/swagger-ui.html`访问到Swagger页面了。

## 配置Swagger2

Swagger的bean实例：Docket

### 基本配置：

```java
//配置Swagger的Docket的Bean实例
@Bean
public Docket docket() {
    return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo());
}

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
```



### 配置扫描接口

`Docket.select()`

```java
@Bean
public Docket docket() {
    return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
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
            .paths(PathSelectors.any())
            .build();
}
```

### 配置是否启用Swagger

```java
@Bean
public Docket docket() {
    return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .enable(true) //配置是否启用Swagger
            .select()
            .apis(RequestHandlerSelectors.basePackage("my.hua.swagger"))
            .paths(PathSelectors.any())
            .build();
}
```

### 配置Swagger分组及配置多个分组

- 通过`groupName()`方法配置分组名称
```java
@Bean
public Docket goodsDocket(Environment environment) {
    return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .enable(true) //配置是否启用Swagger
            .groupName("商品管理API")//配置分组名称
            .select()
            .apis(RequestHandlerSelectors.basePackage("my.hua.swagger"))
            .paths(PathSelectors.ant("/goods/**"))
            .build();
}
```

- 配置多个Docket实例来配置多个分组

```java
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
```

### 其他

- 只要Controller中的方法返回值中存在实体类，就会被扫描到Swagger中，在Models中显示

## Swagger常用注解

整理了一些Swagger常用的注解,详细信息请看官方文档：

- [http://docs.swagger.io/swagger-core/v1.5.X/apidocs/](http://docs.swagger.io/swagger-core/v1.5.X/apidocs/)
- [https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X](https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X)

### `@Api`

用在类上，说明该类的作用，可标记一个Controller类为Swagger资源。

`@Api(value = "/user",tags = {"用户操作类"})`

|属性|备注|
|---|---|
|value  |若未使用tags，该值会被用于设置标签，若使用tags，该值无效|
|tags   |标签，设置多个，用于对资源进行逻辑分组|
|description    |对api资源的描述(过时)|
|basePath   |基本路径可以不配置(过时)|
|position   |如果配置了多个Api，可用该注解改变显示顺序(过时)|
|produces   |For example, "application/json, application/xml"|
|consumes   |For example, "application/json, application/xml"|
|protocols  |Possible values: http, https, ws, wss|
|authorizations |高级特性认证时配置|
|hidden |配置为true,将在文档中隐藏|

### `@ApiOperation`

用在Controller类的方法上，用来描述具体的API

```java
@ApiOperation(value = "通过id查询用户",
    notes = "通过id查询用户信息，参数为int类型",
    response = User.class,
    tags = {"查询"})
```

|属性|备注|
|---|---|
|value  |操作的简要描述|
|notes  |操作的详细描述|
|tags   |标记可用于根据资源或任何其他限定符对操作进行逻辑分组|
|response   |操作的响应类型|
|responseContainer  |声明一个容器，有效值是“List”、“Set”或“Map”。其他任何值都将被忽略|
|responseReference  |指定对响应类型的引用，覆盖任何指定的response()类|
|httpMethod |指定一种请求方式|
|position   |(过时)|
|nickname   |第三方工具使用operationId来惟一地标识该操作|
|produces   |对应于操作的“生成”字段，接受内容类型的逗号分隔值。例如，“application/json, application/xml”将建议此操作生成json和xml输出。|
|consumes   |接受内容类型的逗号分隔值。例如，“application/json, application/xml”将建议此API资源接受json和xml输入|
|protocols  |为该操作设置特定的协议(方案)，可用协议的逗号分隔值。可能的值:http、https、ws、wss。|
|authorizations |获取此操作的授权(安全需求)列表|
|hidden |从操作列表中隐藏操作|
|responseHeaders    |响应可能提供的响应头列表|
|code   |响应状态码，默认200|
|extensions |可选的扩展数组|


### `@ApiParam`

用在Controller类的方法上,用来描述方法参数。

```java
public User selectUserById(@ApiParam(name = "id",value = "用户id",required = true,example = "123") @PathVariable(value = "id", required = true) int id) {
    return new User(id, "Tom");
}
```

|属性|备注|
|---|---|
|name   |参数名称|
|value  |参数简要描述|
|defaultValue   |参数的默认值|
|allowableValues    |限制可接受参数|
|required   |指定是否需要参数，默认false|
|access |允许从API文档中过滤参数|
|allowMultiple  |指定参数是否可以通过多次出现来接受多个值|
|hidden |从参数列表中隐藏参数|
|example    |参数示例|
|examples   |参数示例。仅适用于body参数|
|type   |添加覆盖检测到的类型的能力|
|format |添加提供自定义格式的功能|
|allowEmptyValue    |添加将格式设置为empty的功能|
|readOnly   |增加被指定为只读的能力|
|collectionFormat   |添加使用' array '类型覆盖collectionFormat的功能|

### `@ApiImplicitParams` & `@ApiImplicitParam`

用在controller的方法上,对API操作中的单个参数进行描述。

```java
@ApiImplicitParams({
    @ApiImplicitParam(name = "name", value = "User's name", required = true, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "email", value = "User's email", required = false, dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "id", value = "User ID", required = true, dataType = "long", paramType = "query")
    })
public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
}
```

|属性|备注|
|---|---|
|name   |参数的名称|
|value  |参数的简要描述|
|defaultValue   |描述参数的默认值|
|allowableValues    |限制此参数的可接受值|
|required   |指定是否需要参数，默认false|
|access |允许从API文档中过滤参数|
|allowMultiple  |指定参数是否可以接受多个逗号分隔的值|
|dataType   |参数的数据类型，可以是类名或基元|
|dataTypeClass  |url的路径位置|
|paramType  |参数的参数类型，有效值是路径、查询、正文、标题或表单|
|example    |非主体类型参数的一个示例|
|examples   |参数示例。仅适用于body参数|
|type   |添加覆盖检测到的类型的能力|
|format |添加提供自定义格式的功能|
|format |添加提供自定义格式的功能|
|allowEmptyValue    |添加将格式设置为空的功能|
|collectionFormat   |添加使用' array '类型覆盖collectionFormat的功能|

### `@ApiResponses` & `@ApiResponse` 

描述服务器响应。

```
@ApiResponses(value = {
    @ApiResponse(code = 400, message = "Invalid ID supplied",
            responseHeaders = @ResponseHeader(name = "X-Rack-Cache", description = "Explains whether or not a cache was used", response = Boolean.class)),
    @ApiResponse(code = 404, message = "User not found") })
```

|属性|备注|
|---|---|
|code   |状态码，默认200|
|message    |返回响应信息|
|response   |可选的响应类来描述消息的有效负载|
|reference  |指定对响应类型的引用，覆盖任何指定的response()类|
|responseHeaders    |响应可能提供的响应头列表|
|responseContainer  |声明一个响应容器，有效值是“List”、“Set”或“Map”。其他任何值都将被忽略|

### `@ResponseHeader`

响应头设置。响应可能提供的响应头列表

|属性|备注|
|---|---|
|name   |响应头的名字|
|description    |响应头的长描述|
|response   |响应头的数据类型|
|responseContainer  |声明一个响应容器，有效值是“List”、“Set”或“Map”。其他任何值都将被忽略|



### `@ApiModel`

用在Controller中方法返回的对象上，描述返回对象

```java
@ApiModel(value = "用户",description = "用户实体")
public class User {
    private Integer id;
    private String userName;
}
```

|属性|备注|
|---|---|
|value  |为模型提供一个替代名称，默认使用类名|
|description    |提供一个更长的类描述|
|parent |为模型提供一个超类以允许描述继承|
|discriminator  |支持模型继承和多态性，这是用作鉴别器的字段的名称，基于这个字段，可以断言需要使用哪个子类型|
|subTypes   |继承自此模型的子类型的数组|
|reference  |指定对相应类型定义的引用，覆盖指定的任何其他元数据|

### `@ApiModelProperty`

用于描述Model的属性

```java
public class User {

    @ApiModelProperty(value = "用户id", dataType = "Integer", example = "123")
    private Integer id;

    @ApiModelProperty(name = "userName", value = "姓名", dataType = "String", notes = "用户姓名")
    private String userName
```

|属性|备注|
|---|---|
|value  |属性的简要描述|
|name   |允许重写属性的名称|
|access |允许从API文档中过滤参数|
|allowableValues    |限制此属性的可接受值|
|notes  |目前未使用|
|dataType   |参数的数据类型，这可以是类名或基元。该值将覆盖从类属性读取的数据类型|
|required   |指定是否需要参数，默认false|
|position   |允许显式地对模型中的属性进行排序|
|hidden |允许模型属性隐藏在Swagger模型定义中|
|example    |属性的示例值|
|readOnly   |允许模型属性被指定为只读|
|reference  |指定对相应类型定义的引用，覆盖指定的任何其他元数据|
|allowEmptyValue|允许传递空值|

### `@Authorization`
用在controller的方法上，声明要在资源或操作上使用的授权方案。

|属性|备注|
|---|---|
|value  |用于此资源/操作的授权方案的名称|
|scopes |如果授权模式是OAuth2，则使用的作用域|

### `@AuthorizationScope`

用在controller的方法上,用于定义为已定义授权方案的操作所使用的授权范围。

```java
@ApiOperation(value = "Add a new pet to the store", 
    authorizations = {
      @Authorization(
          value="petoauth", 
          scopes = { @AuthorizationScope(scope = "add:pet") }
            )
        }
    )
public Response addPet(...) {...}
```

|属性|备注|
|---|---|
|scope  |使用OAuth2授权方案的范围，作用域应该在Swagger对象的securityDefinition部分中预先声明|
|description |用于遗留支持|


