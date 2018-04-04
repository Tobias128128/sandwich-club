package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView also_known_tv;
    private TextView origin_tv;
    private TextView description_tv;
    private TextView ingredients_tv;

    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        also_known_tv = findViewById(R.id.also_known_tv);
        origin_tv = findViewById(R.id.origin_tv);
        description_tv = findViewById(R.id.description_tv);
        ingredients_tv = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        List<String> sandwich_also_known = sandwich.getAlsoKnownAs();
        if ((sandwich_also_known != null) && (sandwich_also_known.size() > 0)) {
            for (String also_known_as : sandwich.getAlsoKnownAs()) {
                also_known_tv.append(also_known_as + ", ");
            }
            // deletes the last two characters ", "
            also_known_tv.setText(also_known_tv.getText().subSequence(0, also_known_tv.length() - 2));
        }

        String sandwich_origin = sandwich.getPlaceOfOrigin();
        if(sandwich_origin != null) {
            origin_tv.append(sandwich_origin);
        }

        String sandwich_description = sandwich.getDescription();
        if(sandwich_description != null)
        {
            description_tv.append(sandwich_description);
        }

        List<String> sandwich_ingredients = sandwich.getIngredients();
        if((sandwich_ingredients != null) && (sandwich_ingredients.size() > 0))
        {
            for(String ingredients : sandwich_ingredients)
            {
                ingredients_tv.append(ingredients + ", ");
            }
            // deletes the last two characters ", "
            ingredients_tv.setText(ingredients_tv.getText().subSequence(0, ingredients_tv.length() - 2));
        }
    }
}
