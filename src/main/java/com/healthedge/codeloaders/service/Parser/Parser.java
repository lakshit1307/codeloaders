package com.healthedge.codeloaders.service.Parser;

import com.healthedge.codeloaders.entity.BaseEntity;

import java.io.IOException;
import java.util.List;

public interface Parser {

    public List<BaseEntity> parse(String filePath) throws IOException;
}
