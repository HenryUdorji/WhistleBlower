package com.chocolatedevelopers.whistleblower;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chocolatedevelopers.whistleblower.data.model.TransactionDetails;
import com.chocolatedevelopers.whistleblower.utils.BottomNavigationUtils;
import com.chocolatedevelopers.whistleblower.utils.Tools;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int ACTIVITY_NUM = 0;
    private BottomNavigationView navigation;
    private static final int MAX_STEP = 4;

    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    ArrayList<TransactionDetails> transactionDetailsArrayList;

    private String postfix_array[] = {
            "**** **** **** 6223",
            "**** **** **** 1027",
            "**** **** **** 5519",
            "**** **** **** 4661"
    };
    private String expire_array[] = {
            "08/20",
            "11/23",
            "05/19",
            "06/25",
    };
    private String cvv_array[] = {
            "771",
            "098",
            "334",
            "558",
    };
    private int logo_array[] = {
            R.drawable.ic_visa_new,
            R.drawable.ic_mastercard_new,
            R.drawable.ic_mastercard_new,
            R.drawable.ic_visa_new
    };

    private int color_array[] = {
            R.color.blue_A400,
            R.color.blue_500,
            R.color.amber_800,
            R.color.green_500
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initComponent();

        viewPager = (ViewPager) findViewById(R.id.view_pager);

        // adding bottom dots
        bottomProgressDots(0);

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        viewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin_overlap_payment));
        viewPager.setOffscreenPageLimit(MAX_STEP);
    }

    private void initComponent(){

        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        transactionDetailsArrayList = new ArrayList<>();
        transactionDetailsArrayList.add(new TransactionDetails("Ifeanyi Williams Uba", "45,000", "transfer", "05, July 2021", "verified"));
        transactionDetailsArrayList.add(new TransactionDetails("Henry Udorji", "66,000", "deposit", "22, June 2021", "denied"));
        transactionDetailsArrayList.add(new TransactionDetails("Prevail Excellent", "54,000", "deposit", "02, June 2021", "pending"));
        transactionDetailsArrayList.add(new TransactionDetails("Odogwu Speedy", "23,000", "transfer", "05, May 2021", "denied"));

        //configure adapter
        adapter = new TransactionAdapter(this, transactionDetailsArrayList);

        //set adapter
        recyclerView.setAdapter(adapter);

        //Bottom Navigation
        navigation = findViewById(R.id.navigation);
        BottomNavigationUtils.enableBottomNavigation(this, navigation);
        Menu menu = navigation.getMenu();
        MenuItem item = menu.getItem(ACTIVITY_NUM);
        item.setChecked(true);
    }


    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.changeNavigateionIconColor(toolbar, getResources().getColor(R.color.colorPrimary));
        Tools.setSystemBarColor(this, android.R.color.white);
        Tools.setSystemBarLight(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {
            bottomProgressDots(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void bottomProgressDots(int current_index) {
        LinearLayout dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        ImageView[] dots = new ImageView[MAX_STEP];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle);
            dots[i].setColorFilter(getResources().getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current_index].setImageResource(R.drawable.shape_circle);
            dots[current_index].setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.item_card_payment, container, false);
            ((TextView) view.findViewById(R.id.card_number)).setText(postfix_array[position]);
            ((TextView) view.findViewById(R.id.expire)).setText(expire_array[position]);
            ((TextView) view.findViewById(R.id.cvv)).setText(cvv_array[position]);
            ((ImageView) view.findViewById(R.id.card_logo)).setImageResource(logo_array[position]);
            ((CardView) view.findViewById(R.id.card)).setCardBackgroundColor(getResources().getColor(color_array[position]));

            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return MAX_STEP;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}