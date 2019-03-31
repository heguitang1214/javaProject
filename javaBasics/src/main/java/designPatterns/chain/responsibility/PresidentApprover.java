package designPatterns.chain.responsibility;

public class PresidentApprover extends Approver {

    public PresidentApprover(String Name) {
        super(Name + " President");

    }

    @Override
    public void ProcessRequest(PurchaseRequest request) {
        if (50000 <= request.GetSum()) {
            System.out.println("**This request " + request.GetID()
                    + " will be handled by " + this.Name + " **");
        } else {
            successor.ProcessRequest(request);
        }
    }

}
