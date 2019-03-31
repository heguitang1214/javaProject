package designPatterns.visitor;

/**
 * 雇员的抽象
 */
public abstract class Element {

    abstract public void Accept(Visitor visitor);

}
