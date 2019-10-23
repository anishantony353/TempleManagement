package com.saarit.temple_management.templemanagement.view.adapters;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.saarit.temple_management.templemanagement.BR;
import com.saarit.temple_management.templemanagement.databinding.SubmittedListRowBinding;
import com.saarit.temple_management.templemanagement.model.FormType;
import com.saarit.temple_management.templemanagement.view_model.SubmittedListViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SubmittedListAdapter extends RecyclerView.Adapter<SubmittedListAdapter.MYViewHolder> {

    private List<FormType> formTypes;
    private SubmittedListViewModel vm;
    private int layout_id;

    public SubmittedListAdapter(int layout_id,SubmittedListViewModel vm){

        this.layout_id = layout_id;
        this.vm = vm;

    }

    public void setFormTypes(List<FormType> formTypes){
        this.formTypes = formTypes;
    }


    @NonNull
    @Override
    public MYViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        SubmittedListRowBinding binding = SubmittedListRowBinding.inflate(layoutInflater,parent,false);

        return new MYViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull MYViewHolder holder, int position) {
        holder.bind(vm,position);

    }

    @Override
    public int getItemCount() {

        return formTypes == null?0:formTypes.size();
    }

    class MYViewHolder extends RecyclerView.ViewHolder{

        SubmittedListRowBinding binding;

        public MYViewHolder(SubmittedListRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(SubmittedListViewModel viewmodel,int position){

            binding.setVariable(BR.vm,viewmodel);
            binding.setVariable(BR.pos,position);
            binding.executePendingBindings();

        }
    }
}
