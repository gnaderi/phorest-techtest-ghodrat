package com.naderi.phorest.salon.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naderi.phorest.salon.common.exception.InvalidYamlInputException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class YamlValidator {
    private final static Logger LOGGER = LogManager.getLogger();
    private ObjectMapper yamlObjectMapper;

    public YamlValidator(@Qualifier("yamlObjectMapper") ObjectMapper yamlObjectMapper) {
        this.yamlObjectMapper = yamlObjectMapper;
    }

    public void validate(String fileType, String content) {
        if (!isValid(content)) {
            String errorMsg = String.format("Unable to parse changes of the '%s' Yaml file content:[%s]",
                    fileType, trimContent(content));
            LOGGER.debug(errorMsg);
            throw new InvalidYamlInputException(errorMsg);
        }
    }

    private String trimContent(String content) {
        return StringUtils.isNotBlank(content) && content.length() > 100 ? content.substring(0, 100) : content;
    }

    public boolean isValid(String content) {
        try {
            final JsonParser parser = yamlObjectMapper.createParser(content);
            final TreeNode treeNode = parser.readValueAsTree();
            return treeNode != null;
        } catch (IOException ex) {
            LOGGER.debug("Unable to parse Yaml content[{}]: {}", ex.getMessage(), trimContent(content));
        }
        return false;
    }

}
