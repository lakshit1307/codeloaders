package com.healthedge.codeloaders.batch.client;

import java.util.ArrayList;
import java.util.List;

import com.healthedge.codeloaders.service.FileTypeOrdering;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.healthedge.codeloaders.batch.StagingLoadProcess;
import com.healthedge.codeloaders.dto.BaseResponse;
import com.healthedge.codeloaders.dto.TenantRequest;

@Controller
public class BatchJobController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BatchJobController.class);

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private StagingLoadProcess stagingLoadProcess;

	@Autowired
	private FileTypeOrdering fileTypeOrdering;

	@RequestMapping(method = RequestMethod.POST, value = "/trigger/all", produces = MediaType.APPLICATION_JSON_VALUE)
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

	@RequestMapping(method = RequestMethod.POST, value = "/trigger", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody BaseResponse getThings(@RequestBody TenantRequest tenantRequest) {
		BaseResponse baseResponse = new BaseResponse();
		try {
			runPersistence(tenantRequest.getTenantId(), tenantRequest.getTenantEnvId());
			baseResponse.setStatus("SUCCESS");
		} catch (Exception e) {
			baseResponse.setMessage(e.getMessage());
			baseResponse.setStatus("FAILURE");
		}
		return baseResponse;
	}

	public String runPersistence() throws Exception {
		stagingLoadProcess.startProcess();
		for (String fileType : fileTypeOrdering.getFileTypes()) {
			runClientPersitentJobForFileType(fileType);
		}
		return "SUCCESS";
	}

	public String runPersistence(Integer tenantId, Integer tenantEnvId) throws JobExecutionAlreadyRunningException,
			JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		for (String fileType : fileTypeOrdering.getFileTypes()) {
			runClientPersitentJobForFileType(fileType,tenantId, tenantEnvId);
		}
		return "SUCCESS";
	}

	public String runClientPersitentJobForFileType(String fileType) throws JobExecutionAlreadyRunningException,
			JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		final Job job = (Job) appContext.getBean("partitionerJob");
		JobParameters jobParameters = new JobParametersBuilder().addString("fileType", fileType)
				.addLong("time", System.currentTimeMillis()).addString("tenantId", "all")
				.addString("tenantEnvId", "all").toJobParameters();
		LOGGER.info("Starting the batch job for file: " + "cpt");
		JobExecution execution = jobLauncher.run(job, jobParameters);
		LOGGER.info("Job Status : " + execution.getStatus());
		LOGGER.info("Job succeeded");
		return "SUCCES";
	}

	public String runClientPersitentJobForFileType(String fileType,Integer tenantId, Integer tenantEnvId) throws JobExecutionAlreadyRunningException,
			JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		final Job job = (Job) appContext.getBean("partitionerJob");
		JobParameters jobParameters = new JobParametersBuilder().addString("fileType", fileType)
				.addLong("time", System.currentTimeMillis()).addString("tenantId", tenantId.toString())
				.addString("tenantEnvId", tenantEnvId.toString()).toJobParameters();
		LOGGER.info("Starting the batch job for file: " + "cpt");
		JobExecution execution = jobLauncher.run(job, jobParameters);
		LOGGER.info("Job Status : " + execution.getStatus());
		LOGGER.info("Job succeeded");
		return "SUCCES";
	}


}
