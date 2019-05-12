非HystrixBadRequestException异常：以demo为例，当抛出HystrixBadRequestException时，调用程序可以捕获异常，
没有触发getFallback()，而其他异常则会触发getFallback()，调用程序将获得getFallback()的返回


run()/construct()运行超时：以demo为例，使用无限while循环或sleep模拟超时，触发了getFallback()




