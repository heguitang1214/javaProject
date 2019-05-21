hystrix支持将一个请求结果缓存起来，下一个具有相同key的请求将直接从缓存中取出结果，减少请求开销。
要使用hystrix cache功能，第一个要求是重写getCacheKey()，用来构造cache key；第二个要求是构建context，
如果请求B要用到请求A的结果缓存，A和B必须同处一个context。
通过HystrixRequestContext.initializeContext()和context.shutdown()可以构建一个context，
这两条语句间的所有请求都处于同一个context。

以demo的testWithCacheHits()为例，command2a、command2b、command2c同处一个context，
前两者的cache key都是"2HystrixCommand4RequestCacheTest"（见getCacheKey()），所以command2a执行完后把结果缓存，
command2b执行时就不走run()而是直接从缓存中取结果了，而command2c的cache key是"2NotCache"，
无法从缓存中取结果。此外，通过isResponseFromCache()可检查返回结果是否来自缓存。

