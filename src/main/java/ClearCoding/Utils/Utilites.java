package ClearCoding.Utils;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
//
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


    public static Notification showNotification(String caption, String description, Notification.Type type){
        Notification notification = new Notification(caption, description, type);
        notification.setDelayMsec(2000);
        notification.setPosition(Position.TOP_RIGHT);
        notification.show(Page.getCurrent());
        return notification;
    }

    public static long toLong(String input){
        return Long.parseLong(input);
    }

    public static int getEmployeeRank(String position){
        int rank=0;
        switch (position) {
            case "JD":
                rank = 1;
                break;
            case "MD":
                rank = 2;
                break;
            case "AD":
                rank = 3;
                break;
            case "SD":
                rank = 4;
                break;
            default:
                rank = 0;
        }
        return rank;
    }
}
