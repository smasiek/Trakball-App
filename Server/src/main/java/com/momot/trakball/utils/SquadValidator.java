package com.momot.trakball.utils;

import com.momot.trakball.dao.Squad;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

final public class SquadValidator {

    public static boolean isSquadValid(Squad squad){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = dateFormat.parse(squad.getDate());
            Date currentDate= new Date();

            long diffInMillies = date.getTime() - currentDate.getTime();
            return (diffInMillies>0);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }


}
