package ClearCoding.Utils;

import ClearCoding.Entity.*;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ClearCoding.Utils.Utilites.numberOrNot;
//
public class Crud {

    private static List<String> returnDistinctList(List<String> outputSkils) {
        Set<String> hs = new HashSet<>();
        hs.addAll(outputSkils);
        outputSkils.clear();
        outputSkils.addAll(hs);
        return outputSkils;
    }

    private static boolean isAssignee(Employee employee, Employee next) {
        return Utilites.getEmployeeRank(next.getPosition()) > Utilites.getEmployeeRank(employee.getPosition());
    }

    private static Employee getEmployeebyCRMD(String CRMD, List<Employee> getallemployee, Employee employee) {
        for(Employee next: getallemployee){
            if(isEqualsCRMD(CRMD, next))
                employee = next;
        }
        return employee;
    }

    private static boolean isSenior(Employee employee, Employee next) {
        return Utilites.getEmployeeRank(next.getPosition()) == 4 && Utilites.getEmployeeRank(employee.getPosition()) == 4;
    }

    private static boolean isEqualsAmbient(Employee employee, Employee next) {
        return next.getAmbient().equals(employee.getAmbient());
    }

    private static boolean isEqualsCRMD(String CRMD, Employee next) {
        return next.getCrmd().equals(CRMD);
    }

    private static boolean isEqualsCRMDfromSkillSet(String crmd, Skill_Set next) {
        return isEqualsCRMD(crmd, next.getEmployeeByCrmd());
    }

    private static boolean isLVLofParentfromSkillSet(String parent, Skill_Set next) {
        return next.getSkillBySkillId().getParentId() == getIdOfParent(parent);
    }

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
        employee = getEmployeebyCRMD(CRMD, getallemployee, employee);

        List<Employee> assignees = new ArrayList<Employee>(0);
        for(Employee next: getallemployee){
            if(isEqualsAmbient(employee, next)){
                if(isAssignee(employee, next))
                    assignees.add(next);
                else if (isSenior(employee, next)){
                    if (!isEqualsCRMD(employee.getCrmd(), next))
                        assignees.add(next);
                }
            }
        }

        session.close();
        return assignees;
    }




    public static List<String> getSkillParent() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Skill> SkillList1 = session.createQuery("from Skill").list();
        List<Skill> SkillList2 = session.createQuery("from Skill").list();

        List<String> outputSkils = new ArrayList<String>(0);
        for (Skill next: SkillList1)
            for(Skill next1:SkillList2){
                if(numberOrNot(next.getName()))
                    if(next.getParentId()==next1.getId()){
                        outputSkils.add(next1.getName());
                    }
            }
        session.close();
        return  returnDistinctList(outputSkils);
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
            if(isEqualsCRMDfromSkillSet(crmd, next))
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
            if(isEqualsCRMDfromSkillSet(crmd, next))
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
            if(isEqualsCRMDfromSkillSet(crmd, next))
                if(isLVLofParentfromSkillSet(parent, next))
                        id = next.getId();
        }
        session.close();

        return id;
    }

}
