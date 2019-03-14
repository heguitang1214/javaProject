package com.demo.hystrix.test.observablecommand;

import com.demo.hystrix.test.firsthelloworld.HelloWorldCommand;
import rx.Observable;
import rx.Observer;

/**
 * 注册异步事件回调执行
 *
 * 执行顺序：1.监听依赖项结果  2.onNext事件  3.onCompleted事件
 */
public class HelloWorldObservableCommand {
    public static void main(String[] args) {
        //注册观察者事件拦截
        Observable<String> observe = new HelloWorldCommand("HelloWorldObservableCommandCall").observe();

        //注册结果回调事件
        observe.subscribe(result -> {
            //执行结果处理,result 为HelloWorldCommand返回的结果
            //用户对结果做二次处理.
            System.out.printf("拿到执行结果，可以进一步处理！%s%n", result);

        });

        //注册完整执行生命周期事件
        observe.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                // onNext/onError完成之后最后回调
                System.out.println("onCompleted 链路执行结束");
            }

            @Override
            public void onError(Throwable e) {
                // 当产生异常时回调
                System.out.println("onError called");
                e.printStackTrace();
            }

            @Override
            public void onNext(String v) {
                // 获取结果后回调
                System.out.println("onNext拿到结果后的下一步操作: " + v);
            }
        });
    }
}
