package SkinSenseDatabase;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;


public class SkinSenseFrame extends Application {

    Stage window;
    Button findPatientButton, pdfButton, summaryButton, afterTestButton, beforeTestButton;
    Label findPatientLabel;
    TextField findPatientTextField;
    static PatientImage RArm1, RArm2, RArm3, LArm1, LArm2, LArm3;

    Patient currentPatient;

    Label HistaminaL, RoztoczeFarinae, Kot, AspergiliusFurnigatus, RoztoczePteronyssinus, Pies,
            AlternariaAlternata, ProbaUjemnaL, HistaminaR, BylicaPospolita, Leszczyna, Zyto, PiecTraw, Brzoza,
            Tymotka, ProbaUjemnaR;

    static CheckBox HistaminaLEvaluation, HistaminaLNN, RoztoczeFarinaeEvaluation, RoztoczeFarinaeNN,
            KotEvaluation, KotNN, AspergiliusFurnigatusEvaluation, AspergiliusFurnigatusNN,
            RoztoczePteronyssinusEvaluation, RoztoczePteronyssinusNN, PiesEvaluation, PiesNN,
            AlternariaAlternataEvaluation, AlternariaAlternataNN, ProbaUjemnaLEvaluation, ProbaUjemnaLNN,
            HistaminaREvaluation, HistaminaRNN, BylicaPospolitaEvaluation, BylicaPospolitaNN,
            LeszczynaEvaluation, LeszczynaNN, ZytoEvaluation, ZytoNN, PiecTrawEvaluation, PiecTrawNN,
            BrzozaEvaluation, BrzozaNN, TymotkaEvaluation, TymotkaNN, ProbaUjemnaREvaluation, ProbaUjemnaRNN;

    static ArrayList<CheckBox> EVlist;
    static ArrayList<CheckBox> AIlist;
    static ArrayList<Label> Labellist;

    static TextField patientID, patientBirthDate, patientGender, patientBMI;
    Label patientData;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());

        window = stage;
        window.setTitle("SkinSense Database");

        VBox rightMenu = new VBox(5);
        rightMenu.setAlignment(Pos.CENTER);
        rightMenu.setSpacing(2);
        rightMenu.setPadding(new Insets(2, 2, 2, 2));

        findPatientButton = new Button("Find Patient");
        findPatientButton.setFocusTraversable(true);
        findPatientButton.setPrefSize(100, 30);
        findPatientButton.setOnAction(e -> {
            Platform.runLater(() -> {
                if (IDisInt(findPatientTextField)) {
                    try {
                       currentPatient=findPatient(Integer.parseInt(findPatientTextField.getText()));
                    } catch (SQLException | ParseException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        });

        pdfButton = new Button("Report PDF");
        pdfButton.setPrefSize(100, 30);
        pdfButton.setOnAction(e -> {
            pdfWindow();
        });
        summaryButton = new Button("Summary");
        summaryButton.setPrefSize(100, 30);
        summaryButton.setOnAction(event -> {
            Stage Stage = new Stage();
            Table table = new Table();
            table.start(Stage);
            Stage.show();
        });
        findPatientLabel = new Label("Patient ID: ");
        findPatientLabel.setAlignment(Pos.BASELINE_LEFT);

        findPatientTextField = new TextField();
        findPatientTextField.setPromptText("ID");
        findPatientTextField.setPrefSize(80, 30);

        rightMenu.getChildren().addAll(findPatientLabel, findPatientTextField, findPatientButton,pdfButton, summaryButton);


        GridPane allergensData = new GridPane();
        allergensData.setAlignment(Pos.CENTER);
        allergensData.setHgap(10);
        allergensData.setVgap(5);
        allergensData.setPadding(new Insets(10, 10, 10, 10));

        allergensData.add(new Label("Left hand"), 0, 0, 1, 1);
        allergensData.add(new Label("Ev"), 1, 0, 1, 1);
        allergensData.add(new Label("AI"), 2, 0, 1, 1);

        HistaminaL = new Label("H Histamina");

        allergensData.add(HistaminaL, 0, 1, 1, 1);

        HistaminaLEvaluation = new CheckBox();
        HistaminaLEvaluation.setDisable(true);
        HistaminaLNN = new CheckBox();
        HistaminaLNN.setDisable(true);

        allergensData.add(HistaminaLEvaluation, 1, 1, 1, 1);
        allergensData.add(HistaminaLNN, 2, 1, 1, 1);

        RoztoczeFarinae = new Label("A10 Roztocze farinae");
        allergensData.add(RoztoczeFarinae, 0, 2, 1, 1);

        RoztoczeFarinaeEvaluation = new CheckBox();
        RoztoczeFarinaeEvaluation.setDisable(true);
        RoztoczeFarinaeNN = new CheckBox();
        RoztoczeFarinaeNN.setDisable(true);

        allergensData.add(RoztoczeFarinaeEvaluation, 1, 2, 1, 1);
        allergensData.add(RoztoczeFarinaeNN, 2, 2, 1, 1);

        Kot = new Label("A11 Kot");
        allergensData.add(Kot, 0, 3, 1, 1);

        KotEvaluation = new CheckBox();
        KotEvaluation.setDisable(true);
        KotNN = new CheckBox();
        KotNN.setDisable(true);

        allergensData.add(KotEvaluation, 1, 3, 1, 1);
        allergensData.add(KotNN, 2, 3, 1, 1);

        AspergiliusFurnigatus = new Label("A12 Aspergilius furnigatus");
        AspergiliusFurnigatus.setPrefSize(200, 20);
        allergensData.add(AspergiliusFurnigatus, 0, 4, 1, 1);

        AspergiliusFurnigatusEvaluation = new CheckBox();
        AspergiliusFurnigatusEvaluation.setDisable(true);
        AspergiliusFurnigatusNN = new CheckBox();
        AspergiliusFurnigatusNN.setDisable(true);

        allergensData.add(AspergiliusFurnigatusEvaluation, 1, 4, 1, 1);
        allergensData.add(AspergiliusFurnigatusNN, 2, 4, 1, 1);

        allergensData.add(new Label(" "), 3, 0, 1, 1);
        allergensData.add(new Label("Ev"), 4, 0, 1, 1);
        allergensData.add(new Label("AI"), 5, 0, 1, 1);

        RoztoczePteronyssinus = new Label("A13 Roztocze Pteronyssinus");
        allergensData.add(RoztoczePteronyssinus, 3, 1, 1, 1);

        RoztoczePteronyssinusEvaluation = new CheckBox();
        RoztoczePteronyssinusEvaluation.setDisable(true);
        RoztoczePteronyssinusNN = new CheckBox();
        RoztoczePteronyssinusNN.setDisable(true);

        allergensData.add(RoztoczePteronyssinusEvaluation, 4, 1, 1, 1);
        allergensData.add(RoztoczePteronyssinusNN, 5, 1, 1, 1);

        Pies = new Label("A14 Pies");
        Pies.setPrefSize(200, 20);
        allergensData.add(Pies, 3, 2, 1, 1);

        PiesEvaluation = new CheckBox();
        PiesEvaluation.setDisable(true);
        PiesNN = new CheckBox();
        PiesNN.setDisable(true);

        allergensData.add(PiesEvaluation, 4, 2, 1, 1);
        allergensData.add(PiesNN, 5, 2, 1, 1);

        AlternariaAlternata = new Label("A15 Alternaria alternata");
        AlternariaAlternata.setPrefSize(200, 20);
        allergensData.add(AlternariaAlternata, 3, 3, 1, 1);

        AlternariaAlternataEvaluation = new CheckBox();
        AlternariaAlternataEvaluation.setDisable(true);
        AlternariaAlternataNN = new CheckBox();
        AlternariaAlternataNN.setDisable(true);

        allergensData.add(AlternariaAlternataEvaluation, 4, 3, 1, 1);
        allergensData.add(AlternariaAlternataNN, 5, 3, 1, 1);

        ProbaUjemnaL = new Label("Próba ujemna");
        allergensData.add(ProbaUjemnaL, 3, 4, 1, 1);

        ProbaUjemnaLEvaluation = new CheckBox();
        ProbaUjemnaLEvaluation.setDisable(true);
        ProbaUjemnaLNN = new CheckBox();
        ProbaUjemnaLNN.setDisable(true);

        allergensData.add(ProbaUjemnaLEvaluation, 4, 4, 1, 1);
        allergensData.add(ProbaUjemnaLNN, 5, 4, 1, 1);

        allergensData.add(new Label("Right hand"), 6, 0, 1, 1);
        allergensData.add(new Label("Ev"), 7, 0, 1, 1);
        allergensData.add(new Label("AI"), 8, 0, 1, 1);

        HistaminaR = new Label("H Histamina");
        HistaminaR.setPrefSize(200, 20);
        allergensData.add(HistaminaR, 6, 1, 1, 1);

        HistaminaREvaluation = new CheckBox();
        HistaminaREvaluation.setDisable(true);
        HistaminaRNN = new CheckBox();
        HistaminaRNN.setDisable(true);

        allergensData.add(HistaminaREvaluation, 7, 1, 1, 1);
        allergensData.add(HistaminaRNN, 8, 1, 1, 1);

        BylicaPospolita = new Label("A1 Bylica Pospolita");
        allergensData.add(BylicaPospolita, 6, 2, 1, 1);

        BylicaPospolitaEvaluation = new CheckBox();
        BylicaPospolitaEvaluation.setDisable(true);
        BylicaPospolitaNN = new CheckBox();
        BylicaPospolitaNN.setDisable(true);

        allergensData.add(BylicaPospolitaEvaluation, 7, 2, 1, 1);
        allergensData.add(BylicaPospolitaNN, 8, 2, 1, 1);

        Leszczyna = new Label("A2 Leszyczna");
        Leszczyna.setPrefSize(200, 20);
        allergensData.add(Leszczyna, 6, 3, 1, 1);

        LeszczynaEvaluation = new CheckBox();
        LeszczynaEvaluation.setDisable(true);
        LeszczynaNN = new CheckBox();
        LeszczynaNN.setDisable(true);

        allergensData.add(LeszczynaEvaluation, 7, 3, 1, 1);
        allergensData.add(LeszczynaNN, 8, 3, 1, 1);

        Zyto = new Label("A3 Żyto");
        Zyto.setPrefSize(200, 20);
        allergensData.add(Zyto, 6, 4, 1, 1);

        ZytoEvaluation = new CheckBox();
        ZytoEvaluation.setDisable(true);
        ZytoNN = new CheckBox();
        ZytoNN.setDisable(true);

        allergensData.add(ZytoEvaluation, 7, 4, 1, 1);
        allergensData.add(ZytoNN, 8, 4, 1, 1);

        allergensData.add(new Label(" "), 9, 0, 1, 1);
        allergensData.add(new Label("Ev"), 10, 0, 1, 1);
        allergensData.add(new Label("AI"), 11, 0, 1, 1);

        PiecTraw = new Label("A4 Pięć Traw");
        PiecTraw.setPrefSize(200, 20);
        allergensData.add(PiecTraw, 9, 1, 1, 1);

        PiecTrawEvaluation = new CheckBox();
        PiecTrawEvaluation.setDisable(true);
        PiecTrawNN = new CheckBox();
        PiecTrawNN.setDisable(true);

        allergensData.add(PiecTrawEvaluation, 10, 1, 1, 1);
        allergensData.add(PiecTrawNN, 11, 1, 1, 1);

        Brzoza = new Label("A5 Brzoza");
        Brzoza.setPrefSize(200, 20);
        allergensData.add(Brzoza, 9, 2, 1, 1);

        BrzozaEvaluation = new CheckBox();
        BrzozaEvaluation.setDisable(true);
        BrzozaNN = new CheckBox();
        BrzozaNN.setDisable(true);

        allergensData.add(BrzozaEvaluation, 10, 2, 1, 1);
        allergensData.add(BrzozaNN, 11, 2, 1, 1);

        Tymotka = new Label("A6 Tymotka");
        Tymotka.setPrefSize(200, 20);
        allergensData.add(Tymotka, 9, 3, 1, 1);

        TymotkaEvaluation = new CheckBox();
        TymotkaEvaluation.setDisable(true);
        TymotkaNN = new CheckBox();
        TymotkaNN.setDisable(true);

        allergensData.add(TymotkaEvaluation, 10, 3, 1, 1);
        allergensData.add(TymotkaNN, 11, 3, 1, 1);

        ProbaUjemnaR = new Label("Próba ujemna");
        ProbaUjemnaR.setPrefSize(200, 20);
        allergensData.add(ProbaUjemnaR, 9, 4, 1, 1);

        ProbaUjemnaREvaluation = new CheckBox();
        ProbaUjemnaREvaluation.setDisable(true);
        ProbaUjemnaRNN = new CheckBox();
        ProbaUjemnaRNN.setDisable(true);

        allergensData.add(ProbaUjemnaREvaluation, 10, 4, 1, 1);
        allergensData.add(ProbaUjemnaRNN, 11, 4, 1, 1);
        BorderPane borderPane = new BorderPane();
        borderPane.setRight(rightMenu);
        borderPane.setBottom(allergensData);

        HBox Data = new HBox();
        Data.setPadding(new Insets(20, 2, 2, 250));
        Data.setSpacing(5);

        patientData = new Label("Patient Data: ");
        patientData.setAlignment(Pos.CENTER);
        patientData.setPadding(new Insets(3, 0, 0, 0));

        patientID = new TextField("ID: ");
        patientID.setPrefSize(75, 10);
        patientID.setEditable(false);

        patientBirthDate = new TextField("Birth Date: ");
        patientBirthDate.setPrefSize(175, 10);
        patientBirthDate.setEditable(false);

        patientGender = new TextField("Gender: ");
        patientGender.setPrefSize(125, 10);
        patientGender.setEditable(false);

        patientBMI = new TextField("BMI: ");
        patientBMI.setPrefSize(125, 10);
        patientBMI.setEditable(false);

        Label testLabel = new Label("Test: ");
        testLabel.setPadding(new Insets(3, 0, 0, 0));

        afterTestButton = new Button("After Test");
        afterTestButton.setOnAction(e->{
            loadImageFiles(currentPatient,"1");
        });

        beforeTestButton = new Button("Before Test");
        beforeTestButton.setOnAction(e->{
            loadImageFiles(currentPatient,"0");
        });

        Data.getChildren().addAll(patientData, patientID, patientBirthDate, patientGender, patientBMI,testLabel,afterTestButton,beforeTestButton);
        borderPane.setTop(Data);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 30));

        LArm1= new PatientImage();
        LArm2 = new PatientImage();
        LArm3 = new PatientImage();

        RArm1 = new PatientImage();
        RArm2 = new PatientImage();
        RArm3= new PatientImage();

        LArm1.viewImage.setOnMouseClicked(event -> {
            fullSizeImage(LArm1);
        });
        LArm2.viewImage.setOnMouseClicked(event -> {
            fullSizeImage(LArm2);
        });
        LArm3.viewImage.setOnMouseClicked(event -> {
            fullSizeImage(LArm3);
        });

        RArm1.viewImage.setOnMouseClicked(event -> {
            fullSizeImage(RArm1);
        });
        RArm2.viewImage.setOnMouseClicked(event -> {
            fullSizeImage(RArm2);
        });
        RArm3.viewImage.setOnMouseClicked(event -> {
            fullSizeImage(RArm3);
        });
        Label leftForeArm=new Label("Right \nForearm");
        leftForeArm.setFont(Font.font("Verdana", FontWeight.BOLD, 20));;
        leftForeArm.setPrefHeight(200);

        Label rightForeArm=new Label("Left \nForearm");
        rightForeArm.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        rightForeArm.setPrefHeight(200);

        gridPane.add(leftForeArm,0,0);
        gridPane.add(rightForeArm,0,1);

        gridPane.add(RArm1.viewImage, 1, 0);
        gridPane.add(RArm2.viewImage, 2, 0);
        gridPane.add(RArm3.viewImage, 3, 0);

        gridPane.add(LArm1.viewImage, 1, 1);
        gridPane.add(LArm2.viewImage, 2, 1);
        gridPane.add(LArm3.viewImage, 3, 1);

        borderPane.setCenter(gridPane);

        //Arraylist of GUI elements
        EVlist = new ArrayList<CheckBox>(16) {
            {
                add(HistaminaREvaluation);
                add(BylicaPospolitaEvaluation);
                add(LeszczynaEvaluation);
                add(ZytoEvaluation);
                add(PiecTrawEvaluation);
                add(BrzozaEvaluation);
                add(TymotkaEvaluation);
                add(ProbaUjemnaREvaluation);
                add(HistaminaLEvaluation);
                add(RoztoczeFarinaeEvaluation);
                add(KotEvaluation);
                add(AspergiliusFurnigatusEvaluation);
                add(RoztoczePteronyssinusEvaluation);
                add(PiesEvaluation);
                add(AlternariaAlternataEvaluation);
                add(ProbaUjemnaLEvaluation);
            }
        };
        AIlist = new ArrayList<CheckBox>(16) {
            {
                add(HistaminaRNN);
                add(BylicaPospolitaNN);
                add(LeszczynaNN);
                add(ZytoNN);
                add(PiecTrawNN);
                add(BrzozaNN);
                add(TymotkaNN);
                add(ProbaUjemnaRNN);
                add(HistaminaLNN);
                add(RoztoczeFarinaeNN);
                add(KotNN);
                add(AspergiliusFurnigatusNN);
                add(RoztoczePteronyssinusNN);
                add(PiesNN);
                add(AlternariaAlternataNN);
                add(ProbaUjemnaLNN);
            }
        };
        Labellist = new ArrayList<Label>(16){
            {
                add(HistaminaR);
                add(BylicaPospolita);
                add(Leszczyna);
                add(Zyto);
                add(PiecTraw);
                add(Brzoza);
                add(Tymotka);
                add(ProbaUjemnaR);
                add(HistaminaL);
                add(RoztoczeFarinae);
                add(Kot);
                add(AspergiliusFurnigatus);
                add(RoztoczePteronyssinus);
                add(Pies);
                add(AlternariaAlternata);
                add(ProbaUjemnaL);
            }
        };

        Scene scene = new Scene(borderPane);
        window.setScene(scene);
        window.show();
        SkinSenseDataBase.createDB();
    }

    private boolean IDisInt(TextField input) {
        String errMsgOutofRange = "ID is out of range!";
        try {
            int IDtoFind = Integer.parseInt(input.getText());
            if (IDtoFind <= 0 || IDtoFind > SkinSenseDataBase.patientCount()) {
                throw new NumberFormatException(errMsgOutofRange);
            }
            return true;
        } catch (NumberFormatException e) {
            if (e.getMessage().equals(errMsgOutofRange)) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Input not valid");
                errorAlert.setContentText("ID is out of range!");
                errorAlert.showAndWait();

            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Input not valid");
                errorAlert.setContentText("ID is a number!");
                errorAlert.showAndWait();
            }
            return false;
        }
    }

    static void addTree(File file, Collection<File> all) {
        File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                all.add(child);
                addTree(child, all);
            }
        }
    }

    public static Patient findPatient(int IDtoFind) throws SQLException, ParseException {
        Patient patient;
        patient = SkinSenseDataBase.Connection(IDtoFind);
        patientID.setText("ID: " + patient.getID());
        patientBirthDate.setText("Birth Date: " + new SimpleDateFormat("dd.MM.yyyy").format(patient.getBirthDate()));
        if (patient.getGender() == 0) patientGender.setText("Gender: " + "female");
        else patientGender.setText("Gender: " + "male");
        patientBMI.setText("BMI: " + patient.getBMI());

        for (int i = 0; i < 16; i++) {
            if (patient.alergeny.get(i).evaluation == 1)
            {
                EVlist.get(i).setSelected(true);
            }
            else
            {
                EVlist.get(i).setSelected(false);
            }
            if (patient.alergeny.get(i).decison == 1)
            {
                AIlist.get(i).setSelected(true);
            }
            else
            {
                AIlist.get(i).setSelected(false);
            }
            if(patient.alergeny.get(i).evaluation == 1 || patient.alergeny.get(i).decison == 1)
            {
                Labellist.get(i).setFont(Font.font("Verdana", FontWeight.BOLD, 13));
                Labellist.get(i).setTextFill(Color.RED);
            }
            else
            {
                Labellist.get(i).setFont(Font.getDefault());
                Labellist.get(i).setTextFill(Color.BLACK);
            }
        }
        loadImageFiles(patient,"1");
        loadImageFiles(patient,"0");
        return patient;
    }

    public static void loadImageFiles(Patient patient,String stringFolder) {
        int tmp = 0;
        Collection<File> all = new ArrayList<File>();
        String IDFolder = "";
        if (patient.getID() < 10)
            IDFolder = "00" + Integer.toString(patient.getID());
        else if (patient.getID() < 100)
            IDFolder = "0" + Integer.toString(patient.getID());
        else if (patient.getID() < 1000)
            IDFolder = Integer.toString(patient.getID());
        addTree(new File("." + File.separator + "data" + File.separator + IDFolder + File.separator + stringFolder + File.separator), all);
        File[] ImageArray = new File[all.size()];
        ImageArray = all.toArray(ImageArray);
        for (int i = 0; i < all.size(); i++) {

            if (ImageArray[i].toString().charAt(13) == '1' && ImageArray[i].toString().charAt(39) == '2') {
                //System.out.println("LARM1:  "+ImageArray[i].toString());
                    LArm1.load(ImageArray[i].toString());
                    tmp++;
                    LArm1.viewImage.setVisible(true);
                    if(stringFolder.equals("0")) patient.LArm1FilePathBT=ImageArray[i].toString();
                    if(stringFolder.equals("1")) patient.LArm1FilePathAT=ImageArray[i].toString();
            }
            if (ImageArray[i].toString().charAt(13) == '2' && ImageArray[i].toString().charAt(39) == '2') {
                //System.out.println("LARM2:  "+ImageArray[i].toString());
                    LArm2.load(ImageArray[i].toString());
                    tmp++;
                    LArm2.viewImage.setVisible(true);
                    if(stringFolder.equals("0")) patient.LArm2FilePathBT=ImageArray[i].toString();
                    if(stringFolder.equals("1")) patient.LArm2FilePathAT=ImageArray[i].toString();
            }
            if (ImageArray[i].toString().charAt(13) == '3' && ImageArray[i].toString().charAt(39) == '2') {
                //System.out.println("RARM3:  "+ImageArray[i].toString());
                    LArm3.load(ImageArray[i].toString());
                    tmp++;
                    LArm3.viewImage.setVisible(true);
                    if(stringFolder.equals("0")) patient.LArm3FilePathBT=ImageArray[i].toString();
                    if(stringFolder.equals("1")) patient.LArm3FilePathAT=ImageArray[i].toString();
            }
            if (ImageArray[i].toString().charAt(13) == '1' && ImageArray[i].toString().charAt(39) == '1') {
                //System.out.println("RARM1:  "+ImageArray[i].toString());
                    RArm1.load(ImageArray[i].toString());
                    tmp++;
                    RArm1.viewImage.setVisible(true);
                    if(stringFolder.equals("0")) patient.RArm1FilePathBT=ImageArray[i].toString();
                    if(stringFolder.equals("1")) patient.RArm1FilePathAT=ImageArray[i].toString();
            }
            if (ImageArray[i].toString().charAt(13) == '2' && ImageArray[i].toString().charAt(39) == '1') {
                //System.out.println("RARM2:  "+ImageArray[i].toString());
                    RArm2.load(ImageArray[i].toString());
                    tmp++;
                    RArm2.viewImage.setVisible(true);
                    if(stringFolder.equals("0")) patient.RArm2FilePathBT=ImageArray[i].toString();
                    if(stringFolder.equals("1")) patient.RArm2FilePathAT=ImageArray[i].toString();
            }
            if (ImageArray[i].toString().charAt(13) == '3' && ImageArray[i].toString().charAt(39) == '1') {
                //System.out.println("RARM3:  "+ImageArray[i].toString());
                    RArm3.load(ImageArray[i].toString());
                    tmp++;
                    RArm3.viewImage.setVisible(true);
                    if(stringFolder.equals("0")) patient.RArm3FilePathBT=ImageArray[i].toString();
                    if(stringFolder.equals("1")) patient.RArm3FilePathAT=ImageArray[i].toString();
            }
        }
        if (tmp == 0) {
            LArm1.viewImage.setVisible(false);
            LArm2.viewImage.setVisible(false);
            LArm3.viewImage.setVisible(false);
            RArm1.viewImage.setVisible(false);
            RArm2.viewImage.setVisible(false);
            RArm3.viewImage.setVisible(false);
        }
    }
    public void fullSizeImage(PatientImage patientImage)
    {
        Stage Window = new Stage();
        Window.setTitle("FullSizeImage");
        BorderPane border = new BorderPane();
        VBox box = new VBox();
        try {
            Image image = new Image(new FileInputStream(patientImage.filePath));
            box.getChildren().addAll(new ImageView(image));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        border.setCenter(box);
        Scene scene1 = new Scene(border, patientImage.viewImage.getImage().getWidth(), patientImage.viewImage.getImage().getHeight());
        Window.initModality(Modality.WINDOW_MODAL);
        Window.setScene(scene1);
        Window.show();
    }
    public void pdfWindow()
    {
        Stage newWindow = new Stage();
        newWindow.setTitle("Comment");
        BorderPane borderPane = new BorderPane();

        VBox Text = new VBox(1);
        Text.setPadding(new Insets(10, 10, 10, 10));
        TextArea textArea = new TextArea();
        textArea.setPromptText("Leave comment about patient");
        textArea.setFocusTraversable(false);
        Text.getChildren().addAll(textArea);
        borderPane.setCenter(Text);

        Label label = new Label("Patient ID: ");
        label.setPadding(new Insets(4, 0, 0, 0));

        TextField textField = new TextField();
        textField.setPromptText("ID");
        textField.setFocusTraversable(false);

        HBox Data = new HBox();
        Data.setPadding(new Insets(20, 2, 20, 100));
        Data.setSpacing(5);

        Data.getChildren().addAll(label, textField);
        borderPane.setTop(Data);

        Button generatePDF = new Button("Generate PDF");
        VBox button = new VBox(1);
        button.setPadding(new Insets(0, 0, 10, 0));
        button.setAlignment(Pos.CENTER);
        button.getChildren().add(generatePDF);
        borderPane.setBottom(button);
        generatePDF.setOnAction(event -> {

            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save");
                FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "report.pdf");
                fileChooser.getExtensionFilters().add(filter);
                File selectedFile = fileChooser.showSaveDialog(newWindow);
                if (selectedFile != null && IDisInt(textField)) {
                    Patient p = findPatient(Integer.parseInt(textField.getText()));
                    PatientPDF.PDF(Integer.parseInt(textField.getText()), selectedFile, textArea.getText());
                    HostServices hostServices = getHostServices();
                    hostServices.showDocument(selectedFile.toURI().toString());
                }
                newWindow.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ParseException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        Scene scene = new Scene(borderPane, 500, 300);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.setScene(scene);
        newWindow.show();
    }
}