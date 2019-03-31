package designPatterns.facademode;


import designPatterns.facademode.hometheater.HomeTheaterFacade;

public class Test {
	public static void main(String[] args) {
		HomeTheaterFacade mHomeTheaterFacade=new HomeTheaterFacade();
		
		mHomeTheaterFacade.ready();
		System.out.println("==================");
		mHomeTheaterFacade.play();
	}
}
