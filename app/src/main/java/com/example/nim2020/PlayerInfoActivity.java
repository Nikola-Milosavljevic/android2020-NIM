package com.example.nim2020;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PlayerInfoActivity extends AppCompatActivity {

    public static final String PLAYER_1_NAME = "player_1_name";
    public static final String PLAYER_2_NAME = "player_2_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_info);

        Button button = (Button) findViewById(R.id.button_ok_players);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPlayerInfo();
            }
        });

    }

    private void sendPlayerInfo() {
        EditText editText1 = (EditText) findViewById(R.id.edit_text_player1_name);
        EditText editText2 = (EditText) findViewById(R.id.edit_text_player2_name);

        String s1 = editText1.getText().toString();
        String s2 = editText2.getText().toString();

        if (s1.compareTo("") == 0 || s2.compareTo("") == 0) {
            Toast.makeText(this, R.string.invalid_name, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(PLAYER_1_NAME, s1);
        intent.putExtra(PLAYER_2_NAME, s2);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
