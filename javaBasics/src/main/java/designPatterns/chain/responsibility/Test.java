package designPatterns.chain.responsibility;


public class Test {

    public static void main(String[] args) {

        Client mClient = new Client();
        Approver GroupLeader = new GroupApprover("组长：Tom");
        Approver DepartmentLeader = new DepartmentApprover("部长：Jerry");
        Approver VicePresident = new VicePresidentApprover("副总：Kate");
        Approver President = new PresidentApprover("总裁：Bush");

        GroupLeader.SetSuccessor(VicePresident);
        DepartmentLeader.SetSuccessor(President);
        VicePresident.SetSuccessor(DepartmentLeader);
        President.SetSuccessor(GroupLeader);

        VicePresident.ProcessRequest(mClient.sendRequst(1, 100, 40));
        VicePresident.ProcessRequest(mClient.sendRequst(2, 200, 40));
        VicePresident.ProcessRequest(mClient.sendRequst(3, 300, 40));
        VicePresident.ProcessRequest(mClient.sendRequst(4, 400, 140));

    }


}
