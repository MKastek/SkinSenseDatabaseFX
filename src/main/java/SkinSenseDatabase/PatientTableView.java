package SkinSenseDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class PatientTableView
{
    // Properties of the person (name, address, job)
    private Integer id;
    private Date birthDate;
    private String gender;
    private Double bmi;
    private Date prickDate;
    private ArrayList<Boolean> allergenList = new ArrayList<Boolean>(16);

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public ArrayList<Boolean> getAllergenList() {
        return allergenList;
    }

    public void setAllergenList(ArrayList<Boolean> allergenList) {
        this.allergenList = allergenList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirtDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Double getBmi() {
        return bmi;
    }

    public void setBmi(Double bmi) {
        this.bmi = bmi;
    }

    public Date getPrickDate() {
        return prickDate;
    }

    public void setPrickDate(Date prickDate) {
        this.prickDate = prickDate;
    }

    public PatientTableView(Integer id, Date birthDate, String gender, Double bmi, Date prickDate, ArrayList<Boolean> allergenList ) {
        this.id = id;
        this.birthDate = birthDate;
        this.gender = gender;
        this.bmi = bmi;
        this.prickDate = prickDate;
        this.allergenList=allergenList;
    }
}