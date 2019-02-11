class qiaoGeli extends Cats {
    public static void main(String[] args) {
        qiaoGeli t1 = new qiaoGeli();
    }


}


class Print {
    Print() {
        System.out.println("haha");
    }
}

class Cats {

    static Print test1 = new Print();

    static {
        System.out.println("static Casts");
    }
}