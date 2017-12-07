package com.healthedge.codeloaders.batch.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.healthedge.codeloaders.dto.BaseResponse;

@Controller
public class TriggerJobController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TriggerJobController.class);
	
	@Autowired
    private ApplicationContext appContext;
	
	@Autowired
    private JobLauncher jobLauncher;
	
	@RequestMapping(method = RequestMethod.GET, value = "/trigger", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody BaseResponse getThings() {
		BaseResponse baseResponse=new BaseResponse();
		final Job job = (Job) appContext.getBean("partitionerJob");
		JobParameters jobParameters = new JobParametersBuilder().addString("filePath", "cp")
                .addLong("time",System.currentTimeMillis()).toJobParameters();
		 try {
			 LOGGER.info("Starting the batch job for file: " + "cpt");
             JobExecution execution = jobLauncher.run(job, jobParameters);
             LOGGER.info("Job Status : " + execution.getStatus());
             LOGGER.info("Job succeeded");
             baseResponse.setStatus("SUCCESS");
         } catch (final Exception e) {
             LOGGER.info("Job failed: ",e);
             baseResponse.setStatus("FAILURE");
             baseResponse.setMessage(e.getMessage());
         }
		 return baseResponse;
	}
	
}
