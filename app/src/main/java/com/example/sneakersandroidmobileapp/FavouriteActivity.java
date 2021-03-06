package com.example.sneakersandroidmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;

public class FavouriteActivity extends AppCompatActivity {
    private DataBaseHelper dataBaseHelper;
    private GridView sneakerGridview;
    //private RecyclerView mRecyclerView;
    private ArrayList<SneakerModel> favouriteList;
    private SneakerAdapter sneakerAdapter;
    private RequestQueue mRequestQueue;
    SessionVariableManager sessionVariableManager;
    int sneakerId;
    int userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        //Call our DataBaseHelper object
        dataBaseHelper = new DataBaseHelper(FavouriteActivity.this);

        sessionVariableManager = new SessionVariableManager(FavouriteActivity.this);
        userId = sessionVariableManager.getSession();

        //instantiates our gridview
        sneakerGridview = findViewById(R.id.sneakerGridview);

        //set our list of sneakers based on what is in our database.
        favouriteList = dataBaseHelper.getAllFavourites(userId);

        //initializes our custom array adapter class with our context set to this activity and an array list which fetches all
        //records from our sneakers table.
        sneakerAdapter = new SneakerAdapter(FavouriteActivity.this, favouriteList);

        //This sets our gridview to display the data pulled from
        sneakerGridview.setAdapter(sneakerAdapter);

        //click event for our grid view when an item is clicked
        sneakerGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Based on the item clicked we get the object so we can use that data on another activity
                SneakerModel selectedSneaker = favouriteList.get(i);

                Intent intent = new Intent(FavouriteActivity.this, SneakerDetailsActivity.class);
                intent.putExtra("sneakerId", selectedSneaker.getId());
                intent.putExtra("sneakerImage", selectedSneaker.getMainPicture());
                intent.putExtra("sneakerName", selectedSneaker.getName());
                intent.putExtra("sneakerBrand", selectedSneaker.getBrand());
                intent.putExtra("sneakerColourway", selectedSneaker.getColourWay());
                intent.putExtra("sneakerRetailPrice", selectedSneaker.getPriceCents());
                intent.putExtra("sneakerReleaseDate", selectedSneaker.getReleaseDate());
                intent.putExtra("sneakerStory", selectedSneaker.getShoeStory());
                startActivity(intent);
            }
        });




        /////////////////////////////////////////////////////
        //declare and grab our bottom navigation view
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        //This will get the menu from our nav bar
        Menu menu = bottomNavigationView.getMenu();
        //from our bottom menu it will get the second element at index 1 from it aka our favourites buttom
        MenuItem menuItem = menu.getItem(1);
        //This will then set it to check giving it that highlighted effect of being selected
        menuItem.setChecked(true);

        //Create an event for our navigation items to tell which one is clicked and then execute some code dependant on the item
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //We use a switch statement to check which one is clicked
                switch (item.getItemId()){
                    case R.id.nav_home:
                        //This will navigate from our favourite activity to our home activity when we click on the home
                        //house button in the bottom nav
                        Intent intentHome = new Intent(FavouriteActivity.this, MainActivity.class);
                        startActivity(intentHome);
                        break;
                    case R.id.nav_favourites:
//                        //This will navigate from our favourite activity to our favourites activity when we click on the favourite
//                        //heart button in the bottom nav
//                        Intent intentFavourite = new Intent(ProfileActivity.this, FavouriteActivity.class);
//                        startActivity(intentFavourite);
                        break;
                    case R.id.nav_search:
                        //This will navigate from our favourite activity to our search activity when we click on the search
                        //magnifying glass button in the bottom nav
                        Intent intentSearch = new Intent(FavouriteActivity.this, SearchActivity.class);
                        startActivity(intentSearch);
                        break;
                    case R.id.nav_news:
                        //This will navigate from our favourite activity to our news activity when we click on the news
                        //trending button in the bottom nav
                        Intent intentNews = new Intent(FavouriteActivity.this, NewsActivity.class);
                        startActivity(intentNews);
                        break;
                    case R.id.nav_profile:
//                        //This will navigate from our news activity to our profile activity when we click on the profile
//                        //person button in the bottom nav
                        Intent intentProfile = new Intent(FavouriteActivity.this, ProfileActivity.class);
                        startActivity(intentProfile);
                        break;
                }

                return false;
            }
        });
    }
}
