package com.luwei.plus;

/**
 * @Author: ffq
 * @Description:
 * @Date: Create in 11:12 2018/11/7
 */


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;

public class GeneratorCode {

    private static String outDir = System.getProperty("user.dir") + "/mybatis-plus-generator/src/main/java";
    private static String entity = "entity";
    private static String mapper = "mapper";
    private static String service = "service";
    private static String impl = "service.impl";
    private static String controller = "controller";
    private static String xml = "mapper.xml";


    private static PropertiesBean propertiesBean = null;

    static {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(GeneratorCode.class.getClassLoader().getResource("application.yml").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Yaml yaml = new Yaml();
        propertiesBean = yaml.loadAs(inputStream, PropertiesBean.class);
    }


    private static String[] baseDir = {entity, mapper, service, impl, controller};

    public static void main(String[] args) throws FileNotFoundException {
        //user -> UserService, 设置成true: user -> IUserService
        boolean serviceNameStartWithI = true;
        generateByTables(serviceNameStartWithI, propertiesBean.getPackageName(),
                propertiesBean.getTableNames());
    }


    private static void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
        GlobalConfig config = new GlobalConfig();
        String dbUrl = propertiesBean.getUrl();
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername(propertiesBean.getUsername())
                .setPassword(propertiesBean.getPassword())
                .setDriverName(propertiesBean.getDriverClassName());


        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(false)
                .setEntityLombokModel(true)
                .setTablePrefix(propertiesBean.getTablePrefix())
                .setLogicDeleteFieldName(propertiesBean.getLogicDeleteFieldName())
                .setVersionFieldName(propertiesBean.getVersionFieldName())
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setRestControllerStyle(propertiesBean.getRestControllerStyle())
                .entityTableFieldAnnotationEnable(false)
                .setNaming(NamingStrategy.underline_to_camel)
                //修改替换成你需要的表名，多个表名传数组
                .setInclude(tableNames);
        config.setActiveRecord(true)
                .setAuthor(propertiesBean.getAuthor())
                .setOutputDir(System.getProperty("user.dir") + "/mybatis-plus-generator/src/main/java")
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setFileOverride(true)
                .setEnableCache(false)
                .setIdType(IdType.AUTO)
                .setActiveRecord(false)
                // XML ResultMap
                .setBaseResultMap(true)
                // XML columList;
                .setBaseColumnList(true);
        if (!serviceNameStartWithI) {
            config.setServiceName("%sService");
        }

        PackageConfig pcf = initPackage();
        TemplateConfig tc = initTemplateConfig(packageName);

        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(pcf)
                .setTemplate(tc)
                .execute();
    }

    /**
     * 根据自己的需要，修改哪些包下面的 要覆盖还是不覆盖
     *
     * @param packageName
     */
    private static TemplateConfig initTemplateConfig(String packageName) {
        TemplateConfig tc = new TemplateConfig();
        for (String tmp : baseDir) {
            initVM(tc);
            File file = new File(Paths.get(outDir, String.join("/", packageName.split("\\.")), tmp).toString());
            String[] list = file.list();
            if (list != null && list.length > 0) {
                if (!propertiesBean.getIsOverController()) {
                    tc.setController(null);
                }
                if (!propertiesBean.getIsOverService()) {
                    tc.setService(null);
                }
                if (!propertiesBean.getIsOverServiceImpl()) {
                    tc.setServiceImpl(null);
                }
                if (!propertiesBean.getIsOverEntity()) {
                    tc.setEntity(null);
                }
                if (!propertiesBean.getIsOverXml()) {
                    tc.setXml(null);
                }
                if (!propertiesBean.getIsOverMapper()) {
                    tc.setMapper(null);
                }
            }
        }
        return tc;
    }

    private static void initVM(TemplateConfig tc) {
        if (stringIsNotNull(propertiesBean.getEntityVM())) {
            tc.setEntity(propertiesBean.getEntityVM());
        }
        if (stringIsNotNull(propertiesBean.getMapperVM())) {
            tc.setMapper(propertiesBean.getMapperVM());
        }
        if (stringIsNotNull(propertiesBean.getServiceImplVM())) {
            tc.setServiceImpl(propertiesBean.getServiceImplVM());
        }
        if (stringIsNotNull(propertiesBean.getServiceVM())) {
            tc.setService(propertiesBean.getServiceVM());
        }
        if (stringIsNotNull(propertiesBean.getXmlVM())) {
            tc.setXml(propertiesBean.getXmlVM());
        }
        if (stringIsNotNull(propertiesBean.getControllerVM())) {
            tc.setController(propertiesBean.getControllerVM());
        }
    }

    /**
     * 简单判断字符串是不是为空
     *
     * @param s
     * @return
     */
    private static boolean stringIsNotNull(String s) {
        if (null != s && !s.equals("") && s.length() > 0 && s.trim().length() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 初始化包目录配置
     *
     * @return
     */
    private static PackageConfig initPackage() {
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(propertiesBean.getPackageName());
        packageConfig.setService(service);
        packageConfig.setServiceImpl(impl);
        packageConfig.setController(controller);
        packageConfig.setEntity(entity);
        packageConfig.setXml(xml);
        return packageConfig;
    }
}
