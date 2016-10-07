package com.mirhoseini.fyber.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fyber.api.Offer;
import com.jakewharton.rxbinding.view.RxView;
import com.mirhoseini.fyber.BR;
import com.mirhoseini.fyber.R;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;


/**
 * Created by Mohsen on 30/09/2016.
 */
public class OffersRecyclerViewAdapter extends RecyclerView.Adapter<OfferViewHolder> {

    private ArrayList<Offer> offers = new ArrayList<>();

    private PublishSubject<Offer> notify = PublishSubject.create();

    @Inject
    public OffersRecyclerViewAdapter() {
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

        RxView.clicks(holder.view)
                .map(aVoid -> holder.offer)
                .subscribe(notify::onNext);
    }

    public Observable<Offer> asObservable() {
        return notify.asObservable();
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public void clearOffers() {
        offers = new ArrayList<>();
    }

}
