package ClearCoding.Entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Employee {
    private String crmd;
    private String ambient;
    private String position;

    @Id
    @Column(name = "CRMD", nullable = false, length = 10)
    public String getCrmd() {
        return crmd;
    }

    public void setCrmd(String crmd) {
        this.crmd = crmd;
    }

    @Basic
    @Column(name = "AMBIENT", nullable = true, length = 20)
    public String getAmbient() {
        return ambient;
    }

    public void setAmbient(String ambient) {
        this.ambient = ambient;
    }

    @Basic
    @Column(name = "POSITION", nullable = true, length = 20)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (crmd != null ? !crmd.equals(employee.crmd) : employee.crmd != null) return false;
        if (ambient != null ? !ambient.equals(employee.ambient) : employee.ambient != null) return false;
        if (position != null ? !position.equals(employee.position) : employee.position != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = crmd != null ? crmd.hashCode() : 0;
        result = 31 * result + (ambient != null ? ambient.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }
}
