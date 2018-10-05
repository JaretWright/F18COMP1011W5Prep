package Models;

import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class MobilePhone {

    private String make, model, os;
    private double screenSize, memory, frontCamRes, rearCamRes, price;
    private Image phoneImage;

    public MobilePhone(String make, String model, String os, double screenSize, double memory, double frontCamRes, double rearCamRes, double price, Image phoneImage) {
        this(make, model, os, screenSize, memory, frontCamRes, rearCamRes);
        setPrice(price);
        setPhoneImage(phoneImage);
    }

    public MobilePhone(String make, String model, String os, double screenSize, double memory, double frontCamRes, double rearCamRes)
    {
        setMake(make);
        setModel(model);
        setOs(os);
        setScreenSize(screenSize);
        setMemory(memory);
        setFrontCamRes(frontCamRes);
        setRearCamRes(rearCamRes);
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        ArrayList<String> validMakes = null;

        try
        {
            //get valid mobile phone manufacturers from database
            validMakes = DBConnect.getPhoneManufacturers();
            if (validMakes.contains(make))
                this.make = make;
            else
                throw new IllegalArgumentException("Valid manufacturers are: " + validMakes.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        if (!model.isEmpty() && model.length() <= 30)
            this.model = model;
        else
            throw new IllegalArgumentException("model name cannot be empty and" +
                    "must be less than 30 characters");
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public double getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(double screenSize) {
        this.screenSize = screenSize;
    }

    public double getMemory() {
        return memory;
    }

    public void setMemory(double memory) {
        this.memory = memory;
    }

    public double getFrontCamRes() {
        return frontCamRes;
    }

    public void setFrontCamRes(double frontCamRes) {
        this.frontCamRes = frontCamRes;
    }

    public double getRearCamRes() {
        return rearCamRes;
    }

    public void setRearCamRes(double rearCamRes) {
        this.rearCamRes = rearCamRes;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Image getPhoneImage() {
        return phoneImage;
    }

    public void setPhoneImage(Image phoneImage) {
        this.phoneImage = phoneImage;
    }

    public String toString()
    {
        return String.format("%s %s with %.0f gigs of memory", this.make,
                                this.model, this.memory);
    }

    /**
     * This method will copy the file specified to the images directory on this server and give it
     * a unique name
     */
    public void copyImageFile() throws IOException
    {
        //create a new Path to copy the image into a local directory
        Path sourcePath = imageFile.toPath();

        String uniqueFileName = getUniqueFileName(imageFile.getName());

        Path targetPath = Paths.get("./src/images/"+uniqueFileName);

        //copy the file to the new directory
        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

        //update the imageFile to point to the new File
        imageFile = new File(targetPath.toString());
    }

    /**
     * This method will receive a String that represents a file name and return a
     * String with a random, unique set of letters prefixed to it
     */
    private String getUniqueFileName(String oldFileName)
    {
        String newName;

        //create a Random Number Generator
        SecureRandom rng = new SecureRandom();

        //loop until we have a unique file name
        do
        {
            newName = "";

            //generate 32 random characters
            for (int count=1; count <=32; count++)
            {
                int nextChar;

                do
                {
                    nextChar = rng.nextInt(123);
                } while(!validCharacterValue(nextChar));

                newName = String.format("%s%c", newName, nextChar);
            }
            newName += oldFileName;

        } while (!uniqueFileInDirectory(newName));

        return newName;
    }

    /**
     * This method will search the images directory and ensure that the file name
     * is unique
     */
    public boolean uniqueFileInDirectory(String fileName)
    {
        File directory = new File("./src/images/");

        File[] dir_contents = directory.listFiles();

        for (File file: dir_contents)
        {
            if (file.getName().equals(fileName))
                return false;
        }
        return true;
    }

    /**
     * This method will validate if the integer given corresponds to a valid
     * ASCII character that could be used in a file name
     */
    public boolean validCharacterValue(int asciiValue)
    {

        //0-9 = ASCII range 48 to 57
        if (asciiValue >= 48 && asciiValue <= 57)
            return true;

        //A-Z = ASCII range 65 to 90
        if (asciiValue >= 65 && asciiValue <= 90)
            return true;

        //a-z = ASCII range 97 to 122
        if (asciiValue >= 97 && asciiValue <= 122)
            return true;

        return false;
    }

}
