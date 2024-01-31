package com.blit.utils;


import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidationTest {

    @Test
    public void goodEmail() {
        String e = "dcat@gmail.com";

        assertTrue(Validation.checkEmail(e));
    }

    @Test
    public void missingAt() {
        String e = "dcatgmail.com";

        assertFalse(Validation.checkEmail(e));
    }
    @Test
    public void missingPeriod() {
        String e = "dcat@gmailcom";

        assertFalse(Validation.checkEmail(e));
    }
}
