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

@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        FormLayout formLayout = new FormLayout();
        formLayout.setSpacing(true);
        VerticalLayout mainPage = new VerticalLayout();

        //employeeComboBox
        Label label = new Label("Add Skill Set");
        ComboBox employeeComboBox = new ComboBox();
        for(Employee employeeItem:Crud.getEmployeeList())
            employeeComboBox.addItem(employeeItem.getCrmd());
        employeeComboBox.setInputPrompt("Employee");

        //assigneeComboBox
        ComboBox asigneeComboBox = new ComboBox();
        asigneeComboBox.setNullSelectionAllowed(false);
        asigneeComboBox.setInputPrompt("Assignee");


        employeeComboBox.setNullSelectionAllowed(false);
        employeeComboBox.addValueChangeListener(e -> {
            asigneeComboBox.removeAllItems();
            for (Employee next : Crud.getAssignee(String.valueOf(e.getProperty().getValue())))
                asigneeComboBox.addItems(next.getCrmd());

        });


        //parentComboBox
        ComboBox parentComboBox = new ComboBox();
        parentComboBox.setNullSelectionAllowed(false);
        parentComboBox.addItems(Crud.getSkillParent());
        parentComboBox.setInputPrompt("Skill Parent");


        //skilLvlComboBox
        ComboBox skillLvlComboBox = new ComboBox();
        skillLvlComboBox.setNullSelectionAllowed(false);
        skillLvlComboBox.setInputPrompt("Skill LVL");
        parentComboBox.addValueChangeListener(e -> {
            if(!asigneeComboBox.isEmpty()){
                skillLvlComboBox.removeAllItems();
                for (Skill next : Crud.getSkillLvlWiden(employeeComboBox.getValue().toString(), parentComboBox.getValue().toString(),asigneeComboBox.getValue().toString())) {
                    skillLvlComboBox.addItems(next.getName());
                    //System.out.println(next.getName());
                }
            }else
                Utilites.showErrNotification("Assignee is Empty: ","Please chose an assignee!!!");

        });

        //sendBtn
        Button sendBtn = new Button("SEND");
        sendBtn.setStyleName("btn");

        sendBtn.addClickListener(e->{
            if(employeeComboBox.isEmpty()) {
                Utilites.showErrNotification("Employee : ", "is empty");
                employeeComboBox.setStyleName("v-filterselect-error");
            }
            else if(asigneeComboBox.isEmpty()) {
                employeeComboBox.removeStyleName("v-filterselect-error");
                Utilites.showErrNotification("Asignee : ", "is empty");
                asigneeComboBox.setStyleName("v-filterselect-error");
            }
            else if(parentComboBox.isEmpty()){
                employeeComboBox.removeStyleName("v-filterselect-error");
                asigneeComboBox.removeStyleName("v-filterselect-error");
                Utilites.showErrNotification("Skill Parent : ","is empty");
                parentComboBox.setStyleName("v-filterselect-error");
            }
            else if(skillLvlComboBox.isEmpty()){
                employeeComboBox.removeStyleName("v-filterselect-error");
                asigneeComboBox.removeStyleName("v-filterselect-error");
                parentComboBox.removeStyleName("v-filterselect-error");
                Utilites.showErrNotification("Skill Level : ","is empty");
                skillLvlComboBox.setStyleName("v-filterselect-error");
            }
            else{
                Date today = new Date(new org.joda.time.DateTime().getYear(), new org.joda.time.DateTime().getMonthOfYear() - 1, new org.joda.time.DateTime().getDayOfMonth());

                employeeComboBox.removeStyleName("v-filterselect-error");
                asigneeComboBox.removeStyleName("v-filterselect-error");
                parentComboBox.removeStyleName("v-filterselect-error");
                skillLvlComboBox.removeStyleName("v-filterselect-error");
                Long id = Crud.getIdOfSkillset(employeeComboBox.getValue().toString(), parentComboBox.getValue().toString());

                Insert.updateSkillSet(id, Insert.getSkillByParent(parentComboBox.getValue().toString(), skillLvlComboBox.getValue().toString()) , today, asigneeComboBox.getValue().toString());
                Utilites.showTryNotification("Update With Success: ","This record is updated with success!");

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
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
