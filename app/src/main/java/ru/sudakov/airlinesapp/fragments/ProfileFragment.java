package ru.sudakov.airlinesapp.fragments;

import static ru.sudakov.airlinesapp.MainActivity.PASSWORD;
import static ru.sudakov.airlinesapp.MainActivity.URL_DB;
import static ru.sudakov.airlinesapp.MainActivity.USERNAME;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ThemedSpinnerAdapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ru.sudakov.airlinesapp.R;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_timetable, container, false);

        new Thread(this::readFromDatabase).start();



        return view;
    }

    private void readFromDatabase() {
        try (Connection connection = DriverManager.getConnection(URL_DB, USERNAME, PASSWORD)) {
            System.out.println("We are connected");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                final int id = resultSet.getInt(1);
                final String name = resultSet.getString(2);
                final String email = resultSet.getString(3);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}