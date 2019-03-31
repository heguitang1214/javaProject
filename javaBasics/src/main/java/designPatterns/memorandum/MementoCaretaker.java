package designPatterns.memorandum;

import java.util.HashMap;

public class MementoCaretaker {
    private HashMap<String, MementoIF> mementomap;

    public MementoCaretaker() {
        mementomap = new HashMap<String, MementoIF>();
    }

    public MementoIF retrieveMemento(String name) {
        return mementomap.get(name);
    }

    /**
     * 备忘录赋值方法
     */
    public void saveMemento(String name, MementoIF memento) {
        this.mementomap.put(name, memento);
    }
}
