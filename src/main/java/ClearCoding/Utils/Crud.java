package ClearCoding.Utils;

import ClearCoding.Entity.Employee;
import ClearCoding.Entity.Skill;
import ClearCoding.Entity.Skill_Set;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ClearCoding.Utils.Utilites.isNumber;

//asd
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
        for (Employee temporarEmployee : getallemployee) {
            if (isEqualsCRMD(CRMD, temporarEmployee))
                employee = temporarEmployee;
        }
        return employee;
    }

    private static boolean isSenior(Employee employee, Employee temporarEmployee) {
        return Utilites.getEmployeeRank(temporarEmployee.getPosition()) == 4 && Utilites.getEmployeeRank(employee.getPosition()) == 4;
    }

    private static boolean isEqualsAmbient(Employee employee, Employee temporarEmployee) {
        return temporarEmployee.getAmbient().equals(employee.getAmbient());
    }

    private static boolean isEqualsCRMD(String CRMD, Employee temporarEmployee) {
        return temporarEmployee.getCrmd().equals(CRMD);
    }

    private static boolean isEqualsCRMDfromSkillSet(String crmd, Skill_Set temporarSkillSet) {
        return isEqualsCRMD(crmd, temporarSkillSet.getEmployeeByCrmd());
    }

    private static boolean isLVLofParentfromSkillSet(String parent, Skill_Set temporarSkillSet) {
        return temporarSkillSet.getSkillBySkillId().getParentId() == getIdOfParent(parent);
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
        Employee curentEmployee = new Employee();
        curentEmployee = getEmployeebyCRMD(CRMD, getallemployee, curentEmployee);

        List<Employee> assignees = new ArrayList<Employee>(0);
        for (Employee temporarEmployee : getallemployee) {
            if (isEqualsAmbient(curentEmployee, temporarEmployee)) {
                if (isAssignee(curentEmployee, temporarEmployee))
                    assignees.add(temporarEmployee);
                else if (isSenior(curentEmployee, temporarEmployee)) {
                    if (!isEqualsCRMD(curentEmployee.getCrmd(), temporarEmployee))
                        assignees.add(temporarEmployee);
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
        for (Skill temporarSkill1 : SkillList1)
            for (Skill temporarSkill2 : SkillList2) {
                if (isNumber(temporarSkill1.getName()))
                    if (temporarSkill1.getParentId() == temporarSkill2.getId()) {
                        outputSkils.add(temporarSkill2.getName());
                    }
            }
        session.close();
        return  returnDistinctList(outputSkils);
    }




    public static long getIdOfParent(String parent){
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Skill> skills = session.createQuery("from Skill").list();
        Long parentId = 0L;
        for (Skill temporarSkill : skills) {
            if (temporarSkill.getName().equals(parent)) parentId = temporarSkill.getId();
        }
        session.close();
        return parentId;
    }

    public static Long getCurrentSkill(String crmd, Long parent){
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Skill_Set> skill_set = session.createQuery("from Skill_Set").list();

        long currentSkil = 0;

        for (Skill_Set temporarSkillSet : skill_set) {
            if (isEqualsCRMDfromSkillSet(crmd, temporarSkillSet))
                if (temporarSkillSet.getSkillBySkillId().getParentId() == parent)
                    currentSkil = Utilites.toLong(temporarSkillSet.getSkillBySkillId().getName());
        }


        session.close();
        return currentSkil;
    }

    public static List<Skill> getSkillLvlWiden(String crmd, String parent, String assignee){
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Skill_Set> allSkilSets = session.createQuery("from Skill_Set").list();
        List<Skill> allSkils = session.createQuery("from Skill").list();
        List<Skill> outputSkils = new ArrayList<>(0);


        for (Skill_Set temporarSkillSet : allSkilSets) {
            if (isEqualsCRMDfromSkillSet(crmd, temporarSkillSet))
                for (Skill temporarSkill : allSkils) {
                    if (temporarSkill.getParentId() != null)
                        if (temporarSkill.getParentId() == getIdOfParent(parent))
                            if (isNumber(temporarSkill.getName()))
                                if (Utilites.toLong(temporarSkill.getName()) > getCurrentSkill(crmd, getIdOfParent(parent)) && Utilites.toLong(temporarSkill.getName()) <= getCurrentSkill(assignee, getIdOfParent(parent)))
                                    outputSkils.add(temporarSkill);
                }
        }
        session.close();

        return outputSkils;
    }
//TOD

    public static Long getIdOfSkillset(String crmd, String parent){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Skill_Set> allSkilSets = session.createQuery("from Skill_Set").list();
        Long id = 0L;
        for (Skill_Set temporarSkillSet : allSkilSets) {
            if (isEqualsCRMDfromSkillSet(crmd, temporarSkillSet))
                if (isLVLofParentfromSkillSet(parent, temporarSkillSet))
                    id = temporarSkillSet.getId();
        }
        session.close();

        return id;
    }

}
