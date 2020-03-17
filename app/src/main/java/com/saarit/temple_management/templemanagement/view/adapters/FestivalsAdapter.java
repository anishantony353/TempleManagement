package com.saarit.temple_management.templemanagement.view.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saarit.temple_management.templemanagement.databinding.RowFestivalInfoBinding;
import com.saarit.temple_management.templemanagement.model.Festival;
import com.saarit.temple_management.templemanagement.util.Utility;
import com.saarit.temple_management.templemanagement.view_model.FormType2_ViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FestivalsAdapter extends RecyclerView.Adapter<FestivalsAdapter.MYViewHolder> {

    private String TAG = FestivalsAdapter.class.getSimpleName();

    private List<Festival> festivals = new ArrayList<>();
    private int layout_id;
    private FormType2_ViewModel viewModel;

    public FestivalsAdapter(int layout_id, FormType2_ViewModel viewModel){

        this.layout_id = layout_id;
        this.viewModel = viewModel;


    }

    public void setFestivals(List<Festival> festivals){
        this.festivals = festivals;
    }

    public void addFestival(Festival festival){
        festivals.add(festival);
        //notifyItemInserted(festivals.size() - 1);

    }


    @NonNull
    @Override
    public MYViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        FormListRowBinding binding = FormListRowBinding.inflate(layoutInflater,parent,false);

        View view = LayoutInflater.from(parent.getContext()).inflate(layout_id,parent,false);
        //FormListRowBinding binding = FormListRowBinding.inflate(layoutInflater,parent,false);


        RowFestivalInfoBinding binding = RowFestivalInfoBinding.bind(view);



        return new MYViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull MYViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        Utility.log(TAG,"getItemCount():"+festivals.size());
        return festivals == null?0:festivals.size();
    }

    class MYViewHolder extends RecyclerView.ViewHolder{

        RowFestivalInfoBinding binding;

        public MYViewHolder(RowFestivalInfoBinding binding) {
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
