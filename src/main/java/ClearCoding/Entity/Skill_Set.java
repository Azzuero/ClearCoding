package ClearCoding.Entity;
import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "SKILL_SET", schema = "JUNITTEST", catalog = "")
public class Skill_Set {
    private Time assignedDate;
    private String assigneeId;
    private long id;
    private Employee employeeByCrmd;
    private Skill skillBySkillId;

    @Basic
    @Column(name = "ASSIGNED_DATE", nullable = true)
    public Time getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Time assignedDate) {
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

    @Id
    @Column(name = "ID", nullable = false, precision = 0)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Skill_Set skillSet = (Skill_Set) o;

        if (id != skillSet.id) return false;
        if (assignedDate != null ? !assignedDate.equals(skillSet.assignedDate) : skillSet.assignedDate != null)
            return false;
        if (assigneeId != null ? !assigneeId.equals(skillSet.assigneeId) : skillSet.assigneeId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = assignedDate != null ? assignedDate.hashCode() : 0;
        result = 31 * result + (assigneeId != null ? assigneeId.hashCode() : 0);
        result = 31 * result + (int) (id ^ (id >>> 32));
        return result;
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
}