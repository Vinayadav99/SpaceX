package com.example.assignment.Model;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "member_table")
public class Member {

    @PrimaryKey
    public int id;

    String name;

    String agency;

    String link;

    String status;

    String image;

    @Ignore
    public Member() {
    }

    public Member(int id, String name, String agency, String link, String status, String image) {
        this.id = id;
        this.name = name;
        this.agency = agency;
        this.link = link;
        this.status = status;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
