package com.mirhoseini.fyber.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fyber.api.Offer;
import com.mirhoseini.fyber.Presentation.OffersPresenter;
import com.mirhoseini.fyber.R;
import com.mirhoseini.fyber.di.component.ApplicationComponent;
import com.mirhoseini.fyber.di.module.AppOffersModule;
import com.mirhoseini.fyber.util.AppConstants;
import com.mirhoseini.fyber.util.EndlessRecyclerViewScrollListener;
import com.mirhoseini.fyber.util.ItemSpaceDecoration;
import com.mirhoseini.fyber.util.ValueManager;
import com.mirhoseini.fyber.view.BaseView;
import com.mirhoseini.fyber.view.OffersView;
import com.mirhoseini.fyber.view.adapter.OffersRecyclerViewAdapter;
import com.mirhoseini.utils.Utils;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class OffersFragment extends BaseFragment implements OffersView, SwipeRefreshLayout.OnRefreshListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_API_KEY = "api_key";

    @Inject
    public OffersPresenter presenter;
    @Inject
    Context context;
    @Inject
    ValueManager valueManager;
    @Inject
    LinearLayoutManager layoutManager;
    @Inject
    OffersRecyclerViewAdapter adapter;


    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.no_internet)
    View noInternet;
    @BindView(R.id.network_error)
    View networkError;
    @BindView(R.id.no_content)
    View noContent;
    @BindView(R.id.progress)
    View progress;
    @BindView(R.id.progress_more)
    View progressMore;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    int page;
    private int columnCount = 1;
    private int pages = 0;
    private String apiKey;

    private OnListFragmentInteractionListener listener;

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

    @OnClick(R.id.no_internet)
    void onNoInternetClick() {
        loadOffersData();
    }

    @OnClick(R.id.no_content)
    void onNoContentClick() {
        loadOffersData();
    }

    @OnClick(R.id.network_error)
    void onNetworkErrorClick() {
        loadOffersData();
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

        // add material margins to list items card view
        recyclerView.addItemDecoration(new ItemSpaceDecoration(AppConstants.RECYCLER_VIEW_ITEM_SPACE));

        // allow pull to refresh on list
        swipeRefresh.setOnRefreshListener(this);

        // load data for first run
        if (adapter.getItemCount() == 0)
            loadOffersData();
        else
            initRecyclerView();

        return view;
    }

    @Override
    protected void injectDependencies(ApplicationComponent component, Context context) {
        component
                .plus(new AppOffersModule(context, this, columnCount))
                .inject(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;

        presenter.destroy();
        presenter = null;
    }

    @Override
    public void showProgress() {
        if (page == 1) {
            progress.setVisibility(View.VISIBLE);
        } else {
            progressMore.setVisibility(View.VISIBLE);
        }

        noInternet.setVisibility(View.GONE);
        networkError.setVisibility(View.GONE);
        noContent.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
        swipeRefresh.setRefreshing(false);
        progressMore.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        if (null != listener) {
            listener.showMessage(message);
        }
    }

    @Override
    public void showOfflineMessage() {
        if (null != listener) {
            listener.showOfflineMessage();
        }

        if ( adapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            noInternet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showNetworkConnectionError(boolean isForce) {
        if (null != listener) {
            listener.showNetworkConnectionError(isForce);
        }

        if (adapter.getItemCount() == 0) {
            noInternet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showRetryMessage() {
        Timber.d("Showing Retry Message");

        if (adapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            networkError.setVisibility(View.VISIBLE);
        }

        Snackbar.make(getView(), R.string.retry_message, Snackbar.LENGTH_LONG)
                .setAction(R.string.load_retry, v -> loadOffersData())
                .setActionTextColor(Color.RED)
                .show();
    }

    @Override
    public void setNoOffers() {
        Timber.d("No offer available!");

        pages = 0;
        recyclerView.setVisibility(View.GONE);

        noContent.setVisibility(View.VISIBLE);
    }

    private void loadOffersData() {
        adapter.clearOffers();
        loadMoreOffersData(1);
    }

    private void loadMoreOffersData(int newPage) {
        page = newPage;
        presenter.loadOffersData(Utils.isConnected(context),
                page,
                Locale.getDefault().getLanguage(),
                Utils.getAndroidVersion(),
                Utils.getCurrentTimestamp(),
                Utils.getIPAddress(true),
                Utils.isTablet(context) ? "tablet" : "phone",
                apiKey);
    }

    @Override
    public void setOffersValue(Offer[] offers, int pages) {
        Timber.d("Loaded Page: %d of %d", page, pages);

        if (adapter.getItemCount() == 0) {
//            adapter = new OffersRecyclerViewAdapter(offers, listener);
            adapter.setOffers(offers);
            initRecyclerView();
        } else {
            adapter.addMoreOffers(offers);
            adapter.notifyDataSetChanged();
        }

        page++;
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (page < pages) {
                    Timber.d("Loading more offers, Page: %d", page + 1);

                    loadMoreOffersData(page + 1);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        loadOffersData();
    }

    public interface OnListFragmentInteractionListener extends BaseView {

        void onListFragmentInteraction(Offer offer);

    }
}
