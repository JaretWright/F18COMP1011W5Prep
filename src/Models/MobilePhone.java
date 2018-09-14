package Models;

import javafx.scene.image.Image;

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

    public MobilePhone(String make, String model, String os, double screenSize, double memory, double frontCamRes, double rearCamRes) {
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

}
