package com.marco.amorim.gamelib;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText game_title_input2, game_studio_input2, game_store_link_input2;
    Button update_button, delete_button;

    String id, gameTitle, gameStudio, gameStoreLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        game_title_input2 = findViewById(R.id.game_title_input2);
        game_studio_input2 = findViewById(R.id.game_studio_input2);
        game_store_link_input2 = findViewById(R.id.game_store_link_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        //First we call this
        getAndSetIntentData();

        //Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(gameTitle);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //And only then we call this
                DbHelper myDB = new DbHelper(UpdateActivity.this);
                gameTitle = game_title_input2.getText().toString().trim();
                gameStudio = game_studio_input2.getText().toString().trim();
                gameStoreLink = game_store_link_input2.getText().toString().trim();
                myDB.updateGame(id, gameTitle, gameStudio, gameStoreLink);
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("game_title") &&
                getIntent().hasExtra("game_studio") && getIntent().hasExtra("game_store_link")){
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            gameTitle = getIntent().getStringExtra("game_title");
            gameStudio = getIntent().getStringExtra("game_studio");
            gameStoreLink = getIntent().getStringExtra("game_store_link");

            //Setting Intent Data
            game_title_input2.setText(gameTitle);
            game_studio_input2.setText(gameStudio);
            game_store_link_input2.setText(gameStoreLink);
            Log.d("stev", gameTitle +" "+ gameStudio +" "+ gameStoreLink);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + gameTitle + " ?");
        builder.setMessage("Are you sure you want to delete " + gameTitle + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DbHelper myDB = new DbHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}
