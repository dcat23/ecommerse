package com.blit.utils;

import com.blit.exceptions.InvalidSelectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Prompt {

    private final Logger log = LoggerFactory.getLogger(Prompt.class);

    private final String title;
    private final List<String> options;
    private final Casing casing;
    private String answer;

    enum Casing { UPPER, LOWER, IGNORE, SENSITIVE }

    public Prompt(builder b) {
        this.title = b.title;
        this.options = b.options;
        this.casing = b.casing;
    }

    public static class builder {

        final String title;
        List<String> options;
        Casing casing;

        public builder(String title) {
            this.title = title;
            this.options = new ArrayList<>();
            this.casing = Casing.IGNORE;
        }

        public builder addOption(String option)
        {
            options.add(option);
            return this;
        }

        public builder caseSensitive() {
            casing = Casing.SENSITIVE;
            return this;
        }
        public builder upperCase() {
            casing = Casing.UPPER;
            return this;
        }
        public builder lowerCase() {
            casing = Casing.LOWER;
            return this;
        }



        public Prompt build() {
            return new Prompt(this);
        }
    }

    public void prompt(Scanner scan) {
        log.info(title);

        for (int i=0; i<options.size(); i++) {
            String opt = options.get(i);
            log.info("[{}] {}", (i + 1), opt);
        }


        try {
            answer = validate(scan.nextLine());
        } catch (InvalidSelectionException e) {
            log.error(e.getMessage());
            prompt(scan);
        } catch (NumberFormatException e) {
            log.error("{} must be a number", e.getMessage());
            prompt(scan);
        }
    }

    private String validate(String answer) throws InvalidSelectionException {
        if (options.isEmpty()) return answer;

        int index = Integer.parseInt(answer) - 1;
        if (index >= 0 && index < options.size())
        {
            log.info("[{}]", options.get(index));
            return options.get(index);
        }
        else
        {
            throw new InvalidSelectionException("Selected option " + answer +
                    " must be between 1 and " + options.size());
        }
    }

    public String getAnswer() {
        if (answer == null) {
            throw new RuntimeException("Answer not provided");
        }

        return switch (casing) {
            case LOWER -> answer.toLowerCase();
            case UPPER -> answer.toUpperCase();
            default -> answer;
        };
    }


    /**
     * Starts from index 1
     */
    public int getSelection() {
        if (options.isEmpty())
        {
            return 0;
        }

        return options.indexOf(answer) + 1;

    }

    public static void show(String toDisplay) {
        System.out.println(toDisplay);
    }
}
