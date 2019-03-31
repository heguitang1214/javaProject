package designPatterns.interpreter.cls;

import java.util.HashMap;

public class VarExpresstion extends AbstractExpresstion {
    private String key;

    public VarExpresstion(String _key) {

        this.key = _key;

    }

    @Override
    public Float interpreter(HashMap<String, Float> var) {
        // TODO Auto-generated method stub
        return var.get(this.key);
    }

}
