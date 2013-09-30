package net.animetick.animetick_android.component;

/**
 * Created by kazz on 2013/09/26.
 */
public interface OnClickEvent {

    public boolean isAsync();

    public boolean onClick();

    public void onSuccess();

    public void onFailure();

}
