package ClearCoding;

import ClearCoding.Entity.Employee;
import ClearCoding.Entity.Skill;
import ClearCoding.Utils.Crud;
import ClearCoding.Utils.Utilites;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;

@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        FormLayout formLayout = new FormLayout();
        formLayout.setSpacing(true);
        VerticalLayout mainPage = new VerticalLayout();

        //employeeComboBox
        Label employeeLable = new Label("CRMD");
        ComboBox employeeComboBox = new ComboBox();
        for(Employee employeeItem:Crud.getEmployeeList())
            employeeComboBox.addItem(employeeItem.getCrmd());
        employeeComboBox.setInputPrompt("Employee");

        //assigneeComboBox
        Label comboAsigneelabel = new Label("Asignee CRMD");
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
        Label parentLabel = new Label("Skill Parent");
        ComboBox parentComboBox = new ComboBox();
        parentComboBox.setNullSelectionAllowed(false);
        parentComboBox.addItems(Crud.getSkillParent());

        //skilLvlComboBox
        Label skillLvlLabel = new Label("Skill Lavel");
        ComboBox skillLvlComboBox = new ComboBox();
        skillLvlComboBox.setNullSelectionAllowed(false);
        parentComboBox.addValueChangeListener(e -> {
            skillLvlComboBox.removeAllItems();
            for (Skill next : Crud.getSkillLvlWiden(employeeComboBox.getValue().toString(), parentComboBox.getValue().toString(), asigneeComboBox.getValue().toString())) {
                skillLvlComboBox.addItems(next.getName());
                //System.out.println(next.getName());
            }
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
                this.removeStyleName("v-filterselect-error");
                Utilites.showTryNotification("Click: ","This record is updated with success!");
            }
        });

        formLayout.addComponents(employeeLable, employeeComboBox, comboAsigneelabel, asigneeComboBox, parentLabel, parentComboBox, skillLvlLabel, skillLvlComboBox, sendBtn);
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
