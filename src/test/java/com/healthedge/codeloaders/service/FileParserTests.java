package com.healthedge.codeloaders.service;


import com.healthedge.codeloaders.entity.Service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileParserTests {
    @Autowired
    private FileParser fileParser;

    @MockBean
    private FileSorter fileSorter;


    private final String FILE_PATH="C:\\Code Loader\\codeloaders\\src\\test\\resources\\basedata\\CPT\\OPTUM_CPT_2016_01.txt";

    /*@Before
    public void setUp() throws Exception{

        //code here
    }*/
    @Test
    public void testFileParsing() throws Exception{

        Map<String, Service> record=fileParser.parse(FILE_PATH);
        if(record.containsKey("0003M")&&record.containsKey("0004M")){
            System.out.println("File values are parsed");
            assert true;
        }
        else
            assert false;

    }


}
