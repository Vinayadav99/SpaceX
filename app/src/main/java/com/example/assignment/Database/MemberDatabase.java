package com.example.assignment.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.assignment.Model.Member;

@Database(entities = {Member.class},version = 1)
public abstract class MemberDatabase extends RoomDatabase {

    public abstract MemberDAO getMemberDAO();

}
