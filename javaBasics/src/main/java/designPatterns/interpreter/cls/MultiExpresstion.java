package designPatterns.interpreter.cls;

import java.util.HashMap;

public class MultiExpresstion extends SymbolExpresstion {

	public MultiExpresstion(AbstractExpresstion _left,
			AbstractExpresstion _right) {
		super(_left, _right);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Float interpreter(HashMap<String, Float> var) {
		// TODO Auto-generated method stub
		return super.left.interpreter(var) * super.right.interpreter(var);
	}

}
