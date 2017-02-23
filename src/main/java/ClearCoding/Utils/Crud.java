package ClearCoding.Utils;

import ClearCoding.Entity.*;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ClearCoding.Utils.Utilites.numberOrNot;

public class Crud {

    public static List<Employee> getEmployeeList() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Employee> allEmployees = session.createCriteria(Employee.class).list();

        session.close();
        return allEmployees;
    }

    public static List<Employee> getAssignee(String CRMD) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Employee> getallemployee = session.createQuery("from Employee").list();
        Employee employee = new Employee();
        for(Employee next: getallemployee){
            if(next.getCrmd().equals(CRMD))
                employee = next;
        }

        List<Employee> assignees = new ArrayList<Employee>(0);
        for(Employee next: getallemployee){
            if(next.getAmbient().equals(employee.getAmbient())){
                if(Utilites.getEmployeeRank(next.getPosition()) > Utilites.getEmployeeRank(employee.getPosition()))
                    assignees.add(next);
                else if (Utilites.getEmployeeRank(next.getPosition()) == 4 && Utilites.getEmployeeRank(employee.getPosition()) == 4){
                    if (!next.getCrmd().equals(employee.getCrmd()))
                        assignees.add(next);
                }
            }
        }

        session.close();
        return assignees;
    }



    public static List<String> getSkillParent() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Skill> inputSkills = session.createQuery("from Skill").list();
        List<Skill> input1Skills = session.createQuery("from Skill").list();

        List<String> outputSkils = new ArrayList<String>(0);
        for (Skill next: inputSkills)
            for(Skill next1:input1Skills){
                if(numberOrNot(next.getName()))
                    if(next.getParentId()==next1.getId()){
                        outputSkils.add(next1.getName());
                    }
            }
        Set<String> hs = new HashSet<>();
        hs.addAll(outputSkils);
        outputSkils.clear();
        outputSkils.addAll(hs);
        session.close();
        return outputSkils;
    }


    public static long getIdOfParent(String parent){
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Skill> skills = session.createQuery("from Skill").list();
        Long parentId = 0L;
        for(Skill next: skills){
            if(next.getName().equals(parent)) parentId = next.getId();
        }
        session.close();
        return parentId;
    }

    public static Long getCurrentSkill(String crmd, Long parent){
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Skill_Set> skill_set = session.createQuery("from Skill_Set").list();

        long currentSkil = 0;

        for(Skill_Set next: skill_set){
            if(next.getEmployeeByCrmd().getCrmd().equals(crmd))
                if(next.getSkillBySkillId().getParentId() == parent)
                    currentSkil = Utilites.toLong(next.getSkillBySkillId().getName());
        }


        session.close();
        return currentSkil;
    }

    public static List<Skill> getSkillLvlWiden(String crmd, String parent, String assignee){
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Skill_Set> allSkilSets = session.createQuery("from Skill_Set").list();
        List<Skill> allSkils = session.createQuery("from Skill").list();
        List<Skill> outputSkils = new ArrayList<>(0);


        for (Skill_Set next:allSkilSets){
            if(next.getEmployeeByCrmd().getCrmd().equals(crmd))
                for(Skill next1: allSkils){
                    if(next1.getParentId()!=null)
                        if(next1.getParentId()==getIdOfParent(parent))
                            if(numberOrNot(next1.getName()))
                                if(Utilites.toLong(next1.getName()) > getCurrentSkill(crmd, getIdOfParent(parent)) && Utilites.toLong(next1.getName()) <= getCurrentSkill(assignee, getIdOfParent(parent)))
                                    outputSkils.add(next1);
                }
        }
        session.close();

        return outputSkils;
    }


    public static Long getIdOfSkillset(String crmd, String parent){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Skill_Set> allSkilSets = session.createQuery("from Skill_Set").list();
        Long id = 0L;
        for (Skill_Set next:allSkilSets){
            if(next.getEmployeeByCrmd().getCrmd().equals(crmd))
                if(next.getSkillBySkillId().getParentId() == getIdOfParent(parent))
                        id = next.getId();
        }
        session.close();

        return id;
    }

}
