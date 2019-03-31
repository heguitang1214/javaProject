package designPatterns.interpreter.cls;

import java.util.HashMap;

public abstract class SymbolExpresstion extends AbstractExpresstion {
    protected AbstractExpresstion left;
    protected AbstractExpresstion right;

    public SymbolExpresstion(AbstractExpresstion _left,
                             AbstractExpresstion _right) {
        this.left = _left;

        this.right = _right;
    }

}
