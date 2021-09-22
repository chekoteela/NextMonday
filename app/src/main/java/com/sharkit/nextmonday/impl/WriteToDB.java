package com.sharkit.nextmonday.impl;

import com.sharkit.nextmonday.MySQL.TargetData;
import com.sharkit.nextmonday.Users.MyTarget;

import java.util.Calendar;
import java.util.Map;

public interface WriteToDB {

    public void writeToDB(MyTarget target, Map<String, Boolean> mapRepeats, TargetData targetData, Calendar calendar);
}
