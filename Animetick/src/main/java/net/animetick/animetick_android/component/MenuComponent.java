package net.animetick.animetick_android.component;

/**
 * Created by kazz on 2013/09/26.
 */
public class MenuComponent {

    private Button menuButton;
    private MenuPanel menuPanel;

    public MenuComponent(Button menuButton, MenuPanel menuPanel) {
        this.menuButton = menuButton;
        this.menuPanel = menuPanel;
//        this.menuButton


//        this.menuButton = new Button(menuButtonResource.getView(), new OnClickEvent() {
//            @Override
//            public boolean onClick() {
//                boolean isSuccess = menuButtonResource.getCallback().onClick();
//                if (isSuccess) {
//                    menuPanel.switchOpen();
//                }
//                return isSuccess;
//            }
//
//            @Override
//            public void onSuccess() {
//
//            }
//
//            @Override
//            public void onFailure() {
//
//            }
//        }, menuButtonResource.getTransitionData());
        //this.menuPanel = new MenuPanel(panelResource);
    }

    public void close() {
        menuPanel.close();
    }

}
