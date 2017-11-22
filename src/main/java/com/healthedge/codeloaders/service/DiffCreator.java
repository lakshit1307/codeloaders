package com.healthedge.codeloaders.service;


import com.healthedge.codeloaders.entity.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
public class DiffCreator {

    public static final String CREATE_ACTION = "CREATE";
    public static final String APPEND_ACTION = "APPEND";
    public static final String TERMINATE_ACTION = "TERMINATE";

    private HashMap<String, Service> previous = new HashMap<String, Service>();


    public Map<String, List<Service>> diff(Map<String, Service> current) {

        Map<String, List<Service>> result = new HashMap<String, List<Service>>();
        List<Service> create = new ArrayList<Service>();
        List<Service> append = new ArrayList<Service>();
        List<Service> terminate = new ArrayList<Service>();


        Boolean flag = ifEmptyMemory(previous);
        if (flag) {
            for(String key:current.keySet())
            {
                Service service =current.get(key);
                service.setAction(CREATE_ACTION);
                create.add(service);
            }
            previous.putAll(current);
            current.clear();

        } else {

            for(String key: current.keySet()){
                if(previous.containsKey(key)){
                    Service pojo2=current.get(key);
                    Service pojo1= previous.get(key);
                    if(!pojo1.equals(pojo2)){
                        pojo2.setAction(APPEND_ACTION);
                        append.add(pojo2);
                    }
                    previous.remove(key);
                }
                else{
                    Service pojo=current.get(key);
                    pojo.setAction(CREATE_ACTION);
                    create.add(pojo);
                }
            }
            for(String key: previous.keySet()){
                Service pojo= previous.get(key);
                pojo.setAction(TERMINATE_ACTION);
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

    public Boolean ifEmptyMemory(HashMap<String, Service> hp1) {
        if (hp1.isEmpty()) {

            return true;

        }
        else
            return false;
    }

    public void flushPreviousData () {
        previous.clear();
    }


}
