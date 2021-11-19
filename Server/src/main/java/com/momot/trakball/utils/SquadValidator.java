package com.momot.trakball.utils;

import com.momot.trakball.dao.Squad;

import java.sql.Timestamp;

final public class SquadValidator {
    public static boolean isSquadValid(Squad squad) {
        Timestamp date = squad.getDate();
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());

        long diffInMillies = date.getTime() - currentDate.getTime();
        return (diffInMillies > 0);
    }
}