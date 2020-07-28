package SkinSenseDatabase;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.TableView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static javafx.scene.input.KeyCode.*;

public class Table extends Application
{

    SortedList<PatientTableView> sortedReports;
    Button AllFilterbutton;
    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void start(Stage stage)
    {
        final Label label = new Label("Patients");
        label.setFont(new Font("Arial", 20));
        BorderPane borderPane = new BorderPane();

        // Create a TableView with a list of persons
        TableView<PatientTableView> table = new TableView<>();
        // Add rows to the TableView
        table.getItems().addAll(TableViewHelper.getPersonList());
        // Add columns to the TableView
        table.getColumns().addAll(TableViewHelper.getIdColumn(), TableViewHelper.getBirthDateColumn(),
                TableViewHelper.getGenderColumn(),TableViewHelper.getBMIColumn(),
                TableViewHelper.getPrickDateColumn());

        FilteredList<PatientTableView> flPatient = new FilteredList(TableViewHelper.getPersonList(), p -> true);//Pass the data to a filtered list
        // Set the column resize policy to constrained resize policy
        table.setColumnResizePolicy(javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY);
        // Set the Placeholder for an empty table
        table.setPlaceholder(new Label("No visible columns and/or data exist."));
        table.setPrefSize(800,800);
        stage.setWidth(800);
        stage.setHeight(800);

        // Create the VBox
        VBox root = new VBox();
        // Add the Table to the VBox
        root.getChildren().addAll(label,table);
        // Set the Padding and Border for the VBox
        root.setStyle("-fx-padding: 10;");
        borderPane.setCenter(root);

        VBox filter = new VBox(5);
        filter.setPadding(new Insets(40,10,10,10));
        Label IDsearch = new Label("Search ID");
        TextField textField = new TextField();
        textField.setPromptText("Search here!");

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            //newValue = newValue.trim();
            //System.out.println("new value: " + newValue);

            if (newValue == null || newValue.length() == 0) {
                flPatient.setPredicate(s -> true);
                //System.out.println("no filter " + newValue);
            } else {
                //System.out.println("filter: " + newValue);
                flPatient.setPredicate(s ->
                {
                    String stringInt = String.valueOf(s.getId());
                    return  stringInt.equals(newValue);
                });

            }
        });
        Label bmiLabel = new Label("BMI");
        Label ageLabel = new Label("Age");
        Label genderLabel = new Label("Gender");
        Label alergenLabel = new Label("Alergen");


        ChoiceBox<String> bmiChoiceBox = new ChoiceBox();
        bmiChoiceBox.getItems().addAll("<18", "18-25", ">25","-");
        bmiChoiceBox.setValue("-");


        bmiChoiceBox.setOnAction(event -> {

            bmiLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
            bmiLabel.setTextFill(Color.RED);

            ageLabel.setFont(Font.getDefault());
            ageLabel.setTextFill(Color.BLACK);

            genderLabel.setFont(Font.getDefault());
            genderLabel.setTextFill(Color.BLACK);

            alergenLabel.setFont(Font.getDefault());
            alergenLabel.setTextFill(Color.BLACK);

            flPatient.setPredicate(s ->
            {
                switch (bmiChoiceBox.getValue()) {
                    case "<18":
                        return s.getBmi() < 18;
                    case "18-25":
                        return (s.getBmi() > 18 && s.getBmi() < 25);
                    case ">25":
                        return s.getBmi() > 25;
                    default:
                        return true;
                }
            });

        });



        ChoiceBox<String> ageChoiceBox = new ChoiceBox();
        ageChoiceBox.getItems().addAll("<20", "20-30", "30-40","40-50","50-60","60+","-");
        ageChoiceBox.setValue("-");

        ageChoiceBox.setOnAction(event -> {
            ageLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
            ageLabel.setTextFill(Color.RED);

            bmiLabel.setFont(Font.getDefault());
            bmiLabel.setTextFill(Color.BLACK);

            genderLabel.setFont(Font.getDefault());
            genderLabel.setTextFill(Color.BLACK);

            alergenLabel.setFont(Font.getDefault());
            alergenLabel.setTextFill(Color.BLACK);

            flPatient.setPredicate(s ->
            {
                Period period = Period.between(LocalDate.now(),s.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                switch (ageChoiceBox.getValue()) {
                    case "<20":
                        return Math.abs(period.getYears())< 20;
                    case "20-30":
                        return (Math.abs(period.getYears()) > 20 && Math.abs(period.getYears()) < 30);
                    case "30-40":
                        return (Math.abs(period.getYears()) > 30 && Math.abs(period.getYears()) < 40);
                    case "40-50":
                        return (Math.abs(period.getYears()) > 40 && Math.abs(period.getYears()) < 50);
                    case "50-60":
                        return (Math.abs(period.getYears()) > 50 && Math.abs(period.getYears()) < 60);
                    case "60+":
                        return (Math.abs(period.getYears()) > 60);
                    default:
                        return true;
                }
            });
        });

        ChoiceBox<String> genderChoiceBox = new ChoiceBox();
        genderChoiceBox.getItems().addAll("male", "female","-");
        genderChoiceBox.setValue("-");
        genderChoiceBox.setOnAction(event -> {
            genderLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
            genderLabel.setTextFill(Color.RED);

            bmiLabel.setFont(Font.getDefault());
            bmiLabel.setTextFill(Color.BLACK);

            ageLabel.setFont(Font.getDefault());
            ageLabel.setTextFill(Color.BLACK);

            alergenLabel.setFont(Font.getDefault());
            alergenLabel.setTextFill(Color.BLACK);

            flPatient.setPredicate(s ->
            {
                switch (genderChoiceBox.getValue()) {
                    case "male":
                        return s.getGender().equals("male");
                    case "female":
                        return s.getGender().equals("female");
                    default:
                        return true;
                }
            });

        });

        ChoiceBox<String> alergenChoiceBox = new ChoiceBox();
        alergenChoiceBox.getItems().addAll("HistaminaR", "Bylica Pospolita", "Leszczyna","Zyto","Piec Traw",
                "Brzoza","Tymotka","Proba Ujemna R","Histamina L","Roztocze Farinae","Kot","Aspergilius Furnigatus",
                "Roztocze Pteronyssinus","Pies","Alternaria Alernata","Proba UjemnaL","-");
        alergenChoiceBox.setValue("-");
        alergenChoiceBox.setOnAction(event -> {

            alergenLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
            alergenLabel.setTextFill(Color.RED);

            bmiLabel.setFont(Font.getDefault());
            bmiLabel.setTextFill(Color.BLACK);

            ageLabel.setFont(Font.getDefault());
            ageLabel.setTextFill(Color.BLACK);

            genderLabel.setFont(Font.getDefault());
            genderLabel.setTextFill(Color.BLACK);

            flPatient.setPredicate(s ->
            {
                switch (alergenChoiceBox.getValue()) {
                    case "HistaminaR":
                        return s.getAllergenList().get(0);
                    case "Bylica Pospolita":
                        return s.getAllergenList().get(1);
                    case "Leszczyna":
                        return s.getAllergenList().get(2);
                    case "Zyto":
                        return s.getAllergenList().get(3);
                    case "Piec Traw":
                        return s.getAllergenList().get(4);
                    case "Brzoza":
                        return s.getAllergenList().get(5);
                    case "Tymotka":
                        return s.getAllergenList().get(6);
                    case "Proba Ujemna R":
                        return s.getAllergenList().get(7);
                    case "Histamina L":
                        return s.getAllergenList().get(8);
                    case "Roztocze Farinae":
                        return s.getAllergenList().get(9);
                    case "Kot":
                        return s.getAllergenList().get(10);
                    case "Aspergilius Furnigatus":
                        return s.getAllergenList().get(11);
                    case "Roztocze Pteronyssinus":
                        return s.getAllergenList().get(12);
                    case "Pies":
                        return s.getAllergenList().get(13);
                    case "Alternaria Alernata":
                        return s.getAllergenList().get(14);
                    case "Proba UjemnaL":
                        return s.getAllergenList().get(15);
                    default:
                        return true;
                }
            });

        });

        sortedReports = new SortedList<>(flPatient);
        sortedReports.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedReports);

        AllFilterbutton = new Button("All filter");
        AllFilterbutton.setOnAction(event -> {

            alergenLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
            alergenLabel.setTextFill(Color.RED);

            bmiLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
            bmiLabel.setTextFill(Color.RED);

            ageLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
            ageLabel.setTextFill(Color.RED);

            genderLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
            genderLabel.setTextFill(Color.RED);

            int age1=0, age2=200;
            int alergen=16;
            switch (ageChoiceBox.getValue()) {
                case "<20":
                    age1=0;
                    age2=20;
                    break;
                case "20-30":
                    age1=20;
                    age2=30;
                    break;
                case "30-40":
                    age1=30;
                    age2=40;
                    break;
                case "40-50":
                    age1=40;
                    age2=50;
                    break;
                case "50-60":
                    age1=50;
                    age2=60;
                    break;
                case "60+":
                    age1=60;
                    age2=200;
                    break;
            }
            int finalAge1=age1;
            int finalAge2=age2;

            String gender=null;
            switch (genderChoiceBox.getValue()) {
                case "male":
                    gender="male";
                    break;
                case "female":
                    gender="female";
                    break;
            }
            String finalGender=gender;

            int bmi1=0,bmi2=100;

            switch (bmiChoiceBox.getValue()) {
                case "<18":
                    bmi2=18;
                    break;
                case "18-25":
                    bmi1=18;
                    bmi2=25;
                    break;
                case ">25":
                    bmi1=25;
                    break;
            }

            switch (alergenChoiceBox.getValue()) {
                case "HistaminaR":
                    alergen=0;
                    break;
                case "Bylica Pospolita":
                    alergen=1;
                    break;
                case "Leszczyna":
                    alergen=2;
                    break;
                case "Zyto":
                    alergen=3;
                    break;
                case "Piec Traw":
                    alergen=4;
                    break;
                case "Brzoza":
                    alergen=5;
                    break;
                case "Tymotka":
                    alergen=6;
                    break;
                case "Proba Ujemna R":
                    alergen=7;
                    break;
                case "Histamina L":
                    alergen=8;
                    break;
                case "Roztocze Farinae":
                    alergen=9;
                    break;
                case "Kot":
                    alergen=10;
                    break;
                case "Aspergilius Furnigatus":
                    alergen=11;
                    break;
                case "Roztocze Pteronyssinus":
                    alergen=12;
                    break;
                case "Pies":
                    alergen=13;
                    break;
                case "Alternaria Alernata":
                    alergen=14;
                    break;
                case "Proba UjemnaL":
                    alergen=15;
                    break;
            }
            int alergenFinal=alergen;

            int finalBmi1=bmi1;
            int finalBmi2=bmi2;

            flPatient.setPredicate(s ->
            {
                Period period = Period.between(LocalDate.now(),s.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                if(finalGender!=null&&alergenFinal!=16) return s.getGender().equals(finalGender) && finalAge1 <Math.abs(period.getYears()) && Math.abs(period.getYears())<finalAge2 &&s.getBmi()>finalBmi1 && s.getBmi()<finalBmi2 &&s.getAllergenList().get(alergenFinal);
                else if(finalGender!=null) return s.getGender().equals(finalGender) && finalAge1 <Math.abs(period.getYears()) && Math.abs(period.getYears())<finalAge2 &&s.getBmi()>finalBmi1 && s.getBmi()<finalBmi2;
                else return finalAge1 <Math.abs(period.getYears()) && Math.abs(period.getYears())<finalAge2 && s.getBmi()>finalBmi1 && s.getBmi()<finalBmi2;
            });
            }
        );
        Label filterLabel = new Label("Filter option");
        Button noFilter = new Button("No Filter");

        noFilter.setOnMouseClicked(event -> {
            alergenLabel.setFont(Font.getDefault());
            alergenLabel.setTextFill(Color.BLACK);

            bmiLabel.setFont(Font.getDefault());
            bmiLabel.setTextFill(Color.BLACK);

            ageLabel.setFont(Font.getDefault());
            ageLabel.setTextFill(Color.BLACK);

            genderLabel.setFont(Font.getDefault());
            genderLabel.setTextFill(Color.BLACK);



            ageChoiceBox.setValue("-");
            alergenChoiceBox.setValue("-");
            genderChoiceBox.setValue("-");
            bmiChoiceBox.setValue("-");
            flPatient.setPredicate(s -> true);
        });

        filter.getChildren().addAll(IDsearch,textField,bmiLabel,bmiChoiceBox,ageLabel,ageChoiceBox,genderLabel,genderChoiceBox,alergenLabel,alergenChoiceBox,filterLabel,AllFilterbutton,noFilter);
        borderPane.setLeft(filter);

        // Create the Scene
        Scene scene = new Scene(borderPane);
        // Add the Scene to the Stage
        stage.setScene(scene);
        // Set the Title of the Stage
        stage.setTitle("Patients Summary");
        // Display the Stage
        stage.show();
    }
}