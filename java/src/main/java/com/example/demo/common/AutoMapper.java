package com.example.demo.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class AutoMapper {

    public static void main(String[] args) {
        //创建AutoGenerator，mp中对象生成器
        AutoGenerator ag = new AutoGenerator();
    //设置全局配置
        GlobalConfig gc = new GlobalConfig();
        //设置代码生成位置
        String path = System.getProperty("user.dir");
        System.out.println(path);
        gc.setOutputDir(path+"/src/main/java");
        //设置生成的类的名称
        gc.setMapperName("%sMapper");//所有Dao类都是以Mapper结尾的
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");

        gc.setSwagger2(true);
        //设置作者
        gc.setAuthor("LiuJingxian");
        //设置主键配置
        gc.setIdType(IdType.AUTO);

        ag.setGlobalConfig(gc);

        //设置数据源FataSource
        DataSourceConfig ds = new DataSourceConfig();
        // 注意：填入application.yml文件中的配置即可
        ds.setUrl("jdbc:mysql://8.141.146.38:3306/lazy_school?useUnicode=true&charaterEncoding=utf-8&SSL=true&serverTimezone=Asia/Shanghai");
        // dsc.setSchemaName("public");
        ds.setDriverName("com.mysql.cj.jdbc.Driver");
        ds.setUsername("ljx");
        ds.setPassword("12345678");
        ag.setDataSource(ds);

        //设置package信息
        PackageConfig pc = new PackageConfig();
        //设置模块名称，相当于包名，在包的下面有mapper，service，controller
        pc.setModuleName("management");
        //设置父包名称
        pc.setParent("com.example.demo");
        ag.setPackageInfo(pc);

        //设置策略
        StrategyConfig sc = new StrategyConfig();
        // 配置数据表与实体类名之间映射的策略
        sc.setNaming(NamingStrategy.underline_to_camel);
        // 配置数据表的字段与实体类的属性名之间映射的策略
        sc.setColumnNaming(NamingStrategy.underline_to_camel);
        // 配置 lombok 模式
        sc.setEntityLombokModel(true);
        // 配置 rest 风格的控制器（@RestController）
        sc.setRestControllerStyle(true);
        // 配置驼峰转连字符
        sc.setControllerMappingHyphenStyle(true);
        ag.setStrategy(sc);

        //执行代码生成
        ag.execute();
        
    }
}
