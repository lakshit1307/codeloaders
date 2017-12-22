package com.healthedge.codeloaders.filelistener;

import com.healthedge.codeloaders.CodeloadersApplication;
import com.healthedge.codeloaders.batch.client.BatchJobController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

@Component
public class FilelistenerApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilelistenerApplication.class);

    @Value("${listener.current.data.path}")
    private String currentPath;

    @Value("${listener.inprogress.data.path}")
    private String inProcessPath;

    @Value("${listener.done.file}")
    private String doneFile;

    @Value("${basedata.path}")
    private String baseData;

    @Autowired
    private ApplicationContext applicationContext;

    private WatchService watcher;

    public void registerFileListener () throws Exception {
        LOGGER.info("Registering File listener");
        watcher = FileSystems.getDefault().newWatchService();
        Path dir = Paths.get(currentPath);
        dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        LOGGER.info("Registered File listener for folder [{}]", currentPath);
    }

    public void startFileListener () throws Exception {
        LOGGER.info("File Listener started polling files from folder [{}]", currentPath);
        while (true) {
            WatchKey key;
            key = watcher.take();

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                @SuppressWarnings("unchecked")
                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                String fileName = ev.context().toString();

                if (fileName.equalsIgnoreCase(doneFile)) {
                    if (kind == ENTRY_CREATE) {
                        LOGGER.info("[{}] file created!!!", doneFile);
                        CopyFilesToBasedata.copyFiles(currentPath, inProcessPath);
                        applicationContext.getBean(BatchJobController.class).triggerLoadProcess(inProcessPath);
                        CopyFilesToBasedata.copyFiles(inProcessPath, baseData);
                    } else if (kind == ENTRY_DELETE) {
                        LOGGER.info("[{}] file deleted!!!");
                    }
                }
            }

            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }
    }

//    public static void main(String[] args) {
//
//        try {
//            WatchService watcher = FileSystems.getDefault().newWatchService();
//            Path dir = Paths.get(basePath.concat("\\current"));
//            dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
//
//            System.out.println("Watch Service registered for directory: " + dir.getFileName());
//
//            while (true) {
//                WatchKey key;
//                try {
//                    key = watcher.take();
//                } catch (InterruptedException ex) {
//                    return;
//                }
//
//                for (WatchEvent<?> event : key.pollEvents()) {
//                    WatchEvent.Kind<?> kind = event.kind();
//
//                    @SuppressWarnings("unchecked")
//                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
//                    String fileName = ev.context().toString();
//
//                    if (kind == ENTRY_CREATE && fileName.equals("done.txt")) {
//                        System.out.println("Done.txt file created!!!");
//                        CopyFilesToBasedata.copyFiles(basePath);
//                    } else if (kind == ENTRY_DELETE && fileName.equals("done.txt")) {
//                        System.out.println("Done.txt file deleted!!!");
//                    }
//                }
//
//                boolean valid = key.reset();
//                if (!valid) {
//                    break;
//                }
//            }
//        } catch (IOException ex) {
//            System.err.println(ex);
//        }
//    }
}
