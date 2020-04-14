package com.example.nim2020;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String PLAYER_1_NAME = "player_1_name";
    public static final String PLAYER_2_NAME = "player_2_name";
    public static final String PILE_1_SIZE = "pile_1_size";
    public static final String PILE_2_SIZE = "pile_2_size";
    public static final String PILE_3_SIZE = "pile_3_size";
    public static final String CHECKBOX_STATUS = "checkbox_status";

    private static final int PLAYER_INFO_REQUEST_CODE = 1;

    private int currBackgroundColor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addPlayersButton = findViewById(R.id.button_add_players);
        addPlayersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlayerInfoActivity();
            }
        });

        Button startGameButton = findViewById(R.id.button_start_game);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGameActivity();
            }
        });

        TextView textView = findViewById(R.id.text_view_game_name);
        registerForContextMenu(textView);
    }

    private void startGameActivity() {
        TextView player1 = findViewById(R.id.p1_name);
        TextView player2 = findViewById(R.id.p2_name);
        EditText p1 = findViewById(R.id.pile_1);
        EditText p2 = findViewById(R.id.pile_2);
        EditText p3 = findViewById(R.id.pile_3);
        CheckBox ch = findViewById(R.id.checkbox_hint);

        String name1 = player1.getText().toString();
        String name2 = player2.getText().toString();
        if (name1.isEmpty() || name2.isEmpty()) {
            Toast.makeText(this, getString(R.string.enter_player_name), Toast.LENGTH_SHORT).show();
            return;
        }

        int pile1, pile2, pile3;
        try {
            pile1 = Integer.parseInt(p1.getText().toString());
            pile2 = Integer.parseInt(p2.getText().toString());
            pile3 = Integer.parseInt(p3.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.wrong_pile_size), Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(PLAYER_1_NAME, name1);
        intent.putExtra(PLAYER_2_NAME, name2);
        intent.putExtra(PILE_1_SIZE, pile1);
        intent.putExtra(PILE_2_SIZE, pile2);
        intent.putExtra(PILE_3_SIZE, pile3);
        intent.putExtra(CHECKBOX_STATUS, ch.isChecked());
        startActivity(intent);
    }

    private void startPlayerInfoActivity() {
        Intent intent = new Intent(this, PlayerInfoActivity.class);
        startActivityForResult(intent, PLAYER_INFO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLAYER_INFO_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    String player1name = data.getStringExtra(PlayerInfoActivity.PLAYER_1_NAME);
                    String player2name = data.getStringExtra(PlayerInfoActivity.PLAYER_2_NAME);

                    TextView p1 = findViewById(R.id.p1_name);
                    TextView p2 = findViewById(R.id.p2_name);
                    p1.setText(player1name);
                    p2.setText(player2name);

                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        TextView textView = findViewById(R.id.text_view_game_name);
        switch (item.getItemId()) {
            case R.id.menu_item_settings:
                Toast.makeText(this, R.string.menu_settings, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_item_background_color: // menjamo pozadinu izmedju crvene i zelene i default
                TypedValue val = new TypedValue();
                getTheme().resolveAttribute(android.R.attr.windowBackground, val, true);
                int color = val.data;

                currBackgroundColor = (currBackgroundColor + 1) % 3;
                if (currBackgroundColor == 1)
                    color = Color.RED;
                if (currBackgroundColor == 2)
                    color = Color.GREEN;
                findViewById(android.R.id.content).setBackgroundColor(color);
                return true;

            case R.id.menu_item_increase_font:
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textView.getTextSize() + 1);
                return true;
            case R.id.menu_item_decrease_font:
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textView.getTextSize() - 1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_change_size, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        TextView textView = findViewById(R.id.text_view_game_name);

        switch (item.getItemId()) {
            case R.id.menu_item_increase_font:
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textView.getTextSize() + 2);
                return true;
            case R.id.menu_item_decrease_font:
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textView.getTextSize() - 2);
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }
}
