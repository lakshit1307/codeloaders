package com.healthedge.codeloaders.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.File;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileSorterTests {

    @Autowired
    private FileSorter fileSorter;

    private List<String> actualFileNamesSorted= Arrays.asList("OPTUM_CPT_2016_01.txt","OPTUM_CPT_2016_02.txt",
            "AAOPTUM_CPT_2016_03.txt","ABOPTUM_CPT_2016_04.txt");

    @Test
    public void testFileSorted(){
        File file = new File("src/test/resources/basedata");

        List<String> fileNames=fileSorter.sortFilesInDirectory(file.getAbsolutePath()+ File.separator + "CPT");
        if(fileNames.equals(actualFileNamesSorted)){
            System.out.println("File names are sorted correctly based on date");
            assert true;
        }
        else{
            assert false;
        }


    }
}
