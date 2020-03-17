package com.saarit.temple_management.templemanagement.view.ui.not_in_use;


import android.content.Intent;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saarit.temple_management.templemanagement.R;
import com.saarit.temple_management.templemanagement.databinding.FragmentSubmittedListBinding;
import com.saarit.temple_management.templemanagement.model.not_in_use.FormType;
import com.saarit.temple_management.templemanagement.util.Utility;
import com.saarit.temple_management.templemanagement.view_model.not_in_use.SubmittedListViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class SubmittedListFragment extends Fragment {

    private String TAG = SubmittedListFragment.class.getSimpleName();

    private SubmittedListViewModel viewModel;
    FragmentSubmittedListBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Utility.log(TAG,"onCreate()");

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Utility.log(TAG,"onCreateView()");


        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_submitted_list,container,false);

        View view = binding.getRoot();

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Utility.log(TAG,"onViewCreated()");
        //super.onViewCreated(view, savedInstanceState);

        setupBindings(binding,savedInstanceState);
    }


    private void setupBindings(FragmentSubmittedListBinding binding, Bundle savedInstanceState) {

        //ActivityDogBreedsBinding activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_dog_breeds);
        viewModel = ViewModelProviders.of(this).get(SubmittedListViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        binding.setVm(viewModel);

        setUpObserver();

        if (savedInstanceState == null) {
            setupList();
        }

    }

    private void setupList() {
        viewModel.loading.set(View.VISIBLE);
        viewModel.fetchList();

    }

    private void setUpObserver() {

        Utility.log(TAG,"setUpObserver()");

        viewModel.getList().observe(this, new Observer<List<FormType>>() {
            @Override
            public void onChanged(@Nullable List<FormType> formTypeNotinuses) {

                Utility.log(TAG,"New List received...");

                viewModel.loading.set(View.GONE);

                if(formTypeNotinuses.size() > 0){

                    viewModel.showEmpty.set(View.GONE);
                    viewModel.setListInAdapter(formTypeNotinuses);


                    //Update RecyclerView Adapter from ViewModel


                }else{

                    viewModel.showEmpty.set(View.VISIBLE);

                }

            }
        });

        viewModel.getSelectedForm().observe(this, new Observer<FormType>() {
            @Override
            public void onChanged(@Nullable FormType formTypeNotinuse) {

                Utility.log(TAG,"Selected Form received...");

                startActivity(new Intent(getContext(),FormsActivity.class));

            }
        });

    }

}
