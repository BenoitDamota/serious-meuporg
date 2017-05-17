package com.example.service;

import com.example.dto.TagDTO;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by Florentin NOËL on 16/05/17.
 */
public interface TagService {

    TagDTO listTag(Long id);
    TagDTO createTag(Map<String, Object> values) throws InvocationTargetException, IllegalAccessException;
    TagDTO updateTag(Long id, Map<String, Object> values) throws InvocationTargetException, IllegalAccessException;
    void deleteTag(Long id);
}
