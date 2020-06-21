package com.omer.main;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.http.HttpClient;
import java.util.concurrent.TimeUnit;

public class Main {

    // one instance, reuse
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public static void main(String[] args) throws IOException, InterruptedException {



        long lastLength = 0;

        System.out.println("Starting scraping images");

        for (int i = 434; i < 10000; i++) {

            System.out.println("Info - Image - "+i + " is being scraped");
            URL url = new URL ("https://thispersondoesnotexist.com/image");

            Image image = ImageIO.read(url);

            BufferedImage bufferedImage = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);
            image = image.getScaledInstance(128, 128, Image.SCALE_SMOOTH);


            String path = "/Users/omercemturan/Desktop/cnn_data/";
            String indexString = Integer.toString(i);
            int size = indexString.length();

            String prefix = "";
            for (int j = 0; j <5-size ; j++) {
                prefix+="0";
            }
            String filename = prefix+i+".png";


            File file =  new File(path+filename);

            Graphics g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();

            ImageIO.write(bufferedImage, "png", file);
            TimeUnit.SECONDS.sleep(1);

            if (lastLength == file.length()) {
                System.out.println("Warning - same image scraped again as before, trying again");
                i--;
                continue;
            }


            lastLength = file.length();

        }



    }




}
