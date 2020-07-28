package SkinSenseDatabase;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.element.Table;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class PatientPDF {
    Patient patient;
    static File file;

    static ArrayList<String> alergeny = new ArrayList<String>(){
        {
            add("Histamina");
            add("Bylica Pospolita");
            add("Leszczyna");
            add("Zyto");
            add("Piec Traw");
            add("Brzoza");
            add("Tymotka");
            add("Proba Ujemna");
            add("Histamina");
            add("Roztocze Farinae");
            add("Kot");
            add("Aspergilius Furnigatus");
            add("Roztocze Pteronyssinus");
            add("Pies");
            add("Alternaria Alernata");
            add("Proba Ujemna");
        }
    };

    public static void PDF(int ID, File selectedFile,String comment) throws IOException {
        file = selectedFile;
        try {
            new PatientPDF().createPdf(file.getPath(),ID,comment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createPdf(String dest, int ID,String comment) throws IOException, SQLException {
        try {
            patient = SkinSenseFrame.findPatient(ID);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        FileOutputStream fos = new FileOutputStream(file.getAbsoluteFile());
        PdfWriter writer = new PdfWriter(fos);

        PdfDocument pdf = new PdfDocument(writer);

        Document document = new Document(pdf);
        float [] pointColumnWidths1 = {85F, 85F, 85F, 85F,85F, 85F};
        float [] pointColumnWidths2 = {150F,75F,75F,75F,75F};
        float [] pointColumnWidths3 = {170F,170F,170F};

        Table dataTable1 = new  Table(pointColumnWidths1);
        Table dataTable2 = new  Table(pointColumnWidths2);
        Table dataTable3 = new  Table(pointColumnWidths2);
        Table table1 = new Table(pointColumnWidths3);
        Table table2 = new  Table(pointColumnWidths3);

        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        Text title = new Text("Report "+"Patient "+ID).setFont(font).setFontSize(15F);
        Paragraph p = new Paragraph().add(title);
        p.setTextAlignment(TextAlignment.CENTER);
        document.add(p);

        document.add(new Paragraph("Personal data").setBold());

        dataTable1.addCell(new Cell().add("ID:").setBackgroundColor(Color.CYAN));
        dataTable1.addCell(new Cell().add("Birth Date:").setBackgroundColor(Color.CYAN));
        dataTable1.addCell(new Cell().add("Gender:").setBackgroundColor(Color.CYAN));
        dataTable1.addCell(new Cell().add("BMI:").setBackgroundColor(Color.CYAN));
        dataTable1.addCell(new Cell().add("Prick Date:").setBackgroundColor(Color.CYAN));
        dataTable1.addCell(new Cell().add("Invitro Date:").setBackgroundColor(Color.CYAN));

        dataTable1.addCell(new Cell().add(String.valueOf(patient.getID())));
        dataTable1.addCell(new Cell().add(new SimpleDateFormat("dd.MM.yyyy").format(patient.getBirthDate())));
        if(patient.getGender()==1) dataTable1.addCell(new Cell().add("male"));
        else  dataTable1.addCell(new Cell().add("female"));
        dataTable1.addCell(new Cell().add(String.format("%.2f", patient.getBMI())));
        dataTable1.addCell(new Cell().add(new SimpleDateFormat("dd.MM.yyyy").format(patient.getPrickDate())));
        dataTable1.addCell(new Cell().add(new SimpleDateFormat("dd.MM.yyyy").format(patient.getInVitroDate())));

        dataTable2.addCell(new Cell().add("Alergen").setBold().setBackgroundColor(Color.CYAN));
        dataTable2.addCell(new Cell().add("Bubble [mm]").setBold());
        dataTable2.addCell(new Cell().add("Clinical evaluation").setBold());
        dataTable2.addCell(new Cell().add("Neural network").setBold());
        dataTable2.addCell(new Cell().add("AI decision").setBold());


        for(int i=0; i<8; i++)
        {
            dataTable2.addCell(new Cell().add(alergeny.get(i)).setBackgroundColor(Color.CYAN));
            dataTable2.addCell(new Cell().add(String.valueOf(patient.alergenyHashMap.get(Patient.alergenyString.get(i)).bubble)));
            dataTable2.addCell(new Cell().add(String.valueOf(patient.alergenyHashMap.get(Patient.alergenyString.get(i)).evaluation)));
            dataTable2.addCell(new Cell().add(String.valueOf(patient.alergenyHashMap.get(Patient.alergenyString.get(i)).probabilityNN)));
            dataTable2.addCell(new Cell().add(String.valueOf(patient.alergenyHashMap.get(Patient.alergenyString.get(i)).decison)));
        }

        dataTable3.addCell(new Cell().add("Alergen").setBold().setBackgroundColor(Color.CYAN));
        dataTable3.addCell(new Cell().add("Bubble [mm]").setBold());
        dataTable3.addCell(new Cell().add("Clinical evaluation").setBold());
        dataTable3.addCell(new Cell().add("Neural network").setBold());
        dataTable3.addCell(new Cell().add("AI decision").setBold());

        for(int i=8; i<16; i++)
        {
            dataTable3.addCell(new Cell().add(alergeny.get(i)).setBackgroundColor(Color.CYAN));
            dataTable3.addCell(new Cell().add(String.valueOf(patient.alergenyHashMap.get(Patient.alergenyString.get(i)).bubble)));
            dataTable3.addCell(new Cell().add(String.valueOf(patient.alergenyHashMap.get(Patient.alergenyString.get(i)).evaluation)));
            dataTable3.addCell(new Cell().add(String.valueOf(patient.alergenyHashMap.get(Patient.alergenyString.get(i)).probabilityNN)));
            dataTable3.addCell(new Cell().add(String.valueOf(patient.alergenyHashMap.get(Patient.alergenyString.get(i)).decison)));
        }

        dataTable2.setHorizontalAlignment(HorizontalAlignment.CENTER);
        dataTable2.setTextAlignment(TextAlignment.CENTER);
        document.add(dataTable1);
        document.add(new Paragraph( "\n"));
        document.add(new Paragraph("Right forearm").setBold().setFontSize(14).setHorizontalAlignment(HorizontalAlignment.CENTER));
        document.add(dataTable2);

        document.add(new Paragraph("Left forearm").setBold().setFontSize(14).setHorizontalAlignment(HorizontalAlignment.CENTER));
        dataTable3.setHorizontalAlignment(HorizontalAlignment.CENTER);
        dataTable3.setTextAlignment(TextAlignment.CENTER);
        document.add(dataTable3);

        ImageData LArm1BT = ImageDataFactory.create(patient.LArm1FilePathBT);
        Image LArm1imageBT = new Image(LArm1BT);

        ImageData LArm2BT = ImageDataFactory.create(patient.LArm2FilePathBT);
        Image LArm2imageBT = new Image(LArm2BT);

        ImageData LArm3BT = ImageDataFactory.create(patient.LArm3FilePathBT);
        Image LArm3imageBT = new Image(LArm3BT);

        ImageData RArm1BT = ImageDataFactory.create(patient.RArm1FilePathBT);
        Image RArm1imageBT = new Image(RArm1BT);

        ImageData RArm2BT = ImageDataFactory.create(patient.RArm2FilePathBT);
        Image RArm2imageBT = new Image(RArm2BT);

        ImageData RArm3BT = ImageDataFactory.create(patient.RArm3FilePathBT);
        Image RArm3imageBT = new Image(RArm3BT);

        ImageData LArm1AT = ImageDataFactory.create(patient.LArm1FilePathAT);
        Image LArm1imageAT = new Image(LArm1AT);

        ImageData LArm2AT = ImageDataFactory.create(patient.LArm2FilePathAT);
        Image LArm2imageAT = new Image(LArm2AT);

        ImageData LArm3AT = ImageDataFactory.create(patient.LArm3FilePathAT);
        Image LArm3imageAT = new Image(LArm3AT);

        ImageData RArm1AT = ImageDataFactory.create(patient.RArm1FilePathAT);
        Image RArm1imageAT = new Image(RArm1AT);

        ImageData RArm2AT = ImageDataFactory.create(patient.RArm2FilePathAT);
        Image RArm2imageAT = new Image(RArm2AT);

        ImageData RArm3AT = ImageDataFactory.create(patient.RArm3FilePathAT);
        Image RArm3imageAT = new Image(RArm3AT);

        document.add(new Paragraph( "\n\n\n\n\n"));
        Text titleBeforeTest = new Text("Before Test").setFont(font).setFontSize(13F);
        Paragraph beforeTest = new Paragraph().add(titleBeforeTest);

        table1.addCell(LArm1imageBT.scaleToFit(160,120));
        table1.addCell(LArm2imageBT.scaleToFit(160,170));
        table1.addCell(LArm3imageBT.scaleToFit(160,170));
        table1.addCell(RArm1imageBT.scaleToFit(160,120));
        table1.addCell(RArm2imageBT.scaleToFit(160,170));
        table1.addCell(RArm3imageBT.scaleToFit(160,170));

        document.add(new Paragraph( "\n"));
        Text titleAfterTest = new Text("After Test").setFont(font).setFontSize(13F);
        Paragraph afterTest = new Paragraph().add(titleAfterTest);


        table2.addCell(LArm1imageAT.scaleToFit(160,120));
        table2.addCell(LArm2imageAT.scaleToFit(160,170));
        table2.addCell(LArm3imageAT.scaleToFit(160,170));
        table2.addCell(RArm1imageAT.scaleToFit(160,120));
        table2.addCell(RArm2imageAT.scaleToFit(160,170));
        table2.addCell(RArm3imageAT.scaleToFit(160,170));

        document.add(beforeTest);
        document.add(table1);
        document.add(new Paragraph( "\n"));
        document.add(afterTest);
        document.add(table2);

        document.add(new Paragraph( "User comment: \n").setBold());
        document.add(new Paragraph( comment));
        document.close();
    }



}
