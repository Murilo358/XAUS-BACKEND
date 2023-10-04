package com.XAUS.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

    public class MapperUtils {

        private MapperUtils() {}

        public static ObjectMapper createMapper() {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
            return mapper;
        }
    }

