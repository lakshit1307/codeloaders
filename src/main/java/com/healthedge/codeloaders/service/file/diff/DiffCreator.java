package com.healthedge.codeloaders.service.file.diff;


import com.healthedge.codeloaders.service.file.model.FilePojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiffCreator {

    public static final String CREATE_ACTION = "CREATE";
    public static final String APPEND_ACTION = "APPEND";
    public static final String TERMINATE_ACTION = "TERMINATE";

    private HashMap<String, FilePojo> previous = new HashMap<String, FilePojo>();


    public Map<String, List<FilePojo>> diff(Map<String, FilePojo> current) {

        Map<String, List<FilePojo>> result = new HashMap<String, List<FilePojo>>();
        List<FilePojo> create = new ArrayList<FilePojo>();
        List<FilePojo> append = new ArrayList<FilePojo>();
        List<FilePojo> terminate = new ArrayList<FilePojo>();


        Boolean flag = ifEmptyMemory(previous);
        if (flag) {
            for(String key:current.keySet())
            {
                FilePojo filePojo=current.get(key);
                create.add(filePojo);
            }
            previous.putAll(current);
            current.clear();

        } else {

            for(String key: current.keySet()){
                if(previous.containsKey(key)){
                    FilePojo pojo2=current.get(key);
                    FilePojo pojo1= previous.get(key);
                    if(!pojo1.equals(pojo2)){
                        append.add(pojo2);
                    }
                    previous.remove(key);
                }
                else{
                    FilePojo pojo=current.get(key);
                    create.add(pojo);
                }
            }
            for(String key: previous.keySet()){
                FilePojo pojo= previous.get(key);
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

    public Boolean ifEmptyMemory(HashMap<String, FilePojo> hp1) {
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
