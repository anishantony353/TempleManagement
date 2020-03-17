package com.saarit.temple_management.templemanagement.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saarit.temple_management.templemanagement.databinding.RowWorshipingTypeInfoBinding;
import com.saarit.temple_management.templemanagement.model.WorshipingType;
import com.saarit.temple_management.templemanagement.util.Utility;
import com.saarit.temple_management.templemanagement.view_model.FormType2_ViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WorshipingTypeAdapter extends RecyclerView.Adapter<WorshipingTypeAdapter.MYViewHolder>{

    private String TAG = WorshipingTypeAdapter.class.getSimpleName();

    private List<WorshipingType> worshipingTypes = new ArrayList<>();
    private int layout_id;
    private FormType2_ViewModel viewModel;

    public WorshipingTypeAdapter(int layout_id, FormType2_ViewModel viewModel){

        this.layout_id = layout_id;
        this.viewModel = viewModel;


    }

    public void setWorshipingTypes(List<WorshipingType> worshipingTypes){
        this.worshipingTypes = worshipingTypes;
    }

    public void addWorshipingType(WorshipingType worshipingType){
        worshipingTypes.add(worshipingType);
        //notifyItemInserted(festivals.size() - 1);

    }


    @NonNull
    @Override
    public MYViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        FormListRowBinding binding = FormListRowBinding.inflate(layoutInflater,parent,false);

        View view = LayoutInflater.from(parent.getContext()).inflate(layout_id,parent,false);
        //FormListRowBinding binding = FormListRowBinding.inflate(layoutInflater,parent,false);


        RowWorshipingTypeInfoBinding binding = RowWorshipingTypeInfoBinding.bind(view);



        return new MYViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull MYViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        Utility.log(TAG,"getItemCount():"+worshipingTypes.size());
        return worshipingTypes == null?0:worshipingTypes.size();
    }

    class MYViewHolder extends RecyclerView.ViewHolder{

        RowWorshipingTypeInfoBinding binding;

        public MYViewHolder(RowWorshipingTypeInfoBinding binding) {
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
