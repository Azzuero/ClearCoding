package ClearCoding.Utils;

import ClearCoding.Entity.Employee;
import ClearCoding.Entity.Skill;
import org.hibernate.Query;
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
        Query query = session.createQuery("from Employee where CRMD =:crmd");
        query.setParameter("crmd", CRMD);
        List<Employee> getemployeebycrmd = query.list();
        List<Employee> getallemployee = session.createQuery("from Employee").list();
        List<Employee> assignees = new ArrayList<Employee>(0);
        for (Employee next : getemployeebycrmd) {
            for (Employee nextAll : getallemployee) {
                if (Utilites.getEmployeeRank(next.getPosition()) < Utilites.getEmployeeRank(nextAll.getPosition()))
                    assignees.add(nextAll);
                else if (Utilites.getEmployeeRank(next.getPosition()) == 4 && Utilites.getEmployeeRank(nextAll.getPosition()) == 4)
                    if (!next.getCrmd().equals(nextAll.getCrmd()))
                        assignees.add(nextAll);
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
                        // System.out.println(next1.getName());
                    }
            }
        Set<String> hs = new HashSet<String>();
        hs.addAll(outputSkils);
        outputSkils.clear();
        outputSkils.addAll(hs);

        session.close();

        return outputSkils;
    }

    public static List<Skill> getSkillLevel(String parent) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from Skill where name =:parent");
        query.setParameter("parent", parent);

        List<Skill> parentList = query.list();

        Query query1 = session.createQuery("from Skill where parentId =:par");
        query1.setParameter("par", parentList.get(0).getId());

        List<Skill> outputList = new ArrayList<>(0);
        outputList.addAll(query1.list());

        return outputList;
    }
}
