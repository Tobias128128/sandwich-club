package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        String mainName;
        String placeOfOrigin;
        String description;
        String image;
        List<String> alsoKnownAs = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();
        JSONObject sandwich;

        try {
            sandwich = new JSONObject(json);
            JSONObject name = sandwich.getJSONObject("name");
            mainName = name.getString("mainName");
            JSONArray jsonArray_alsoKnownAs = name.getJSONArray("alsoKnownAs");
            for (int i = 0; i < jsonArray_alsoKnownAs.length(); i++) {
                alsoKnownAs.add(jsonArray_alsoKnownAs.getString(i));
            }
            placeOfOrigin = sandwich.getString("placeOfOrigin");
            description = sandwich.getString("description");
            image = sandwich.getString("image");
            JSONArray jsonArray_ingredients = sandwich.getJSONArray("ingredients");
            for (int i = 0; i < jsonArray_ingredients.length(); i++) {
                ingredients.add(jsonArray_ingredients.getString(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

    }

}
