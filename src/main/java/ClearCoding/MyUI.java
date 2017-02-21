package ClearCoding;

import javax.servlet.annotation.WebServlet;

import ClearCoding.Entity.*;
import ClearCoding.Utils.Crud;
import ClearCoding.Utils.HibernateUtil;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import org.hibernate.Session;

import javax.servlet.annotation.WebServlet;
import java.util.List;




import java.util.List;

@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        FormLayout formLayout = new FormLayout();
        formLayout.setSpacing(true);
        VerticalLayout Principal_Page = new VerticalLayout();
        Label EmployeeLable = new Label("CRMD");

        ComboBox comboAsignee = new ComboBox();

        Label comboAsigneelabel = new Label("Asignee CRMD");
        comboAsignee.setNullSelectionAllowed(false);
        comboAsignee.setStyleName("center input");
        comboAsignee.setInputPrompt("\uF2C0 Username");

        DateField dateSkill = new DateField();
        ComboBox Employee = Crud.getEmployee();
        Employee.setNullSelectionAllowed(false);
        Employee.addValueChangeListener(e -> {
            comboAsignee.removeAllItems();
            for (Employee next : Crud.getAssignee(String.valueOf(e.getProperty().getValue())))
                comboAsignee.addItems(next.getCrmd());

        });
        ComboBox comboParent = new ComboBox();
        comboParent.setNullSelectionAllowed(false);
        comboParent.addItems(Crud.getSkillParent());
        Label comboParentlabel = new Label("Skill Parent");

        ComboBox combolvl = new ComboBox();
        Label combolvlLabel = new Label("Skill Lavel");

        combolvl.setNullSelectionAllowed(false);
        comboParent.addValueChangeListener(e -> {
            combolvl.removeAllItems();
            for (Skill next : Crud.getSkillLevel(String.valueOf(e.getProperty().getValue()))) {
                combolvl.addItems(next.getName());
                System.out.println(next.getName());
            }

        });
        Button send = new Button("Send");
        send.setStyleName("btn");


        formLayout.addComponents( EmployeeLable, Employee, comboAsigneelabel,comboAsignee, dateSkill, comboParentlabel, comboParent, combolvlLabel, combolvl, send);
        Principal_Page.addComponents(formLayout);
        Principal_Page.setMargin(true);
        Principal_Page.setSpacing(true);
        session.close();
        setContent(Principal_Page);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
