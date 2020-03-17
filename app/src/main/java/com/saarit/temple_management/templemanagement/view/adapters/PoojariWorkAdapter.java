package com.saarit.temple_management.templemanagement.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saarit.temple_management.templemanagement.databinding.RowPoojariWorkInfoBinding;
import com.saarit.temple_management.templemanagement.databinding.RowWorshipingHouseInfoBinding;
import com.saarit.temple_management.templemanagement.model.PoojariWork;
import com.saarit.temple_management.templemanagement.model.WorshipingHouse;
import com.saarit.temple_management.templemanagement.util.Utility;
import com.saarit.temple_management.templemanagement.view_model.FormType2_ViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PoojariWorkAdapter extends RecyclerView.Adapter<PoojariWorkAdapter.MYViewHolder> {
    private String TAG = PoojariWorkAdapter.class.getSimpleName();

    private List<PoojariWork> poojariWorks = new ArrayList<>();
    private int layout_id;
    private FormType2_ViewModel viewModel;

    public PoojariWorkAdapter(int layout_id, FormType2_ViewModel viewModel){

        this.layout_id = layout_id;
        this.viewModel = viewModel;


    }

    public void setPoojariWorks(List<PoojariWork> poojariWorks){
        this.poojariWorks = poojariWorks;
    }

    public void addPoojariWork(PoojariWork poojariWork){
        poojariWorks.add(poojariWork);
        //notifyItemInserted(festivals.size() - 1);

    }


    @NonNull
    @Override
    public MYViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        FormListRowBinding binding = FormListRowBinding.inflate(layoutInflater,parent,false);

        View view = LayoutInflater.from(parent.getContext()).inflate(layout_id,parent,false);
        //FormListRowBinding binding = FormListRowBinding.inflate(layoutInflater,parent,false);


        RowPoojariWorkInfoBinding binding = RowPoojariWorkInfoBinding.bind(view);



        return new MYViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull MYViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        Utility.log(TAG,"getItemCount():"+poojariWorks.size());
        return poojariWorks == null?0:poojariWorks.size();
    }

    class MYViewHolder extends RecyclerView.ViewHolder{

        RowPoojariWorkInfoBinding binding;

        public MYViewHolder(RowPoojariWorkInfoBinding binding) {
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
