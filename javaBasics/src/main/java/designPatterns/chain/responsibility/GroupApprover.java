package designPatterns.chain.responsibility;

public class GroupApprover extends Approver {

	public GroupApprover(String Name) {
		super(Name+" GroupLeader");

	}

	@Override
	public void ProcessRequest(PurchaseRequest request) {
		if (request.GetSum() < 5000) {
			System.out.println("**This request " + request.GetID()
					+ " will be handled by "
					+ this.Name + " **");
		} else {
			successor.ProcessRequest(request);
		}
	}

}
