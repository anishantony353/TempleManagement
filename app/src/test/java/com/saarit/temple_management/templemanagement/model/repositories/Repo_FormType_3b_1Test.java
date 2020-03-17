package com.saarit.temple_management.templemanagement.model.repositories;

import android.content.Context;

import com.saarit.temple_management.templemanagement.model.FormType_3b_1;
import com.saarit.temple_management.templemanagement.model.repositories.local_storage.FormType_3b_1_Dao;
import com.saarit.temple_management.templemanagement.testing_objects.Inserter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Single;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
public class Repo_FormType_3b_1Test {

    Repo_FormType_3b_1 repo_formType_3b_1;

    @Mock
    Context context;

    @Mock
    Inserter inserter;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        /*repo_formType_3b_1 = new Repo_FormType_3b_1(context);*/
        repo_formType_3b_1 = new Repo_FormType_3b_1(inserter);
    }

    @Test
    public void getStringData() {
        doReturn(repo_formType_3b_1.getStringData()).when(repo_formType_3b_1).getStringData();

        System.out.println(repo_formType_3b_1.getStringData());
    }

    @Test
    public void getMutableLiveData(){
    MutableLiveData<String> mutableLiveData = new MutableLiveData<>("hii");
    mutableLiveData.setValue("From Mocked Repo");

        /*ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);*/

        /*doReturn(mutableLiveData).when(repo_formType_3b_1).getMutableLiveData();*/

        System.out.println(repo_formType_3b_1.getMutableLiveData().getValue());

        /*IF you mock an object...and call a method of mocked object,verify after the call
        whether the method has been called or not */

    }

    @Test
    public void insertValue(){

        ArgumentCaptor<Integer> arg = ArgumentCaptor.forClass(Integer.class);

        repo_formType_3b_1.insertValue(1);

        verify(inserter).insertValue(arg.capture());

        assertEquals(1,(Object)arg.getValue());
    }
}