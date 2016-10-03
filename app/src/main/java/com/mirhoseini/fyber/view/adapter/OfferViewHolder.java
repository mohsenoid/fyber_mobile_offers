package com.mirhoseini.fyber.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fyber.api.Offer;

/**
 * Created by Mohsen on 03/10/2016.
 */
public class OfferViewHolder extends RecyclerView.ViewHolder {

    public final View view;
    Offer offer;
    private ViewDataBinding binding;

    public OfferViewHolder(View view) {
        super(view);
        this.view = view;

        binding = DataBindingUtil.bind(view);

    }

    public ViewDataBinding getBinding() {
        return binding;
    }

}
