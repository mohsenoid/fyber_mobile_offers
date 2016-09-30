package com.mirhoseini.fyber.model;

import com.fyber.api.Offer;
import com.fyber.api.OfferResponse;
import com.mirhoseini.fyber.client.FyberApi;
import com.mirhoseini.fyber.model.OffersInteractor;
import com.mirhoseini.fyber.model.OffersInteractorImpl;
import com.mirhoseini.fyber.util.Constants;
import com.mirhoseini.fyber.util.SchedulerProvider;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class OffersInteractorImplTest {

    OffersInteractor interactor;
    FyberApi api;
    SchedulerProvider scheduler;
    Offer[] expectedOffers;
    OfferResponse expectedResult;

    @Before
    public void setup() {
        api = mock(FyberApi.class);
        scheduler = mock(SchedulerProvider.class);

        // Set up the stub we want to return in the mock
        Offer offer = new Offer();
        offer.setTitle("Test Offer");

        expectedOffers = new Offer[1];
        expectedOffers[0] = offer;

        // put the test offer in a test api result
        expectedResult = new OfferResponse();
        expectedResult.setOffers(expectedOffers);
        expectedResult.setPages(1);

        // moch scheduler to run immediately
        when(scheduler.mainThread())
                .thenReturn(Schedulers.immediate());
        when(scheduler.backgroundThread())
                .thenReturn(Schedulers.immediate());

        // mock api result with expected result
        when(api.getOffers(any(String.class), any(Integer.class), any(String.class), any(String.class), any(String.class), any(Long.class), any(String.class), any(String.class), any(Boolean.class), any(String.class), any(String.class), any(Integer.class), any(Integer.class), any(String.class)))
                .thenReturn(Observable.just(expectedResult));

        // create a real new interactor using mocked api and scheduler
        interactor = new OffersInteractorImpl(api, scheduler);
    }

    @Test
    public void testLoadOffers() throws Exception {
        TestSubscriber<OfferResponse> testSubscriber = new TestSubscriber<>();

        // call interactor with some random params
        interactor.loadOffers(1, Constants.FORMAT_JSON, 2, "userId", "local", "osVersion", 3, "adId", true, "ip", "pub0", Constants.OFFER_TYPES, "device", "apiKey")
                .subscribe(testSubscriber);

        // it must return the expectedResult with no error
        testSubscriber.assertNoErrors();
        testSubscriber.assertReceivedOnNext(Collections.singletonList(expectedResult));
    }

}
