package com.example.nim2020;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private String player1;
    private String player2;
    private int pile1;
    private int pile2;
    private int pile3;
    private boolean isChecked;
    private int onTheMove;
    private int rb_selected_id;

    TextView textView;
    EditText editText;
    RadioButton rb1;
    RadioButton rb2;
    RadioButton rb3;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        textView = (TextView) findViewById(R.id.text_view_on_the_move);
        editText = (EditText) findViewById(R.id.edit_text_take_val);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group_piles);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rb_selected_id = checkedId;
            }
        });

        rb1 = (RadioButton) findViewById(R.id.radio_button_1);
        rb2 = (RadioButton) findViewById(R.id.radio_button_2);
        rb3 = (RadioButton) findViewById(R.id.radio_button_3);

        button = (Button) findViewById(R.id.button_play);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeMove();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            player1 = intent.getStringExtra(MainActivity.PLAYER_1_NAME);
            player2 = intent.getStringExtra(MainActivity.PLAYER_2_NAME);
            pile1 = intent.getIntExtra(MainActivity.PILE_1_SIZE, 0);
            pile2 = intent.getIntExtra(MainActivity.PILE_2_SIZE, 0);
            pile3 = intent.getIntExtra(MainActivity.PILE_3_SIZE, 0);
            isChecked = intent.getBooleanExtra(MainActivity.CHECKBOX_STATUS, false);

            rb1.setText(String.valueOf(pile1));
            rb2.setText(String.valueOf(pile2));
            rb3.setText(String.valueOf(pile3));

            onTheMove = 1;
            changeName();
        }
    }

    private void changeName() {
        if (pile1 == 0 && pile2 == 0 && pile3 == 0) {
            textView.setText(R.string.game_over);
            return;
        }
        if (onTheMove == 1) {
            textView.setText(getString(R.string.on_the_move) + " " + player1);
        } else {
            textView.setText(getString(R.string.on_the_move) + " " + player2);
        }
    }

    private void makeMove() {

        if (editText.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.enter_valid_number), Toast.LENGTH_SHORT).show();
            return;
        }

        int removed;
        try {
            removed = Integer.valueOf(editText.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.enter_valid_number), Toast.LENGTH_SHORT).show();
            return;
        }
        if (removed <= 0) {
            Toast.makeText(this, getString(R.string.enter_valid_number), Toast.LENGTH_SHORT).show();
            return;
        }

        switch (rb_selected_id) {
            case R.id.radio_button_1:
                if (removed > pile1) {
                    Toast.makeText(this, getString(R.string.cant_take_this_much), Toast.LENGTH_SHORT).show();
                    return;
                }
                pile1 = pile1 - removed;
                rb1.setText(String.valueOf(pile1));
                break;
            case R.id.radio_button_2:
                if (removed > pile2) {
                    Toast.makeText(this, getString(R.string.cant_take_this_much), Toast.LENGTH_SHORT).show();
                    return;
                }
                pile2 = pile2 - removed;
                rb2.setText(String.valueOf(pile2));
                break;
            case R.id.radio_button_3:
                if (removed > pile3) {
                    Toast.makeText(this, getString(R.string.cant_take_this_much), Toast.LENGTH_SHORT).show();
                    return;
                }
                pile3 = pile3 - removed;
                rb3.setText(String.valueOf(pile3));
                break;
        }

        if (pile1 == 0 && pile2 == 0 && pile3 == 0) {
            Toast.makeText(this, (onTheMove == 1 ? player1 : player2)
                + " " + getString(R.string.wins), Toast.LENGTH_LONG).show();
            button.setClickable(false);
        }

        onTheMove = 3 - onTheMove;
        changeName();
        editText.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_game_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_hint:
                if (!isChecked)
                    return super.onOptionsItemSelected(item);
                int sum = pile1 ^ pile2 ^ pile3;
                if (sum == 0) {
                    Toast.makeText(this, ":(", Toast.LENGTH_LONG).show();
                    return true;
                }
                int p1 = pile1 ^ sum, p2 = pile2 ^ sum, p3 = pile3 ^ sum;
                if (p1 < pile1) {
                    Toast.makeText(this, 1 + ": " + (pile1 - p1), Toast.LENGTH_LONG).show();
                    return true;
                }
                if (p2 < pile2) {
                    Toast.makeText(this, 2 + ": " + (pile2 - p2), Toast.LENGTH_LONG).show();
                    return true;
                }
                if (p3 < pile3) {
                    Toast.makeText(this, 3 + ": " + (pile3 - p3), Toast.LENGTH_LONG).show();
                    return true;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
