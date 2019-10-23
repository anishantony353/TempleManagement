package com.saarit.temple_management.templemanagement.view.adapters;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.saarit.temple_management.templemanagement.BR;
import com.saarit.temple_management.templemanagement.databinding.FormListRowBinding;
import com.saarit.temple_management.templemanagement.model.FormName;
import com.saarit.temple_management.templemanagement.view_model.FormsViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FormListAdapter extends RecyclerView.Adapter<FormListAdapter.MYViewHolder> {

    private List<FormName> forms;
    private FormsViewModel vm;
    private int layout_id;

    public FormListAdapter(int layout_id, FormsViewModel vm){

        this.layout_id = layout_id;
        this.vm = vm;

    }

    public void setForms(List<FormName> forms){
        this.forms = forms;
    }


    @NonNull
    @Override
    public MYViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        FormListRowBinding binding = FormListRowBinding.inflate(layoutInflater,parent,false);

        return new MYViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull MYViewHolder holder, int position) {
        holder.bind(vm,position);

    }

    @Override
    public int getItemCount() {

        return forms == null?0:forms.size();
    }

    class MYViewHolder extends RecyclerView.ViewHolder{

        FormListRowBinding binding;

        public MYViewHolder(FormListRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(FormsViewModel viewmodel,int position){

            binding.setVariable(BR.vm,viewmodel);
            binding.setVariable(BR.pos,position);
            binding.executePendingBindings();

        }
    }
}
