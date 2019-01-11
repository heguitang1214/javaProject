package ai.yunxi.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ComputeService {
	public static final Logger log = LoggerFactory.getLogger(ComputeService.class);

	public int compute(int m) {
		log.info("=============> invoke compute");
		int ret = 0;

		if (log.isDebugEnabled())
			log.debug("=============> " + m);

		try {
			// 1.做计算之前没有判断
			ret = 1 / m;
		} catch (ArithmeticException e) {
			// 2.生吞异常
		}

		return ret;
	}
}
