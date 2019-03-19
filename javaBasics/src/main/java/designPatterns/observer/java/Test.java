package designPatterns.observer.java;

/**
 * 测试
 */
public class Test {
    public static void main(String[] args) {
        CurrentConditions mCurrentConditions;
        ForcastConditions mForcastConditions;
        WeatherData mWeatherData;

        mCurrentConditions = new CurrentConditions();
        mForcastConditions = new ForcastConditions();
        mWeatherData = new WeatherData();

        mWeatherData.addObserver(mCurrentConditions);
        mWeatherData.addObserver(mForcastConditions);
        mWeatherData.setData(30, 150, 40);

        System.out.println("=======================");

        mWeatherData.deleteObserver(mCurrentConditions);
        mWeatherData.setData(35, 150, 60);

    }
}
