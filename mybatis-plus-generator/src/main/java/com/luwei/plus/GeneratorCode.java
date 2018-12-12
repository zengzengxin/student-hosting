package com.luwei.plus;

/**
 * @Author: ffq
 * @Description:
 * @Date: Create in 11:12 2018/11/7
 */

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.luwei.common.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
public class GeneratorCode {

    public static void main(String[] args) throws IOException {
        //user -> UserService, 设置成true: user -> IUserService
        boolean serviceNameStartWithI = false;
        generateByTables(serviceNameStartWithI, propertiesBean.getPackageName(),
                propertiesBean.getTableNames());

        //创建所有的实体VO DTO

        //删除指定文件
        File srcFile = new File(new StringBuilder().append(outDir).append("/")
                .append(propertiesBean.getPackageName().replace(".", "/"))
                .append("/")
                .append(mapper).toString());
        org.apache.commons.io.FileUtils.deleteDirectory(srcFile);
        //创建web文件
        createPOJO();
    }

    /**
     * @param
     * @Author: ffq
     * @Date: 2018/12/7 17:12
     * @Description:创建所有的VO、DTO
     */
    private static void createPOJO() throws IOException {
        //获取实体文件的路径
        String srcDir = new StringBuilder().append(outDir).append("/")
                .append(propertiesBean.getPackageName().replace(".", "/"))
                .append("/")
                .append(entity)
                .append("/")
                .append(propertiesBean.getBeanNames())
                .append("/")
                .append(propertiesBean.getVoDTODir().replace(".", "/")).toString();
        File srcFile = new File(srcDir);
        if (!srcFile.exists() || srcFile.listFiles().length <= 0) {
            log.error("创建VO、DTO失败：找不到Entity位置");
            return;
        }


        for (File file : srcFile.listFiles()) {
            //复制
            String entity = file.getName().substring(0, file.getName().lastIndexOf("."));
            if (!FileUtils.getFileSuffix(file.getName()).equals("java")) {
                continue;
            }
            //将文件移动到下一个包 User实体 user包
//            创建web文件
            String parentDir = srcDir.substring(0, srcDir.lastIndexOf("/") + 1) + "web";
            File parentFileDir = new File(parentDir);
            parentFileDir.mkdir();
            IOReadWriter(file, new File(parentFileDir, file.getName()));
        }

    }


    private static String outDir = System.getProperty("user.dir") + "/mybatis-plus-generator/src/main/java";
    private static String entity = "models";
    private static String mapper = "mapper";
    private static String service = "service";
    private static String impl = "service";
    private static String controller = "controller";
    private static String xml = "mapper";
//    private static List<File> deleteFile = new ArrayList<>();

    private static PropertiesBean propertiesBean = null;

    //保存所有实体的名字
    private static Set<String> entityName = new HashSet<>();

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


    private static void IOReadWriter(File srcFile, File targetFile) throws IOException {
        if (!targetFile.exists()) {
            targetFile.createNewFile();
        }
        BufferedReader br = null;
        BufferedWriter bw = null;
        String line = null;
        Boolean isDelete = false;
        //源文件名 无后缀
        String src = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
        //目标文件名
        String target = targetFile.getName().substring(0, targetFile.getName().lastIndexOf("."));
        try {
            // 根据文件路径创建缓冲输入流
            br = new BufferedReader(new FileReader(srcFile));
            bw = new BufferedWriter(new FileWriter(targetFile));

            // 循环读取文件的每一行, 对需要修改的行进行修改, 放入缓冲对象中
            while ((line = br.readLine()) != null) {
                // 此处根据实际需要修改某些行的内容
                if (line.contains("interface") || line.contains("abstract")) {
                    //直接退出，提前关闭
                    isDelete = true;
                    return;
                }

                if (line.contains("package")) {
//                    com.luwei.models.user.pojo.cms
                    line = line.substring(0, line.lastIndexOf(".") + 1) + "web;";
                }

                if (line.contains(src)) {
                    line = line.replace(src, target);
                }
                if (line.contains("@TableName")) {
                    continue;
                }
                if (line.contains("@TableId")) {
                    continue;
                }
                if (line.contains("@TableLogic")) {
                    continue;
                }
                if (line.contains("deleted")) {
                    continue;
                }

                if (target.contains("QueryDTO") && line.contains("@Accessors")) {
                    continue;
                }

                bw.append(line);
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                }
            }
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    bw = null;
                }
            }

            if (isDelete) {
                boolean delete = targetFile.delete();
                System.out.println(delete);
            }
        }
    }


    private static String[] baseDir = {entity, mapper, service, impl, controller};


    private static void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
        GlobalConfig config = new GlobalConfig();
        String dbUrl = propertiesBean.getUrl();
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername(propertiesBean.getUsername())
                .setPassword(propertiesBean.getPassword())
                .setDriverName(propertiesBean.getDriverClassName())
                .setTypeConvert(new MySqlTypeConvert());


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
                .setOpen(false)//设置运行后不打开文件
                .setIdType(IdType.AUTO)
                //Service名字
                .setServiceImplName("%sService")
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


        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
        InjectionConfig abc = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
//                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                map.put("voDTODir", propertiesBean.getVoDTODir());
                this.setMap(map);
            }
        };
        //自定义文件输出位置（非必须）
        List<FileOutConfig> fileOutList = new ArrayList<>();
        fileOutList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return System.getProperty("user.dir") + "/mybatis-plus-generator/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        //VO、DTO对应的位置

        String voDTODir = System.getProperty("user.dir") + "/mybatis-plus-generator/src/main/java/" + packageName.replace(".", "/")
                + "/" + entity + "/" + propertiesBean.getBeanNames().replace(".", "/") + "/" + propertiesBean.getVoDTODir().replace(".", "/") + "/";
        fileOutList.add(new FileOutConfig("/templates/vo.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return voDTODir + tableInfo.getEntityName() + "VO" + StringPool.DOT_JAVA;
            }
        });
        fileOutList.add(new FileOutConfig("/templates/addDTO.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return voDTODir + tableInfo.getEntityName() + "AddDTO" + StringPool.DOT_JAVA;
            }
        });
        fileOutList.add(new FileOutConfig("/templates/updateDTO.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return voDTODir + tableInfo.getEntityName() + "UpdateDTO" + StringPool.DOT_JAVA;
            }
        });
        fileOutList.add(new FileOutConfig("/templates/queryDTO.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return voDTODir + tableInfo.getEntityName() + "QueryDTO" + StringPool.DOT_JAVA;
            }
        });
        abc.setFileOutConfigList(fileOutList);

        new AutoGenerator()
                .setCfg(abc)
                .setGlobalConfig(config)
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
                    //关闭默认xml生成，调整生成至 根目录
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
        //没什么用，就是parent.module.controller，不是我们想要的效果
//        packageConfig.setModuleName("test");
        packageConfig.setParent(propertiesBean.getPackageName());
        packageConfig.setService(service + "." + propertiesBean.getBeanNames());
        packageConfig.setServiceImpl(impl + "." + propertiesBean.getBeanNames());
        packageConfig.setController(controller);
        packageConfig.setEntity(entity + "." + propertiesBean.getBeanNames());
//        packageConfig.setXml(xml);
//        packageConfig.setXml(null);
        packageConfig.setMapper(entity + "." + propertiesBean.getBeanNames());
        return packageConfig;
    }
}
