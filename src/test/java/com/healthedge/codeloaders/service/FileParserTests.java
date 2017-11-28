package com.healthedge.codeloaders.service;


import com.healthedge.codeloaders.dto.FileMetadata;
import com.healthedge.codeloaders.entity.Service;
import org.apache.commons.io.FilenameUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileParserTests {
    @Autowired
    private FileParser fileParser;

    @MockBean
    private FileSorter fileSorter;

    @MockBean
    private FileMetadata fileMetadata;


    private String filePath;

    @Before
    public void setUp() throws Exception{

        when(FilenameUtils.getName(any(String.class))).thenReturn("OPTUM_CPT_2016_01");
        when(fileMetadata.getFileType().toLowerCase(Locale.getDefault())).thenReturn("cp");


    }
    @Test
    public void testFileParsing() throws Exception{



       fileParser.parse(filePath);

    }


}
