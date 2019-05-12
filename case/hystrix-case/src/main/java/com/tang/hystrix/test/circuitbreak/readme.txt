熔断机制相当于电路的跳闸功能，举个栗子，我们可以配置熔断策略为当请求错误比例在10s内>50%时，该服务将进入熔断状态，
后续请求都会进入fallback。

以demo为例，我们通过withCircuitBreakerRequestVolumeThreshold配置10s内请求数超过3个时熔断器开始生效，
通过withCircuitBreakerErrorThresholdPercentage配置错误比例>50%时开始熔断，然后for循环执行execute()触发run()，
在run()里，如果name是小于10的偶数则正常返回，否则超时，通过多次循环后，超时请求占所有请求的比例将大于50%，
就会看到后续请求都不进入run()而是进入getFallback()，因为不再打印"running run():" + name了。

