package com.crio.shorturl;

import java.security.KeyStore.Entry;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class XUrlImpl implements XUrl{

    HashMap<String,String>url = new HashMap<>();
    HashMap<String,Integer>urlCount = new HashMap<>();

    static final String str="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // If longUrl already has a corresponding shortUrl, return that shortUrl
    // If longUrl is new, create a new shortUrl for the longUrl and return it
    @Override
    public String registerNewUrl(String longUrl){

        if(url.containsKey(longUrl)){
            return url.get(longUrl);
        }
        
        Random rnd = new Random();
        StringBuilder sb =new StringBuilder();
        sb.append("http://short.url/");
        for (int i = 0; i <9; i++) {
            sb.append(str.charAt(rnd.nextInt(str.length())));
        }
        url.put(longUrl, sb.toString());
        return sb.toString();
    }

     // If shortUrl is already present, return null
    // Else, register the specified shortUrl for the given longUrl
    // Note: You don't need to validate if longUrl is already present,assume it is always new i.e. it hasn't been seen before
    @Override
    public String registerNewUrl(String longUrl, String shortUrl) {
        if(url.containsValue(shortUrl)){
            return null;
        }
        url.put(longUrl, shortUrl);
        return shortUrl;
    }

    // If shortUrl doesn't have a corresponding longUrl, return null
    // Else, return the corresponding longUrl
    @Override
    public String getUrl(String shortUrl) {
        if(url.containsValue(shortUrl)){
            for (Map.Entry<String, String> entry : url.entrySet()) {
                if(entry.getValue().equals(shortUrl)){
                    int count =(urlCount.get(entry.getKey())==null)? 0 : urlCount.get(entry.getKey());
                    urlCount.put(entry.getKey(), count+1); 
                    return entry.getKey();  
                }
            }
        }
        return null;
    }

     // Return the number of times the longUrl has been looked up using getUrl()
    @Override
    public Integer getHitCount(String longUrl) {
        if(url.get(longUrl)==null)
            return 0;
        else 
            return urlCount.get(longUrl);
        //return (url.get(longUrl)==null ? 0 : urlCount.get(longUrl));
    }

     // Delete the mapping between this longUrl and its corresponding shortUrl
    // Do not zero the Hit Count for this longUrl
    @Override
    public String delete(String longUrl) {
        return url.remove(longUrl);
    }

}