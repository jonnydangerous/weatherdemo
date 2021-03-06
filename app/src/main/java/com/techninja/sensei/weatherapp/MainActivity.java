package com.techninja.sensei.weatherapp;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.techninja.sensei.weatherapp.Models.WeatherResponse;
import com.techninja.sensei.weatherapp.Persistence.IRepository;
import com.techninja.sensei.weatherapp.Persistence.Repository;
import com.techninja.sensei.weatherapp.Persistence.SharedPreferencesWrapper;
import com.techninja.sensei.weatherapp.Presenters.MainPresenter;
import com.techninja.sensei.weatherapp.Services.OpenWeatherWrapper;
import com.techninja.sensei.weatherapp.Utilities.FontHelper;
import com.techninja.sensei.weatherapp.Views.CitiesListAdapter;
import com.techninja.sensei.weatherapp.Views.IMainView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, IMainView {

    private SharedPreferences mSharedPreferences;
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = "MainActivity";
    private MainPresenter _presenter;
    private Toolbar _toolbar;
    private DrawerLayout drawerLayout;
    private ListView _citiesListView;
    private OpenWeatherWrapper _weatherService;
    protected static final int NAV_DRAWER_ITEM_INVALID = -1;
    AddCityDialog _dialog;
    private CitiesListAdapter _cityListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(_toolbar);
        TextView addAction = (TextView) _toolbar.findViewById(R.id.action_add_icon);
        //        setupNavDrawer();

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences preferences = getSharedPreferences("WeatherAppSettings", 0);
        SharedPreferencesWrapper preferencesWrapper = new SharedPreferencesWrapper(preferences);
        InputStream file = null;

        try {
            file = getApplication().getAssets().open("city.list.us.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        IRepository repository = Repository.getInstance(preferencesWrapper, file);
        _citiesListView = (ListView) findViewById(R.id.citiesList);
        _weatherService = new OpenWeatherWrapper(getString(R.string.api_key), getResources(), getPackageName());
        _dialog = new AddCityDialog();
        _presenter = new MainPresenter(repository, this, _weatherService, _dialog);
        _presenter.UpdateView();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
        RelativeLayout mainView = (RelativeLayout) findViewById(R.id.main_container);
        Typeface iconFont = FontHelper.getTypeface(getApplicationContext(), FontHelper.FONTAWESOME);
        FontHelper.markAsIconContainer(mainView, iconFont);

        _cityListAdapter = new CitiesListAdapter(this, R.layout.city_item, new ArrayList<WeatherResponse>() {
        }, _weatherService);
        _citiesListView.setAdapter(_cityListAdapter);
    }

    /**
     * Sets up the navigation drawer.
     */
    private void setupNavDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout == null) {
            // current activity does not have a drawer.
            return;
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerSelectListener(navigationView);
            setSelectedItem(navigationView);
        }

        //logD(TAG, "navigation drawer setup finished");
    }

    /**
     * Updated the checked item in the navigation drawer
     *
     * @param navigationView the navigation view
     */
    private void setSelectedItem(NavigationView navigationView) {
        // Which navigation item should be selected?
        int selectedItem = getSelfNavDrawerItem(); // subclass has to override this method
        navigationView.setCheckedItem(selectedItem);
    }

    /**
     * Handles the navigation item click and starts the corresponding activity.
     *
     * @param item the selected navigation item
     */
    private void goToNavDrawerItem(int item) {
        switch (item) {
//            case R.id.nav_quotes:
//                startActivity(new Intent(this, ListActivity.class));
//                finish();
//                break;
//            case R.id.nav_samples:
//                startActivity(new Intent(this, ViewSamplesActivity.class));
//                break;
//            case R.id.nav_settings:
//                startActivity(new Intent(this, SettingsActivity.class));
//                break;
        }
    }

    /**
     * Returns the navigation drawer item that corresponds to this Activity. Subclasses
     * have to override this method.
     */
    protected int getSelfNavDrawerItem() {
        return NAV_DRAWER_ITEM_INVALID;
    }

    protected void openDrawer() {
        if (drawerLayout == null)
            return;

        drawerLayout.openDrawer(GravityCompat.START);
    }

    protected void closeDrawer() {
        if (drawerLayout == null)
            return;

        drawerLayout.closeDrawer(GravityCompat.START);
    }

    /**
     * Handles the navigation item click.
     *
     * @param itemId the clicked item
     */
    private void onNavigationItemClicked(final int itemId) {
        if (itemId == getSelfNavDrawerItem()) {
            // Already selected
            closeDrawer();
            return;
        }

        goToNavDrawerItem(itemId);
    }

    /**
     * Creates the item click listener.
     *
     * @param navigationView the navigation view
     */
    private void setupDrawerSelectListener(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        drawerLayout.closeDrawers();
                        onNavigationItemClicked(menuItem.getItemId());
                        return true;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem add = menu.findItem(R.id.action_add);
        TextView icon = (TextView) add.getActionView().findViewById(R.id.action_add_icon);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(add);
            }
        });

        Typeface iconFont = FontHelper.getTypeface(getApplicationContext(), FontHelper.FONTAWESOME);
        FontHelper.markAsIconContainer(icon, iconFont);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                _dialog.show(getSupportFragmentManager(), "AddCityDialog");
                _dialog.AddCityClickEvent = (Integer cityId) -> {
                    _presenter.AddCity(cityId);
                    ;
                    return cityId;
                };
                _dialog.SetChangeEvent(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.toString().length() >= 3) {
                            _presenter.LookupCity(s.toString());
                        }
                    }
                });
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Log.i("Action Button Pressed", "");
//                Intent intent = new Intent(this, EditProject.class);
//                startActivity(intent);
//                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void SetCities(List<WeatherResponse> cities) {
        _cityListAdapter.DataItems.clear();
        _cityListAdapter.DataItems.addAll(cities);
        _cityListAdapter.notifyDataSetChanged();

        _citiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                _presenter.SetSelectedCity(position);
            }
        });
    }

    @Override
    public Callback<WeatherResponse> GetWeatherCallback(final int currentCityPosition) {
        return new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                Log.i("Response", response.toString());
                _presenter.WeatherData.add(response.body());
                if (currentCityPosition == _presenter.CityCount) {
                    _presenter.UpdateCityData();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.i("Failure", t.toString());
            }
        };
    }

    @Override
    public void SetSelectedCity(WeatherResponse weatherResponse, int icon) {
        TextView selectedCityName = (TextView) findViewById(R.id.selectedCityName);
        selectedCityName.setText(weatherResponse.getName());

        TextView selectedCityTemp = (TextView) findViewById(R.id.selectedCityTemp);
        selectedCityTemp.setText(Math.round(weatherResponse.getMain().getTemp()) + "\u00b0 F");

        TextView selectedCityWeather = (TextView) findViewById(R.id.selectedCityWeather);
        selectedCityWeather.setText("Current condition: " + weatherResponse.getWeather().get(0).getDescription());

        ImageView iconView = (ImageView) findViewById(R.id.weather_icon);
        iconView.setImageResource(icon);
        TextView lowTemp = (TextView) findViewById(R.id.selectedCityLoTemp);
        TextView hiTemp = (TextView) findViewById(R.id.selectedCityHiTemp);
        lowTemp.setText(Math.round(weatherResponse.getMain().getTempMin()) + "\u00b0 F");
        hiTemp.setText(Math.round(weatherResponse.getMain().getTempMax()) + "\u00b0 F");
    }
}
