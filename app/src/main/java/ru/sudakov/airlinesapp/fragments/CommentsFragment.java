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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ru.sudakov.airlinesapp.R;

public class CommentsFragment extends Fragment {

    private EditText editComment;

    private TableLayout tableLayout;

    private Button buttonComment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comments, container, false);

        editComment = view.findViewById(R.id.editTextComment);
        tableLayout = view.findViewById(R.id.tableLayoutComment);
        buttonComment = view.findViewById(R.id.buttonComment);

        new Thread(this::readFromDatabase).start();

        buttonComment.setOnClickListener(v -> {
            String title = editComment.getText().toString();

            new Thread(() -> writeToDatabase(title)).start();
        });

        return view;

    }

    private void readFromDatabase() {
        try (Connection connection = DriverManager.getConnection(URL_DB, USERNAME, PASSWORD)) {
            System.out.println("We are connected");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM comments");
            while (resultSet.next()) {
                final int id = resultSet.getInt(1);
                final String title = resultSet.getString(4);

                requireActivity().runOnUiThread(() -> addRowToTable(id, title));
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void addRowToTable(int id, String title) {
        Typeface customTypeface = Typeface.create("sans-serif-light", Typeface.NORMAL);
        TableRow tableRow = new TableRow(getActivity());

        tableRow.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView textView1 = new TextView(getActivity());
        textView1.setText(String.valueOf(id));
        textView1.setPadding(20, 20, 20, 20);
        textView1.setGravity(Gravity.CENTER);
        textView1.setTypeface(customTypeface);
        tableRow.addView(textView1);

        TextView textView2 = new TextView(getActivity());
        textView2.setText(title);
        textView2.setPadding(20, 20, 20, 20);
        textView2.setGravity(Gravity.CENTER);
        textView2.setTypeface(customTypeface);
        tableRow.addView(textView2);

        tableLayout.addView(tableRow);
    }

    private void writeToDatabase(String title){

        try (Connection connection = DriverManager.getConnection(URL_DB, USERNAME, PASSWORD)) {
            System.out.println("We are connected");

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO comments (title, user_id)\n" +
                    "VALUES (?, 2)");

            preparedStatement.setString(1, title);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            requireActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Успешно", Toast.LENGTH_LONG).show());

        } catch (SQLException e){
            System.out.println(e);
            requireActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Ошибка", Toast.LENGTH_LONG).show());
        }

    }
}