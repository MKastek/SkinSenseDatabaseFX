package SkinSenseDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.nio.file.Files.newBufferedReader;

public class CSVRead {

    public static void main(String filePath) {
        List<Patient> patients = readPatientsFromCSV(filePath);
        System.out.println(patients.get(10).toString());
        Connection conn;
        try{
            conn = DriverManager.getConnection(	"jdbc:h2:./test", "sa", "");
            String updateString = "INSERT INTO Pacjenci (Id,`BirthDate`,`Gender`,`Age`,`Bmi`,`PrickDate`,`InVitroDate`,";

            for(int i=0; i<16; i++)
            {
                String alergenString="`"+ Patient.alergenyString.get(i)+"bubble"+"`,"+
                        "`"+ Patient.alergenyString.get(i)+"Ev"+"`,"+
                        "`"+ Patient.alergenyString.get(i)+"probability"+"`,"+
                        "`"+ Patient.alergenyString.get(i)+"AI`";
                if(i!=15) alergenString+=",";
                updateString+=alergenString;
            }
            updateString+=") VALUES (";
            for(int i=0; i<70; i++)
            {
                updateString+="?, ";
            }
            updateString+="?);";


            PreparedStatement statement;
            statement=conn.prepareStatement(updateString);
            for(Patient patient:patients)
            {
                statement.setInt(1,patient.getID());
                statement.setDate(2,new java.sql.Date(patient.getBirthDate().getTime()));
                statement.setInt(3,patient.getGender());
                statement.setInt(4,patient.getAge());
                statement.setDouble(5,patient.getBMI());
                statement.setDate(6,new java.sql.Date(patient.getPrickDate().getTime()));
                statement.setDate(7,new java.sql.Date(patient.getInVitroDate().getTime()));
                for(int i=8; i<72; i+=4) {
                    int j=(i-8)/4;
                    statement.setInt(i, patient.alergenyHashMap.get(Patient.alergenyString.get(j)).bubble);
                    statement.setInt(i+1, patient.alergenyHashMap.get(Patient.alergenyString.get(j)).evaluation);
                    statement.setDouble(i+2, patient.alergenyHashMap.get(Patient.alergenyString.get(j)).probabilityNN);
                    statement.setInt(i+3, patient.alergenyHashMap.get(Patient.alergenyString.get(j)).decison);
                }

                statement.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        System.out.println();

    }

    private static List<Patient> readPatientsFromCSV(String fileName)
    {
        List<Patient> patients = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);
        try (BufferedReader br = newBufferedReader(pathToFile, StandardCharsets.UTF_8)){
            String line = br.readLine();

            while(line != null) {
                String[] attributes = line.split(";");
                Patient patient = createPatient(attributes);
                patients.add(patient);
                line = br.readLine();
            }
        } catch (IOException | ParseException ioe) {
            ioe.printStackTrace();
        }
        return patients;
    }
    protected static Patient createPatient(String [] metadata) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        int ID = Integer.parseInt(metadata[0]);
        Date birthDate = format.parse(metadata[1]);
        int gender = Integer.parseInt(metadata[2]);
        int age = Integer.parseInt(metadata[3]);
        double bmi = Double.parseDouble(metadata[4]);
        Date prickDate = format.parse(metadata[5]);
        Date inVitroDate = format.parse(metadata[6]);
        ArrayList <Alergen> arrayList=new ArrayList<Alergen>(16);

        for (int i=7; i<71; i+=4)
        {
            arrayList.add(new Alergen(Integer.parseInt(metadata[i]),Integer.parseInt(metadata[i+1]),Double.parseDouble(metadata[i+2]),Integer.parseInt(metadata[i+3])));
        }

        return new Patient(ID,birthDate,gender,age,bmi,prickDate,inVitroDate,arrayList);
    }

}
