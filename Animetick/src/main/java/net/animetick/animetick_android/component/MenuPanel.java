package net.animetick.animetick_android.component;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by kazz on 2013/09/26.
 */
public class MenuPanel {

    private boolean isOpen = false;
    private View panelLayout;
    private ObjectAnimator transX;
//    private List<Button> buttonList = new ArrayList<Button>();

//    public MenuPanel(PanelResource panelResource) {
//        this.panelLayout = panelResource.getPanelLayout();
//        this.setupTransition();
//        for (final ButtonResource buttonResource : panelResource.getButtonResourceList()) {
//            buttonList.add(new Button(buttonResource.getView(), new OnClickEvent() {
//                @Override
//                public boolean onClick() {
//                    buttonResource.getCallback().onClick();
//                    return false;
//                }
//            }, buttonResource.getTransitionData()));
//        }
//    }
    public MenuPanel(View panelLayout) {
        this.panelLayout = panelLayout;
        setupTransition();
    }

    private void setupTransition() {
        this.transX = ObjectAnimator.ofFloat(this.panelLayout, "translationX", 0,
                                             -this.panelLayout.getMeasuredWidth());
        this.transX.setDuration(150);
        this.transX.setInterpolator(new AccelerateInterpolator(5));
    }

    public void switchOpen() {
        if (isOpen) {
            close();
        } else {
            open();
        }
    }

    public void open() {
        transX.start();
        isOpen = true;
    }

    public void close() {
        transX.reverse();
        isOpen = false;
    }

}
