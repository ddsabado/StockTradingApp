package com.example.stocktradingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stocktradingapp.adapter.QuoteAdapter;
import com.example.stocktradingapp.adapter.TransactionAdapter;
import com.example.stocktradingapp.adapter.WatchListAdapter;
import com.example.stocktradingapp.data.AccountBalance;
import com.example.stocktradingapp.data.Item;
import com.example.stocktradingapp.data.Stock;
import com.example.stocktradingapp.data.Transaction;
import com.example.stocktradingapp.tasks.BuyTask;
import com.example.stocktradingapp.tasks.QuoteTask;
import com.example.stocktradingapp.tasks.WatchListTask;
import com.example.stocktradingapp.token.DownloadAccessTokenTask;
import com.example.stocktradingapp.token.Storage;
import com.example.stocktradingapp.ui.main.CustomPagerAdapter;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Navigation extends AppCompatActivity {

    private RecyclerView recyclerViewQuote;
    private RecyclerView recyclerViewWatchList;
    //ADAPTER
    private QuoteAdapter quoteAdapter;
    private WatchListAdapter watchListAdapter;
    private TransactionAdapter transactionsAdapter;
    //BTN
    private Button buttonQuote;
    private Button buttonWithdrawFunds;
    private Button buttonFundAccount;
    private Button buttonAddToWatchList;
    private Button buttonBuy;
    //EDIT TEXT
    private EditText editTextQuote;
    private EditText editTextStockCode;
    //TEXTVIEW
    private TextView tvAccountBalance;
    private TextView tvBuyingPower;
    private TextView tvBid;
    private TextView tvAsk;
    private TextView tvAskSize;
    private TextView tvAskPrice;
    private TextView tvBidSize;
    private TextView tvBidPrice;
    private TextView tvAmount;




    private ArrayList<Stock> watchList;

    private OkHttpClient.Builder httpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        //VIEWPAGER
        final ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new CustomPagerAdapter(this));
        //RECYCLER VIEW
        recyclerViewQuote = (RecyclerView) findViewById(R.id.recycler_view_quote);

        //ACCESS TOKEN DOWNLOAD
        new DownloadAccessTokenTask(getApplicationContext()).execute();
        //initAccountData();
        //TODO: SELECT TAB 0 ON APP LAUNCH

        watchList = new ArrayList<Stock>();
        //watchListAdapter = new WatchListAdapter(watchList);
/*
        buttonFundAccount = (Button)findViewById(R.id.buttonFundAccount);
        buttonFundAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "Button Clicked");
                Intent intent = new Intent(Navigation.this, FundAccount.class);
                startActivity(intent);
            }
        });
*/

        //TABS
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        /*
        TabLayout.Tab tab1 = tabs.getTabAt(1);
        TabLayout.Tab tab0 = tabs.getTabAt(0);
        tab1.select();
        tab0.select();
        */

        tabs.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){     //ACCOUNT

                    initAccountData();
                    buttonWithdrawFunds = (Button)findViewById(R.id.buttonWithdrawFunds);
                    buttonFundAccount = (Button)findViewById(R.id.buttonFundAccount);

                    //FUND ACCOUNT BUTTON
                    buttonFundAccount.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("TAG", "Fund Account Clicked");
                            Intent intent = new Intent(Navigation.this, FundAccount.class);
                            startActivity(intent);
                        }
                    });
                    //WITHDRAW FUND BUTTON
                    buttonWithdrawFunds.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("TAG", "Withdraw Fund Clicked");
                            Intent intent =  new Intent(Navigation.this, FundAccount.class);
                            startActivity(intent);

                        }
                    });
                }
                else if(tab.getPosition() == 1){       //TRANSACTIONS
                    initAccountTransactions();
                }
                else if(tab.getPosition() == 2){    // QUOTE
                    buttonQuote = (Button) findViewById(R.id.buttonQuote);
                    buttonBuy = (Button) findViewById(R.id.buttonBuy);
                    editTextQuote = (EditText) findViewById(R.id.editTextQuote);
                    recyclerViewQuote = (RecyclerView) findViewById(R.id.recycler_view_quote);

                    //tv declarations
                    tvBid = (TextView)findViewById(R.id.tvBid);
                    tvAsk = (TextView)findViewById(R.id.tvAsk);
                    tvAskSize = (TextView)findViewById(R.id.tvAskSize);
                    tvAskPrice = (TextView)findViewById(R.id.tvAskPrice);
                    tvBidSize = (TextView)findViewById(R.id.tvBidSize);
                    tvBidPrice = (TextView)findViewById(R.id.tvBidPrice);

                    buttonQuote.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("TAG", "Quote Clicked");
                            new QuoteTask(getApplicationContext(), recyclerViewQuote, editTextQuote, quoteAdapter).execute();
                        }
                    });

                    buttonBuy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("TAG", "Buy Clicked");
                            new QuoteTask(getApplicationContext(), recyclerViewQuote, editTextQuote, quoteAdapter).execute();
                            new BuyTask(getApplicationContext(), editTextQuote).execute();
                        }
                    });
                }
                else if(tab.getPosition() == 3){    //WATCHLIST
                    editTextStockCode = findViewById(R.id.editTextStockCode);
                    buttonAddToWatchList = findViewById(R.id.buttonAddToWatchList);
                    recyclerViewWatchList = findViewById(R.id.recycler_view_watchlist);
                    buttonAddToWatchList.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new WatchListTask(getApplicationContext(),recyclerViewWatchList, editTextStockCode, watchListAdapter, watchList).execute();
                        }
                    });

                }
                else if(tab.getPosition() == 4){       //TRADE



                }
                else if(tab.getPosition() == 5){       //STOCK MARKET
                    final TickerView tickerView = findViewById(R.id.tickerView);
                    tickerView.setAnimationInterpolator(new OvershootInterpolator());
                    tickerView.setCharacterLists(TickerUtils.provideNumberList());
                    tickerView.setAnimationDuration(5000);
                    tickerView.setGravity(Gravity.START);
                    tickerView.setText("1234");
                    tickerView.setText("4321");


                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            // TO DO: ON UNSELECT
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            // TO DO: ON RESELECT
            }
        });



    }

    private void initAccountData(){
        //Token Webservice
        final String accessToken = new Storage(getApplicationContext()).getAccessToken();
        Log.d("Transaction Token",accessToken);

        httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("Authorization", accessToken).build();
                return chain.proceed(request);
            }
        });

        //Create Webservice
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utils.baseAccountsUrl)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WebService webService = retrofit.create(WebService.class);
        Call<AccountBalance> call = webService.getAccountBalance();
        call.enqueue(new Callback<AccountBalance>() {
            @Override
            public void onResponse(Call<AccountBalance> call, Response<AccountBalance> response) {
                Log.d("Account Balance Success", response.message()+response.body());
                if(response.body() != null) {
                    tvAccountBalance = (TextView) findViewById(R.id.tvActualBalance);
                    tvBuyingPower = (TextView) findViewById(R.id.tvBuyingPower);
                    tvAccountBalance.setText(response.body().getAvailableBalance().getAmount());
                    tvBuyingPower.setText(response.body().getAvailableBalance().getAmount());
                }
                else{
                    tvAccountBalance = (TextView) findViewById(R.id.tvActualBalance);
                    tvBuyingPower = (TextView) findViewById(R.id.tvBuyingPower);
                    tvAccountBalance.setText("null");
                    tvBuyingPower.setText("null");
                    Toast.makeText(getApplicationContext(),response.code()+ ":" + response.message(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<AccountBalance> call, Throwable t) {
                Log.d("Account Balance Failure", t.getMessage());
                Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    private void initAccountTransactions(){
        final String accessToken = new Storage(getApplicationContext()).getAccessToken();
        Log.d("Transaction Token",accessToken);

        httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("Authorization", accessToken).build();
                return chain.proceed(request);
            }
        });
        //Create Webservice

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utils.baseAccountsUrl)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WebService webService = retrofit.create(WebService.class);
        Call<Transaction> call = webService.getAccountTransactions();
        call.enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                Log.d("Account Trans Success", response.message()+response.body());
                if(response.body() != null) {
                    generateTransactionList(response.body().getItems());
                }
                else{
                    Toast.makeText(getApplicationContext(),response.code()+ ":" + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {
                Log.d("Account Balance Failure", t.getMessage());
                Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void generateTransactionList(List<Item> itemList){
        RecyclerView recyclerView = findViewById(R.id.recyclerview_trans);

        transactionsAdapter = new TransactionAdapter(itemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(transactionsAdapter);
        transactionsAdapter.notifyDataSetChanged();

    }
}