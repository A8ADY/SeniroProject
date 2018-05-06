package interfaces;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Arbiter on 04/05/2018.
 */

public class SignUpTest {
    SignUp test;
    @Test
    public void isValid() throws Exception {

        String email = "aba@a.com";
        test = new SignUp();
        assertTrue(test.isValid(email));

    }

    @Test
    public void validEmail() throws Exception {

        String email = "aba@abcom";
        assertFalse(test.isValid(email));

    }
}