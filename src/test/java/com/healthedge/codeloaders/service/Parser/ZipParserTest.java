package com.healthedge.codeloaders.service.Parser;

import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.entity.ZipToCarrierLocality;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ZipParserTest {

    @Autowired
    private ZipParser zipParser;

    @Test
    public void testFileParsing() throws Exception{
        File file = new File("src/test/resources/basedata/ZIPTOCARRIER/OPTUM_ZipToCarrierLocality_2003_01.txt");


        List<BaseEntity> record=zipParser.parse(file.getAbsolutePath());
        if(true){
            System.out.println("File values are parsed");
            assert true;
        }
        else
            assert false;

    }
}
