package com.example.mincongsitu_greenflag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText emailInput;
    TextView emailError;
    EditText passwordInput;
    EditText pwReEntry;
    CharSequence email;
    CharSequence password;
    TextView pwError;
    CharSequence reEntry;
    TextView reEntryError;
    ImageButton btn_back;
    ImageButton btn_next;
    AccountDB bdHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailInput = findViewById(R.id.et_email_input);
        emailError = findViewById(R.id.tv_email_error);
        passwordInput = findViewById(R.id.et_password_input);
        pwError = findViewById(R.id.error_password_error);
        pwReEntry = findViewById(R.id.et_re_password);
        reEntryError = findViewById(R.id.tv_password_mismatch);

        btn_back = findViewById(R.id.btn_back);
        btn_next = findViewById(R.id.btn_next);

        emailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                emailInput.setBackgroundResource(R.color.white);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                email = emailInput.getText();
                if(email.toString().equals("")){
                    emailInput.setBackgroundResource(R.color.white);
                }
                if((isValidEmail(email))){
                    emailInput.setBackgroundResource(R.drawable.valid_entry);
                    emailInput.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.tick,0);
                }else{
                    emailInput.setBackgroundResource(R.drawable.edit_text_border);
                    emailInput.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    //did not implement checking to see if email is created yet;
                }

            }
        });

        passwordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordInput.setBackgroundResource(R.color.white);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                password = passwordInput.getText();

                if(password.toString().equals("")){
                    passwordInput.setBackgroundResource(R.color.white);
                    pwError.setVisibility(View.GONE);
                }
                if(isValidPassword(password)){
                    passwordInput.setBackgroundResource(R.drawable.valid_entry);
                    passwordInput.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.tick,0);
                    pwError.setVisibility(View.GONE);
                }else{
                    passwordInput.setBackgroundResource(R.drawable.edit_text_border);
                    pwError.setVisibility(View.VISIBLE);
                    passwordInput.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    if(reEntryError.getVisibility() == View.VISIBLE){
                        reEntryError.setVisibility(View.GONE);
                    }
                }
            }
        });

        pwReEntry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pwReEntry.setBackgroundResource(R.color.white);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                password = passwordInput.getText();
                reEntry = pwReEntry.getText().toString();

                if(reEntry.equals("")){
                    pwReEntry.setBackgroundResource(R.color.white);
                    reEntryError.setVisibility(View.GONE);
                }
                if(reEntry.equals(password.toString())&&isValidPassword(password)){
                    pwReEntry.setBackgroundResource(R.drawable.valid_entry);
                    pwReEntry.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.tick,0);
                    reEntryError.setVisibility(View.GONE);
                }else{
                    pwReEntry.setBackgroundResource(R.drawable.edit_text_border);
                    reEntryError.setVisibility(View.VISIBLE);
                    pwReEntry.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);

                    if(pwError.getVisibility() == View.VISIBLE){
                        pwError.setVisibility(View.GONE);
                    }

                }
            }
        });

        btn_back.setOnClickListener(view -> {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);

        });

        email = emailInput.getText();
        password = passwordInput.getText();
        reEntry = pwReEntry.getText();

        if(isValidEmail(email)&&isValidPassword(password)&&(password.toString().equals(reEntry.toString()))){
            btn_next.setClickable(true);
        }

        btn_next.setOnClickListener(view -> {

            bdHandler = new AccountDB(RegisterActivity.this);

            if(bdHandler.doesEmailExist(email.toString()) > 0){
                emailError.setVisibility(View.VISIBLE);
            }else{
                bdHandler.addNewAccount(email.toString(),password.toString());
                Toast.makeText(this,"Congrats! Account created!",Toast.LENGTH_LONG).show();

            }

            Intent intent = new Intent(this,Registered.class);
            startActivity(intent);

        });



    }

    public static boolean isValidEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static boolean isValidPassword(CharSequence password) {
        Pattern pattern;
        Matcher matcher;
        String pwPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";

        pattern = Pattern.compile(pwPattern);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }





}