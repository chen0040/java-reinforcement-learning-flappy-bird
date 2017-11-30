package com.github.chen0040.jrl.flappybird.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Created by xschen on 12/8/15.
 */
public class ResourceFileUtils {
    private static final Logger logger = LoggerFactory.getLogger(ResourceFileUtils.class);

    public static InputStream getResourceStream(String filename)  {
        ClassLoader classLoader = ResourceFileUtils.class.getClassLoader();
        return classLoader.getResourceAsStream(filename);
    }

    public static InputStreamReader getResource(String filename) throws IOException {
        return new InputStreamReader(getResourceStream(filename));
    }

    public static boolean resourceExists(String filename){
        ClassLoader classLoader = ResourceFileUtils.class.getClassLoader();
        URL url = classLoader.getResource(filename);
        try{
            url.openStream();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static void lines(String filename, Consumer<Stream<String>> callback) {
        if(callback == null) {
            logger.error("There is not callback for lines");
            return;
        }

        try(BufferedReader reader = new BufferedReader(getResource(filename))){
            callback.accept(reader.lines());
        }
        catch (IOException e) {
            logger.error("Failed to read the file " + filename, e);
        }
    }

    public static List<String> lines(String filename) {

        List<String> result = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(getResource(filename))){
            result.addAll(reader.lines().collect(Collectors.toList()));
        }
        catch (IOException e) {
            logger.error("Failed to read the file " + filename, e);
        }

        return result;
    }

    public static void forEachLine(String filename, Consumer<String> callback) {
        lines(filename, lines -> lines.forEach(callback));
    }


    public static String readToEnd(String filename) {
        StringBuilder sb = new StringBuilder();
        lines(filename, stringStream -> sb.append(stringStream.collect(Collectors.joining("\n"))));
        return sb.toString();
    }


    public static byte[] getBytes(String filename) {
        InputStream inputStream = getResourceStream(filename);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        try {
            while((length = inputStream.read(buffer)) > 0){
                baos.write(buffer, 0, length);
            }
        }
        catch (IOException e) {
            logger.error("Failed to get bytes from " + filename, e);
        }
        return baos.toByteArray();
    }


}
