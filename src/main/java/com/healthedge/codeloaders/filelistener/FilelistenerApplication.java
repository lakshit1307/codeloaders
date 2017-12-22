package com.healthedge.codeloaders.filelistener;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

public class FilelistenerApplication {

    private static String basePath = "C:\\Users\\adps2\\Documents\\CODE LOADER";

    public static void main(String[] args) {

        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            Path dir = Paths.get(basePath.concat("\\current"));
            dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

            System.out.println("Watch Service registered for directory: " + dir.getFileName());

            while (true) {
                WatchKey key;
                try {
                    key = watcher.take();
                } catch (InterruptedException ex) {
                    return;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    @SuppressWarnings("unchecked")
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    String fileName = ev.context().toString();

                    if (kind == ENTRY_CREATE && fileName.equals("done.txt")) {
                        System.out.println("Done.txt file created!!!");
                        CopyFilesToBasedata.copyFiles(basePath);
                    } else if (kind == ENTRY_DELETE && fileName.equals("done.txt")) {
                        System.out.println("Done.txt file deleted!!!");
                    }
                }

                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
