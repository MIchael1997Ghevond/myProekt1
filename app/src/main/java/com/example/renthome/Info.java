package com.example.renthome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Info extends AppCompatActivity {

    public EditText editText0;
    public EditText editText1;
    public EditText editText2;
    public EditText editText3;
    public TextView textView0;
    public TextView textView1;
    public TextView textView2;
    public TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        editText0 = findViewById(R.id.Address);
        editText1 = findViewById(R.id.floor);
        editText2 = findViewById(R.id.price);
        editText3 = findViewById(R.id.phone_number);
        textView0 = findViewById(R.id.red_text0);
        textView1 = findViewById(R.id.red_text1);
        textView2 = findViewById(R.id.red_text2);
        textView3 = findViewById(R.id.red_text3);


        final Button buttonnext = (Button) findViewById(R.id.buttonnext1234);
        buttonnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView0.setText("");
                textView1.setText("");
                textView2.setText("");
                textView3.setText("");

                if ((editText0.getText().toString().trim().length() != 0) && (editText1.getText().toString().trim().length() != 0)
                        && (editText2.getText().toString().trim().length() != 0) && (editText3.getText().toString().trim().length() != 0)) {


                    try {


                        String address = editText0.getText().toString();
                        String floor = editText1.getText().toString();
                        String price = editText2.getText().toString();
                        String phone_number = editText3.getText().toString();


                        Intent intent = new Intent(Info.this, MapsInput.class);
                        intent.putExtra("Address", address);
                        intent.putExtra("Floor", floor);
                        intent.putExtra("Price", price);
                        intent.putExtra("Phone_number", phone_number);
                        startActivity(intent);
                        finish();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (editText0.getText().toString().trim().length() == 0) {
                    textView0.setText("There is an empty field");
                } else if (editText1.getText().toString().trim().length() == 0) {
                    textView1.setText("There is an empty field");
                } else if (editText2.getText().toString().trim().length() == 0) {
                    textView2.setText("There is an empty field");
                } else if (editText3.getText().toString().trim().length() == 0) {
                    textView3.setText("There is an empty field");
                }

            }

        });

    }
    @Override
    public void onBackPressed() {

        try {
            Intent intent = new Intent(Info.this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }
}
