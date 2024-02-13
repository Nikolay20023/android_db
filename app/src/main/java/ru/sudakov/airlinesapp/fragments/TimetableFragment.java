package ru.sudakov.airlinesapp.fragments;

import static ru.sudakov.airlinesapp.MainActivity.PASSWORD;
import static ru.sudakov.airlinesapp.MainActivity.URL_DB;
import static ru.sudakov.airlinesapp.MainActivity.USERNAME;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ru.sudakov.airlinesapp.R;

public class TimetableFragment extends Fragment {
    private TableLayout tableLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);
        tableLayout = view.findViewById(R.id.tableLayoutTimetable);

        new Thread(this::readFromDatabase).start();

        return view;
    }

    private void readFromDatabase() {
        try (Connection connection = DriverManager.getConnection(URL_DB, USERNAME, PASSWORD)) {
            System.out.println("We are connected");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM bilets");
            while (resultSet.next()) {
                final int id = resultSet.getInt(1);
                final String name = resultSet.getString(2);
                final double price = resultSet.getDouble(3);
                final String destination = resultSet.getString(4);
                requireActivity().runOnUiThread(() -> addRowToTable(id, name, price, destination));
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void addRowToTable(int id, String name, double price, String destination) {
        Typeface customTypeface = Typeface.create("sans-serif-light", Typeface.NORMAL);
        TableRow tableRow = new TableRow(getActivity());

        tableRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        TextView textView1 = new TextView(getActivity());
        textView1.setText(String.valueOf(id));
        textView1.setPadding(20, 20, 20, 20);
        textView1.setGravity(Gravity.CENTER);
        textView1.setTypeface(customTypeface);
        tableRow.addView(textView1);

        TextView textView2 = new TextView(getActivity());
        textView2.setText(name);
        textView2.setPadding(20, 20, 20, 20);
        textView2.setGravity(Gravity.CENTER);
        textView2.setTypeface(customTypeface);
        tableRow.addView(textView2);

        TextView textView3 = new TextView(getActivity());
        textView3.setText(String.valueOf(price));
        textView3.setPadding(20, 20, 20, 20);
        textView3.setGravity(Gravity.CENTER);
        textView3.setTypeface(customTypeface);
        tableRow.addView(textView3);

        TextView textView4 = new TextView(getActivity());
        textView4.setText(destination);
        textView4.setPadding(20, 20, 20, 20);
        textView4.setGravity(Gravity.CENTER);
        textView4.setTypeface(customTypeface);
        tableRow.addView(textView4);

        tableLayout.addView(tableRow);
    }
}