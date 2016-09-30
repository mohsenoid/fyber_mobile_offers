package com.mirhoseini.fyber.test.robolectric.view.activity;

import com.mirhoseini.fyber.BuildConfig;
import com.mirhoseini.fyber.R;
import com.mirhoseini.fyber.test.robolectric.support.ShadowSnackbar;
import com.mirhoseini.fyber.view.activity.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import static com.mirhoseini.fyber.test.robolectric.support.Assert.assertAlertDialogIsShown;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;


/**
 * Created by Mohsen on 30/09/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, shadows = {ShadowSnackbar.class})
public class MainActivityRobolectricTest {

    final static String TEST_TEXT = "This is a test text.";
    private MainActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(MainActivity.class);

        assertNotNull(activity);
    }

    @Test
    public void testShowToastMessage() throws Exception {
        activity.showMessage(TEST_TEXT);

        assertThat(TEST_TEXT, equalTo(ShadowToast.getTextOfLatestToast()));
    }

    @Test
    public void testShowInternetConnectionError() throws Exception {
        activity.showNetworkConnectionError(true);

        assertAlertDialogIsShown(R.string.utils__no_connection_title, R.string.utils__no_connection);
    }

    @Test
    public void testShowDoubleInternetConnectionError() throws Exception {
        activity.showNetworkConnectionError(true);
        activity.showNetworkConnectionError(true);

        assertAlertDialogIsShown(R.string.utils__no_connection_title, R.string.utils__no_connection);
    }

}
