package com.healthedge.codeloaders.service;



import com.healthedge.codeloaders.entity.Service;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DiffCreatorTests {

    private final String FILE_PATH="C:\\Code Loader\\codeloaders\\src\\test\\resources\\basedata\\CPT\\OPTUM_CPT_2016_01.txt";

    @Autowired
    private DiffCreator diffCreator;


    @Before
    public void setUp() throws Exception{

       // when(fileSorter.sortFilesInDirectory(any(String.class))).thenReturn(returnDummyFilesSorted());

    }

    @Test
    public void testDiffCreator() {

        List<Service> createList=new ArrayList<>();
        List<Service> appendList=new ArrayList<>();
        List<Service> terminateList=new ArrayList<>();

        Map<String,Service> records=returnFileRecordsParsed();
        Map<String, List<Service>> diffRecords =diffCreator.diff(FILE_PATH,records);

        if(diffRecords.containsKey(DiffCreator.CREATE_ACTION)) {
            createList = diffRecords.get(DiffCreator.CREATE_ACTION);
        }
        if(diffRecords.containsKey(DiffCreator.APPEND_ACTION)) {
            appendList = diffRecords.get(DiffCreator.APPEND_ACTION);
        }
        if(diffRecords.containsKey(DiffCreator.TERMINATE_ACTION)) {
            terminateList = diffRecords.get(DiffCreator.TERMINATE_ACTION);
        }
        if(!terminateList.isEmpty()||!appendList.isEmpty()||!createList.isEmpty()){
            System.out.println("Map of CREATE,APPEND and TERMINATE list of records is created");
            assert true;
        }
        else
            assert false;

    }




    public Map<String,Service> returnFileRecordsParsed(){

        //FILE RECORDS
        Service service;
        String code;
        Map<String,Service> serviceRecords=new HashMap<>();
        for(int i=0;i<10;++i) {
            service = returnServiceObject();
            code = service.getCode();
            serviceRecords.put(code,service);
        }
        return serviceRecords;
    }
    private Service returnServiceObject(){

        //SERVICE CLASS OBJECT
        Service service=new Service();
        service.setCode(getRandomServiceCode());
        service.setServiceLongDesciption("Scoliosis, DNA analysis of 53 single nucleotide polymorphisms");
        service.setServiceShortDesciption("SALIVA PROGNOSTIC RISK SCORE");
        service.setServiceAlternateDesciption("DNA analysis of 53 single nucleotide polymorphisms");

        return service;
    }

    private String getRandomServiceCode() {

        //GENERATING RANDOM SERVICE CODE FOR SERVICE OBJECT

        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();

    }
}
