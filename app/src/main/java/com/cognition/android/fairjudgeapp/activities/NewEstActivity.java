package com.cognition.android.fairjudgeapp.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;

import com.cognition.android.fairjudgeapp.R;
import com.cognition.android.fairjudgeapp.models.DatabaseHelper;
import com.cognition.android.fairjudgeapp.models.Establishment;

public class NewEstActivity extends AppCompatActivity {

    LinearLayoutCompat lytParent;
    Toolbar toolbar;
    TextInputLayout txtReviewerIdWrap, txtEstablishmentNameWrap, txtFoodTypeWrap, txtLocationWrap;
    TextInputEditText txtReviewerId, txtEstablishmentName, txtFoodType, txtLocation;
    AppCompatSpinner txtEstablishmentType, txtRating;
    AppCompatButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_est);

        initViews();
    }

    private void initViews() {
        lytParent = findViewById(R.id.lytParent);
        toolbar = findViewById(R.id.toolbar);
        txtReviewerIdWrap = findViewById(R.id.txtReviewerIdWrap);
        txtReviewerId = findViewById(R.id.txtReviewerId);
        txtEstablishmentNameWrap = findViewById(R.id.txtEstablishmentNameWrap);
        txtEstablishmentName = findViewById(R.id.txtEstablishmentName);
        txtFoodTypeWrap = findViewById(R.id.txtFoodTypeWrap);
        txtFoodType = findViewById(R.id.txtFoodType);
        txtLocationWrap = findViewById(R.id.txtLocationWrap);
        txtLocation = findViewById(R.id.txtLocation);
        btnAdd = findViewById(R.id.btnAdd);
        txtEstablishmentType = findViewById(R.id.txtEstablishmentType);
        txtRating = findViewById(R.id.txtRating);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ArrayAdapter<CharSequence> establishmentTypeAdapter = ArrayAdapter.createFromResource(NewEstActivity.this,
                R.array.establishment_types, android.R.layout.simple_spinner_item);
        establishmentTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txtEstablishmentType.setAdapter(establishmentTypeAdapter);

        ArrayAdapter<CharSequence> establishmentRatingAdapter = ArrayAdapter.createFromResource(NewEstActivity.this,
                R.array.establishment_ratings, android.R.layout.simple_spinner_item);
        establishmentRatingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txtRating.setAdapter(establishmentRatingAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reviewerId = txtReviewerId.getText().toString().trim();
                String establishmentName = txtEstablishmentName.getText().toString().trim();
                String establishmentType = txtEstablishmentType.getSelectedItem().toString();
                String foodType = txtFoodType.getText().toString().trim();
                String location = txtLocation.getText().toString().trim();
                String ratingText = txtRating.getSelectedItem().toString();
                int rating;

                switch (ratingText) {
                    case "0 Stars":
                        rating = 0;
                        break;

                    case "1 Star":
                        rating = 1;
                        break;

                    case "2 Stars":
                        rating = 2;
                        break;

                    case "3 Stars":
                        rating = 3;
                        break;

                    case "4 Stars":
                        rating = 4;
                        break;

                    case "5 Stars":
                        rating = 5;
                        break;

                    default:
                        rating = 0;
                        break;
                }

                if (reviewerId.isEmpty())
                    txtReviewerIdWrap.setError(getString(R.string.required_field));

                if (establishmentName.isEmpty())
                    txtEstablishmentNameWrap.setError(getString(R.string.required_field));


                if (!reviewerId.isEmpty() && !establishmentName.isEmpty()) {
                    DatabaseHelper databaseHelper = new DatabaseHelper(NewEstActivity.this);

                    Establishment establishment = new Establishment(
                            reviewerId,
                            establishmentName,
                            establishmentType,
                            foodType.isEmpty() ? null : foodType,
                            location.isEmpty() ? null : location,
                            rating
                    );

                    databaseHelper.insertEstablishment(establishment);
                    Snackbar.make(lytParent, R.string.added_successfully, Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
