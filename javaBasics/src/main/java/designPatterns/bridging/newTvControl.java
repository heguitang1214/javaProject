package designPatterns.bridging;

import designPatterns.bridging.control.Control;

public class newTvControl extends TvControlabs {
    private int ch = 0;
    private boolean ison = false;
    private int prech = 0;

    public newTvControl(Control mControl) {
        super(mControl);
    }

    @Override
    public void Onoff() {
        if (ison) {
            ison = false;
            mControl.Off();
        } else {
            ison = true;
            mControl.On();
        }

    }

    @Override
    public void nextChannel() {
        prech = ch;
        ch++;
        mControl.setChannel(ch);

    }

    @Override
    public void preChannel() {
        prech = ch;
        ch--;
        if (ch < 0) {
            ch = 200;
        }
        mControl.setChannel(ch);

    }


    public void setChannel(int nch) {
        prech = ch;
        ch = nch;
        mControl.setChannel(ch);

    }

    public void Back() {
        mControl.setChannel(prech);
    }
}
