package ClearCoding.Entity;

import javax.persistence.*;
import java.util.Date;
//
@Entity
@Table(name = "SKILL_SET")
public class Skill_Set {
    private long id;
    private Employee employeeByCrmd;
    private Skill skillBySkillId;
    private Date assignedDate;
    private String assigneeId;

    public Skill_Set() {
    }

    public Skill_Set(long id, Employee employeeByCrmd, Skill skillBySkillId, Date assignedDate, String assigneeId) {
        this.id = id;
        this.employeeByCrmd = employeeByCrmd;
        this.skillBySkillId = skillBySkillId;
        this.assignedDate = assignedDate;
        this.assigneeId = assigneeId;
    }

    @Id
    @Column(name = "ID", nullable = false, precision = 0)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "CRMD", referencedColumnName = "CRMD", nullable = false)
    public Employee getEmployeeByCrmd() {
        return employeeByCrmd;
    }

    public void setEmployeeByCrmd(Employee employeeByCrmd) {
        this.employeeByCrmd = employeeByCrmd;
    }

    @ManyToOne
    @JoinColumn(name = "SKILL_ID", referencedColumnName = "ID", nullable = false)
    public Skill getSkillBySkillId() {
        return skillBySkillId;
    }

    public void setSkillBySkillId(Skill skillBySkillId) {
        this.skillBySkillId = skillBySkillId;
    }

    @Basic
    @Column(name = "ASSIGNED_DATE", nullable = true)
    public Date getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Date assignedDate) {
        this.assignedDate = assignedDate;
    }

    @Basic
    @Column(name = "ASSIGNEE_ID", nullable = true, length = 10)
    public String getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
    }


}