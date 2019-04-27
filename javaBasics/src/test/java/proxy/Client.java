package proxy;

class Client{
    public static void main(String[] args) {
        ProxyDemo dnm1 = new ProxyDemo(new Xiaoxiannv());
        Teacher teacher = (Teacher) dnm1.getProxyInstance();
        teacher.xiaoxiannvTeach();

        System.out.println();
        System.out.println("=========我是分割线==========");
        System.out.println();

        ProxyDemo dnm2 = new ProxyDemo(new Tang());
        JavaTeacher javaTeacher = (JavaTeacher) dnm2.getProxyInstance();
        javaTeacher.javaTeacher();

    }
}