package com.blit.utils;

import com.blit.exceptions.InvalidSelectionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Prompt {

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
        System.out.println(title);

        for (int i=0; i<options.size(); i++) {
            String opt = options.get(i);
            System.out.println("[" + (i + 1) + "] " + opt);
        }


        try {
            answer = validate(scan.nextLine());
        } catch (InvalidSelectionException e) {
            throw new RuntimeException(e);
        }



    }

    private String validate(String answer) throws InvalidSelectionException {
        if (!options.isEmpty()) {
            int index = Integer.parseInt(answer) - 1;
            if (index >= options.size() || index < 0)
            {
                throw new InvalidSelectionException("Selected option " + answer +
                        " must be between 1 and " + options.size());
            }
            System.out.println(options.get(index));
            return options.get(index);
        }

        return answer;
    }

    public String getAnswer() {
        if (answer == null) {
            throw new RuntimeException("answer not provided");
        }

        return switch (casing) {
            case LOWER -> answer.toLowerCase();
            case UPPER -> answer.toUpperCase();
            default -> answer;
        };
    }


    public int getSelection() {
        if (options.isEmpty())
        {
            return Integer.parseInt(answer);
        }

        return options.indexOf(answer);
    }
}
