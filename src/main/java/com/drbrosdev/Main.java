package com.drbrosdev;

import com.drbrosdev.actions.ActionsCreator;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
//        System.out.println(System.getProperty("user.home"));
        try {
            var action = ActionsCreator.create(args);
            if (action == null) {
                System.out.println();
                return;
            }

            action.run();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println("---DEVELOPER INFO---");
            System.out.println("Command " + Arrays.toString(args));
            e.printStackTrace();
        }
    }
}