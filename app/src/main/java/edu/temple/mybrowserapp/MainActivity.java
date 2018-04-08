package edu.temple.mybrowserapp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView urlEditText;
    FrameLayout pageDisplay;
    Button button;
    FragmentManager fm;
    WebFragment webFragment;
    ArrayList<Fragment> fragmentArray;
    private static final String URL = "web url";

    FragmentStatePagerAdapter fragmentStatePagerAdapter;
    ViewPager viewPager;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentArray = new ArrayList<>();
        count = 0;

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        urlEditText = findViewById(R.id.urlEditText);
        pageDisplay = findViewById(R.id.webFrame);

        fm = getSupportFragmentManager();
        fragmentStatePagerAdapter = new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                    return fragmentArray.get(position);
            }

            @Override
            public int getCount() {
                return fragmentArray.size();
            }

            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                urlEditText.setText(fragmentArray.get(position).getArguments().getString(URL));
                count = position+1;
            }
        };

        viewPager = findViewById(R.id.viewPager);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener());
        viewPager.setAdapter(fragmentStatePagerAdapter);

        webFragment = WebFragment.newInstance("");
        fragmentArray.add(webFragment);
        fragmentStatePagerAdapter.notifyDataSetChanged();
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create fragment and pass the URL text to it
                String url = urlEditText.getText().toString();
                count = fragmentArray.size();
                webFragment.loadUrl(url);
               fragmentStatePagerAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.newButton:
                //store fragment in PagerAdapter and remove current fragment from screen
                webFragment = WebFragment.newInstance("");
                urlEditText.setText("");
                fragmentArray.add(webFragment);
                fragmentStatePagerAdapter.notifyDataSetChanged();
                viewPager.setCurrentItem(fragmentArray.size()-1);
                return true;
            case R.id.prevButton:
                //replace current fragment with previous fragment (if there is one)
                if (count > 1) {
                    count--;
                    urlEditText.setText(fragmentArray.get(count - 1).getArguments().getString(URL));
                    webFragment = (WebFragment) fragmentArray.get(count - 1);
                    viewPager.setCurrentItem(count-1);
                    fragmentStatePagerAdapter.notifyDataSetChanged();
                }
                return true;
            case R.id.nextButton:
                //replace current fragment with next fragment (if there is one)
                if (count < fragmentArray.size()) {
                    urlEditText.setText(fragmentArray.get(count).getArguments().getString(URL));
                webFragment = (WebFragment) fragmentArray.get(count);
                    viewPager.setCurrentItem(count);
                fragmentStatePagerAdapter.notifyDataSetChanged();
                    count++;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
