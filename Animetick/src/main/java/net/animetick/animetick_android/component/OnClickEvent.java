package net.animetick.animetick_android.component;

/**
 * Created by kazz on 2013/09/26.
 */
public class OnClickEvent {
    protected boolean isAsync = false;

    public OnClickEvent() {}

    public OnClickEvent(boolean isAsync) {
        this.isAsync = isAsync;
    }

    public boolean isAsync() {
        return isAsync;
    }

    public boolean onClick() {
        return true;
    }

    public void onSuccess() {}

    public void onFailure() {}

}
