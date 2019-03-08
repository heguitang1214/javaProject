package datastructure.seqlist;

/**
 * @Author heguitang
 * @Date 2019/1/24 17:14
 * @Version 1.0
 * @Desc 顺序表测试
 */
public class Test {

    public static void main(String[] args) {

        SeqList<String> seqList = new SeqList<>();
        seqList.add("aaaa");
        seqList.add("bbbb");
        seqList.add("aaaa");

//        seqList.removeAll("aaaa");
        System.out.println(seqList.length());
        System.out.println(seqList.toString());


    }

}
