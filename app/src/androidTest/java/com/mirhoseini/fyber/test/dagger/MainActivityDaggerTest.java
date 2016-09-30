package com.mirhoseini.fyber.test.dagger;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.fyber.api.Offer;
import com.fyber.api.OfferResponse;
import com.mirhoseini.fyber.R;
import com.mirhoseini.fyber.client.FyberApi;
import com.mirhoseini.fyber.test.dagger.di.component.TestApplicationComponent;
import com.mirhoseini.fyber.util.Constants;
import com.mirhoseini.fyber.util.ValueManager;
import com.mirhoseini.fyber.view.activity.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by Mohsen on 30/09/2016.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityDaggerTest {

    public static final String TEST_OFFER_TITLE = "Test Offer";
    public static final String TEST_OFFER_TEASER = "Test Offer Teaser";
    public static final String TEST_OFFER_PAYLOAD = "12";


    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<>(
            MainActivity.class,
            true,
            false);   // do not launch the activity immediately

    @Inject
    FyberApi api;

    @Inject
    ValueManager valueManager;

    Offer[] expectedOffers;
    OfferResponse expectedResult;

    @Before
    public void setUp() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        MockFyberApplication app = (MockFyberApplication) instrumentation.getTargetContext().getApplicationContext();
        TestApplicationComponent component = (TestApplicationComponent) app.getComponent();
        component.inject(this);

        // Set up the stub we want to return in the mock
        Offer offer = new Offer();
        offer.setTitle(TEST_OFFER_TITLE);
        offer.setTeaser(TEST_OFFER_TEASER);
        offer.setPayout(TEST_OFFER_PAYLOAD);

        expectedOffers = new Offer[1];
        expectedOffers[0] = offer;

        // put the test offer in a test api result
        expectedResult = new OfferResponse();
        expectedResult.setOffers(expectedOffers);
        expectedResult.setPages(1);
        expectedResult.setCode(Constants.CODE_OK);

        // Set up the mock
        when(api.getOffers(any(String.class), any(Integer.class), any(String.class), any(String.class), any(String.class), any(Long.class), any(String.class), any(String.class), any(Boolean.class), any(String.class), any(String.class), any(Integer.class), any(Integer.class), any(String.class)))
                .thenReturn(Observable.just(expectedResult));

        when(valueManager.isConnected()).thenReturn(true);
        when(valueManager.getAdId()).thenReturn("adId");
        when(valueManager.getAdIdEnabled()).thenReturn(false);
        when(valueManager.getApplicationId()).thenReturn(123);
        when(valueManager.getApiKey()).thenReturn("apiKey");
    }

    @Test
    public void shouldBeAbleToMockTheOffers() {
        // Launch the activity
        mainActivity.launchActivity(new Intent());

        // Check that the view is what we expect it to be
        onView(withId(R.id.title)).check(matches(withText(TEST_OFFER_TITLE)));
        onView(withId(R.id.teaser)).check(matches(withText(TEST_OFFER_TEASER)));
        onView(withId(R.id.payload)).check(matches(withText(TEST_OFFER_PAYLOAD)));
    }
}

