package ClearCoding.Utils;

import ClearCoding.Entity.Employee;
import ClearCoding.Entity.Skill;
import ClearCoding.Entity.Skill_Set;
import com.vaadin.ui.Notification;
import org.hibernate.Session;

import java.util.Date;

public class Insert {

    public static void insertSkillSet(Long id, Employee employee, Skill skill, Date assignedDate, String assignee){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Skill_Set skill_set = new Skill_Set(id, employee, skill, assignedDate, assignee);

        session.save(skill_set);

        session.getTransaction().commit();
        session.close();
    }

    public static void updateSkillSet(Long id, Employee employee, Skill skill, Date assignedDate, String assignee){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Skill_Set skill_set = (Skill_Set) session.get(Skill_Set.class, id);

        skill_set.setEmployeeByCrmd(employee);
        skill_set.setSkillBySkillId(skill);
        skill_set.setAssignedDate(assignedDate);
        skill_set.setAssigneeId(assignee);

        Notification.show("Update: ","This record is updated with success!" ,Notification.Type.TRAY_NOTIFICATION);

        session.update(skill_set);

        session.getTransaction().commit();
        session.close();
    }

}
