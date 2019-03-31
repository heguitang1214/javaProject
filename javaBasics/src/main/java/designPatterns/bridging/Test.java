package designPatterns.bridging;


import designPatterns.bridging.control.LGControl;
import designPatterns.bridging.control.SharpControl;
import designPatterns.bridging.control.SonyControl;

public class Test {
    public static void main(String[] args) {
        TvControl mLGTvControl;
        mLGTvControl = new TvControl(new LGControl());
        mLGTvControl.Onoff();
        mLGTvControl.nextChannel();
        mLGTvControl.nextChannel();
        mLGTvControl.preChannel();
        mLGTvControl.Onoff();

        TvControl mSonyTvControl;
        mSonyTvControl = new TvControl(new SonyControl());
        mSonyTvControl.Onoff();
        mSonyTvControl.preChannel();
        mSonyTvControl.preChannel();
        mSonyTvControl.preChannel();
        mSonyTvControl.Onoff();

        newTvControl mSharpTvControl;
        mSharpTvControl = new newTvControl(new SharpControl());
        mSharpTvControl.Onoff();
        mSharpTvControl.nextChannel();
        mSharpTvControl.setChannel(9);
        mSharpTvControl.setChannel(28);
        mSharpTvControl.Back();
        mSharpTvControl.Onoff();

    }


}
