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
}
