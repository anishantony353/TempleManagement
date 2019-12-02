package com.saarit.temple_management.templemanagement.model;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Temple_master {

    @PrimaryKey
    @ColumnInfo(name ="temple_id")
    @SerializedName("temple_id")
    public int templeId;

    @ColumnInfo(name ="temple_name")
    @SerializedName("temple_name")
    public String temple;

    @ColumnInfo(name ="village_id")
    @SerializedName("village_id")
    public int village_id;

    @ColumnInfo(name ="village_name")
    @SerializedName("village_name")
    public String village;

    @ColumnInfo(name ="taluka_id")
    @SerializedName("taluka_id")
    public int taluka_id;

    @ColumnInfo(name ="taluka_name")
    @SerializedName("taluka_name")
    public String taluka;

    @ColumnInfo(name ="district_id")
    @SerializedName("district_id")
    public int district_id;

    @ColumnInfo(name ="district_name")
    @SerializedName("district_name")
    public String district;

    @NonNull
    @Override
    public String toString() {
        return temple;
    }
}
