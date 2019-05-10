package com.example.stocktradingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.stocktradingapp.tasks.FundAccountTask;

public class FundAccount extends AppCompatActivity {
    //EditText
    private EditText editTextAccFrom;
    private EditText editTextAccTo;
    private EditText editTextAmount;
    private EditText editTextCurrency;
    private EditText editTextNotes;
    private EditText editTextPayee;
    //Button
    private Button buttonTransfer;
    //TextView
    private TextView tvTransId;
    private TextView tvTransDate;
    private TextView tvTransTime;
    private TextView tvTransactionDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_account);

        editTextAccFrom = (EditText)findViewById(R.id.editTextAccountFrom);
        editTextAccTo = (EditText)findViewById(R.id.editTextAccountTo);
        editTextAmount = (EditText)findViewById(R.id.editTextAmount);
        editTextCurrency = (EditText)findViewById(R.id.editTextCurrency);
        editTextNotes = (EditText)findViewById(R.id.editTextNotes);
        editTextPayee = (EditText)findViewById(R.id.editTextPayee);

        tvTransId = (TextView)findViewById(R.id.tvTransactionId);
        tvTransDate = (TextView)findViewById(R.id.tvTransactionDate);
        tvTransTime = (TextView)findViewById(R.id.tvTransactionTime);
        tvTransactionDetails = (TextView)findViewById(R.id.tvTransDetails);

        tvTransactionDetails.setVisibility(View.INVISIBLE);


        buttonTransfer = (Button) findViewById(R.id.buttonTransfer);
        buttonTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new FundAccountTask(getApplicationContext(), editTextAccFrom, editTextPayee, editTextAccTo,
                        editTextAmount, editTextCurrency, editTextNotes,
                        tvTransId, tvTransDate, tvTransTime, tvTransactionDetails).execute();
            }
        });


    }
}
