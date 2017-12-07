package com.healthedge.codeloaders.batch;

import com.healthedge.codeloaders.service.DiffCreator;
import com.healthedge.codeloaders.service.FileSorter;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
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
    private DiffCreator diffCreator;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private ApplicationContext appContext;


    public void startProcess () {
        final Job job = (Job) appContext.getBean("stagingJob");
        for (final String fileType : getFileTypes()) {
            diffCreator.flushPreviousData();
            final String directoryPath = baseData + File.separator + fileType;
            final List<String> sortedFileNames = fileSorter.sortFilesInDirectory(directoryPath);
            for (final String file : sortedFileNames) {
                final String filePath = directoryPath + File.separator + file;
                CodeLoaderContext.getInstance().setCurrentFilePath(filePath);

                JobParameters jobParameters = new JobParametersBuilder().addString("filePath", filePath)
                        .addLong("time",System.currentTimeMillis()).toJobParameters();

                //Initiate jobs for each file
                try {
                    LOGGER.info("Starting batch job for file [{}]", filePath);
                    JobExecution execution = jobLauncher.run(job, jobParameters);
                    LOGGER.info("Batch Job succeeded for file [{}]", filePath);
                    //file status to be added here if success
                } catch (final Exception e) {
                    LOGGER.error("Batch job failed for file [{}] with exception", filePath, ExceptionUtils.getStackTrace(e));
                    //failure status update
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
