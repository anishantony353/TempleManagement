package com.saarit.temple_management.templemanagement.view.adapters.not_in_use;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.saarit.temple_management.templemanagement.BR;
import com.saarit.temple_management.templemanagement.databinding.SavedListRowBinding;
import com.saarit.temple_management.templemanagement.model.not_in_use.FormType;
import com.saarit.temple_management.templemanagement.view_model.not_in_use.SavedListViewModel;

import java.util.List;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

public class SavedListAdapter extends RecyclerView.Adapter<SavedListAdapter.MYViewHolder> {

    private List<FormType> formTypeNotinuses;
    private SavedListViewModel vm;
    private int layout_id;

    public SavedListAdapter(int layout_id, SavedListViewModel vm){

        this.layout_id = layout_id;
        this.vm = vm;

    }

    public void setFormTypeNotinuses(List<FormType> formTypeNotinuses){
        this.formTypeNotinuses = formTypeNotinuses;
    }


    @NonNull
    @Override
    public MYViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        SavedListRowBinding binding = SavedListRowBinding.inflate(layoutInflater,parent,false);

        return new MYViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull MYViewHolder holder, int position) {
        holder.bind(vm,position);

    }

    @Override
    public int getItemCount() {

        return formTypeNotinuses == null?0: formTypeNotinuses.size();
    }

    class MYViewHolder extends RecyclerView.ViewHolder{

        SavedListRowBinding binding;

        public MYViewHolder(SavedListRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(SavedListViewModel viewmodel, int position){

            binding.setVariable(BR.vm,viewmodel);
            binding.setVariable(BR.pos,position);
            binding.executePendingBindings();

        }
    }
}
