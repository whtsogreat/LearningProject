https://www.navicat.com/en/download/navicat-premium#mac

Mysql：

作业 
https://book.douban.com/subject/3354490/

周二上午10点
读书笔记：各种指令的用法 select create update from (至少前15章)
在mysql上用指令建立一个表

# 数据库读书笔记

### 数据库基础

**数据库(database)** 保存有组织的数据的容器(通常是一个文件或一组文件)。

**表(table)** 某种特定类型数据的结构化清单。

**模式(schema)** 关于数据库和表的布局及特性的信息。

**列(column)** 表中的一个字段。所有表都是由一个或多个列组成的。

**数据类型(datatype)** 所容许的数据的类型。每个表列都有相应的数据类型，它限制(或容许)该列中存储的数据。

**行(row)** 表中的一个记录。

**主键(primary key)**一列(或一组列)，其值能够唯一区分表 中每个行。

**关键字(key word)** 作为MySQL语言组成部分的一个保留字。决不要用关键字命名一个表或列。

**子句(clause)** SQL语句由子句构成，有些子句是必需的，而有的是可选的。一个子句通常由一个关键字和所提供的数据组成。

**NULL 无值(no value)**，它与字段包含0、空字符串或仅仅包含空格不同。

**操作符(operator)** 用来联结或改变WHERE子句中的子句的关键 字。也称为逻辑操作符(logical operator)。

### 基本语法

#### SHOW

SHOW DATABASES;

SHOW TABLES;

SHOW COLUMNS FROM CUSTOMERS;

SHOW STATUS，用于显示广泛的服务器状态信息;

SHOW CREATE DATABASE和SHOW CREATE TABLE，分别用来显示创建特定数据库或表的MySQL语句;

SHOW GRANTS，用来显示授予用户(所有用户或特定用户)的安全权限;

SHOW ERRORS和SHOW WARNINGS，用来显示服务器错误或警告消息。

#### SELECT

SELECT prod_name FROM products;

SELECT prod_id, prod_name, prod_price FROM products;

SELECT * FROM products;

SELECT DISTINCT vend_id FROM products; 只返回不同(唯一)的
vend_id行;

SELECT prod_name FROM products LIMIT 5; 返回不多于5行；

SELECT prod_name FROM products LIMIT 5， 5； 返回从行5开始的5行；

SELECT prod_name FROM products LIMIT 5 OFFSET 5；也是可以的。

*注：检索出来的第一行为行0而不是行1。因此，LIMIT1,1 将检索出第二行而不是第一行。*

使用完全限定的表名

SELECT products.prod_name FROM products;

SELECT products.prod_name FROM crashcrouse.products;

##### 排序数据

SELECT prod_name FROM products ORDER BY prod_name;

*注：通常，ORDERBY子句中使用的列将是为显示所选择的列。但是，实际上并不一定要这样，用非检索的列排序数据是完全合法的。*

SELECT prod_id, prod_price prod_name FROM products ORDER BY prod_price, prod_name; 按两个列对结果进行排序；

SELECT prod_id, prod_price prod_name FROM products ORDER BY prod_price DESC;  按价格以降序排序产品；

SELECT prod_id, prod_price prod_name FROM products ORDER BY prod_price DESC, prod_name; 以降序排序产品，再对产品名排序；

*注：如果想在多个列上进行降序排序，必须对每个列指定DESC关键字。*

*与DESC相反的关键字是ASC(ASCENDING)，在升序排序时可以指定它。 但实际上，ASC没有多大用处，因为升序是默认的(如果既不指定ASC也 不指定DESC，则假定为ASC)。*

###### WHERE

SELECT prod_name, prod_price FROM products WHERE prod_price = 2.50;

*注：在同时使用ORDERBY和WHERE子句时，应 该让ORDER BY位于WHERE之后，否则将会产生错误。*

SELECT语句有一个特殊的WHERE子句，可用来检查具有NULL值的列。

SELECT prod_name FROM products WHERE prod_price IS NULL;

*注：**NULL与不匹配** 在通过过滤选择出不具有特定值的行时，你可能希望返回具有NULL值的行。但是，不行。因为未知具有特殊的含义，数据库不知道它们是否匹配，所以在匹配过滤或不匹配过滤时不返回它们。*

*因此，在过滤数据时，一定要验证返回数据中确实给出了被过滤列具有NULL的行。*

###### AND	OR	IN	NOT

SELECT prod_name, prod_price FROM products WHERE (vend_id = 1002 OR vend_id = 1003) AND prod_price >= 10;

SELECT prod_name, prod_price FROM products WHERE vend_id IN (1002,1003) ORDER BY prod_name;

SELECT prod_name, prod_price FROM products WHERE vend_id NOT IN (1002,1003) ORDER BY prod_name;

*注：**MySQL中的NOT** MySQL支持使用NOT对IN、BETWEEN和 EXISTS子句取反，这与多数其他DBMS允许使用NOT对各种条件 取反有很大的差别。*

###### 百分号(%)通配符

SELECT prod_id, prod_name FROM products WHERE prod_name LIKE 'jet%'; 找出所有以词jet起头的产品；

SELECT prod_id, prod_name FROM products WHERE prod_name LIKE '%anvi1%'; 匹配任何位置包含文本anvil的值；

SELECT prod_id, prod_name FROM products WHERE prod_name LIKE 's%e'; 出以s起头以e结尾的所有产品。

*注：注意NULL 虽然似乎%通配符可以匹配任何东西，但有一个例外，即NULL。即使是WHERE prod_name LIKE '%'也不能匹配用值NULL作为产品名的行。*

###### 下划线(_)通配符

下划线的用途与%一样，但下划线只匹配单个字符而不是多个字符。

SELECT prod_id, prod_name FROM products WHERE prod_name LIKE '_ ton anvi1';

###### 正则表达式

SELECT prod_name FROM products WHERE prod_name REGEXP '1000' ORDER BY prod_name; REGEXP后所跟的东西作为正则表达式处理。

SELECT prod_name FROM products WHERE prod_name REGEXP '.000' ORDER BY prod_name; 匹配任意一个字符

*LIKE匹配整个列。如果被匹配的文本在列值中出现，LIKE将不会找到它，相应的行也不被返回(除非使用通配符)。而REGEXP在列值内进行匹配，如果被匹配的文本在 列值中出现，REGEXP将会找到它，相应的行将被返回。这是一个非常重要的差别。*

*REGEXP能不能用来匹配整个列值(从而起与LIKE相同的作用)?答案是肯定的，使用^和$定位符(anchor)即可。*

*匹配不区分大小写	MySQL中的正则表达式匹配(自版本 3.23.4后)不区分大小写(即，大写和小写都匹配)。为区分大 小写，可使用BINARY关键字，如WHERE prod_name REGEXP BINARY 'JetPack .000'。*

SELECT prod_name FROM products WHERE prod_name REGEXP '1000｜2000' ORDER BY prod_name; 

SELECT prod_name FROM products WHERE prod_name REGEXP '[123] Ton' ORDER BY prod_name; 

*字符集合也可以被否定，即，它们将匹配除指定字符外的任何东西。 为否定一个字符集，在集合的开始处放置一个^即可。因此，尽管[123] 匹配字符1、2或3，但[ ^123 ]却匹配除这些字符外的任何东西。*

SELECT prod_name FROM products WHERE prod_name REGEXP '[1-5] Ton' ORDER BY prod_name; 

为了匹配特殊字符，必须用\\为前导。\\-表示查找-，\\.表示查找.。

SELECT vend_name FROM vendors WHERE vend_name REGEXP '\\\\.' ORDER BY vend_name; 

\\\也用来引用元字符(具有特殊含义的字符)

\\\f 换页 \\\n 换行 \\\r 回车 \\\t 制表 \\\v 纵向制表

可以匹配字符类

[:alnum:]	任意字母和数字(同[a-zA-Z0-9]) 

[:alpha:]	 任意字符(同[a-zA-Z])

[:blank:]	 空格和制表(同[\\t])

[:cntrl:]	   ASCII控制字符(ASCII 0到31和127) 

[:digit​]	    任意数字(同[0-9])

[:graph:]	与[:print:]相同，但不包括空格 

[:lower:]	 任意小写字母(同[a-z])

[:print:]	  任意可打印字符 

[:punct:]	既不在[:alnum:]又不在[:cntrl:]中的任意字符 

[:space:]	包括空格在内的任意空白字符(同[\\f\\n\\r\\t\\v]) 

[:upper:]	任意大写字母(同[A-Z]) 

[:xdigit:]	任意十六进制数字(同[a-fA-F0-9])

匹配多个实例

\* 				 0个或多个匹配
 \+ 				1个或多个匹配(等于{1,})
 ? 				 0个或1个匹配(等于{0,1}) {n} 指定数目的匹配
 {n,} 			不少于指定数目的匹配
 {n,m} 		 匹配数目的范围(m不超过255)

SELECT prod_name FROM products WHERE prod_name REGEXP '\\\\(0-9) sticks?\\\\' ORDER BY prod_name; 

SELECT prod_name FROM products WHERE prod_name REGEXP '[[:digit:]]{4}' ORDER BY prod_name; 匹配连在一起的4位数字。

##### 创建计算字段

###### 拼接字段

SELECT Concat(vend_name, '(', vend_country, ')') FROM vendors ORDER BY vend_name;

RTrim()函数去掉值右边的所有空格。通过使用RTrim()，各个 列都进行了整理。

SELECT Concat(RTrim(vend_name), '(', RTrim(vend_country), ')') FROM vendors ORDER BY vend_name;

*注：**Trim函数** MySQL除了支持RTrim()，还支持LTrim()(去掉串左边的空格)以及 Trim()(去掉串左右两边的空格)。*

SELECT Concat(RTrim(vend_name), '(', RTrim(vend_country), ')') AS vend_title FROM vendors ORDER BY vend_name; 计算字段命名为vend_title。

###### 执行算术计算

SELECT prod_id, quantity, item_price, quantity*item_price AS expanded_price FROM orderitems WHERE order_num = 2005;

##### 使用数据处理函数

Upper()将文本转换为大写。

SELECT vend_name, Upper(vend_name) AS vend_name_upcase FROM vendors ORDER BY vend_name; 

##### 日期和时间处理函数

SELECT cust_id, order_num FROM orders WHERE order_date = '2005-09-01';

SELECT cust_id, order_num FROM orders WHERE Date(order_date) = '2005-09-01';

SELECT cust_id, order_num FROM orders WHERE Time(order_date) = '09:10:15';

SELECT cust_id, order_num FROM orders WHERE Date(order_date) BETWEEN  '2005-09-01' AND '20050930';

SELECT cust_id, order_num FROM orders WHERE YEAR(order_date) = 2005 AND Month(order_date) = 9;

##### 汇总数据

SELECT AVG(DISTINCT prod_price) AS avg_price FROM products WHERE vend_id = 1003; 用了DISTINCT参数，平均值只 考虑各个不同的价格。

SELECT COUNT(*) AS num_items, MIN(prod_price) AS price_min, MAX(prod_price) AS price_max, AVG(prod_price) AS price_avg FROM products;

##### 分组数据

SELECT vend_id, COUNT(*) AS num_prods FROM products GROUP BY vend_id;

使用ROLLUP 使用WITHROLLUP关键字，可以得到每个分组以 及每个分组汇总级别(针对每个分组)的值。

SELECT vend_id, COUNT(*) AS num_prods FROM products GROUP BY vend_id WITH ROLLUP;

SELECT cust_id, COUNT() AS orders FROM orders GROUP BY cust_id HAVING COUNT() >=2; 列出至少有两个订单的所有顾客(括号里应该有*)

SELECT vend_id, COUNT() AS num_prods FROM products WHERE prod_price >= 10 GROUP BY vend_id HAVING COUNT() >= 2;

##### 创建联结

SELECT vend_name, prod_name, prod_price FROM vendors, products WHERE vendors.vend_id = products.vend_id ORDER BY vend_name, prod_name;

**笛卡儿积(cartesian product)** 由没有联结条件的表关系返回的结果为笛卡儿积。检索出的行的数目将是第一个表中的行数乘以第二个表中的行数。

SELECT vend_name, prod_name, prod_price FROM vendors INNER JOIN products ON vendors.vend_id = products.vend_id;

##### 插入数据

INSERT INTO customers(cust_id, cust_name) SELECT cust_id, cust_name FROM custnew;

##### 更新和删除数据

UPDATE customers SET cust_email = 'elmer@fudd.com' WHERE cust_id = 10005;

UPDATE customers SET cust_name = 'The Fudds', cust_email = 'elmer@fudd.com' WHERE cust_id = 10005;

**IGNORE关键字** 如果用UPDATE语句更新多行，并且在更新这些行中的一行或多行时出一个现错误，则整个UPDATE操作被取消 (错误发生前更新的所有行被恢复到它们原来的值)。为即使是发生错误，也继续进行更新，可使用IGNORE关键字，如下所示: UPDATE IGNORE customers...

DELETE FROM customers WHERE cust_id = 10006;

**更快的删除** 如果想从表中删除所有行，不要使用DELETE。 可使用TRUNCATE TABLE语句，它完成相同的工作，但速度更快(TRUNCATE实际是删除原来的表并重新创建一个表，而不是逐行删除表中的数据)。

##### 创建和操纵表

CREATE TABLE customers

(cust_id int NOT NULL AUTO_INCREMENT,

cust_name char(50) NOT NULL,

cust_address char(50) NULL,

PRIMARY KEY (cust_id)

)ENGINE=InnoDB;



ALTER TABLE vendors ADD vend_phone CHAR(20);

ALTER TABLE vendors DROP COLUMN vend_phone;

定义外键

ALTER TABLE orders ADD CONSTRAINT fk_orders_customers FOREIGN KEY (cust_id) REFERENCES customers(cust_id);

删除表

DROP TABLE customers2;

重命名表

RENAME TABLE customers2 TO customers;

##### 创建组合查询

SELECT vend_id FROM products WHERE prod_price <= 5

UNION

SELECT vend_id FROM products WHERE vend_id IN (1001,1002);

*不允许使用多条ORDER BY子句*

*如果想返回所有匹配行，可使用UNION ALL而不是UNION。*

##### 使用全文本搜索

CREATE TABLE customers

(cust_id int NOT NULL AUTO_INCREMENT,

cust_name char(50) NOT NULL,

cust_address char(50) NULL,

PRIMARY KEY (cust_id)，

**FULLTEXT(note_text)**

)ENGINE=**MyISAM**;

*在索引之后，使用两个函数Match()和Against()执行全文本搜索，其中Match()指定被搜索的列，Against()指定要使用的搜索表达式。全文本搜索的一 个重要部分就是对结果排序。*

SELECT note_text FROM productnotes WHERE Match(note_text) Against('rabbit');

SELECT note_text FROM productnotes WHERE note_text LIKE '%rabbit%'; 等同于上面的指令，但是不排序。



<<<<<<< Updated upstream
=======
高性能Mysql
提交PR
明天12点。

# 高性能mysql读书笔记

### Mysql逻辑架构

###### 读写锁

共享锁（shared lock）和排他锁（exclusive lock）又叫

读锁（read lock）和 写锁（write lock）

###### 表锁（table lock）

MySQL中最基本的锁策略，并且是开销最小的策略。一个用户在对表进行写操作前，需要先获得写锁，这会阻塞其他用户对该表的所有读写操作。

###### 行级锁（row lock）

可以最大程度的支持并发处理（同时也带来了最大的锁开销）。

###### 事务

一组原子性的SQL查询，或者说一个独立的工作单元。

可以使用start transaction语句开始一个事务，然后commit提交或者rollback撤销。

事务的ACID：原子性（atomicity）、一致性（consistency）、隔离性（isolation）和持久性（durability）。

###### 隔离级别

READ UNCOMMITTED（未提交读）

READ COMMITTED（提交读）

REPEATABLE READ（可重复读）MySQL的默认事务隔离级别

SERIALIZABLE（可串行化）

###### 多版本并发控制（MVCC）

通过保存数据在某个时间点的快照来实现。

###### ALTER TABLE

ALTER TABLE mytable ENGINE = InnoDB；修改引擎

### MySQL基准测试

基准测试有两种主要策略：一是针对整个系统的整体测试，另外是单独测试MySQL。这两种策略也被称为集成式（full-stack）以及单组件式（single-component）基准测试。

###### 测试何种指标

吞吐量、响应时间或者延迟、并发性、可扩展性等。

###### 基准测试方法

避免常见错误：

​	使用真实数据的子集而不是全集；

​	使用错误的数据分布；

​	使用不真实的分布参数；

​	在多用户场景中，只做单用户的测试；

​	在单服务器上测试分布式应用；

​	与真实用户行为不匹配；

​	反复执行同一个查询；

​	没有检查错误；

​	忽略了系统预热（warm up）的过程；

​	使用默认的服务器配置；

​	测试时间太短。

###### 设计和规划基准测试

###### 基准测试应该运行多长时间

###### 获取系统性能和状态

###### 获得准确的测试结果

###### 运行基准测试并分析结果

###### 绘图的重要性

##### 基准测试工具

###### 集成式测试工具

ab：一个Apache HTTP服务器基准测试工具。它可以测试HTTP服务器每秒最多可以处理多少请求。

http_load：概念上和ab类似，也被设计为对Web服务器进行测试，但比ab要更加灵活。

JMeter：Java应用程序，可以加载其他应用并测试其性能。

###### 单组件式测试工具

mysqlslap：可以模拟服务器的负载，并输出计时信息。

MySQL Benchmark Suite（sql-bench）：用于在不同数据库服务器上进行比较测试。

Super Smack：用于MySQL和PostgreSQL的基准测试工具，可以提供压力测试和负载生成。

Database Test Suite：类似某些工业标准测试的测试工具集，例如由事务处理性能委员会（TPC）制定的各种标准。

Percona's TPCC-MySQL Tool

sysbench：多线程系统压测工具。

###### MySQL的BENCHMARK()函数

测试某些特定操作的执行速度。

>>>>>>> Stashed changes
