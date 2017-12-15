package com.healthedge.codeloaders.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileTypeOrderingTest {

    @Autowired
    private FileTypeOrdering fileTypeOrdering;

    @Test
    public void getFileTypes() throws Exception {
        List<String>  fileTypes = fileTypeOrdering.getFileTypes();
        Assert.assertNotNull(fileTypes);
        System.out.println(fileTypes);

    }

}