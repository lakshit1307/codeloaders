package com.healthedge.codeloaders.myparser;

import com.healthedge.codeloaders.service.FileParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParserImplTest {

    @Autowired
    private AbstractParser abstractParser;

    @Test
    public void parse() throws Exception {
        File file = new File("src/test/resources/basedata/CPT/OPTUM_CPT_2016-01-01.txt");

        MyFileMetaData fileMetaData = new MyFileMetaData("CPT", file.getAbsolutePath());
        List<Map<String, String>> result = abstractParser.parse(fileMetaData);

        System.out.println(result);

        file = new File("src/test/resources/basedata/ZipToCarrierLocality/OPTUM_ZipToCarrierLocality_2003-01-01.txt");

        fileMetaData = new MyFileMetaData("ZIP", file.getAbsolutePath());
        result = abstractParser.parse(fileMetaData);

        System.out.println(result);

    }

}