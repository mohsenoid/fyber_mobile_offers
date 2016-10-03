package com.mirhoseini.fyber.di.module;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.mirhoseini.fyber.di.scope.OffersScope;
import com.mirhoseini.fyber.view.adapter.OffersRecyclerViewAdapter;
import com.mirhoseini.fyber.view.fragment.OffersFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mohsen on 03/10/2016.
 */
@Module
public class AppOffersModule extends OffersModule {
    private final Context context;
    private final int columnCount;
    private final OffersFragment.OnListFragmentInteractionListener listener;

    public AppOffersModule(Context context, OffersFragment fragment, int columnCount) {
        super(fragment);

        this.context = context;
        this.columnCount = columnCount;

        if (context instanceof OffersFragment.OnListFragmentInteractionListener) {
            listener = (OffersFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Provides
    @OffersScope
    public OffersFragment.OnListFragmentInteractionListener provideOnListFragmentInteractionListener() {
        return listener;
    }

    @Provides
    @OffersScope
    public LinearLayoutManager provideLayoutManager() {
        if (columnCount == 1) {
            return new LinearLayoutManager(context);
        } else {
            return new GridLayoutManager(context, columnCount);
        }
    }

    @Provides
    @OffersScope
    public OffersRecyclerViewAdapter provideOffersRecyclerViewAdapter() {
        return new OffersRecyclerViewAdapter(listener);
    }

}
