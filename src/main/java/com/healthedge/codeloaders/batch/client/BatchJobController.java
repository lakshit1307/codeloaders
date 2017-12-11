package com.healthedge.codeloaders.batch.client;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.healthedge.codeloaders.batch.StagingLoadProcess;
import com.healthedge.codeloaders.dto.BaseResponse;

@Controller
public class BatchJobController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BatchJobController.class);

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private StagingLoadProcess stagingLoadProcess;

	@RequestMapping(method = RequestMethod.GET, value = "/trigger", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody BaseResponse getThings() {
		BaseResponse baseResponse = new BaseResponse();
		try {
			runPersistence();
			baseResponse.setStatus("SUCCESS");
		} catch (Exception e) {
			baseResponse.setMessage(e.getMessage());
			baseResponse.setStatus("FAILURE");
		}
		return baseResponse;
	}
	
	public String runPersistence() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		stagingLoadProcess.startProcess();
		for (String fileType : getFileTypes()) {
			runClientPersitentJobForFileType(fileType);
		}
		return "SUCCESS";
	}

	public String runClientPersitentJobForFileType(String fileType) throws JobExecutionAlreadyRunningException,
			JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		final Job job = (Job) appContext.getBean("partitionerJob");
		JobParameters jobParameters = new JobParametersBuilder().addString("filePath", fileType)
				.addLong("time", System.currentTimeMillis()).toJobParameters();
		LOGGER.info("Starting the batch job for file: " + "cpt");
		JobExecution execution = jobLauncher.run(job, jobParameters);
		LOGGER.info("Job Status : " + execution.getStatus());
		LOGGER.info("Job succeeded");
		return "SUCCES";
	}

	private List<String> getFileTypes() {
		final List<String> fileTypes = new ArrayList<String>();
		fileTypes.add("cp");
		fileTypes.add("hp");
		return fileTypes;
	}

}
