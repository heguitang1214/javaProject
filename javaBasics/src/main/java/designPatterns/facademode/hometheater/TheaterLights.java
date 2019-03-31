package designPatterns.facademode.hometheater;

public class TheaterLights {

	private static TheaterLights instance = null;

	private TheaterLights() {

	}

	public static TheaterLights getInstance() {
		if (instance == null) {
			instance = new TheaterLights();
		}

		return instance;
	}

	public void on() {
		System.out.println("打开剧场灯光！");
	}

	public void off() {
		System.out.println("关闭剧场灯光！");
	}

	public void dim(int d) {
		System.out.println("剧场灯光明亮度为：" + d + "%");
	}

	public void bright() {
		dim(100);
		System.out.println("剧场灯光亮！");
	}
}
