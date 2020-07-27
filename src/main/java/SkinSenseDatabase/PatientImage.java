package SkinSenseDatabase;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PatientImage {

    private Image blankImage;
    private Image loadedImage;
    public String filePath;
    ImageView viewImage;
    PatientImage(){
        viewImage=new ImageView();
    }
    PatientImage(PatientImage p){
        viewImage=new ImageView();
        this.viewImage=p.viewImage;
        this.filePath=p.filePath;
        this.loadedImage=p.loadedImage;
    }

    public void load(String file)
    {
        filePath=file;
        try {
            FileInputStream imageStream = new FileInputStream(file);
            loadedImage=new Image(imageStream);
            viewImage.setImage(loadedImage);
            viewImage.setPreserveRatio(true);
            viewImage.setFitHeight(250);
            viewImage.setFitHeight(250);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}

