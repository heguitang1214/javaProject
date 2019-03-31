package designPatterns.visitor;

import java.util.HashMap;

public class Employees {
    private HashMap<String, Employee> employees;

    public Employees() {
        employees = new HashMap();
    }

    public void Attach(Employee employee) {
        employees.put(employee.getName(), employee);
    }

    public void Detach(Employee employee) {
        employees.remove(employee);
    }

    public Employee getEmployee(String name) {
        return employees.get(name);
    }

    public void Accept(Visitor visitor) {
        for (Employee e : employees.values()) {
            e.Accept(visitor);
        }
    }
}
