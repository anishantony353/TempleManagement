package com.saarit.temple_management.templemanagement.view.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saarit.temple_management.templemanagement.databinding.RowDonationProductInfoBinding;
import com.saarit.temple_management.templemanagement.model.DonatedProduct;
import com.saarit.temple_management.templemanagement.util.Utility;
import com.saarit.temple_management.templemanagement.view_model.FormType4_ViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DonatedProductsAdapter extends RecyclerView.Adapter<DonatedProductsAdapter.MYViewHolder> {

    private String TAG = DonatedProductsAdapter.class.getSimpleName();

    private List<DonatedProduct> DonatedProducts = new ArrayList<>();
    private int layout_id;
    private FormType4_ViewModel viewModel;

    public DonatedProductsAdapter(int layout_id, FormType4_ViewModel viewModel){

        this.layout_id = layout_id;
        this.viewModel = viewModel;


    }

    public void setDonatedProducts(List<DonatedProduct> DonatedProducts){
        this.DonatedProducts = DonatedProducts;
    }

    public void addDonatedProduct(DonatedProduct DonatedProduct, int position){
        DonatedProducts.add(DonatedProduct);
        notifyItemInserted(DonatedProducts.size() - 1);
        //notifyItemInserted(DonatedProducts.size() - 1);

    }


    @NonNull
    @Override
    public MYViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        FormListRowBinding binding = FormListRowBinding.inflate(layoutInflater,parent,false);

        View view = LayoutInflater.from(parent.getContext()).inflate(layout_id,parent,false);
        //FormListRowBinding binding = FormListRowBinding.inflate(layoutInflater,parent,false);


        RowDonationProductInfoBinding binding = RowDonationProductInfoBinding.bind(view);



        return new MYViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull MYViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        Utility.log(TAG,"getItemCount():"+DonatedProducts.size());
        return DonatedProducts == null?0:DonatedProducts.size();
    }

    class MYViewHolder extends RecyclerView.ViewHolder{

        RowDonationProductInfoBinding binding;

        public MYViewHolder(RowDonationProductInfoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(int position){
            Utility.log(TAG,"About to Bind");
            binding.setViewmodel(viewModel);
            binding.setPos(position);
            binding.executePendingBindings();

        }
    }
}
