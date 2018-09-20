package Tests;

import Models.MobilePhone;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MobilePhoneTest {

    MobilePhone validPhone;

    @Before
    public void setUp() throws Exception {
        validPhone = new MobilePhone("Apple","iPhone","iOS",10,64,12,12);
    }

    @Test
    public void setMake() {
        try{
            validPhone.setMake("Fred");
            fail("Fred is not a valid phone manufacturer");
        }
        catch (IllegalArgumentException e){ }
    }

    @Test
    public void setModel() {
        validPhone.setModel("Jaret");
        assertEquals("Jaret", validPhone.getModel());
    }

    @Test
    public void setOs() {
    }

    @Test
    public void setScreenSize() {
    }

    @Test
    public void setMemory() {
    }

    @Test
    public void setFrontCamRes() {
    }

    @Test
    public void setRearCamRes() {
    }

    @Test
    public void setPrice() {
    }

    @Test
    public void setPhoneImage() {
    }
}