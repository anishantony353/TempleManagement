package com.saarit.temple_management.templemanagement.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saarit.temple_management.templemanagement.databinding.RowRespectedPersonInfoBinding;
import com.saarit.temple_management.templemanagement.model.RespectedPerson;
import com.saarit.temple_management.templemanagement.util.Utility;
import com.saarit.temple_management.templemanagement.view_model.FormType2_ViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RespectedPersonAdapter extends RecyclerView.Adapter<RespectedPersonAdapter.MYViewHolder>{

    private String TAG = RespectedPersonAdapter.class.getSimpleName();

    private List<RespectedPerson> respectedPersons = new ArrayList<>();
    private int layout_id;
    private FormType2_ViewModel viewModel;

    public RespectedPersonAdapter(int layout_id, FormType2_ViewModel viewModel){

        this.layout_id = layout_id;
        this.viewModel = viewModel;


    }

    public void setRespectedPersons(List<RespectedPerson> respectedPersons){
        this.respectedPersons = respectedPersons;
    }

    public void addRespectedPerson(RespectedPerson respectedPerson){
        respectedPersons.add(respectedPerson);
        //notifyItemInserted(festivals.size() - 1);

    }


    @NonNull
    @Override
    public MYViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        FormListRowBinding binding = FormListRowBinding.inflate(layoutInflater,parent,false);

        View view = LayoutInflater.from(parent.getContext()).inflate(layout_id,parent,false);
        //FormListRowBinding binding = FormListRowBinding.inflate(layoutInflater,parent,false);


        RowRespectedPersonInfoBinding binding = RowRespectedPersonInfoBinding.bind(view);



        return new MYViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull MYViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        Utility.log(TAG,"getItemCount():"+respectedPersons.size());
        return respectedPersons == null?0:respectedPersons.size();
    }

    class MYViewHolder extends RecyclerView.ViewHolder{

        RowRespectedPersonInfoBinding binding;

        public MYViewHolder(RowRespectedPersonInfoBinding binding) {
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
