搜索 	command 鼠标点击关键字

###### 需求流程

​	Resource写api（controller）	

​	service写逻辑（view）（可以没有）

​	repository写数据操作（model）（底层数据库增删改查）

​	swagger测试	

​	git提交pr

master include changelog

重新启动项目之前要先 kill -9 $(lsof -i:8080 -t) 