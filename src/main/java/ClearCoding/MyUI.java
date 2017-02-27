package ClearCoding;

import ClearCoding.Entity.Employee;
import ClearCoding.Entity.Skill;
import ClearCoding.Utils.Crud;
import ClearCoding.Utils.Insert;
import ClearCoding.Utils.Utilites;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;
import java.util.Date;
//
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        FormLayout formLayout = new FormLayout();
        formLayout.setSpacing(true);
        VerticalLayout mainPage = new VerticalLayout();
        Label label = new Label("Add Skill Set");

            //employeeComboBox
            ComboBox employeeComboBox = new ComboBox();
            for(Employee employeeItem:Crud.getEmployeeList())
                employeeComboBox.addItem(employeeItem.getCrmd());
            employeeComboBox.setInputPrompt("Employee");
            employeeComboBox.setNullSelectionAllowed(false);

            employeeComboBox.addValueChangeListener(e -> {
                employeeComboBox.removeStyleName("v-filterselect-error");
            });

            //assigneeComboBox
            ComboBox asigneeComboBox = new ComboBox();
            asigneeComboBox.setNullSelectionAllowed(false);
            asigneeComboBox.setInputPrompt("Assignee");


            employeeComboBox.addValueChangeListener(e -> {
                asigneeComboBox.removeStyleName("v-filterselect-error");
                asigneeComboBox.removeAllItems();
                for (Employee next : Crud.getAssignee(String.valueOf(e.getProperty().getValue())))
                    asigneeComboBox.addItems(next.getCrmd());

            });

           asigneeComboBox.addValueChangeListener(e -> {
                asigneeComboBox.removeStyleName("v-filterselect-error");
           });


            //parentComboBox
            ComboBox parentComboBox = new ComboBox();
            parentComboBox.setNullSelectionAllowed(false);
            parentComboBox.addItems(Crud.getSkillParent());
            parentComboBox.setInputPrompt("Skill Parent");
            parentComboBox.addValueChangeListener(e -> {
                parentComboBox.removeStyleName("v-filterselect-error");
            });


            //skilLvlComboBox
            ComboBox skillLvlComboBox = new ComboBox();
            skillLvlComboBox.setNullSelectionAllowed(false);
            skillLvlComboBox.setInputPrompt("Skill LVL");
            parentComboBox.addValueChangeListener(e -> {
                if(!asigneeComboBox.isEmpty()){
                    skillLvlComboBox.removeStyleName("v-filterselect-error");
                    skillLvlComboBox.removeAllItems();
                    for (Skill next : Crud.getSkillLvlWiden(employeeComboBox.getValue().toString(), parentComboBox.getValue().toString(),asigneeComboBox.getValue().toString())) {
                        skillLvlComboBox.addItems(next.getName());
                    }
                }else
                    Utilites.showNotification("Assignee is Empty: ","Please chose an assignee!!!", Notification.Type.ERROR_MESSAGE);

            });

        //sendBtn//
            Button sendBtn = new Button("SEND");
            sendBtn.setStyleName("btn");

            sendBtn.addClickListener(e->{
                if(employeeComboBox.isEmpty()) {
                    Utilites.showNotification("Employee : ", "is empty",Notification.Type.ERROR_MESSAGE);
                    employeeComboBox.setStyleName("v-filterselect-error");
                }
                else if(asigneeComboBox.isEmpty()) {
                    Utilites.showNotification("Asignee : ", "is empty",Notification.Type.ERROR_MESSAGE);
                    asigneeComboBox.setStyleName("v-filterselect-error");
                }
                else if(parentComboBox.isEmpty()){
                    Utilites.showNotification("Skill Parent : ","is empty",Notification.Type.ERROR_MESSAGE);
                    parentComboBox.setStyleName("v-filterselect-error");
                }
                else if(skillLvlComboBox.isEmpty()){
                    Utilites.showNotification("Skill Level : ","is empty", Notification.Type.ERROR_MESSAGE);
                    skillLvlComboBox.setStyleName("v-filterselect-error");
                }
                else{
                    Date today = new Date(new org.joda.time.DateTime().getYear(), new org.joda.time.DateTime().getMonthOfYear() - 1, new org.joda.time.DateTime().getDayOfMonth());

                    Long id = Crud.getIdOfSkillset(employeeComboBox.getValue().toString(), parentComboBox.getValue().toString());

                    Insert.updateSkillSet(id, Insert.getSkillByParent(parentComboBox.getValue().toString(), skillLvlComboBox.getValue().toString()) , today, asigneeComboBox.getValue().toString());
                    Utilites.showNotification("Update With Success: ","This record is updated with success!", Notification.Type.TRAY_NOTIFICATION);

                    skillLvlComboBox.removeAllItems();
                    for (Skill next : Crud.getSkillLvlWiden(employeeComboBox.getValue().toString(), parentComboBox.getValue().toString(),asigneeComboBox.getValue().toString())) {
                        skillLvlComboBox.addItems(next.getName());
                    }

                }
            });

        formLayout.addComponents(label, employeeComboBox, asigneeComboBox, parentComboBox,  skillLvlComboBox, sendBtn);
        mainPage.addComponents(formLayout);
        mainPage.setMargin(true);
        mainPage.setSpacing(true);
        setContent(mainPage);
        //finita la comedia
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
