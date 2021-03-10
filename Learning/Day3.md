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
