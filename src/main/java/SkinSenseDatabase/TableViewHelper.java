package SkinSenseDatabase;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TableViewHelper {
    // Returns an observable list of persons
    public static ObservableList<PatientTableView> getPersonList() {
        ObservableList<PatientTableView> listPatient = FXCollections.observableArrayList();
        int numberOfPatients = SkinSenseDataBase.patientCount();
        Patient patient;
        for (int ii = 1; ii <= numberOfPatients; ii++) {
            //Poprawic
            if (ii != 83) {
                try {
                    patient = SkinSenseDataBase.Connection(ii);
                    ArrayList<Boolean> list = new ArrayList<Boolean>();
                    for(int i=0; i<16; i++)
                    {
                        if(patient.alergeny.get(i).decison==1 && patient.alergeny.get(i).evaluation==1)
                        {
                            list.add(true);
                        }
                        else
                        {
                            list.add(false);
                        }
                    }
                    String gender;
                    if (patient.getGender() == 0) gender = "male";
                    else gender = "female";

                    listPatient.add(new PatientTableView(
                            patient.getID(),
                            patient.getBirthDate(),
                            gender,
                            patient.getBMI(),
                            patient.getPrickDate(),
                            list));

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        return listPatient;
    }

    // Returns Person Id TableColumn
    public static TableColumn<PatientTableView, Integer> getIdColumn() {
        TableColumn<PatientTableView, Integer> idCol = new TableColumn<>("Id");
        PropertyValueFactory<PatientTableView, Integer> idCellValueFactory = new PropertyValueFactory<>("id");
        idCol.setCellValueFactory(idCellValueFactory);
        return idCol;
    }

    // Returns First Name TableColumn
    public static TableColumn<PatientTableView, Date> getBirthDateColumn() {
        TableColumn<PatientTableView, Date> birthdateCol = new TableColumn<>("Birthdate");
        PropertyValueFactory<PatientTableView, Date> BirthDateCellValueFactory = new PropertyValueFactory<>("birthDate");
        birthdateCol.setCellValueFactory(BirthDateCellValueFactory);
        birthdateCol.setCellFactory(column -> {
            TableCell<PatientTableView, Date> cell = new TableCell<PatientTableView, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        this.setText(format.format(item));

                    }
                }
            };

            return cell;
        });
        return birthdateCol;
    }

    // Returns Last Name TableColumn
    public static TableColumn<PatientTableView, String> getGenderColumn() {
        TableColumn<PatientTableView, String> genderCol = new TableColumn<>("Gender");
        PropertyValueFactory<PatientTableView, String> lastNameCellValueFactory = new PropertyValueFactory<>("gender");
        genderCol.setCellValueFactory(lastNameCellValueFactory);
        return genderCol;
    }

    // Returns Street TableColumn
    public static TableColumn<PatientTableView, Float> getBMIColumn() {
        TableColumn<PatientTableView, Float> bmiCol = new TableColumn<>("BMI");
        PropertyValueFactory<PatientTableView, Float> bmiCellValueFactory = new PropertyValueFactory<>("bmi");
        bmiCol.setCellValueFactory(bmiCellValueFactory);
        return bmiCol;
    }

    // Returns ZipCode TableColumn
    public static TableColumn<PatientTableView, Date> getPrickDateColumn() {
        TableColumn<PatientTableView, Date> prickDateCol = new TableColumn<>("Prick Date");
        PropertyValueFactory<PatientTableView, Date> PrickDateCellValueFactory = new PropertyValueFactory<>("prickDate");
        prickDateCol.setCellValueFactory(PrickDateCellValueFactory);
        prickDateCol.setCellFactory(column -> {
            TableCell<PatientTableView, Date> cell = new TableCell<PatientTableView, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        this.setText(format.format(item));

                    }
                }
            };

            return cell;
        });

        return prickDateCol;

    }
}


