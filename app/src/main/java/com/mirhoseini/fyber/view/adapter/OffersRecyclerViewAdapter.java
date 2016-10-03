package com.mirhoseini.fyber.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fyber.api.Offer;
import com.mirhoseini.fyber.BR;
import com.mirhoseini.fyber.R;
import com.mirhoseini.fyber.view.fragment.OffersFragment;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by Mohsen on 30/09/2016.
 */
public class OffersRecyclerViewAdapter extends RecyclerView.Adapter<OfferViewHolder> {

    private final OffersFragment.OnListFragmentInteractionListener listener;

    private ArrayList<Offer> offers = new ArrayList<>();

    public OffersRecyclerViewAdapter(OffersFragment.OnListFragmentInteractionListener listener) {
        this.listener = listener;
    }

    public void setOffers(Offer[] offers) {
        this.offers = new ArrayList<>(Arrays.asList(offers));
    }

    public void addMoreOffers(Offer[] offers) {
        this.offers.addAll(new ArrayList<>(Arrays.asList(offers)));
    }

    @Override
    public OfferViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_offer, parent, false);
        return new OfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OfferViewHolder holder, int position) {

        final Offer offer = offers.get(position);

        holder.offer = offer;
        holder.getBinding().setVariable(BR.offer, offer);
        holder.getBinding().executePendingBindings();

        holder.view.setOnClickListener(v -> {
            if (null != listener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                listener.onListFragmentInteraction(holder.offer);
            }
        });
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public void clearOffers() {
        offers = new ArrayList<>();
    }

}
