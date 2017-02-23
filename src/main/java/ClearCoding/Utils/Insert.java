package ClearCoding.Utils;

import ClearCoding.Entity.*;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

public class Insert {

    public static Skill getSkillByParent(String parent, String name){
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Skill> skills = session.createQuery("from Skill").list();
        Skill returnedSkill = new Skill();

        for(Skill next: skills){
            if(next.getParentId()!=null)
                if(next.getParentId()==Crud.getIdOfParent(parent) && next.getName().equals(name))
                    returnedSkill = next;
        }
        session.close();

        return returnedSkill;
    }
    public static void updateSkillSet(Long id, Skill skill ,Date assignedDate, String assignee){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Skill_Set skill_set = (Skill_Set) session.get(Skill_Set.class, id);
        skill_set.setSkillBySkillId(skill);
        skill_set.setAssignedDate(assignedDate);
        skill_set.setAssigneeId(assignee);

        session.update(skill_set);

        session.getTransaction().commit();
        session.close();
    }

}
