package com.drbrosdev.actions;

import com.drbrosdev.Jsec;

import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DecryptAction implements Action {
    private final Path inFilePath;
    private final Path outFilePath;

    public DecryptAction(Path in, Path out) {
        if (!in.isAbsolute()) throw new IllegalArgumentException("Input file path is not absolute.");
        if (!out.isAbsolute()) throw new IllegalArgumentException("Output file path is not absolute.");
        this.inFilePath = in;
        this.outFilePath = out;
    }

    public DecryptAction(Path in) {
        if (!in.isAbsolute()) throw new IllegalArgumentException("Input file path is not absolute.");
        this.inFilePath = in;

        var fileName = in.getFileName();
        var timestamp = DateTimeFormatter.ofPattern("[yyyy-MM-dd_HH:mm:ss]").format(LocalDateTime.now());
        var currentDir = inFilePath.getParent();
        this.outFilePath = Path.of(currentDir.toString(), timestamp + "-dec-" + fileName);
    }

    @Override
    public void run() {
        try {
            System.out.println("Decrypting [" + inFilePath + "]\t...");
            var content = Files.readAllBytes(inFilePath);
            var jsec = Jsec.getInstance();
            var decryptedData = jsec.decryptContent(content);
            System.out.println("---" + inFilePath.getFileName() + " CONTENT---");
            System.out.println(new String(decryptedData, Charset.defaultCharset()));

            try (FileOutputStream stream = new FileOutputStream(outFilePath.toString())) {
                stream.write(decryptedData);
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
