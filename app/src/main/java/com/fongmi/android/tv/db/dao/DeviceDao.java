package com.github.tvbox.gongjin.db.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.github.tvbox.gongjin.bean.Device;

import java.util.List;

@Dao
public abstract class DeviceDao extends BaseDao<Device> {

    @Query("SELECT * FROM Device")
    public abstract List<Device> findAll();

    @Query("DELETE FROM Device")
    public abstract void delete();
}
