# 基于Spring Boot的外卖项目

## 1.开发环境搭建

## 1.1数据库环境搭建

### 1.1.1创建数据库

![image-20220730093639686](C:\Users\WhiteCatSama\AppData\Roaming\Typora\typora-user-images\image-20220730093639686.png) 

### 1.1.2表的创建

在delicious库中创建以下几个表:

employee员工表、category菜品和套餐分类表、dish菜品表、setmeal套餐表、setmeal_dish套餐菜品关系表、dish_flavor菜品口味关系表、user用户表(C端)、address_book地址簿表、shopping_cart购物车表、orders订单表、order_detail订单明细表

![image-20220730094046409](C:\Users\WhiteCatSama\AppData\Roaming\Typora\typora-user-images\image-20220730094046409.png) 

**建表语句**

```sql
DROP TABLE IF EXISTS `address_book`;
CREATE TABLE `address_book` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `consignee` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '收货人',
  `sex` tinyint(4) NOT NULL COMMENT '性别 0 女 1 男',
  `phone` varchar(11) COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `province_code` varchar(12) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '省级区划编号',
  `province_name` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '省级名称',
  `city_code` varchar(12) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '市级区划编号',
  `city_name` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '市级名称',
  `district_code` varchar(12) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '区级区划编号',
  `district_name` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '区级名称',
  `detail` varchar(200) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '详细地址',
  `label` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '标签',
  `is_default` tinyint(1) NOT NULL DEFAULT '0' COMMENT '默认 0 否 1是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='地址管理';

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `type` int(11) DEFAULT NULL COMMENT '类型   1 菜品分类 2 套餐分类',
  `name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '分类名称',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '顺序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_category_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜品及套餐分类';

DROP TABLE IF EXISTS `dish`;
CREATE TABLE `dish` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '菜品名称',
  `category_id` bigint(20) NOT NULL COMMENT '菜品分类id',
  `price` decimal(10,2) DEFAULT NULL COMMENT '菜品价格',
  `code` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '商品码',
  `image` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '图片',
  `description` varchar(400) COLLATE utf8_bin DEFAULT NULL COMMENT '描述信息',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '0 停售 1 起售',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '顺序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_dish_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜品管理';

DROP TABLE IF EXISTS `dish_flavor`;
CREATE TABLE `dish_flavor` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dish_id` bigint(20) NOT NULL COMMENT '菜品',
  `name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '口味名称',
  `value` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '口味数据list',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜品口味关系表';


DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '姓名',
  `username` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `phone` varchar(11) COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `sex` varchar(2) COLLATE utf8_bin NOT NULL COMMENT '性别',
  `id_number` varchar(18) COLLATE utf8_bin NOT NULL COMMENT '身份证号',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态 0:禁用，1:正常',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='员工信息';

DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `number` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '订单号',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '订单状态 1待付款，2待派送，3已派送，4已完成，5已取消',
  `user_id` bigint(20) NOT NULL COMMENT '下单用户',
  `address_book_id` bigint(20) NOT NULL COMMENT '地址id',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `checkout_time` datetime NOT NULL COMMENT '结账时间',
  `pay_method` int(11) NOT NULL DEFAULT '1' COMMENT '支付方式 1微信,2支付宝',
  `amount` decimal(10,2) NOT NULL COMMENT '实收金额',
  `remark` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `phone` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `user_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `consignee` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='订单表';


DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '名字',
  `image` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '图片',
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `dish_id` bigint(20) DEFAULT NULL COMMENT '菜品id',
  `setmeal_id` bigint(20) DEFAULT NULL COMMENT '套餐id',
  `dish_flavor` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '口味',
  `number` int(11) NOT NULL DEFAULT '1' COMMENT '数量',
  `amount` decimal(10,2) NOT NULL COMMENT '金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='订单明细表';

DROP TABLE IF EXISTS `setmeal`;
CREATE TABLE `setmeal` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `category_id` bigint(20) NOT NULL COMMENT '菜品分类id',
  `name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '套餐名称',
  `price` decimal(10,2) NOT NULL COMMENT '套餐价格',
  `status` int(11) DEFAULT NULL COMMENT '状态 0:停用 1:启用',
  `code` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '编码',
  `description` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '描述信息',
  `image` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '图片',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_setmeal_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='套餐';

DROP TABLE IF EXISTS `setmeal_dish`;
CREATE TABLE `setmeal_dish` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `setmeal_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '套餐id ',
  `dish_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '菜品id',
  `name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '菜品名称 （冗余字段）',
  `price` decimal(10,2) DEFAULT NULL COMMENT '菜品原价（冗余字段）',
  `copies` int(11) NOT NULL COMMENT '份数',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='套餐菜品关系';

DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `image` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '图片',
  `user_id` bigint(20) NOT NULL COMMENT '主键',
  `dish_id` bigint(20) DEFAULT NULL COMMENT '菜品id',
  `setmeal_id` bigint(20) DEFAULT NULL COMMENT '套餐id',
  `dish_flavor` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '口味',
  `number` int(11) NOT NULL DEFAULT '1' COMMENT '数量',
  `amount` decimal(10,2) NOT NULL COMMENT '金额',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='购物车';

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
  `phone` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `sex` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '性别',
  `id_number` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '身份证号',
  `avatar` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  `status` int(11) DEFAULT '0' COMMENT '状态 0:禁用，1:正常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户信息';

```

## 1.2Maven项目搭建

### 1.2.1创建Spring Boot工程

![image-20220730095722425](C:\Users\WhiteCatSama\AppData\Roaming\Typora\typora-user-images\image-20220730095722425.png) 

![image-20220730095819054](C:\Users\WhiteCatSama\AppData\Roaming\Typora\typora-user-images\image-20220730095819054.png) 

### 1.2.2导入依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.2</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.whitecatsama</groupId>
    <artifactId>delicious_take_out</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>delicious_take_out</name>
    <description>Demo project for Spring Boot</description>
    <properties>
        <!--    JDK的版本  -->
        <java.version>1.8</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--    持久层事务管理 -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-tx</artifactId>
        </dependency>
        <!--    MySQL驱动    -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <!--    MyBatis Plus依赖    -->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.4.2</version>
        </dependency>
        <!--    实体类与JSON互转    -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.76</version>
        </dependency>
        <!--   德鲁伊连接池     -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.23</version>
        </dependency>
        <!--    常用工具类    -->
        <dependency>
            <groupId>commons-lang</groupId>
            <artifactId>commons-lang</artifactId>
            <version>2.6</version>
        </dependency>
        <!--     LomBok插件       -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <!--    测试    -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>

```

### 1.2.3yml/yaml配置

```yaml
#TomCat端口
server:
  port: 8080
#应用名
spring:
  application:
    name: delicious_take_out
#数据源
  datasource:
    druid:
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://localhost:3306/delicious?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true
      username: root
      password: 123456
mybatis-plus:
  configuration:
    #按驼峰名映射
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  global-config:
    db-config:
      id-type: ASSIGN_ID
```

### 1.2.4导入前端页面

将前端页面放入static或者template目录下。

![image-20220730102401930](C:\Users\WhiteCatSama\AppData\Roaming\Typora\typora-user-images\image-20220730102401930.png) 

如果放在其他目录下需要编写配置类，继承WebMvcConfigurationSupport类重写addResourceHandlers方法进行静态资源映射，将对应访问路径放行，具体操作如下：

```java
@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("静态资源映射...");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }
}
```

注：调试的时候还是出现的404错误，结果发现是addResourceLocations的参数中没加入classpath:

启动类的初始配置如下所示：

```java
@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
public class DeliciousTakeOutApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliciousTakeOutApplication.class, args);
        log.info("项目启动成功!");
    }
}
```

## 2.后台管理系统登录功能开发

### 2.1构建项目结构

![](C:\Users\WhiteCatSama\AppData\Roaming\Typora\typora-user-images\image-20220731154034197.png)  

common存放公共的东西，config存放配置类，controller存放控制器，domain存放实体类，mapper存放MybatisPlus的映射类，service存放业务类

### 2.2创建Employee实体类

```java
@Data
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String name;

    private String password;

    private String phone;

    private String sex;

    private String idNumber;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;

}
```

### 2.3创建服务返回结果类Result和返回值Code类

```java
@Data
public class Result{

    private Integer code;

    private String msg;

    private Object data;

    private Map map = new HashMap();

    public Result() {
    }

    public Result(Integer code, String msg, Object data, Map map) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.map = map;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, Object data,String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public Result add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
```

Code中编码

```java
/*
* 这里的编码与前端进行沟通对接
* */
public class Code {
    public static final Integer LOGIN_SUCCESS = 10001;  //登录成功
    public static final Integer LOGIN_FAILED = 10000;   //登录失败
    public static final Integer LOGOUT_FAILED = 10010;  //登出失败
    public static final Integer LOGOUT_SUCCESS = 10011; //登出成功
    public static final Integer SAVE_SUCCESS = 10021;   //新增成功
    public static final Integer SAVE_FAILED = 10020;    //新增失败
}

```

### 2.4创建EmployeeMapper

```java
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
    //继承BaseMapper就可以使用MybatisPlus提供的默认的CRUD操作
}
```

### 2.5创建EmployeeService及其实现类

```java
@Transactional
public interface EmployeeService extends IService<Employee> {
}
```

```java
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
```



### 2.6创建EmployeeController控制器

```java
@RestController
@Slf4j
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public Result login(HttpServletRequest request, @RequestBody Employee employee){
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        if(emp==null){
            return new Result(Code.LOGIN_FAILED,"用户名不存在");
        }
        if(!emp.getPassword().equals(password)){
            return new Result(Code.LOGIN_FAILED,"密码错误");
        }
        if(emp.getStatus()==0){
            return new Result(Code.LOGIN_FAILED,"账号已禁用");
        }
        request.getSession().setAttribute("employee",emp.getId());
        return new Result(Code.LOGIN_SUCCESS,emp,"登录成功");
    }

}
```

## 3.后台管理系统退出功能开发

更新EmployeeController

```java
@PostMapping("/logout")
public Result logout(HttpServletRequest request){
    request.getSession().removeAttribute("employee");
    return new Result(Code.LOGOUT_SUCCESS,"退出成功");
}
```

## 4.登录拦截

启动类加入@ServletComponentScan注解

拦截器

```java
@Slf4j
@Component
@WebFilter(filterName = "logincheckfilter",urlPatterns = "/")
public class LoginCheckFilter implements Filter {
    private static final AntPathMatcher PATH_MATCHER= new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String requestUri = request.getRequestURI();
        String urls[] = new String[]{
                "/employee/login","/employee/logout","/backend/**","/fornt/**"
        };
        boolean check = check(urls,requestUri);
        if(check){
            filterChain.doFilter(request,response);
            return;
        }
        if(request.getSession().getAttribute("employee")!=null){
            filterChain.doFilter(request,response);
            return;
        }
        response.getWriter().write(JSON.toJSONString(new Result(0,"NOTLOGIN")));
        return;
    }
    public boolean check(String[]urls,String requestUri){
        for (String url : urls) {
           boolean flag = PATH_MATCHER.match(url,requestUri);
           if(flag){
               return true;
           }
        }
        return false;

    }
}
```

## 5.新增员工

```java
@PostMapping
public Result addEmployee(HttpServletRequest request,@RequestBody Employee employee){
    employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
    employee.setCreateTime(LocalDateTime.now());
    employee.setUpdateTime(LocalDateTime.now());
    employee.setCreateUser((Long) request.getSession().getAttribute("employee"));
    employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
    employeeService.save(employee);
    return new Result(Code.SAVE_SUCCESS,"添加员工成功");
}
```

## 6.异常处理类

异常处理类GlobalExceptionHandler

```java
/*
* 全局异常处理
* */
@ControllerAdvice(annotations = RestController.class)
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    //    异常处理方法
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());
        String[] split = ex.getMessage().split(" ");
        if(ex.getMessage().contains("Duplicate entry")){
          return new Result(Code.SAVE_FAILED,split[2]+"已存在,新增失败");
        }
        return new Result(Code.SAVE_FAILED,"未知错误");
    }
}

```

## 7.员工分页查询

### 7.1创建MybatisPlus配置类

用于分页查询的配置类MyBatisPlusConfig

```java
/*
* MyBatisPlus配置分页插件
* */
@Configuration
public class MyBatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }
}
```

### 7.2员工查询

在EmployeeController中添加方法

```java
@GetMapping("/page")
public Result page(int page, int pageSize, String name) {
    log.info("page={},pageSize={},name={}",page,pageSize,name);
    Page pageInfo = new Page(page,pageSize);
    LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.like(!StringUtils.isEmpty(name),Employee::getName,name);
    queryWrapper.orderByDesc(Employee::getUpdateTime);
    employeeService.page(pageInfo,queryWrapper);
    return new Result(Code.GET_SUCCESS,pageInfo,"查询成功");
}
```

## 8.启用/禁用员工账号与编辑员工信息

### 8.1扩展消息转换器

消息转换器，这里由于MybatisPlus ID是通过雪花算法得到的与前端ID传递有精度损失，所以这里扩展了消息转换器。

```java
package com.whitecatsama.delicious_take_out.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

/**
 * 对象映射器:基于jackson将Java对象转为json，或者将json转为Java对象
 * 将JSON解析为Java对象的过程称为 [从JSON反序列化Java对象]
 * 从Java对象生成JSON的过程称为 [序列化Java对象到JSON]
 */
public class JacksonObjectMapper extends ObjectMapper {

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    public JacksonObjectMapper() {
        super();
        //收到未知属性时不报异常
        this.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

        //反序列化时，属性不存在的兼容处理
        this.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);


        SimpleModule simpleModule = new SimpleModule()
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                .addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
                .addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)))

                .addSerializer(BigInteger.class, ToStringSerializer.instance)
                .addSerializer(Long.class, ToStringSerializer.instance)
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
                .addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));

        //注册功能模块 例如，可以添加自定义序列化器和反序列化器
        this.registerModule(simpleModule);
    }
}

```

在WebMvcConfig重写WebMvcConfigurationSupport的extendMessageConverters方法

```java
@Override
protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
    messageConverter.setObjectMapper(new JacksonObjectMapper());
    converters.add(0,messageConverter);
}
```

### 8.2启用/禁用及修改员工账号

```java
@PutMapping
public Result update(HttpServletRequest request,@RequestBody Employee employee) {
    employee.setUpdateTime(LocalDateTime.now());
    employee.setUpdateUser((Long)request.getSession().getAttribute("employee"));
    employeeService.updateById(employee);
    return new Result(Code.UPDATE_SUCCESS,"员工信息修改成功");
}
```

### 8.3编辑页面时的数据获取

```java
@GetMapping("/{id}")
public Result getInfoById(@PathVariable Long id){
    Employee employee = employeeService.getById(id);
    return new Result(Code.GET_SUCCESS,employee,"查询成功");
}
```

## 9.公共字段自动填充

相比于AOP，MybatisPlus提供的公共字段自动填充更快速便捷。

实现步骤：在实体类的属性上加入@TableField注解，指定自动填充的策略；按框架要求编写

元数据对象处理器，在此类中统一为公共字段赋值，为此需要实现MetaObjectHandler接口。

```java
@TableField(fill = FieldFill.INSERT)
private LocalDateTime createTime;

@TableField(fill = FieldFill.INSERT_UPDATE)
private LocalDateTime updateTime;

@TableField(fill = FieldFill.INSERT)
private Long createUser;

@TableField(fill = FieldFill.INSERT_UPDATE)
private Long updateUser;
```

```java
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Autowired
    private HttpServletRequest request;

    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser", request.getSession().getAttribute("employee"));
        metaObject.setValue("updateUser", request.getSession().getAttribute("employee"));
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", 
                           request.getSession().getAttribute("employee"));
    }
}
```

这里request对象可以像我一样使用HttpServletRequest自动注入，也可以使用TreadLocal进行实现，在该线程中set方法一个线程局部变量值(用户ID)然后再通过其get方法获取到ID，最后在进行释放，不过需要注意ThreadLocal容易造成内存泄漏的问题。

这里可以对Controller中需要进行自动填充的内容注释掉

```java
    @PostMapping
    public Result addEmployee(HttpServletRequest request, @RequestBody Employee employee) {
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setCreateUser((Long) request.getSession().getAttribute("employee"));
//        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        employeeService.save(employee);
        return new Result(Code.SAVE_SUCCESS, "添加员工成功");
    }
```

```java
    @PutMapping
    public Result update(HttpServletRequest request,@RequestBody Employee employee) {
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser((Long)request.getSession().getAttribute("employee"));
        employeeService.updateById(employee);
        return new Result(Code.UPDATE_SUCCESS,"员工信息修改成功");
```

## 10.新增分类

### 10.1实体类Category创建

```java
/**
 * 分类
 */
@Data
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //类型 1 菜品分类 2 套餐分类
    private Integer type;

    //分类名称
    private String name;

    //顺序
    private Integer sort;

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    //创建人
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    //修改人
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    //是否删除
    private Integer isDeleted;

}
```

### 10.2创建CategoryMapper接口

```java
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
```

### 10.3创建CategoryService接口及其实现类

```java
@Transactional
public interface CategoryService extends IService<Category> {
}
```

```java
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
```

### 10.4创建CategoryController控制器

这里由于前面有过相同的异常处理类中对应异常的处理，所以插入相同分类会拦截异常报错。

```java
@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result addCategory(@RequestBody Category category){
        categoryService.save(category);
        return new Result(Code.SAVE_SUCCESS,"新增分类成功");
    }

}
```

## 11.分类分页查询

```java
    @GetMapping("/page")
    public Result page(int page,int pageSize){
        Page<Category> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        categoryService.page(pageInfo,queryWrapper);
        return new Result(Code.GET_SUCCESS,pageInfo,"查询成功");
    }
```



## 12.删除功能

由于需要判断该菜品/套餐中是否有关联的菜所以还需要再创建对应的实体类。

### 12.1实体类Dish创建

```java
/**
 菜品
 */
@Data
public class Dish implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    //菜品名称
    private String name;


    //菜品分类id
    private Long categoryId;


    //菜品价格
    private BigDecimal price;


    //商品码
    private String code;


    //图片
    private String image;


    //描述信息
    private String description;


    //0 停售 1 起售
    private Integer status;


    //顺序
    private Integer sort;


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;


    //是否删除
    private Integer isDeleted;

}

```

### 12.2实体类Setmeal创建

```java
/**
 * 套餐
 */
@Data
public class Setmeal implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    //分类id
    private Long categoryId;


    //套餐名称
    private String name;


    //套餐价格
    private BigDecimal price;


    //状态 0:停用 1:启用
    private Integer status;


    //编码
    private String code;


    //描述信息
    private String description;


    //图片
    private String image;


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;


    //是否删除
    private Integer isDeleted;
}
```

### 12.3创建DishMapper接口

```java
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
```

### 12.4创建SetmealMapper接口

```java
@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {
}
```

### 12.5创建DishService

```java
@Transactional
public interface DishService extends IService<Dish> {
}
```

### 12.6创建SetmealService

```java
@Transactional
public interface SetmealService extends IService<Setmeal> {
}
```

### 12.7创建实现类DishServiceImpl

```java
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}
```

### 12.8创建实现类SetmealServiceImpl

```java
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}
```

### 12.9新增CategoryService接口及实现类的删除方法与实现

```java
public interface CategoryService extends IService<Category> {
    public boolean removeById(Long id);
}
```

```java
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;
    @Override
    public boolean removeById(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int dishCount = dishService.count(dishLambdaQueryWrapper);
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int setmealCount = setmealService.count(setmealLambdaQueryWrapper);
        if(dishCount>0||setmealCount>0){
            return false;
        }
        else
        {
            super.removeById(id);
            return true;
        }

    }
}
```

### 12.10创建CategoryController删除方法

这里删除功能的路径就是Deletemapping而不是Deletemapping("/ids")，因为对应的category.js中啊ajax请求的路径就是/category，并不是restful风格

```java
@DeleteMapping
public Result deleteById(Long ids){

    if(categoryService.remove(ids)){
        return new Result(Code.DELETE_SUCCESS,"删除成功");
    }
    else
    {
        return new Result(Code.DELETE_FAILED,"已关联数据，删除失败");
    }

}
```

## 13.修改分类

```java
@PutMapping
public Result editCategroy(@RequestBody Category category){
    categoryService.updateById(category);
    return new Result(Code.UPDATE_SUCCESS,"编辑成功");
}
```

## 14.文件的上传下载

### 14.1文件上传

#### 14.1.1创建CommonController

```java
@RestController
@RequestMapping("/common")
public class CommonController {
    }
}
```

#### 14.1.2修改yml配置

```yaml
#图片存放的路径参数
delicious:
  picture:
    path: F:\workspace\springmvc\springmvc\delicious_take_out\src\main\resources\pic\
```

#### 14.1.3编写CommonController中upload方法

这里可以使用file.getOriginalFilename()命名可能会出现重名覆盖，所以这里采用UUID生成随机名。

```java
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${delicious.picture.path}")
    private String BasePath;
    @PostMapping("/upload")
    public Result upload(MultipartFile file){
        String fileName = UUID.randomUUID().toString();
        fileName = fileName + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        log.info(file.toString());
        try{file.transferTo(new File(BasePath+fileName));}
            catch(IOException ex){
                ex.printStackTrace();
                return new Result(Code.UPLOAD_FAILED,"上传失败");
            }
        return new Result(Code.UPLOAD_SUCCESS,fileName,"上传成功");
    }
}
```

### 14.2文件下载

```java
    @GetMapping("/download")
    public void downLoad(String name, HttpServletResponse response){
        try {
            FileInputStream fileInputStream = new FileInputStream(BasePath+name);
            ServletOutputStream servletOutputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            int lenth = 0;
            byte[] bytes = new byte[2048];
            while((lenth=fileInputStream.read(bytes))>0){
                servletOutputStream.write(bytes,0,lenth);
                servletOutputStream.flush();
            }
            fileInputStream.close();
            servletOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```

## 15.新增菜品

### 15.1实体类DishFlavor创建

```java
/**
菜品口味
 */
@Data
public class DishFlavor implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    //菜品id
    private Long dishId;


    //口味名称
    private String name;


    //口味数据list
    private String value;


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;


    //是否删除
    private Integer isDeleted;

}
```

### 15.2创建DishFlavorMapper接口

```java
@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {
}
```

### 15.3创建DishFlavorService接口

```java
@Transactional
public interface DishFlavorService extends IService<DishFlavor> {
}
```

### 15.4创建DishFlavorServiceImpl实现类

```java
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
```

### 15.5创建DishController控制器

```java
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;
    
}
```

### 15.6编写CategoryController获取菜品种类方法



```java
@GetMapping("/list")
public Result getCategoryList(Category category){
    LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(category.getType()!=null, Category::getType,category.getType());
    queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
     List list = categoryService.list(queryWrapper);
    return new Result(Code.GET_SUCCESS,list,"菜品查询成功");
}
```
