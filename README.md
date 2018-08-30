# springall
spring技术选型集成，不同分支代表不同技术选型，集成了常用技术的spring分支，不再零散。

## canal分支

基于阿里开源的数据库订阅组件来获取DB变更，从而触发一些数据异构、同步、更新的任务。

`canal client`客户端的使用参见代码，需要并发多任务时可以投递到`MessageQueue`中。

