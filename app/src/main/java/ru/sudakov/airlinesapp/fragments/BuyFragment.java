package ru.sudakov.airlinesapp.fragments;

import static ru.sudakov.airlinesapp.MainActivity.PASSWORD;
import static ru.sudakov.airlinesapp.MainActivity.URL_DB;
import static ru.sudakov.airlinesapp.MainActivity.USERNAME;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ru.sudakov.airlinesapp.R;

public class BuyFragment extends Fragment {
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextDestination;
    private EditText editTextNumber;
    private Button buttonBuy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buy, container, false);

        editTextName = view.findViewById(R.id.editTextName);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextDestination = view.findViewById(R.id.editTextDestination);
        editTextNumber = view.findViewById(R.id.editTextNumber);
        buttonBuy = view.findViewById(R.id.buttonBuy);

        buttonBuy.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String email = editTextEmail.getText().toString();
            String destination = editTextDestination.getText().toString();
            int numberOfTickets = Integer.parseInt(editTextNumber.getText().toString());

            new Thread(() -> writeToDatabase(name, destination, numberOfTickets)).start();

        });

        return view;
    }

    private void writeToDatabase(String name, String destination, int numberOfTickets) {
        try (Connection connection = DriverManager.getConnection(URL_DB, USERNAME, PASSWORD)) {
            System.out.println("We are connected");
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO bilets (title, price, flight, user_id)\n" +
                    "VALUES (?, ?, ?, 2)");

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, 12000 * numberOfTickets);
            preparedStatement.setString(3, destination);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            requireActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Покупка совершена", Toast.LENGTH_LONG).show());
        } catch (SQLException e) {
            System.out.println(e);
            requireActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Ошибка при оформлении заказа", Toast.LENGTH_LONG).show());
        }
    }

}