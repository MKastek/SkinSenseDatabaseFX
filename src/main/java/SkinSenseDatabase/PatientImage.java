package SkinSenseDatabase;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PatientImage {

    private Image loadedImage;
    public String filePath;
    ImageView viewImage;
    PatientImage() throws FileNotFoundException {
        viewImage=new ImageView();
        FileInputStream imageStream = new FileInputStream("./blankImage.png");
        viewImage.setImage(new Image(imageStream));
        viewImage.setPreserveRatio(true);
        viewImage.setFitHeight(SkinSenseFrame.windowHeight*0.25);
        viewImage.setFitHeight(SkinSenseFrame.windowHeight*0.25);
    }

    public void load(String file)
    {
        filePath=file;
        try {
            FileInputStream imageStream = new FileInputStream(file);
            loadedImage=new Image(imageStream);
            viewImage.setImage(loadedImage);
            viewImage.setPreserveRatio(true);
            viewImage.setFitHeight(SkinSenseFrame.windowHeight*0.3);
            viewImage.setFitHeight(SkinSenseFrame.windowHeight*0.3);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}

