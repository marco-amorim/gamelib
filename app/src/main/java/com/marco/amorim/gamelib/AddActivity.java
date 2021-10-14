package com.marco.amorim.gamelib;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    EditText game_title_input, game_studio_input, game_store_link;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        game_title_input = findViewById(R.id.game_title_input);
        game_studio_input = findViewById(R.id.game_studio_input);
        game_store_link = findViewById(R.id.game_store_link_input);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper myDB = new DbHelper(AddActivity.this);
                myDB.addGame(game_title_input.getText().toString().trim(),
                        game_studio_input.getText().toString().trim(),
                        game_store_link.getText().toString().trim());
            }
        });
    }
}
