package com.blit.controllers;

import com.blit.daos.BootcampDAO;
import com.blit.daos.BootcampDAOImpl;
import com.blit.exceptions.EmailExistsException;
import com.blit.exceptions.InvalidEmailFormatException;
import com.blit.models.*;
import com.blit.utils.Prompt;

import java.util.List;
import java.util.Scanner;

import static com.blit.models.User.Type.STUDENT;
import static com.blit.models.User.Type.TEACHER;

public class BootcampController {

    private static Scanner scan = new Scanner(System.in);
    private final BootcampDAO bootcampDAO = new BootcampDAOImpl();

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
        final String MENU_TITLE = "What would you like to do?";

        Prompt menu = null;
        if (activeUser().exists())
        {
            menu = new Prompt.builder(MENU_TITLE)
                    .addOption("Logout")
                    .addOption("Exit")
                    .build();
        } else {
            menu = new Prompt.builder(MENU_TITLE)
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
        return bootcampDAO.getUser();
    }

    private void login() {
        Prompt email = new Prompt.builder("What is your email?")
                .lowerCase()
                .build();
        Prompt password = new Prompt.builder("What is your password?")
                .caseSensitive()
                .build();

        email.prompt(scan);
        password.prompt(scan);

        bootcampDAO.authenticate(email.getAnswer(), password.getAnswer());
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

        boolean authenticated = false;
        while (!authenticated)
        {
            try {
                authenticated = bootcampDAO.register(new NewUser(
                        userType.getAnswer(),
                        name.getAnswer(),
                        email.getAnswer(),
                        password.getAnswer()));
            } catch (EmailExistsException | InvalidEmailFormatException e) {
                System.out.println(e.getMessage());
                email.prompt(scan);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

        System.out.println(email.getAnswer() + " registered as " + userType);

    }
    private void studentMenu() {
        Prompt menu = new Prompt.builder(
                "What would you like to do?")
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

    private void logout() {
        bootcampDAO.resetUser();
    }

    private void viewCourses() {



        List<Course> courses = bootcampDAO.getCourses();
    }

    private void enroll() {

    }

    private void teacherMenu() {
//        todo: teacher menu
//        create course, view courses,
        Teacher teacher = (Teacher) activeUser();

    }




}
