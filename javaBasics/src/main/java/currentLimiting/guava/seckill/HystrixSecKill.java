package currentLimiting.guava.seckill;

import com.netflix.hystrix.*;

/**
 * @Author 无双老师
 * @Date 2018/9/28
 * @Description Hystrix秒杀限流：商品瞬间被秒，不能达到较好的秒杀效果
 */
public class HystrixSecKill extends HystrixCommand<String> {

    private final String name;

    public HystrixSecKill(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HystrixSecKillGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("HystrixSecKill"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("HystrixSecKill"))
                .andCommandPropertiesDefaults(
                	HystrixCommandProperties.Setter()
                		.withExecutionTimeoutInMilliseconds(5000)
                )
                .andThreadPoolPropertiesDefaults(
                	HystrixThreadPoolProperties.Setter()
						//允许同时有10个线程进入秒杀
                		.withCoreSize(10)
                )
        );
        this.name = name;
    }

	/**
	 * 正常的业务逻辑
	 * @return
	 * @throws Exception
	 */
	@Override
    protected String run() throws Exception {
		if (CountUtils.TOTAL_COUNT.get() > 0) {
			//库存减1
			CountUtils.decrease();
			System.out.println("恭喜您，秒杀成功！！！");
		} else {
			System.out.println("秒杀失败，商品已售完");
		}

		return name;
    }

	/**
	 * 降级逻辑
	 * @return
	 */
	@Override
    protected String getFallback() {
		System.out.println("秒杀失败，请继续努力~");
        return "秒杀失败，请继续努力~";
    }


	public static void main(String[] args) throws InterruptedException {
		for(int i = 0; i < 100; i++) {
			try {
				new HystrixSecKill(String.valueOf(i)).queue();
			} catch(Exception e) {
				System.out.println("run()抛出HystrixBadRequestException时，被捕获到这里" + e.getCause());
			}
		}

		Thread.sleep(5000);
	}

}