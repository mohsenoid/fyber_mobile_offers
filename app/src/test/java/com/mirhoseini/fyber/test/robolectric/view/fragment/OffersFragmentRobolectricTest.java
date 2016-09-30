package com.mirhoseini.fyber.test.robolectric.view.fragment;

import android.view.View;

import com.mirhoseini.fyber.BuildConfig;
import com.mirhoseini.fyber.R;
import com.mirhoseini.fyber.test.robolectric.support.ShadowSnackbar;
import com.mirhoseini.fyber.util.AppValueManager;
import com.mirhoseini.fyber.util.ValueManager;
import com.mirhoseini.fyber.view.activity.MainActivity;
import com.mirhoseini.fyber.view.fragment.OffersFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static com.mirhoseini.fyber.test.robolectric.support.Assert.assertSnackbarIsShown;
import static com.mirhoseini.fyber.test.robolectric.support.Assert.assertViewIsNotVisible;
import static com.mirhoseini.fyber.test.robolectric.support.Assert.assertViewIsVisible;
import static com.mirhoseini.fyber.test.robolectric.support.ViewLocator.getView;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Mohsen on 30/09/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, shadows = {ShadowSnackbar.class})
public class OffersFragmentRobolectricTest {

    private MainActivity activity;
    private OffersFragment fragment;

    @Before
    public void setUp() throws Exception {
        // avoid loading login activity and recommended value exceptions
        ValueManager valueManager = new AppValueManager(RuntimeEnvironment.application);
        valueManager.setApiKey("test api key");
        valueManager.setAdId("test ad id");
        valueManager.setApplicationId(123);
        valueManager.setAdIdEnabled(false);
        assertNotNull(valueManager.getApiKey());

        // setup activity
        activity = Robolectric.setupActivity(MainActivity.class);
        assertNotNull(activity);

        // setup fragment
        fragment = (OffersFragment) activity.getSupportFragmentManager().findFragmentByTag(activity.TAG_OFFERS_FRAGMENT);
        assertNotNull(fragment);

    }

    @Test
    public void testShowProgress() throws Exception {
        fragment.showProgress();

        View progress = getView(fragment, R.id.progress);
        assertViewIsVisible(progress);

        View progressMore = getView(fragment, R.id.progress_more);
        assertViewIsNotVisible(progressMore);

        View noInternet = getView(fragment, R.id.no_internet);
        assertViewIsNotVisible(noInternet);

        View noContent = getView(fragment, R.id.no_content);
        assertViewIsNotVisible(noContent);

        View networkError = getView(fragment, R.id.network_error);
        assertViewIsNotVisible(networkError);
    }

    @Test
    public void testHideProgress() throws Exception {
        fragment.hideProgress();

        View progress = getView(fragment, R.id.progress);
        assertViewIsNotVisible(progress);

        View progressMore = getView(fragment, R.id.progress_more);
        assertViewIsNotVisible(progressMore);

    }

    @Test
    public void testShowOfflineMessage() throws Exception {
        fragment.showOfflineMessage();

        assertSnackbarIsShown(R.string.offline_message);

        View noInternet = getView(fragment, R.id.no_internet);
        assertViewIsVisible(noInternet);
    }

    @Test
    public void testShowRetryMessage() throws Exception {
        fragment.showRetryMessage();

        assertSnackbarIsShown(R.string.retry_message);

        View networkError = getView(fragment, R.id.network_error);
        assertViewIsVisible(networkError);
    }

    @Test
    public void testOnDestroy() throws Exception {
        fragment.onDetach();

        assertNull(fragment.presenter);
    }

}