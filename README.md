## 启动方式

![image-20200607192445492](https://img-blog.csdnimg.cn/20200607195015823.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0Z1amllMTk5Nw==,size_16,color_FFFFFF,t_70)

## 数据库配置示例

```properties
driver = com.mysql.cj.jdbc.Driver
user = root
password = root
##使用的数据库类型
usingDB = mysql
url = jdbc:mysql://localhost:3306/marking?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false
##实体类包的路径
poPackage =com.fuyouj.entity.test
##项目路径
srcPath = E:\\mooc\\手写Spring源码\\simpleframework\\src\\main\\java
##指定自己的查询类，必须继承自Query
queryClass = com.framework.orm.core.impl.MySQLQueryImpl
##线程池配置
poolMin = 10
poolMax = 100
#
#E:\mooc\手写Spring源码\simpleframework\src\main\java\com\fuyouj\entity\test
```

## 根据数据库生成实力类 说明

```java
Map<String, TableInfo> tables = TableContext.tables;

        TypeConverter typeConverter = new MySQLTypeConverterImpl();
//批量生成
        for (TableInfo value : tables.values()) {
            System.out.println("生成:"+value.getTableName());
            //true  使用lombok注解
            createJavaFile(value,typeConverter,true);
        }
```

## 查询测试

```java
  @Test
    void testDelete(){
        TableContext.loadPoTables();
        Paper_url paperUrl = new Paper_url();
        paperUrl.setPaper_id(222);
        new MySQLQueryImpl().deleteByPrimaryKey(paperUrl.getClass(),222);
    }
    @Test
    void testAdd(){
        TableContext.loadPoTables();
        Paper_url paperUrl = new Paper_url();
        paperUrl.setPaper_id(223);
        paperUrl.setURL("safsff?????");
        new MySQLQueryImpl().insert(paperUrl);
    }
    @Test
    void testUpdate(){
        TableContext.loadPoTables();
        Paper_url paperUrl = new Paper_url();
        paperUrl.setPaper_id(222);
        paperUrl.setURL("aaa");
        new MySQLQueryImpl().updateByPrimaryKey(paperUrl);
    }
    @Test
    void testQueryRows(){
        TableContext.loadPoTables();
        List<Object> list = new MySQLQueryImpl().queryRows("select * from paper_url",
                Paper_url.class, null);
//        list.forEach(a-> System.out.println(a));
    }
    @Test
    void 连表查询测试 (){
        String  sql = "SELECT paper_url.*,paper.examId FROM\n" +
                "paper_url,paper\n";
        List<Object> list = new  MySQLQueryImpl().queryRows(sql, PaperDTO.class,null);
        list.forEach(a-> System.out.println(a));
    }
```

