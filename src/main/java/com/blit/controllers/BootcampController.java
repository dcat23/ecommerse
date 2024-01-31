package com.blit.controllers;

import com.blit.daos.BootcampDAO;
import com.blit.daos.BootcampDAOImpl;
import com.blit.exceptions.EmailExistsException;
import com.blit.models.Guest;
import com.blit.models.Student;
import com.blit.models.Teacher;
import com.blit.models.User;
import com.blit.utils.Prompt;

import java.util.Base64;
import java.util.Scanner;

public class BootcampController {

    private static Scanner scan = new Scanner(System.in);
    private final BootcampDAO bootcampDAO = new BootcampDAOImpl();

    public static void main(String[] args) {

        BootcampController controller = new BootcampController();

        controller.start();

        switch (controller.activeUser().getType()) {
            case TEACHER -> controller.teacherMenu();
            case STUDENT -> controller.studentMenu();
            default -> controller.mainMenu();
        }

    }

    private void start() {
        Prompt start = new Prompt.builder("What would you like to do?")
                .addOption("Login")
                .addOption("Register")
                .build();

        start.prompt(scan);

        switch (start.getAnswer()) {
            case "Login" -> login();
            case "Register" -> register();
            default -> mainMenu();
        }

    }


    private void mainMenu() {
//        options: login, register, exit

    }

    private User activeUser() {
        return bootcampDAO.getUser();
    }

    private void login() {
        Prompt email = new Prompt.builder(
                "Enter your email?")
                .lowerCase()
                .build();
        Prompt password = new Prompt.builder(
                "What is your password?")
                .caseSensitive()
                .build();

        email.prompt(scan);
        password.prompt(scan);

        String encrypted = encode(password.getAnswer());
        bootcampDAO.authenticate(email.getAnswer(), encrypted);
    }

    private void register() {
        Prompt userType = new Prompt.builder(
                "Which type of user are you?")
                .addOption("Teacher")
                .addOption("Student")
                .upperCase()
                .build();

        userType.prompt(scan);

        User.Type type = User.Type.valueOf(userType.getAnswer());


        Prompt name = new Prompt.builder("What is your name?").build();
        Prompt email = new Prompt.builder("Enter your email").build();
        Prompt password = new Prompt.builder("Choose a password")
                .caseSensitive()
                .build();


        name.prompt(scan);
        email.prompt(scan);
        password.prompt(scan);

        User newUser = new Guest();
        if (type == User.Type.STUDENT)
        {
            newUser = new Student(
                    name.getAnswer(),
                    email.getAnswer(),
                    password.getAnswer());
        }
        else if (type == User.Type.TEACHER)
        {
            newUser = new Teacher(
                    name.getAnswer(),
                    email.getAnswer(),
                    password.getAnswer());
        }


        boolean authenticated = false;
        while (!authenticated)
        {
            try {
                bootcampDAO.register(newUser);

                authenticated = true;
            } catch (EmailExistsException e) {
                e.printStackTrace();
                email.prompt(scan);
                newUser.setEmail(email.getAnswer());
            }

        }


    }
    private void studentMenu() {
//        todo: enroll, view courses
        Student student = (Student) activeUser();

    }

    private void teacherMenu() {
//        todo: teacher menu
//        create course, view courses,
        Teacher teacher = (Teacher) activeUser();

    }

    private static String encode(String passwordToHash) {
         return new String(Base64.getEncoder()
                .encode(passwordToHash.getBytes()));
    }


}
