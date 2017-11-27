package com.healthedge.codeloaders.business;

import com.healthedge.codeloaders.dao.ServiceDao;
import com.healthedge.codeloaders.entity.Service;
import com.healthedge.codeloaders.service.DiffCreator;
import com.healthedge.codeloaders.service.FileParser;
import com.healthedge.codeloaders.service.FileSorter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoadPendingCodesTests {


    @MockBean
    private FileSorter fileSorter;

    @MockBean
    private DiffCreator diffCreator;

    @MockBean
    private FileParser fileParser;

    @MockBean
    private ServiceDao serviceDao;

    @Autowired
    private LoadPendingCodes loadPendingCodes;

    public enum Actions{
        CREATE,APPEND,TERMINATE
    }

    @Before
    public void setUp() throws Exception{

        when(fileSorter.sortFilesInDirectory(any(String.class))).thenReturn(returnDummyFilesSorted());
        when(fileParser.parse(any(String.class))).thenReturn(new HashMap<>(returnFileRecordsParsed()));
        when(diffCreator.diff(any(String.class), Mockito.anyMap())).thenReturn(new HashMap<>(returnDiffCreator()));
        when(serviceDao.update(any(Service.class))).thenReturn(true);
        when(serviceDao.save(any(Service.class))).thenReturn(true);
        when(serviceDao.terminate(any(Service.class))).thenReturn(true);
    }



    @Test
    public void testStartProcess() {

        loadPendingCodes.startProcess();

    }

    public List<String> returnDummyFilesSorted(){

        return new ArrayList<>(Arrays.asList("OPTUM_CPT_2016_01",
                "OPTUM_CPT_2016_02", "OPTUM_CPT_2016_03"));
    }

    public Map<String,Service> returnFileRecordsParsed(){

        //FILE RECORDS
        Service service;
        String code;
        Map<String,Service> serviceRecords=new HashMap<>();
        for(int i=0;i<10;++i) {
            service = returnServiceObject();
            code = service.getServiceCode();
            serviceRecords.put(code,service);
        }
        return serviceRecords;
    }

    public Map<String,List<Service>> returnDiffCreator(){

        Service service;

        //CREATE LIST
        List<Service> createList=new ArrayList<>();
        Map<String,List<Service>> map=new HashMap<>();
        for(int i=0;i<4;++i){
            service=returnServiceObject();
            createList.add(service);
        }
        map.put(String.valueOf(Actions.CREATE),createList);

        //APPEND LIST
        List<Service> appendList=new ArrayList<>();
        for(int i=0;i<4;++i){
            service=returnServiceObject();
            appendList.add(service);
        }
        map.put(String.valueOf(Actions.APPEND),appendList);

        //TERMINATE LIST
        List<Service> terminateList=new ArrayList<>();
        for(int i=0;i<4;++i){
            service=returnServiceObject();
            terminateList.add(service);
        }
        map.put(String.valueOf(Actions.TERMINATE),terminateList);

        return map;


    }

    private Service returnServiceObject(){

        //SERVICE CLASS OBJECT
        Service service=new Service();
        service.setServiceCode(getRandomServiceCode());
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




