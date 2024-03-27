# aoyou-entertainment

**遨游桌游密室平台**

项目简介：这是一个为桌游和密室爱好者提供的服务平台。项目基础架构采用最新的技术选型（Java 17 + Spring Boot 3 + Spring Cloud 2022），核心业务包括：密室和桌游店铺的搜索、相关周边商品的在线购买、珍品的限时秒杀等。底层采用缓存、消息队列以及分库分表等技术支持海量用户访问以及数据存储。

技术架构：Spring Boot、Spring Cloud、Spring Security、MyBatis-Plus、MySQL、Redis、RabbitMQ等中间件

项目地址：https://github.com/THU-wsy/aoyou-entertainment

项目亮点：

- 整体采用**分布式微服务架构**，使用 Nacos 作服务注册和配置中心，使用 Gateway 进行请求转发和过滤，微服务之间使用 OpenFeign 进行远程调用。
- 使用 Spring Security 框架**整合 JWT 技术**完成认证授权的功能，采用白名单方式将登录用户信息保存到 Redis 并返回 token 给用户，实现**单点登录**后即可访问所有微服务。
- 使用 Redis 来缓存桌游店、密室店、商品详情等高频访问数据，并且采用合理的策略解决了高并发读场景下可能发生的**缓存穿透、缓存击穿、缓存雪崩问题**。
- 通过旁路缓存模式，即先更新数据库再删除缓存的策略，保证了 MySQL 与 Redis 之间的**数据最终一致性**，其中删除缓存的步骤由消息队列 RabbitMQ 确保成功。
- 通过 **Redis Lua 脚本原子特性**，完成珍品秒杀时查询库存、一人一单校验、扣减库存的功能。
- 通过 **RabbitMQ 的延迟队列**，完成用户下单15分钟后未支付情况下取消订单的功能。
- 对于店铺和商品的搜索功能，使用 Elasticsearch 实现全文检索
