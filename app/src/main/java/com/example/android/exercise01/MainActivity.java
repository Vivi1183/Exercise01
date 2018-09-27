package com.example.android.exercise01;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppDatabase dataBase;

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText idEditTexxt;
    private Button okButton;
    private TextView allUsers;
    private TextView testTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (dataBase == null) {
            dataBase = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "testDB2").build();
        }

        firstNameEditText = (EditText) findViewById(R.id.first_name_edit_text);
        lastNameEditText = (EditText) findViewById(R.id.last_name_edit_text);
        idEditTexxt = (EditText) findViewById(R.id.id_edit_text);
        okButton = (Button) findViewById(R.id.button_ok);
        allUsers = (TextView) findViewById(R.id.text_view);

        displayAllUsers();
    }


    public void displayAllUsers() {


        //Асинхронная задача
        new AsyncTask<Void, Void, String>() {


            @Override
            protected String doInBackground(final Void... params) {
                // something you know that will take a few seconds
                UserDao userDao1 = dataBase.userDao();
                List<User> allUsersList = userDao1.getAll();

                StringBuilder allStringUsers = new StringBuilder();
                for (User user : allUsersList) {
                    allStringUsers.append(user.toString() + "\n");
                }
                return allStringUsers.toString();
            }


            @Override
            protected void onPostExecute(final String allStringUsers) {
                allUsers.setText(allStringUsers);
            }
        }.execute();
    }


    public void addInList(View view) {
        // здесь надо реализовать чтение данных о пользователе из соответствующих полей и создать User'a


        String firstNameString = firstNameEditText.getText().toString();
        String lastNameString = lastNameEditText.getText().toString();
        final User user = new User();
        user.setFirstName(firstNameString);
        user.setLastName(lastNameString);

        // считать
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void... params) {
                // сохранение пользователя в базу данных
                UserDao userDao1 = dataBase.userDao();
                userDao1.insertAll(user);
                // ничего возвращать не нужно. Оставь так как есть
                return null;
            }

            @Override
            protected void onPostExecute(final Void result) {
                displayAllUsers();
                // можешь еще запустить тут какой-нибудь popup (всплывающее окно) с информацией о том, что пользователь удален
            }
        }.execute();

    }

    public void deleteUser (View view){
        // здесь надо реализовать чтение данных о пользователе из соответствующих полей и создать User'a


        String firstNameString = firstNameEditText.getText().toString();
        String lastNameString = lastNameEditText.getText().toString();
        String idString = idEditTexxt.getText().toString();
        int id = Integer.parseInt(idString);
        final User user = new User();
        user.setFirstName(firstNameString);
        user.setLastName(lastNameString);
        user.setUid(id);

        // считать
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void... params) {
                // сохранение пользователя в базу данных
                UserDao userDao1 = dataBase.userDao();
                userDao1.delete(user);
                // ничего возвращать не нужно. Оставь так как есть
                return null;
            }

            @Override
            protected void onPostExecute(final Void result) {
                displayAllUsers();
                // можешь еще запустить тут какой-нибудь popup (всплывающее окно) с информацией о том, что пользователь удален
            }
        }.execute();

    }
}
