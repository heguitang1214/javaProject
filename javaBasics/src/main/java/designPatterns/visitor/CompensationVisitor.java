package designPatterns.visitor;

public class CompensationVisitor implements Visitor {

    @Override
    public void visit(Element element) {
        Employee employee = ((Employee) element);

        System.out.println(employee.getName() + "'s Compensation is "
                + (employee.getDegree() * employee.getVacationDays() * 10));
    }

}
