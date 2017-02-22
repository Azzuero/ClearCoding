package ClearCoding.Utils;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;

public class Utilites {


    public static boolean numberOrNot(String input){
        try
        {
            Integer.parseInt(input);
        }
        catch(NumberFormatException ex)
        {
            return false;
        }
        return true;
    }




    public static Notification showErrNotification(String caption, String description){
        Notification notification = new Notification(caption, description, Notification.Type.ERROR_MESSAGE);
        notification.setDelayMsec(2000);
        notification.setPosition(Position.TOP_RIGHT);
        notification.show(Page.getCurrent());
        return notification;
    }
    public static Notification showTryNotification(String caption, String description){
        Notification notification = new Notification(caption, description, Notification.Type.TRAY_NOTIFICATION);
        notification.setDelayMsec(2000);
        notification.setPosition(Position.TOP_RIGHT);
        notification.setStyleName("notify");
        notification.show(Page.getCurrent());
        return notification;
    }
}
