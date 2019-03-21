package com.example.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.os.Build.ID;

/**
 * Created by ADMIN on 12/4/2018.
 */

public class JsonUtils {
    private static String RESULT = "results";
    private static String ISO_639_1 = "iso_639_1";
    private static String ISO_3166_1 = "iso_3166_1";
    private static String KEY = "key";
    private static String NAME = "name";
    private static String SITE = "site";
    private static String SIZE = "size";
    private static String TYPE = "type";
    private static String AUTHOR= "author";
    private static String CONTENT= "content";
    private static String URL= "url";
    public static ArrayList<TrailerInfo> parseTrailerInfoJson(String s){
        ArrayList<TrailerInfo> list=new ArrayList<>();
        try {
            JSONObject json=new JSONObject(s);
            JSONArray results=json.getJSONArray(RESULT);
            for (int i=0;i<results.length();i++){
                JSONObject position=results.getJSONObject(i);

                String id=position.optString(ID);
                String iso_639_1=position.optString(ISO_639_1);
                String iso_3166_1=position.optString(ISO_3166_1);
                String key=position.optString(KEY);
                String name=position.optString(NAME);
                String site=position.optString(SITE);
                String size=position.optString(SIZE);
                String type=position.optString(TYPE);

                TrailerInfo info=new TrailerInfo();
                info.setId(id);
                info.setIso_639_1(iso_639_1);
                info.setIso_3166_1(iso_3166_1);
                info.setKey(key);
                info.setName(name);
                info.setSite(site);
                info.setSize(size);
                info.setType(type);

                list.add(info);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static ArrayList<ReviewInfo> parseReviewInfoJson(String s){
        ArrayList<ReviewInfo> list=new ArrayList<>();
        try {
            JSONObject json=new JSONObject(s);
            JSONArray results=json.getJSONArray(RESULT);

            for (int i=0;i<results.length();i++){
                JSONObject position=results.getJSONObject(i);
                String author=position.optString(AUTHOR);
                String content=position.optString(CONTENT);
                String id=position.optString(ID);
                String url=position.optString(URL);

                ReviewInfo info=new ReviewInfo();
                info.setAuthor(author);
                info.setContent(content);
                info.setId(id);
                info.setUrl(url);

                list.add(info);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
