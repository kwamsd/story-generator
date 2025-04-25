package com.example.storygenerator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class AccueilActivity extends AppCompatActivity {
    private TextView tvBienvenue;
    private RecyclerView rvMotsCles;
    private Button btnGenerer;
    private MotsClesAdapter adapter;
    private String prenom = "Enfant";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        tvBienvenue = findViewById(R.id.tvBienvenue);
        rvMotsCles   = findViewById(R.id.rvMotsCles);
        btnGenerer   = findViewById(R.id.btnGenerer);

        btnGenerer.setEnabled(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quel est ton prénom ?");

        final EditText input = new EditText(this);
        input.setHint("Ton prénom");
        builder.setView(input);

        builder.setCancelable(false);
        builder.setPositiveButton("OK", (dialog, which) -> {
            String saisie = input.getText().toString().trim();
            if (!saisie.isEmpty()) {
                prenom = saisie;
            }
            tvBienvenue.setText(getString(R.string.salut, prenom));
            btnGenerer.setEnabled(true);
        });

        builder.show();

        String[] mots = getResources().getStringArray(R.array.mots_cles);
        List<String> list = new ArrayList<>();
        for (String m : mots) list.add(m);

        adapter = new MotsClesAdapter(list);
        rvMotsCles.setLayoutManager(new GridLayoutManager(this, 2));
        rvMotsCles.setAdapter(adapter);

        btnGenerer.setOnClickListener(v -> {
            ArrayList<String> choix = adapter.getChoix();
            Intent i = new Intent(this, HistoireActivity.class);
            i.putStringArrayListExtra("choix", choix);
            i.putExtra("prenom", prenom);
            startActivity(i);
        });
    }
}
