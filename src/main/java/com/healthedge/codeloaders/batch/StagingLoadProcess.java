package com.healthedge.codeloaders.batch;

import com.healthedge.codeloaders.service.FileSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class StagingLoadProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(StagingLoadProcess.class);

    @Value("${basedata.path}")
    private String baseData;

    @Autowired
    private FileSorter fileSorter;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;


    public void startProcess () {

        for (final String fileType : getFileTypes()) {
            final String directoryPath = baseData + File.separator + fileType;
            final List<String> sortedFileNames = fileSorter.sortFilesInDirectory(directoryPath);
            for (final String file : sortedFileNames) {
                final String filePath = directoryPath + File.separator + file;
                CodeLoaderContext.getInstance().setCurrentFilePath(filePath);

                JobParameters jobParameters = new JobParametersBuilder().addString("filePath", filePath)
                        .addLong("time",System.currentTimeMillis()).toJobParameters();

                //Initiate jobs for each file
                try {
                    System.out.println("Starting the batch job for file: " + filePath);
                    JobExecution execution = jobLauncher.run(job, jobParameters);
                    System.out.println("Job Status : " + execution.getStatus());
                    System.out.println("Job succeeded");
                } catch (final Exception e) {
                    e.printStackTrace();
                    System.out.println("Job failed");
                }
            }
        }
    }

    //TODO: get data from db or config
    private List<String> getFileTypes () {
        final List<String> fileTypes = new ArrayList<String>();
        fileTypes.add("CPT");
        fileTypes.add("HCPCS");

        return fileTypes;
    }
}
