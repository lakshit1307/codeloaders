package com.healthedge.codeloaders.batch;

import com.healthedge.codeloaders.dao.ClientDao;
import com.healthedge.codeloaders.dao.FileStatusDao;
import com.healthedge.codeloaders.entity.FileStatus;
import com.healthedge.codeloaders.service.DiffCreator;
import com.healthedge.codeloaders.service.FileSorter;
import com.healthedge.codeloaders.service.InitLoadTracker;
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
import java.util.Date;
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

    @Autowired
    private InitLoadTracker initLoadTracker;

    @Autowired
    private FileStatusDao fileStatusDao;

    private FileStatus fileStatus=new FileStatus();


    public void startProcess () {
        final Job job = (Job) appContext.getBean("stagingJob");
        for (final String fileType : getFileTypes()) {
            diffCreator.flushPreviousData();
            final String directoryPath = baseData + File.separator + fileType;
            String startFile= initLoadTracker.startFileProcessingOf(fileType);
            if(!startFile.equals(initLoadTracker.EOF)){

            final List<String> sortedFileNames = fileSorter.sortFilesInDirectory(directoryPath);
            for (int i=sortedFileNames.indexOf(startFile);i<sortedFileNames.size();++i) {

                final String filePath = directoryPath + File.separator + sortedFileNames.get(i);
                CodeLoaderContext.getInstance().setCurrentFilePath(filePath);

                JobParameters jobParameters = new JobParametersBuilder().addString("filePath", filePath)
                        .addLong("time",System.currentTimeMillis()).toJobParameters();

                //Initiate jobs for each file
                long startTime = System.currentTimeMillis();
                long endTime = 0;
                try {
                    LOGGER.info("Processing for file [{}] started at [{}]", filePath, new Date());
                    this.saveFileStatus(fileType, sortedFileNames.get(i), FileStatus.IN_PROGRESS);

                    LOGGER.info("Starting batch job for file [{}]", filePath);
                    JobExecution execution = jobLauncher.run(job, jobParameters);
                    LOGGER.info("Batch Job succeeded for file [{}]", filePath);
                    endTime = System.currentTimeMillis();
                    LOGGER.info("Processing of file [{}] finished successfully at [{}]", filePath, new Date());
                    LOGGER.info("Total time required to process file [{}]: {} ms", filePath, endTime - startTime);
                    this.updateFileStatus(fileType, sortedFileNames.get(i), FileStatus.PERSISTED);
                } catch (final Exception e) {
                    endTime = System.currentTimeMillis();
                    LOGGER.info("Processing of file [{}] finished successfully at [{}]", filePath, new Date());
                    LOGGER.info("Total time required to process file [{}]: {} ms", filePath, endTime - startTime);
                    LOGGER.error("Batch job failed for file [{}] with exception", filePath, ExceptionUtils.getStackTrace(e));
                    this.updateFileStatus(fileType, sortedFileNames.get(i), FileStatus.FAILURE);
                }
            }
            }
            else{
                LOGGER.info(fileType+" is upto date in CLOADER DB");
            }



        }
    }

    private void saveFileStatus (String fileType, String fileName, String status) {
        fileStatus.setCodeType(fileType);
        fileStatus.setFileName(fileName);
        fileStatus.setStatus(status);
        fileStatusDao.save(fileStatus);
    }

    private void updateFileStatus (String fileType, String fileName, String status) {
        fileStatus.setCodeType(fileType);
        fileStatus.setFileName(fileName);
        fileStatus.setStatus(status);
        fileStatusDao.updateStatus(fileStatus);
    }

    //TODO: get data from db or config
    private List<String> getFileTypes () {
        final List<String> fileTypes = new ArrayList<String>();
        fileTypes.add("CPT");
        fileTypes.add("HCPCS");

        return fileTypes;
    }
}
