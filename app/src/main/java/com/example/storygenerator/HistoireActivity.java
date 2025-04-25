package com.example.storygenerator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import org.json.*;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HistoireActivity extends AppCompatActivity {
    private static final String TAG = "HistoireActivity";
    private TextView tvTitre, tvContenu, tvQ1, tvQ2;
    private RadioGroup rgQ1, rgQ2;
    private Button btnSoumettre, btnNouvelle;
    private JSONObject currentStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histoire);

        tvTitre      = findViewById(R.id.tvTitre);
        tvContenu    = findViewById(R.id.tvContenu);
        tvQ1         = findViewById(R.id.tvQ1);
        tvQ2         = findViewById(R.id.tvQ2);
        rgQ1         = findViewById(R.id.rgQ1);
        rgQ2         = findViewById(R.id.rgQ2);
        btnSoumettre = findViewById(R.id.btnSoumettre);
        btnNouvelle  = findViewById(R.id.btnNouvelle);

        List<String> choix = getIntent().getStringArrayListExtra("choix");
        try {
            currentStory = pickRandomStory(choix);
            displayStory(currentStory);
        } catch (Exception e) {
            Log.e(TAG, "Erreur JSON ou IO", e);
        }

        btnSoumettre.setOnClickListener(v -> showQuizResult());
        btnNouvelle.setOnClickListener(v -> {
            startActivity(new Intent(this, AccueilActivity.class));
            finish();
        });
    }

    private JSONObject pickRandomStory(List<String> mots) throws Exception {
        InputStream is = getAssets().open("histoires.json");
        byte[] buf = new byte[is.available()];
        int readBytes = is.read(buf);
        is.close();
        JSONArray arr = new JSONArray(new String(buf, StandardCharsets.UTF_8));
        List<JSONObject> filtered = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            JSONObject o = arr.getJSONObject(i);
            JSONArray keys = o.getJSONArray("mots_cles");
            outer:
            for (String m : mots) {
                for (int j = 0; j < keys.length(); j++) {
                    if (keys.getString(j).equalsIgnoreCase(m)) {
                        filtered.add(o);
                        break outer;
                    }
                }
            }
        }
        if (filtered.isEmpty()) filtered.add(arr.getJSONObject(0));
        return filtered.get(new Random().nextInt(filtered.size()));
    }

    private void displayStory(JSONObject o) throws JSONException {
        tvTitre.setText(o.getString("titre"));
        tvContenu.setText(o.getString("contenu"));

        JSONArray quiz = o.getJSONArray("quiz");

        // Question 1
        JSONObject q1 = quiz.getJSONObject(0);
        tvQ1.setText(q1.getString("question"));
        populateRadioGroup(rgQ1, q1.getJSONArray("options"));

        // Question 2
        JSONObject q2 = quiz.getJSONObject(1);
        tvQ2.setText(q2.getString("question"));
        populateRadioGroup(rgQ2, q2.getJSONArray("options"));
    }

    private void populateRadioGroup(RadioGroup rg, JSONArray opts) throws JSONException {
        rg.removeAllViews();
        for (int i = 0; i < opts.length(); i++) {
            RadioButton rb = new RadioButton(this);
            rb.setText(opts.getString(i));
            rb.setTextColor(ContextCompat.getColor(this, R.color.white));
            rg.addView(rb);
        }
    }

    private void showQuizResult() {
        try {
            JSONArray quiz = currentStory.getJSONArray("quiz");
            int score = 0;
            if (isCorrect(rgQ1, quiz.getJSONObject(0).getString("answer"))) score++;
            if (isCorrect(rgQ2, quiz.getJSONObject(1).getString("answer"))) score++;
            new AlertDialog.Builder(this)
                    .setTitle("Résultat")
                    .setMessage("Tu as " + score + "/2 bonnes réponses !")
                    .setPositiveButton("OK", null)
                    .show();
        } catch (Exception e) {
            Log.e(TAG, "Erreur lors du quiz", e);
        }
    }

    private boolean isCorrect(RadioGroup rg, String answer) {
        int id = rg.getCheckedRadioButtonId();
        if (id == -1) return false;
        RadioButton sel = findViewById(id);
        return sel.getText().toString().equals(answer);
    }
}
