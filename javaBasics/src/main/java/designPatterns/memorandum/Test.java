package designPatterns.memorandum;

public class Test {

    public static void main(String[] args) {
        MementoCaretaker mMementoCaretaker = new MementoCaretaker();
        Originator mOriginator = new Originator();
        Originator2 mOriginator2 = new Originator2();

        System.out.println("*****Originator*****");
        mOriginator.testState1();
        mMementoCaretaker
                .saveMemento("Originator", mOriginator.createMemento());
        mOriginator.showState();
        mOriginator.testState2();
        mOriginator.showState();
        mOriginator.restoreMemento(mMementoCaretaker
                .retrieveMemento("Originator"));
        mOriginator.showState();

        System.out.println("*****Originator 2*****");
        mOriginator2.testState1();
        mOriginator2.showState();
        mMementoCaretaker.saveMemento("Originator2",
                mOriginator2.createMemento());
        mOriginator2.testState2();
        mOriginator2.showState();
        mOriginator2.restoreMemento(mMementoCaretaker
                .retrieveMemento("Originator2"));
        mOriginator2.showState();

        //System.out.println("*****Originator&&Originator 2*****");
        //	 mOriginator.restoreMemento(mMementoCaretaker
        //	 .retrieveMemento("Originator2"));
        //	 mOriginator.showState();

    }

}
