package SkinSenseDatabase;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class SkinSenseDataBase {
    static Connection conn;
    public static void createDB() throws SQLException {
        Connection conn = null;
        try {
            //conn = DriverManager.getConnection("jdbc:mysql://db4free.net/testdatabase2341", "mkastek",
              //              "Imk12345!");
            conn = DriverManager.getConnection(	"jdbc:h2:./test", "sa", "");
            Statement statement = conn.createStatement();
            // Usuwanie tabeli jesli juz istnieje - kolejne uruchomienie przykladu nie wygeneruje bledu:
            statement.executeUpdate("DROP TABLE IF EXISTS `Pacjenci`;");
            String createString = "CREATE TABLE `Pacjenci` (" +
                    "`Id` int(6) unsigned NOT NULL auto_increment," +
                    "`BirthDate` date default NULL," +
                    "`Gender` int default NULL," +
                    "`Age` int default NULL,"+
                    "`Bmi` float default NULL," +
                    "`PrickDate` date default NULL,"+
                    "`InVitroDate` date default NULL,";
            for(int i=0; i<16; i++)
            {
                String alergenString="`"+ Patient.alergenyString.get(i)+"bubble"+"` int default NULL,"+
                "`"+ Patient.alergenyString.get(i)+"Ev"+"` int default NULL,"+
                "`"+ Patient.alergenyString.get(i)+"probability"+"` float default NULL,"+
                "`"+ Patient.alergenyString.get(i)+"AI"+"` int default NULL,";
                createString+=alergenString;
            }
            createString+= "PRIMARY KEY  (`Id`)" + ");";

            statement.executeUpdate(createString);

        }catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        CSVRead.main("DANE.txt");
    }
    static public Patient Connection(int patientID) throws SQLException, ParseException {

        conn = DriverManager.getConnection(	"jdbc:h2:./test", "sa", "");
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM Pacjenci WHERE Id = " +patientID);
        ArrayList<Alergen> arrayList=new ArrayList<Alergen>(16);
        int ID,gender,age;
        Date birthDate,prickDate,inVitroDate;
        double BMI;
        Patient patient = null;

        ResultSet rs = statement.getResultSet();
        ResultSetMetaData md  = rs.getMetaData();
        if(rs.next()) {
            ID = Integer.parseInt(String.valueOf(rs.getObject(1)));
            birthDate=new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(rs.getObject(2)));
            gender=Integer.parseInt(String.valueOf(rs.getObject(3)));
            age=Integer.parseInt(String.valueOf(rs.getObject(4)));
            BMI=Double.parseDouble(String.valueOf(rs.getObject(5)));
            prickDate=new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(rs.getObject(6)));
            inVitroDate=new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(rs.getObject(7)));
            for(int i=8; i<72; i+=4) {
                arrayList.add(new Alergen(Integer.parseInt(String.valueOf(rs.getObject(i))), Integer.parseInt(String.valueOf(rs.getObject(i+1))), Double.parseDouble(String.valueOf(rs.getObject(i+2))), Integer.parseInt(String.valueOf(rs.getObject(i+3)))));
            }
            patient = new Patient(ID,birthDate,gender,age,BMI,prickDate,inVitroDate,arrayList);
        }
        return  patient;
    }

    static public int patientCount() {
        ResultSet rs=null;
        int IDcount=0;
        try {
            conn = DriverManager.getConnection(	"jdbc:h2:./test", "sa", "");
            Statement statement = conn.createStatement();
            statement.execute("SELECT * FROM Pacjenci");
            rs = statement.getResultSet();
            rs.last();
            IDcount=rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return IDcount;
    }
}
