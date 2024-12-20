package pers.cierra_runis.Command;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Command {
    public void start() {
        while (true) {

            List<String> modules = List.of("Matrix", "Manage", "JavaHome");
            String select = String.join("|", modules);

            Scanner scanner = new Scanner(System.in);
            System.out.printf("Enter command just like /module (%s)\n> ", select);
            String str = scanner.nextLine();

            if (Objects.equals(str, "/exit")) {
                System.out.print("\33[32;1mSee you next time.\33[0m\n");
                System.exit(0);
            }

            int i = 0;
            for (; i < modules.size(); i++) {
                if (Objects.equals(str, "/module " + modules.get(i))) {
                    System.out.printf("\33[32;1mWelcome to %s module.\33[0m\n", modules.get(i));
                    switch (i) {
                        case 0: {
                            new CommandForMatrix().start();
                            break;
                        }
                        case 1: {
                            new CommandForManage().start();
                            break;
                        }
                        case 2: {
                            new CommandForJavaHome().start();
                            break;
                        }
                    }
                    break;
                }

            }
            if (i == modules.size()) {
                System.out.print("\33[31;1mThis command is illegal!\33[0m\n");
                System.out.print("Check by this: \33[31;1m" + "/module (Matrix|Manage|JavaHome)" + "\33[0m\n\n");
            }
        }
    }
}