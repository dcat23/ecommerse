package com.blit.utils;

import org.junit.Test;

public class PromptTest {

    @Test
    public void promptSelection() {
        Prompt p = new Prompt.builder("test prompt")
                .addOption("firs")
                .addOption("second")
                .addOption("third")
                .build();

    }
}
