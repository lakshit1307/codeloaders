package com.healthedge.codeloaders.service;


import com.healthedge.codeloaders.dto.FileMetadata;
import com.healthedge.codeloaders.entity.Service;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@org.springframework.stereotype.Service
public class DiffCreator {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiffCreator.class);

    public static final String CREATE_ACTION = "CREATE";
    public static final String APPEND_ACTION = "APPEND";
    public static final String TERMINATE_ACTION = "TERMINATE";


    private final Map<String, Service> previous = new ConcurrentHashMap<>();

    public DiffCreator () {
        LOGGER.info("DiffCreator class initialized");
    }


    public Map<String, List<Service>> diff(final String filePath, final Map<String, Service> current) {

        final String fileName = FilenameUtils.getName(filePath);
        final FileMetadata fileMetadata = new FileMetadata(fileName);
        final DateTime fileDate=fileMetadata.getFileDate();

        final Map<String, List<Service>> result = new ConcurrentHashMap<>();
        final List<Service> create = new ArrayList<Service>();
        final List<Service> append = new ArrayList<Service>();
        final List<Service> terminate = new ArrayList<Service>();


        if (MapUtils.isEmpty(previous)) {
            for(final String key : current.keySet())
            {
                final Service service = current.get(key);
                service.setAction(CREATE_ACTION);
                service.setEffectiveStartDate(fileDate.toDate());
                create.add(service);
            }
            previous.putAll(current);
            current.clear();

        } else {
            for(final String key: current.keySet()){
                if(previous.containsKey(key)){
                    final Service pojo2 = current.get(key);
                    final Service pojo1 = previous.get(key);
                    if(!pojo1.equals(pojo2)){
                        pojo2.setAction(APPEND_ACTION);
                        append.add(pojo2);
                    }
                    previous.remove(key);
                }
                else{
                    final Service pojo = current.get(key);
                    pojo.setAction(CREATE_ACTION);
                    pojo.setEffectiveStartDate(fileDate.toDate());
                    create.add(pojo);
                }
            }
            for(final String key: previous.keySet()){
                final Service pojo= previous.get(key);
                pojo.setAction(TERMINATE_ACTION);
                pojo.setEffectiveEndDate(fileDate.toDate());
                terminate.add(pojo);

            }
            previous.clear();
            previous.putAll(current);
            current.clear();

        }

        result.put(CREATE_ACTION, create);
        result.put(APPEND_ACTION, append);
        result.put(TERMINATE_ACTION, terminate);

        return result;


    }

    public void flushPreviousData () {
        previous.clear();
    }


}
