package com.mirhoseini.fyber.view.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.fyber.api.Offer;
import com.mirhoseini.fyber.Presentation.MainPresenter;
import com.mirhoseini.fyber.R;
import com.mirhoseini.fyber.di.component.ApplicationComponent;
import com.mirhoseini.fyber.di.module.MainModule;
import com.mirhoseini.fyber.view.MainView;
import com.mirhoseini.fyber.view.fragment.OffersFragment;
import com.mirhoseini.utils.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class MainActivity extends BaseActivity implements MainView, OffersFragment.OnListFragmentInteractionListener {

    private static final int REQUEST_CODE_LOGIN = 1;
    public static final String TAG_OFFERS_FRAGMENT = "offers_fragment";

    // injecting dependencies via Dagger
    @Inject
    Context context;
    @Inject
    Resources resources;
    @Inject
    MainPresenter presenter;

    // injecting views via ButterKnife
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private OffersFragment offersFragment;
    AlertDialog internetConnectionDialog;
    private String apiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // inject views using ButterKnife
        ButterKnife.bind(this);

        setupToolbar();

        Timber.d("Main Activity Created");
    }

    @Override
    protected void injectDependencies(ApplicationComponent component) {
        component
                .plus(new MainModule(this))
                .inject(this);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.logo);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.checkLogin();

        Timber.d("Main Activity Resumed");
    }

    @Override
    public void doLogin() {
        openLoginActivity();
    }

    @Override
    public void isLogin(String apiKey) {
        this.apiKey = apiKey;
        createFragment();
        attachFragment();
    }

    @Override
    public void showLoginMessage() {
        showMessage(resources.getString(R.string.login_required));
    }

    private void createFragment() {
        // change column count according fo screen orientation
        int columnCount;
        if (Utils.isPortrait(context))
            columnCount = 1;
        else
            columnCount = 2;

        offersFragment = OffersFragment.newInstance(columnCount, apiKey);
    }

    private void attachFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.offers_fragment, offersFragment, TAG_OFFERS_FRAGMENT);
        fragmentTransaction.commitAllowingStateLoss();
    }



    private void openLoginActivity() {
        startActivityForResult(LoginActivity.newIntent(context), REQUEST_CODE_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_LOGIN) {
            if (resultCode == RESULT_OK) {
                createFragment();
            } else {
                presenter.checkLogin();
            }
        }
    }

    @Override
    public void showMessage(String message) {
        Timber.d("Showing Message: %s", message);

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showOfflineMessage() {
        Timber.d("Showing Offline Message");

        Snackbar.make(toolbar, R.string.offline_message, Snackbar.LENGTH_LONG)
                .setAction(R.string.go_online, v -> {
                    startActivity(new Intent(
                            Settings.ACTION_WIFI_SETTINGS));
                })
                .setActionTextColor(Color.GREEN)
                .show();
    }

    @Override
    public void showNetworkConnectionError(boolean isForce) {
        Timber.d("Showing Network Connection Error Message");

        hideInternetConnectionError();
        internetConnectionDialog = Utils.showNoInternetConnectionDialog(this, isForce);
    }

    public void hideInternetConnectionError() {
        if (internetConnectionDialog != null)
            internetConnectionDialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login_menu:
                openLoginActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListFragmentInteraction(Offer offer) {
//        Intent detailsIntent = DetailsActivity.newIntent(context, offer);
//        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedView, "cover");
//        ActivityCompat.startActivity(this, detailsIntent, options.toBundle());
    }

}
