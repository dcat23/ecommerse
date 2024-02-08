package com.blit.controllers;

import com.blit.services.BootcampService;
import com.blit.services.BootcampServiceImpl;
import com.blit.exceptions.EmailExistsException;
import com.blit.exceptions.InvalidCredentialsException;
import com.blit.exceptions.InvalidEmailFormatException;
import com.blit.exceptions.UserNotFoundException;
import com.blit.models.*;
import com.blit.utils.Prompt;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.blit.models.User.Type.STUDENT;
import static com.blit.models.User.Type.TEACHER;

public class BootcampController {

    private static final String EMAIL_PROMPT = "What is your email?";
    private static final String PASSWORD_PROMPT = "What is your password?";
    private static final String MENU_PROMPT = "What would you like to do?";

    private static Scanner scan = new Scanner(System.in);
    private final BootcampService bootcampService = new BootcampServiceImpl();

    private static boolean RUNNING = false;

    public static void main(String[] args) {

        BootcampController controller = new BootcampController();

        controller.start();

        while(RUNNING) {
            switch (controller.activeUser().getType()) {
                case TEACHER -> controller.teacherMenu();
                case STUDENT -> controller.studentMenu();
                default -> controller.mainMenu();
            }
        }
    }

    private void start() {
        RUNNING = true;
    }
    private void exit() {
        RUNNING = false;
    }

    private void mainMenu() {

        Prompt menu = null;
        if (activeUser().exists())
        {
            menu = new Prompt.builder(greet())
                    .addOption("Logout")
                    .addOption("Exit")
                    .build();
        } else {
            menu = new Prompt.builder(greet())
                    .addOption("Login")
                    .addOption("Register")
                    .addOption("Exit")
                    .build();
        }

        menu.prompt(scan);

        switch (menu.getAnswer()) {
            case "Login" -> login();
            case "Logout" -> logout();
            case "Register" -> register();
            case "Exit" -> exit();
            default -> mainMenu();
        }

    }

    private User activeUser() {
        return bootcampService.getUser();
    }

    private void login() {
        Prompt email = new Prompt.builder(EMAIL_PROMPT)
                .lowerCase()
                .build();
        Prompt password = new Prompt.builder(PASSWORD_PROMPT)
                .caseSensitive()
                .build();

        email.prompt(scan);
        password.prompt(scan);

        Attempt attempt = new Attempt(3);

        while (attempt.available())
        {
            try {
                bootcampService.authenticate(
                        email.getAnswer(),
                        password.getAnswer());
                attempt.success();

            } catch (UserNotFoundException e) {
                attempt.error(e);
                Prompt tryAgain = new Prompt.builder(MENU_PROMPT)
                        .addOption("Try again")
                        .addOption("Return to main menu")
                        .build();

                tryAgain.prompt(scan);
                if (tryAgain.getSelection() == 2) return;
                email.prompt(scan);

            } catch (InvalidCredentialsException e) {
                attempt.error(e);
                attempt.decrement();
                password.prompt(scan);
            }

        }
    }
    private void register() {
        Prompt userType = new Prompt.builder("Which type of user are you?")
                .addOption(TEACHER.toString())
                .addOption(STUDENT.toString())
                .build();
        Prompt name = new Prompt.builder("What is your name?").build();
        Prompt email = new Prompt.builder("Enter your email").build();
        Prompt password = new Prompt.builder("Choose a password")
                .caseSensitive()
                .build();

        userType.prompt(scan);
        name.prompt(scan);
        email.prompt(scan);
        password.prompt(scan);

        Attempt attempt = new Attempt(3);

        while (attempt.available())
        {
            try {
                bootcampService.register(new NewUser(
                        userType.getAnswer(),
                        name.getAnswer(),
                        email.getAnswer(),
                        password.getAnswer()));
                attempt.success();
            } catch (EmailExistsException | InvalidEmailFormatException e) {
                attempt.error(e);
                email.prompt(scan);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

        System.out.println(email.getAnswer() + " registered as " + userType.getAnswer());

    }
    private void studentMenu() {
        Prompt menu = new Prompt.builder(greet())
                .addOption("Enroll into a new course")
                .addOption("View all courses")
                .addOption("Logout")
                .build();

        menu.prompt(scan);
        switch(menu.getSelection())
        {
            case 1 -> enroll();
            case 2 -> viewCourses();
            case 3 -> logout();
        }
    }
    private void teacherMenu() {
        Prompt menu = new Prompt.builder(greet())
                .addOption("Create a new course")
                .addOption("View created courses")
                .addOption("Logout")
                .build();

        menu.prompt(scan);
        switch(menu.getSelection())
        {
            case 1 -> createCourse();
            case 2 -> viewCourses();
            case 3 -> logout();
        }
    }
    private void logout() {
        bootcampService.resetUser();
    }

    private void viewCourses() {
        List<Course> courses = new ArrayList<>();

        if (activeUser() instanceof Teacher teacher)
        {
            courses = teacher.getCourses();
        }
        else if (activeUser() instanceof Student student)
        {
            courses = student.getCourses();
        }

        if (courses.isEmpty())
        {
            System.out.println("No courses to show");
            return;
        }

        Prompt.builder pb = new Prompt.builder("All Courses");
        for (Course c : courses) {
            pb.addOption(c.getName());
        }

        Prompt course = pb.build();
        course.prompt(scan);

        Course selected = courses.get(course.getSelection() - 1);
        System.out.println(selected.getName());
    }

    private void enroll() {

    }

    private void createCourse() {
        Teacher teacher = (Teacher) activeUser();

        Prompt name = new Prompt.builder("Give the course a name").build();

        name.prompt(scan);

        Attempt attempt = new Attempt(10);

        while (attempt.available())
        {
            if (Course.validName(name.getAnswer()))
            {
                break;
            }
            name.prompt(scan);
        }
        teacher.addCourse(name.getAnswer());
        System.out.println("Added " + name.getAnswer() +
                "by "  + teacher.getName());
    }

    private String greet() {
        String name = activeUser().getName();

        return "\nHello, " + name + "!\n" + MENU_PROMPT;

    }

    static class Attempt {
        private int count;

        Attempt() {
            this.count = 5;
        }

        Attempt(int count) {
            this.count = count;
        }

        boolean available() {
            return count > 0;
        }

        public int decrement() {
            if (available())
            {
                --count;
            }
            return count;
        }

        public void error(Exception e) {
            System.out.println(e.getMessage() + " [attempts: " + count + "]\n");
        }

        public void success() {
            count = 0;
        }
    }
}
