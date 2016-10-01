package com.mirhoseini.fyber.view.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.fyber.api.Offer;
import com.mirhoseini.fyber.R;
import com.mirhoseini.fyber.client.GoogleAds;
import com.mirhoseini.fyber.di.component.ApplicationComponent;
import com.mirhoseini.fyber.di.module.OffersModule;
import com.mirhoseini.fyber.util.Constants;
import com.mirhoseini.fyber.util.EndlessRecyclerViewScrollListener;
import com.mirhoseini.fyber.util.ItemSpaceDecoration;
import com.mirhoseini.fyber.util.ValueManager;
import com.mirhoseini.fyber.view.BaseView;
import com.mirhoseini.fyber.view.adapter.OffersRecyclerViewAdapter;
import com.mirhoseini.fyber.viewmodel.OffersViewModel;
import com.mirhoseini.utils.Utils;

import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by Mohsen on 19/07/16.
 */

public class OffersFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_API_KEY = "api_key";

    // injecting dependencies via Dagger
    @Inject
    Context context;
    @Inject
    Resources resources;
    @Inject
    OffersViewModel viewModel;

    // injecting views via ButterKnife
    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.no_internet)
    ImageView noInternet;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.progress_more)
    ProgressBar progressMore;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    int page;
    private int columnCount = 1;
    private String apiKey;
    private OnListFragmentInteractionListener listener;
    private OffersRecyclerViewAdapter adapter;
    private CompositeSubscription subscriptions;
    private LinearLayoutManager layoutManager;
    private int pages = 0;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OffersFragment() {
    }

    public static OffersFragment newInstance(int columnCount, String apiKey) {
        OffersFragment fragment = new OffersFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(ARG_API_KEY, apiKey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            columnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            apiKey = getArguments().getString(ARG_API_KEY);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers, container, false);

        ButterKnife.bind(this, view);

        // allow pull to refresh on list
        swipeRefresh.setOnRefreshListener(this);

        initAdapter();

        initLayoutManager();

        initRecyclerView();

        initBindings();

        loadOffersData();

        return view;
    }

    private void initLayoutManager() {
        if (columnCount == 1) {
            layoutManager = new LinearLayoutManager(context);
        } else {
            layoutManager = new GridLayoutManager(context, columnCount);
        }
    }

    private void initAdapter() {
        adapter = new OffersRecyclerViewAdapter(listener);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            listener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    protected void injectDependencies(ApplicationComponent component) {
        component.plus(new OffersModule()).inject(this);
    }

    protected void initBindings() {
        // Observable that emits when the RecyclerView is scrolled to the bottom
        Observable<Integer> infiniteScrollObservable = Observable.create(subscriber -> {
            recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount) {
                    if (page < pages) {
                        int newPage = page + 1;
                        Timber.d("Loading more movies, Page: %d", newPage);

                        subscriber.onNext(newPage);
                    }
                }
            });
        });

        subscriptions = new CompositeSubscription();

        subscriptions.addAll(
                // Bind loading status to show/hide progress
                viewModel
                        .isLoadingObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setIsLoading),

                // Bind list of offers
                viewModel
                        .offersObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setOffersValue, this::onLoadError, this::onLoadComplete),

                // Bind total pages of offers
                viewModel
                        .pagesObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setPagesValue),

                // Trigger next page load when RecyclerView is scrolled to the bottom
                infiniteScrollObservable.subscribe(page -> loadMoreOffersData(page))
        );
    }

    private void onLoadError(Throwable throwable) {
        Timber.e(throwable, "Load error!");

        showMessage(throwable.getMessage());

        if (Utils.isConnected(context)) {
            showRetryMessage();
        } else {
            showNetworkConnectionError(adapter.getItemCount() == 0);
        }
    }

    private void onLoadComplete() {
        if (!Utils.isConnected(context))
            showOfflineMessage();
    }

    private void showMessage(String message) {
        if (null != listener)
            listener.showMessage(message);
    }

    public void setIsLoading(boolean isLoading) {
        if (isLoading)
            showProgress();
        else
            hideProgress();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        listener = null;
        subscriptions.unsubscribe();
    }

    public void showProgress() {
        if (page == 1) {
            progress.setVisibility(View.VISIBLE);
            swipeRefresh.setRefreshing(true);
        } else {
            progressMore.setVisibility(View.VISIBLE);
        }

        noInternet.setVisibility(View.GONE);
    }

    public void hideProgress() {
        progress.setVisibility(View.GONE);
        swipeRefresh.setRefreshing(false);
        progressMore.setVisibility(View.GONE);
    }

    public void showOfflineMessage() {
        if (null != listener) {
            listener.showOfflineMessage();
        }

        if (null == adapter || adapter.getItemCount() == 0) {
            noInternet.setVisibility(View.VISIBLE);
        }
    }

    public void showNetworkConnectionError(boolean isForce) {
        if (null != listener) {
            listener.showNetworkConnectionError(isForce);
        }
    }

    public void showRetryMessage() {
        Timber.d("Showing Retry Message");

        Snackbar.make(getView(), R.string.retry_message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.load_retry, v -> loadOffersData())
                .setActionTextColor(Color.RED)
                .show();
    }

    private void loadOffersData() {
        loadMoreOffersData(1);
    }

    private void loadMoreOffersData(int newPage) {
        page = newPage;

        Observable.combineLatest(
                GoogleAds.getAdIdObservable(context),
                GoogleAds.getAdIdEnabledObservable(context),
                (String adId, Boolean adIdEnabled) ->
                        viewModel.loadOffersDataObservable(
                                page,
                                ValueManager.getApplicationId(context),
                                ValueManager.getUserId(context),
                                Locale.getDefault().getLanguage(),
                                Utils.getAndroidVersion(),
                                ValueManager.getCurrentTimestamp(),
                                adId,
                                adIdEnabled,
                                ValueManager.getIPAddress(true),
                                ValueManager.getPub0(context),
                                Constants.OFFER_TYPES,
                                ValueManager.isTablet(context) ? "tablet" : "phone",
                                apiKey))
                .subscribeOn(Schedulers.io())
                .subscribe(done -> {
                }, this::onLoadError);
    }

    public void setOffersValue(ArrayList<Offer> offers) {
        Timber.d("Loaded Page: %d", page);

        if (null != offers) {
            adapter.setOffers(offers);
            adapter.notifyDataSetChanged();
        }

        if (!Utils.isConnected(context))
            showOfflineMessage();

        page++;
    }

    public void setPagesValue(int pages) {
        Timber.d("Loaded total Pages count: %d", pages);

        this.pages = pages;
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(layoutManager);

        // add material margins to list items card view
        recyclerView.addItemDecoration(new ItemSpaceDecoration(Constants.RECYCLER_VIEW_ITEM_SPACE));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        loadOffersData();
    }

    public interface OnListFragmentInteractionListener extends BaseView {
        void onListFragmentInteraction(Offer movie);
    }

}