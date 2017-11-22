package com.healthedge.codeloaders;

import com.healthedge.codeloaders.dao.ServiceDao;
import com.healthedge.codeloaders.entity.Service;
import com.healthedge.codeloaders.service.file.diff.DiffCreator;
import com.healthedge.codeloaders.service.file.parser.FileParser;
import com.healthedge.codeloaders.service.file.sort.FileNameDateComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.File;
import java.util.*;


@ComponentScan(basePackages = "com.healthedge.*")
@EntityScan( basePackages = "com.healthedge.codeloaders.entity" )
@EnableJpaRepositories("com.healthedge.codeloaders.repository")
@SpringBootApplication
public class CodeloadersApplication {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(CodeloadersApplication.class);
	private String basedata = "C:\\basedata";
	private static final FileParser FILE_PARSER = new FileParser();
	private static final DiffCreator DIFF_CREATOR = new DiffCreator();
	private static final ServiceDao PERSIST_DATA=new ServiceDao();
	private static  Service service=new Service();
	List<Service> ls=new ArrayList<Service>();

	public static void main(String[] args) {
		LOGGER.debug("sdsdsd");
		SpringApplication.run(CodeloadersApplication.class, args);

		new Main().startProcess();
	}

	private void startProcess () {
		for (String fileType : getFileTypes()) {
			DIFF_CREATOR.flushPreviousData();
			String directoryPath = basedata + File.separator + fileType;
			List<String> sortedFileNames = getSortedFiles(directoryPath);
			for (String file : sortedFileNames) {
				String filePath = directoryPath + File.separator + file;
				try {
					Map<String, Service> record = FILE_PARSER.parse(filePath);
					Map<String, List<Service>> diffRecords = DIFF_CREATOR.diff(record);
					/*System.out.println("CREATE");
					System.out.println("=====================");
					System.out.println(diffRecords.get(DiffCreator.CREATE_ACTION));
					System.out.println("APPEND");
					System.out.println("=====================");
					System.out.println(diffRecords.get(DiffCreator.APPEND_ACTION));
					System.out.println("TERMINATE");
					System.out.println("=====================");
					System.out.println(diffRecords.get(DiffCreator.TERMINATE_ACTION)+"\n DONE!!! \n");*/
					//Persist Data
					ls=diffRecords.get(DiffCreator.CREATE_ACTION);
                    for(int i=0;i<ls.size();++i) {
                    	service=ls.get(i);
                        PERSIST_DATA.save(service);
                        System.out.println(i);
                    }


				} catch (Exception ex) {
					//TODO: Log the error with file name
				}

			}


		}
	}

	private List<String> getSortedFiles (String directoryPath) {
		List<String> fileNames = new ArrayList<String>();
		File filePath = new File(directoryPath);
		if (filePath.exists() && filePath.isDirectory()) {
			fileNames = Arrays.asList(filePath.list());
			Collections.sort(fileNames, new FileNameDateComparator());
		}

		return fileNames;
	}

	//TODO: get data from db or config
	private List<String> getFileTypes () {
		List<String> fileTypes = new ArrayList<String>();
		fileTypes.add("CPT");
		fileTypes.add("HCPCS");

		return fileTypes;
	}
}
