package com.github.chen0040.jrl.flappybird.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by xschen on 12/6/2017.
 */
public class StringUtils {

   public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
           Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

   public static boolean validateEmail(String emailStr) {
      Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
      return matcher.find();
   }

   public static String stripQuotation(String s) {
      if(s.startsWith("\"")) {
         s = s.substring(1, s.length());
      }
      if(s.endsWith("\"")) {
         s = s.substring(0, s.length()-1);
      }
      return s;
   }

   public static boolean isEmpty(String text) {
      if(text != null) text = text.trim();
      return text == null || text.equals("");
   }

   public static String cleanup(String text){
      String[] parts = text.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase().split("\\s+");
      StringBuilder sb = new StringBuilder();
      for(String p : parts) {
         sb.append(p);
      }

      return sb.toString();
   }


   public static long parseLong(String text, long defaultValue) {
      long result = defaultValue;
      try{
         result = Long.parseLong(text);
      } catch(Exception exception) {
         result = defaultValue;
      }
      return result;
   }
}
