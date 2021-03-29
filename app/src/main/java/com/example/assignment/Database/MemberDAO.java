package com.example.assignment.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.assignment.Model.Member;
import java.util.List;

@Dao
public interface MemberDAO {

    @Insert
    public void addMember(Member member);

    @Update
    public void updateMember(Member member);

    @Delete
    public void deleteMember(Member member);

    @Query("select * from member_table")
    public List<Member> getAllMember();

    @Query("delete from member_table")
    public void deleteAllMember();

}
