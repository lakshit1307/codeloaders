package com.healthedge.codeloaders.myparser;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public abstract class AbstractParser {

    public List<String> readLines (String filePath, boolean skipFirstLine) throws Exception {
        List<String> result = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line = "";

        if (skipFirstLine) {
            br.readLine();//consuming the first line
        }

        while ((line = br.readLine()) != null) {
            if (line.length() > 0) {
                result.add(line);
            }
        }

        return result;
    }

    public abstract List<Map<String, String>> parse (MyFileMetaData fileMetaData) throws Exception;
}
