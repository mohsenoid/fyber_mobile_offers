package com.mirhoseini.fyber.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fyber.api.Offer;
import com.mirhoseini.fyber.BR;
import com.mirhoseini.fyber.R;
import com.mirhoseini.fyber.view.fragment.OffersFragment;

import java.util.ArrayList;


/**
 * Created by Mohsen on 30/09/2016.
 */
public class OffersRecyclerViewAdapter extends RecyclerView.Adapter<OffersRecyclerViewAdapter.ViewHolder> {

    private final OffersFragment.OnListFragmentInteractionListener listener;

    private ArrayList<Offer> offers = new ArrayList<>();

    public OffersRecyclerViewAdapter(OffersFragment.OnListFragmentInteractionListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_offer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

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

    public void setOffers(ArrayList<Offer> offers) {
        this.offers = offers;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View view;
        Offer offer;
        private ViewDataBinding binding;

        public ViewHolder(View view) {
            super(view);
            this.view = view;

            binding = DataBindingUtil.bind(view);

        }

        public ViewDataBinding getBinding() {
            return binding;
        }

    }
}
