## 启动方式

![image-20200607192445492](..\simpleframework\README.assets\image-20200607192445492.png)

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
            createJavaFile(value,typeConverter,true);
        }
```

