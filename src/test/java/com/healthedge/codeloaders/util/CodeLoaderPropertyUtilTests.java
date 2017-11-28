package com.healthedge.codeloaders.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Properties;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CodeLoaderPropertyUtilTests {

    @Autowired
    private CodeLoaderPropertyUtil codeLoaderPropertyUtil;
   /* @Before
    public void setUp() throws Exception{

        when(fileSorter.sortFilesInDirectory(any(String.class))).thenReturn(returnDummyFilesSorted());

    }*/
    @Test
    public void testGetProperties() {

        Properties properties=codeLoaderPropertyUtil.getProperties();
        if(!properties.isEmpty()){
            assert true;
        }

    }
}
