package com.healthedge.codeloaders.batch;

import com.healthedge.codeloaders.entity.Service;
import com.healthedge.codeloaders.service.FileSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CodeLoadProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodeLoadProcess.class);

    @Value("${basedata.path}")
    private String baseData;

    @Autowired
    private FileSorter fileSorter;

    public void startProcess () {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(SpringBatchPartitionConfig.class);
        context.refresh();

        final JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        final Job job = (Job) context.getBean("partitionerJob");

        for (final String fileType : getFileTypes()) {
            final String directoryPath = baseData + File.separator + fileType;
            final List<String> sortedFileNames = fileSorter.sortFilesInDirectory(directoryPath);
            for (final String file : sortedFileNames) {
                final String filePath = directoryPath + File.separator + file;
                CodeLoaderContext.getInstance().setCurrentFilePath(filePath);

                //Initiate jobs for each file
                try {
                    System.out.println("Starting the batch job for file: " + filePath);
                    JobExecution execution = jobLauncher.run(job, new JobParameters());
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
