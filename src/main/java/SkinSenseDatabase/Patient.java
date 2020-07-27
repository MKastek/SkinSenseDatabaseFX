package SkinSenseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Patient {
    int ID;
    Date birthDate;
    int gender;
    int age;
    double BMI;
    Date prickDate;
    Date inVitroDate;
    String LArm1FilePathBT,LArm2FilePathBT,LArm3FilePathBT,RArm1FilePathBT,RArm2FilePathBT,RArm3FilePathBT;
    String LArm1FilePathAT,LArm2FilePathAT,LArm3FilePathAT,RArm1FilePathAT,RArm2FilePathAT,RArm3FilePathAT;


    ArrayList<Alergen> alergeny = new ArrayList<Alergen>(16);
    static ArrayList<String> alergenyString = new ArrayList<String>(){
        {
            add("HistaminaR");
            add("BylicaPospolita");
            add("Leszczyna");
            add("Zyto");
            add("PiecTraw");
            add("Brzoza");
            add("Tymotka");
            add("ProbaUjemnaR");
            add("HistaminaL");
            add("RoztoczeFarinae");
            add("Kot");
            add("AspergiliusFurnigatus");
            add("RoztoczePteronyssinus");
            add("Pies");
            add("AlternariaAlernata");
            add("ProbaUjemnaL");
        }
    };
     HashMap<String, Alergen> alergenyHashMap = new HashMap<>();
    public Patient() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getBMI() {
        return BMI;
    }

    public void setBMI(double BMI) {
        this.BMI = BMI;
    }

    public Date getPrickDate() {
        return prickDate;
    }

    public void setPrickDate(Date prickDate) {
        this.prickDate = prickDate;
    }

    public Date getInVitroDate() {
        return inVitroDate;
    }

    public void setInVitroDate(Date inVitroDate) {
        this.inVitroDate = inVitroDate;
    }


    public Patient(int ID, Date birthDate, int gender, int age, double BMI, Date prickDate, Date inVitroDate,  ArrayList<Alergen> alergeny) {
        this.ID = ID;
        this.birthDate = birthDate;
        this.gender = gender;
        this.age = age;
        this.BMI = BMI;
        this.prickDate = prickDate;
        this.inVitroDate = inVitroDate;
        this.alergeny = alergeny;
        for(int i=0; i<alergeny.size(); i++)
        {
            alergenyHashMap.put(alergenyString.get(i),alergeny.get(i));
        }

    }

    @Override
    public String toString() {
        return "Patient{" +
                "ID=" + ID +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                ", age=" + age +
                ", BMI=" + BMI +
                ", prickDate=" + prickDate +
                ", inVitroDate=" + inVitroDate +
                alergeny.toString()+
                '}';
    }


}
