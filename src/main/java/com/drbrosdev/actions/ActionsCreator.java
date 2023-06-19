package com.drbrosdev.actions;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ActionsCreator {

    public static Action create(String[] args) {
        if (args.length == 0) throw new IllegalArgumentException("No actions.");

        switch (args[0]) {
            case "enc", "dec" -> {
                var currentDirPath = Paths.get("").toAbsolutePath();
                switch (args.length) {
                    case 2 -> {
                        var inPath = Path.of(args[1]);
                        if (!inPath.isAbsolute()) {
                            inPath = Path.of(currentDirPath.toString(), inPath.toString());
                        }
                        if(args[0].equals("enc")) return new EncryptAction(inPath);
                        if(args[0].equals("dec")) return new DecryptAction(inPath);
                    }
                    case 3 -> {
                        var inPath = Path.of(args[1]);
                        var outPath = Path.of(args[2]);

                        if (!inPath.isAbsolute()) {
                            inPath = Path.of(currentDirPath.toString(), inPath.toString());
                        }
                        if (!outPath.isAbsolute()) {
                            outPath = Path.of(currentDirPath.toString(), outPath.toString());
                        }
                        if(args[0].equals("enc")) return new EncryptAction(inPath, outPath);
                        if(args[0].equals("dec")) return new DecryptAction(inPath, outPath);
                    }
                    default -> throw new IllegalStateException("Provide proper file paths.");
                }
            }
            case "key" -> {
                if(args.length != 2) throw new IllegalStateException("Provide a key.");
                return new KeyAction(args[1]);
            }
            case "help" -> {
                return new HelpAction();
            }
            default -> throw new IllegalArgumentException("Action " + args[0] + " unrecognized.");
        }

        return null;
    }
}
