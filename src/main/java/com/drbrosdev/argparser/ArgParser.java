package com.drbrosdev.argparser;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ArgParser {
    private final Action action;
    private final Path inFilePath;
    private final Path outFilePath;

    public ArgParser(String[] args) {
        if(args.length == 0) throw new IllegalArgumentException("No action provided.");

        switch (args[0]) {
            case "enc" -> this.action = Action.ENCRYPT;
            case "dec" -> this.action = Action.DECRYPT;
            case "help" -> this.action = Action.HELP;
            default -> throw new IllegalArgumentException("Action " + args[0] + " unrecognized.");
        }

        this.inFilePath = Path.of(args[1]);

        if(args.length == 2) {
            //currentDir is null when using in current directory
            var currentDir = inFilePath.getParent().toString();
            var filename = inFilePath.getFileName().toString();
            var timestamp =  DateTimeFormatter.ofPattern("(yyyy-MM-dd_HH:mm:ss)").format(LocalDateTime.now());
            this.outFilePath = Paths.get(currentDir, timestamp + "(" + args[0] + ")" + filename);
        } else {
            this.outFilePath = Path.of(args[2]);
        }
    }

    public Action action() {
        return action;
    }

    public Path inFilePath() {
        return inFilePath;
    }

    public Path outFilePath() {
        return outFilePath;
    }
}
