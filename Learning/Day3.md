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



**需要新添加的内容：**

1. **解释explain用法；**
2. **Mysql所有的锁的全程简称中英文；**
3. **ACID。**

#### **explain用法**

mysql> explain select * from user_info where id = 2\G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: user_info
   partitions: NULL
         type: const
possible_keys: PRIMARY
          key: PRIMARY
      key_len: 8
          ref: const
         rows: 1
     filtered: 100.00
        Extra: NULL
1 row in set, 1 warning (0.00 sec)

各列的含义如下:

id: SELECT 查询的标识符. 每个 SELECT 都会自动分配一个唯一的标识符.
select_type: SELECT 查询的类型.
table: 查询的是哪个表
partitions: 匹配的分区
type: join 类型
possible_keys: 此次查询中可能选用的索引
key: 此次查询中确切使用到的索引.
ref: 哪个字段或常数与 key 一起被使用
rows: 显示此查询一共扫描了多少行. 这个是一个估计值.
filtered: 表示此查询条件所过滤的数据的百分比
extra: 额外的信息

**重要字段：**

###### select_type

select_type 表示了查询的类型, 它的常用取值有:

- SIMPLE, 表示此查询不包含 UNION 查询或子查询
- PRIMARY, 表示此查询是最外层的查询
- UNION, 表示此查询是 UNION 的第二或随后的查询
- DEPENDENT UNION, UNION 中的第二个或后面的查询语句, 取决于外面的查询
- UNION RESULT, UNION 的结果
- SUBQUERY, 子查询中的第一个 SELECT
- DEPENDENT SUBQUERY: 子查询中的第一个 SELECT, 取决于外面的查询. 即子查询依赖于外层查询的结果.

###### table

表示查询涉及的表或衍生表

###### type

type 字段比较重要, 它提供了判断查询是否高效的重要依据依据. 通过 type 字段, 我们判断此次查询是全表扫描还是索引扫描等.

**type常用类型**

type 常用的取值有:

- system: 表中只有一条数据. 这个类型是特殊的 const 类型.
- const: 针对主键或唯一索引的等值查询扫描, 最多只返回一行数据. const 查询速度非常快, 因为它仅仅读取一次即可.
  例如下面的这个查询, 它使用了主键索引, 因此 type 就是 const 类型的.

mysql> explain select * from user_info where id = 2\G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: user_info
   partitions: NULL
         type: const
possible_keys: PRIMARY
          key: PRIMARY
      key_len: 8
          ref: const
         rows: 1
     filtered: 100.00
        Extra: NULL
1 row in set, 1 warning (0.00 sec)

- eq_ref: 此类型通常出现在多表的 join 查询, 表示对于前表的每一个结果, 都只能匹配到后表的一行结果. 并且查询的比较操作通常是 =, 查询效率较高. 例如:

mysql> EXPLAIN SELECT * FROM user_info, order_info WHERE user_info.id = order_info.user_id\G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: order_info
   partitions: NULL
         type: index
possible_keys: user_product_detail_index
          key: user_product_detail_index
      key_len: 314
          ref: NULL
         rows: 9
     filtered: 100.00
        Extra: Using where; Using index
*************************** 2. row ***************************
           id: 1
  select_type: SIMPLE
        table: user_info
   partitions: NULL
         type: eq_ref
possible_keys: PRIMARY
          key: PRIMARY
      key_len: 8
          ref: test.order_info.user_id
         rows: 1
     filtered: 100.00
        Extra: NULL
2 rows in set, 1 warning (0.00 sec)

- ref: 此类型通常出现在多表的 join 查询, 针对于非唯一或非主键索引, 或者是使用了最左前缀规则索引的查询. 
  例如下面这个例子中, 就使用到了 ref 类型的查询:

mysql> EXPLAIN SELECT * FROM user_info, order_info WHERE user_info.id = order_info.user_id AND order_info.user_id = 5\G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: user_info
   partitions: NULL
         type: const
possible_keys: PRIMARY
          key: PRIMARY
      key_len: 8
          ref: const
         rows: 1
     filtered: 100.00
        Extra: NULL
*************************** 2. row ***************************
           id: 1
  select_type: SIMPLE
        table: order_info
   partitions: NULL
         type: ref
possible_keys: user_product_detail_index
          key: user_product_detail_index
      key_len: 9
          ref: const
         rows: 1
     filtered: 100.00
        Extra: Using index
2 rows in set, 1 warning (0.01 sec)

- range: 表示使用索引范围查询, 通过索引字段范围获取表中部分数据记录. 这个类型通常出现在 =, <>, >, >=, <, <=, IS NULL, <=>, BETWEEN, IN() 操作中.
  当 type 是 range 时, 那么 EXPLAIN 输出的 ref 字段为 NULL, 并且 key_len 字段是此次查询中使用到的索引的最长的那个.
  例如下面的例子就是一个范围查询:

mysql> EXPLAIN SELECT *
    ->         FROM user_info
    ->         WHERE id BETWEEN 2 AND 8 \G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: user_info
   partitions: NULL
         type: range
possible_keys: PRIMARY
          key: PRIMARY
      key_len: 8
          ref: NULL
         rows: 7
     filtered: 100.00
        Extra: Using where
1 row in set, 1 warning (0.00 sec)

- index: 表示全索引扫描(full index scan), 和 ALL 类型类似, 只不过 ALL 类型是全表扫描, 而 index 类型则仅仅扫描所有的索引, 而不扫描数据.
  index 类型通常出现在: 所要查询的数据直接在索引树中就可以获取到, 而不需要扫描数据. 当是这种情况时, Extra 字段会显示 Using index.
  例如:

mysql> EXPLAIN SELECT name FROM  user_info \G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: user_info
   partitions: NULL
         type: index
possible_keys: NULL
          key: name_index
      key_len: 152
          ref: NULL
         rows: 10
     filtered: 100.00
        Extra: Using index
1 row in set, 1 warning (0.00 sec)

上面的例子中, 我们查询的 name 字段恰好是一个索引, 因此我们直接从索引中获取数据就可以满足查询的需求了, 而不需要查询表中的数据. 因此这样的情况下, type 的值是 index, 并且 Extra 的值是 Using index.

- ALL: 表示全表扫描, 这个类型的查询是性能最差的查询之一. 通常来说, 我们的查询不应该出现 ALL 类型的查询, 因为这样的查询在数据量大的情况下, 对数据库的性能是巨大的灾难. 如一个查询是 ALL 类型查询, 那么一般来说可以对相应的字段添加索引来避免.
  下面是一个全表扫描的例子, 可以看到, 在全表扫描时, possible_keys 和 key 字段都是 NULL, 表示没有使用到索引, 并且 rows 十分巨大, 因此整个查询效率是十分低下的.

mysql> EXPLAIN SELECT age FROM  user_info WHERE age = 20 \G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: user_info
   partitions: NULL
         type: ALL
possible_keys: NULL
          key: NULL
      key_len: NULL
          ref: NULL
         rows: 10
     filtered: 10.00
        Extra: Using where
1 row in set, 1 warning (0.00 sec)

**type 类型的性能比较**

通常来说, 不同的 type 类型的性能关系如下:
ALL < index < range ~ index_merge < ref < eq_ref < const < system
ALL 类型因为是全表扫描, 因此在相同的查询条件下, 它是速度最慢的.
而 index 类型的查询虽然不是全表扫描, 但是它扫描了所有的索引, 因此比 ALL 类型的稍快.
后面的几种类型都是利用了索引来查询数据, 因此可以过滤部分或大部分数据, 因此查询效率就比较高了.

###### possible_keys

possible_keys 表示 MySQL 在查询时, 能够使用到的索引. 注意, 即使有些索引在 possible_keys 中出现, 但是并不表示此索引会真正地被 MySQL 使用到. MySQL 在查询时具体使用了哪些索引, 由 key 字段决定.

###### key

此字段是 MySQL 在当前查询时所真正使用到的索引.

###### key_len

表示查询优化器使用了索引的字节数. 这个字段可以评估组合索引是否完全被使用, 或只有最左部分字段被使用到.
key_len 的计算规则如下:

- 字符串
  - char(n): n 字节长度
  - varchar(n): 如果是 utf8 编码, 则是 3 *n + 2字节; 如果是 utf8mb4 编码, 则是 4* n + 2 字节.

- 数值类型
  - TINYINT: 1字节
  - SMALLINT: 2字节
  - MEDIUMINT: 3字节
  - INT: 4字节
  - BIGINT: 8字节

- 时间类型
  - DATE: 3字节
  - TIMESTAMP: 4字节
  - DATETIME: 8字节

- 字段属性: NULL 属性 占用一个字节. 如果一个字段是 NOT NULL 的, 则没有此属性.

###### rows

rows 也是一个重要的字段. MySQL 查询优化器根据统计信息, 估算 SQL 要查找到结果集需要扫描读取的数据行数.
这个值非常直观显示 SQL 的效率好坏, 原则上 rows 越少越好.

###### Extra

Explain 中的很多额外的信息会在 Extra 字段显示, 常见的有以下几种内容:

- Using filesort
  当 Extra 中有 Using filesort 时, 表示 MySQL 需额外的排序操作, 不能通过索引顺序达到排序效果. 一般有 Using filesort, 都建议优化去掉, 因为这样的查询 CPU 资源消耗大.
- Using index
  "覆盖索引扫描", 表示查询在索引树中就可查找所需数据, 不用扫描表数据文件, 往往说明性能不错.
- Using temporary
  查询有使用临时表, 一般出现于排序, 分组和多表 join 的情况, 查询效率不高, 建议优化.



#### **Mysql所有的锁的全程简称中英文**

共享锁（shared lock）	排他锁（exclusive lock）

读锁（read lock）			写锁（write lock）

表锁（table lock）		   行级锁（row lock）

表级锁（table-level locking）页面锁（page-level locking）

行级锁（row-level locking）

表共享读锁 （Table Read Lock）表独占写锁 （Table Write Lock）

共享锁（S）	排他锁（X）

意向锁（Intention Locks）

意向共享锁（IS）	意向排他锁（IX）

间隙锁（Next-Key锁）

乐观锁(Optimistic Lock)	悲观锁(Pessimistic Lock)

##### **共享锁与排他锁**

- 共享锁（读锁）：其他事务可以读，但不能写。
- 排他锁（写锁） ：其他事务不能读取，也不能写。

##### **粒度锁**

MySQL 不同的存储引擎支持不同的锁机制，所有的存储引擎都以自己的方式显现了锁机制，服务器层完全不了解存储引擎中的锁实现：

- MyISAM 和 MEMORY 存储引擎采用的是表级锁（table-level locking）
- BDB 存储引擎采用的是页面锁（page-level locking），但也支持表级锁
- InnoDB 存储引擎既支持行级锁（row-level locking），也支持表级锁，但默认情况下是采用行级锁。

##### **不同粒度锁的比较：**

- 表级锁：开销小，加锁快；不会出现死锁；锁定粒度大，发生锁冲突的概率最高，并发度最低。 

- - 这些存储引擎通过总是一次性同时获取所有需要的锁以及总是按相同的顺序获取表锁来避免死锁。
  - 表级锁更适合于以查询为主，并发用户少，只有少量按索引条件更新数据的应用，如Web 应用

- 行级锁：开销大，加锁慢；会出现死锁；锁定粒度最小，发生锁冲突的概率最低，并发度也最高。

- - 最大程度的支持并发，同时也带来了最大的锁开销。
  - 在 InnoDB 中，除单个 SQL 组成的事务外，锁是逐步获得的，这就决定了在 InnoDB 中发生死锁是可能的。
  - 行级锁只在存储引擎层实现，而Mysql服务器层没有实现。 行级锁更适合于有大量按索引条件并发更新少量不同数据，同时又有并发查询的应用，如一些在线事务处理（OLTP）系统

- 页面锁：开销和加锁时间界于表锁和行锁之间；会出现死锁；锁定粒度界于表锁和行锁之间，并发度一般。

##### MyISAM表级锁模式：

- 表共享读锁 （Table Read Lock）：不会阻塞其他用户对同一表的读请求，但会阻塞对同一表的写请求；
- 表独占写锁 （Table Write Lock）：会阻塞其他用户对同一表的读和写操作；

可以设置改变读锁和写锁的优先级： 

- 通过指定启动参数low-priority-updates，使MyISAM引擎默认给予读请求以优先的权利。 
- 通过执行命令SET LOW_PRIORITY_UPDATES=1，使该连接发出的更新请求优先级降低。 
- 通过指定INSERT、UPDATE、DELETE语句的LOW_PRIORITY属性，降低该语句的优先级。
- 给系统参数max_write_lock_count设置一个合适的值，当一个表的读锁达到这个值后，MySQL就暂时将写请求的优先级降低，给读进程一定获得锁的机会。

在自动加锁的情况下，MyISAM 总是一次获得 SQL 语句所需要的全部锁，这也正是 MyISAM 表不会出现死锁（Deadlock Free）的原因。 

MyISAM存储引擎支持并发插入，以减少给定表的读和写操作之间的争用。

- 当concurrent_insert设置为0时，不允许并发插入。 
- 当concurrent_insert设置为1时，如果MyISAM表中没有空洞（即表的中间没有被删除的行），MyISAM允许在一个线程读表的同时，另一个线程从表尾插入记录。这也是MySQL的默认设置。 
- 当concurrent_insert设置为2时，无论MyISAM表中有没有空洞，都允许在表尾并发插入记录。

如果 Table_locks_waited 的值比较高，则说明存在着较严重的表级锁争用情况：

mysql> SHOW STATUS LIKE 'Table%';
+-----------------------+---------+
| Variable_name | Value |
+-----------------------+---------+
| Table_locks_immediate | 1151552 |
| Table_locks_waited | 15324 |
+-----------------------+---------+

##### **InnoDB锁模式：**

InnoDB 实现了以下两种类型的**行锁**： 

- 共享锁（S）：允许一个事务去读一行，阻止其他事务获得相同数据集的排他锁。 
- 排他锁（X）：允许获得排他锁的事务更新数据，阻止其他事务取得相同数据集的共享读锁和排他写锁。

为了允许行锁和表锁共存，实现多粒度锁机制，InnoDB 还有两种内部使用的意向锁（Intention Locks），这两种意向锁都是**表锁**：

- 意向共享锁（IS）：事务打算给数据行加行共享锁，事务在给一个数据行加共享锁前必须先取得该表的 IS 锁。 
- 意向排他锁（IX）：事务打算给数据行加行排他锁，事务在给一个数据行加排他锁前必须先取得该表的 IX 锁。

##### **InnoDB加锁方法：**

- 意向锁是 InnoDB 自动加的， 不需用户干预。 

- 对于 UPDATE、 DELETE 和 INSERT 语句， InnoDB
  会自动给涉及数据集加排他锁（X)；

- 对于普通 SELECT 语句，InnoDB 不会加任何锁；
  事务可以通过以下语句显式给记录集加共享锁或排他锁：

- - 共享锁（S）：SELECT * FROM table_name WHERE ... LOCK IN SHARE MODE。 其他 session 仍然可以查询记录，并也可以对该记录加 share mode 的共享锁。但是如果当前事务需要对该记录进行更新操作，则很有可能造成死锁。
  - 排他锁（X)：SELECT * FROM table_name WHERE ... FOR UPDATE。其他 session 可以查询该记录，但是不能对该记录加共享锁或排他锁，而是等待获得锁

- **隐式锁定：**

  InnoDB在事务执行过程中，使用两阶段锁协议：

  随时都可以执行锁定，InnoDB会根据隔离级别在需要的时候自动加锁；

  锁只有在执行commit或者rollback的时候才会释放，并且所有的锁都是在**同一时刻**被释放。

##### **InnoDB 行锁实现方式：**

- InnoDB 行锁是通过给索引上的索引项加锁来实现的，这一点 MySQL 与 Oracle 不同，后者是通过在数据块中对相应数据行加锁来实现的。InnoDB 这种行锁实现特点意味着：只有通过索引条件检索数据，InnoDB 才使用行级锁，否则，InnoDB 将使用表锁！
- 不论是使用主键索引、唯一索引或普通索引，InnoDB 都会使用行锁来对数据加锁。
- 只有执行计划真正使用了索引，才能使用行锁：即便在条件中使用了索引字段，但是否使用索引来检索数据是由 MySQL 通过判断不同执行计划的代价来决定的，如果 MySQL 认为全表扫描效率更高，比如对一些很小的表，它就不会使用索引，这种情况下 InnoDB 将使用表锁，而不是行锁。因此，在分析锁冲突时，
  别忘了检查 SQL 的执行计划（可以通过 explain 检查 SQL 的执行计划），以确认是否真正使用了索引。
- 由于 MySQL 的行锁是针对索引加的锁，不是针对记录加的锁，所以虽然多个session是访问不同行的记录， 但是如果是使用相同的索引键， 是会出现锁冲突的（后使用这些索引的session需要等待先使用索引的session释放锁后，才能获取锁）。 应用设计的时候要注意这一点。

##### **InnoDB的间隙锁：**

当我们用范围条件而不是相等条件检索数据，并请求共享或排他锁时，InnoDB会给符合条件的已有数据记录的索引项加锁；对于键值在条件范围内但并不存在的记录，叫做“间隙（GAP)”，InnoDB也会对这个“间隙”加锁，这种锁机制就是所谓的间隙锁（Next-Key锁）。

很显然，在使用范围条件检索并锁定记录时，InnoDB这种加锁机制会阻塞符合条件范围内键值的并发插入，这往往会造成严重的锁等待。因此，在实际应用开发中，尤其是并发插入比较多的应用，我们要尽量优化业务逻辑，尽量使用相等条件来访问更新数据，避免使用范围条件。 

##### InnoDB使用间隙锁的目的：

1. 防止幻读，以满足相关隔离级别的要求；
2. 满足恢复和复制的需要：

可以通过检查 InnoDB_row_lock 状态变量来分析系统上的行锁的争夺情况：

mysql> show status like 'innodb_row_lock%'; 
+-------------------------------+-------+ 
| Variable_name | Value | 
+-------------------------------+-------+ 
| InnoDB_row_lock_current_waits | 0 | 
| InnoDB_row_lock_time | 0 | 
| InnoDB_row_lock_time_avg | 0 | 
| InnoDB_row_lock_time_max | 0 | 
| InnoDB_row_lock_waits | 0 | 
+-------------------------------+-------+ 
5 rows in set (0.01 sec)

##### **LOCK TABLES 和 UNLOCK TABLES**

Mysql也支持lock tables和unlock tables，这都是在服务器层（MySQL Server层）实现的，和存储引擎无关，它们有自己的用途，并不能替代事务处理。 （除了禁用了autocommint后可以使用，其他情况不建议使用）： 

- LOCK TABLES 可以锁定用于当前线程的表。如果表被其他线程锁定，则当前线程会等待，直到可以获取所有锁定为止。 
- UNLOCK TABLES 可以释放当前线程获得的任何锁定。当前线程执行另一个 LOCK TABLES 时，或当与服务器的连接被关闭时，所有由当前线程锁定的表被隐含地解锁

##### **LOCK TABLES语法：**

- 在用 LOCK TABLES 对 InnoDB 表加锁时要注意，要将AUTOCOMMIT 设为 0，否则MySQL 不会给表加锁；
- 事务结束前，不要用 UNLOCK TABLES 释放表锁，因为 UNLOCK TABLES会隐含地提交事务；
- COMMIT 或 ROLLBACK 并不能释放用 LOCK TABLES 加的表级锁，必须用UNLOCK TABLES 释放表锁。

##### **一些优化锁性能的建议**

- 尽量使用较低的隔离级别； 
- 精心设计索引， 并尽量使用索引访问数据， 使加锁更精确， 从而减少锁冲突的机会
- 选择合理的事务大小，小事务发生锁冲突的几率也更小
- 给记录集显示加锁时，最好一次性请求足够级别的锁。比如要修改数据的话，最好直接申请排他锁，而不是先申请共享锁，修改时再请求排他锁，这样容易产生死锁
- 不同的程序访问一组表时，应尽量约定以相同的顺序访问各表，对一个表而言，尽可能以固定的顺序存取表中的行。这样可以大大减少死锁的机会
- 尽量用相等条件访问数据，这样可以避免间隙锁对并发插入的影响
- 不要申请超过实际需要的锁级别
- 除非必须，查询时不要显示加锁。 MySQL的MVCC可以实现事务中的查询不用加锁，优化事务性能；MVCC只在COMMITTED READ（读提交）和REPEATABLE READ（可重复读）两种隔离级别下工作
- 对于一些特定的事务，可以使用表锁来提高处理速度或减少死锁的可能

**乐观锁(Optimistic Lock)：**

假设不会发生并发冲突，只在提交操作时检查是否违反数据完整性。 乐观锁不能解决脏读的问题。

**悲观锁(Pessimistic Lock)：**

假定会发生并发冲突，屏蔽一切可能违反数据完整性的操作。

#### **事务的ACID**

典型的MySQL事务是如下操作的：

start transaction;
……  #一条或多条sql语句
commit;

**自动提交**

MySQL中默认采用的是自动提交（autocommit）模式。在自动提交模式下，如果没有start transaction显式地开始一个事务，那么每个sql语句都会被当做一个事务执行提交操作。

如果关闭了autocommit，则所有的sql语句都在一个事务中，直到执行了commit或rollback，该事务结束，同时开始了另外一个事务。

**特殊操作**

在MySQL中，存在一些特殊的命令，如果在事务中执行了这些命令，会马上强制执行commit提交事务；如DDL语句(create table/drop table/alter table)、lock tables语句等等。

不过，常用的select、insert、update和delete命令，都不会强制提交事务。

###### **原子性（Atomicity，或称不可分割性）**

原子性是指一个事务是一个不可分割的工作单位，其中的操作要么都做，要么都不做；如果事务中一个sql语句执行失败，则已执行的语句也必须回滚，数据库退回到事务前的状态。

###### **持久性（Durability）**

持久性是指事务一旦提交，它对数据库的改变就应该是永久性的。接下来的其他操作或故障不应该对其有任何影响。

###### **隔离性（Isolation）**

隔离性追求的是并发情形下事务之间互不干扰。简单起见，我们主要考虑最简单的读操作和写操作(加锁读等特殊读操作会特殊说明)，那么隔离性的探讨，主要可以分为两个方面：

- (一个事务)写操作对(另一个事务)写操作的影响：锁机制保证隔离性
- (一个事务)写操作对(另一个事务)读操作的影响：MVCC保证隔离性

**事务隔离级别**

**读未提交 READ UNCOMMITTED：**可以读取未提交的数据，未提交的数据称为脏数据，所以又称脏读。此时：幻读，不可重复读和脏读均允许；

**读已提交 READ COMMITTED：**只能读取已经提交的数据；此时：允许幻读和不可重复读，但不允许脏读，所以RC隔离级别要求解决脏读；

**可重复读 REPEATABLE READ：**同一个事务中多次执行同一个select,读取到的数据没有发生改变；此时：允许幻读，但不允许不可重复读和脏读，所以RR隔离级别要求解决不可重复读；

**可串行化 SERIALIZABLE:** 幻读，不可重复读和脏读都不允许，所以serializable要求解决幻读。

**MVCC（多版本并发控制）**

mysql中，默认的事务隔离级别是可重复读（repeatable-read），为了解决不可重复读，innodb采用了MVCC（多版本并发控制）来解决这一问题。

MVCC是利用在每条数据后面加了隐藏的两列（创建版本号和删除版本号），每个事务在开始的时候都会有一个递增的版本号

MVCC手段只适用于Msyql隔离级别中的读已提交（Read committed）和可重复读（Repeatable Read）

Read uncimmitted由于存在脏读，即能读到未提交事务的数据行，所以不适用MVCC。原因是MVCC的创建版本和删除版本只要在事务提交后才会产生。

串行化由于是会对所涉及到的表加锁，并非行锁，自然也就不存在行的版本控制问题。

MVCC主要作用于事务性的，有行锁控制的数据库模型。

概括来说，InnoDB实现的RR，通过锁机制（包含next-key lock）、MVCC（包括数据的隐藏列、基于undo log的版本链、ReadView）等，实现了一定程度的隔离性，可以满足大多数场景的需要。

不过需要说明的是，RR虽然避免了幻读问题，但是毕竟不是Serializable，不能保证完全的隔离。

###### **一致性（Consistency）**

一致性是指事务执行结束后，**数据库的完整性约束没有被破坏，事务执行的前后都是合法的数据状态。**数据库的完整性约束包括但不限于：实体完整性（如行的主键存在且唯一）、列完整性（如字段的类型、大小、长度要符合要求）、外键约束、用户自定义完整性（如转账前后，两个账户余额的和应该不变）。