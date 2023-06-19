package com.drbrosdev.actions;

import com.drbrosdev.Jsec;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EncryptAction implements Action {
    private final Path inFilePath;
    private final Path outFilePath;

    public EncryptAction(Path in, Path out) {
        if (!in.isAbsolute()) throw new IllegalArgumentException("Input file path is not absolute.");
        if (!out.isAbsolute()) throw new IllegalArgumentException("Output file path is not aboslute.");
        this.inFilePath = in;
        this.outFilePath = out;
    }

    public EncryptAction(Path in) {
        if (!in.isAbsolute()) throw new IllegalArgumentException("Input file path is not absolute.");
        this.inFilePath = in;

        var fileName = in.getFileName();
        var timestamp = DateTimeFormatter.ofPattern("[yyyy-MM-dd_HH:mm:ss]").format(LocalDateTime.now());
        var currentDir = inFilePath.getParent();
        this.outFilePath = Path.of(currentDir.toString(),timestamp + "-enc-" + fileName);
    }

    @Override
    public void run() {
        try {
            System.out.println("Encrypting [" + inFilePath + "]\t...");
            var content = Files.readAllBytes(inFilePath);
            var jsec = Jsec.getInstance();
            var encryptedData = jsec.encryptContent(content);

            try (FileOutputStream stream = new FileOutputStream(outFilePath.toString())) {
                stream.write(encryptedData);
                System.out.println("Finished.");
            }
        } catch (NoSuchFileException noSuchFileException) {
            System.out.println("File does not exist.");
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println("\n---DEVELOPER INFO---");
            e.printStackTrace();
        }
    }
}
