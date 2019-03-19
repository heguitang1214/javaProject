package designPatterns.observer.demo;


public class Test {

    public static void main(String[] args) {

        CurrentConditions mCurrentConditions;
        ForcastConditions mForcastConditions;
        WeatherDataSt mWeatherDataSt;

        mWeatherDataSt = new WeatherDataSt();
        mCurrentConditions = new CurrentConditions();
        mForcastConditions = new ForcastConditions();

        mWeatherDataSt.registerObserver(mCurrentConditions);
        mWeatherDataSt.registerObserver(mForcastConditions);

        mWeatherDataSt.setData(30, 150, 40);

        System.out.println("=============================================");

        mWeatherDataSt.removeObserver(mCurrentConditions);
        mWeatherDataSt.setData(40, 250, 50);
    }

}
