package designPatterns.observer.java;

import java.util.Observable;

public class WeatherData extends Observable {
    private float mTemperatrue;
    private float mPressure;
    private float mHumidity;

    public float getTemperature() {
        return mTemperatrue;
    }

    public float getPressure() {
        return mPressure;

    }

    public float getHumidity() {
        return mHumidity;
    }


    public void dataChange() {
        this.setChanged();
        this.notifyObservers(new Data(getTemperature(), getPressure(), getHumidity()));

    }


    public void setData(float mTemperatrue, float mPressure, float mHumidity) {
        this.mTemperatrue = mTemperatrue;
        this.mPressure = mPressure;
        this.mHumidity = mHumidity;
        dataChange();
    }

    public class Data {
        public float mTemperatrue;
        public float mPressure;
        public float mHumidity;

        public Data(float mTemperatrue, float mPressure, float mHumidity) {
            this.mTemperatrue = mTemperatrue;
            this.mPressure = mPressure;
            this.mHumidity = mHumidity;
        }
    }

}
