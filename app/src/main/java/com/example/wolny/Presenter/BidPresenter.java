package com.example.wolny.Presenter;

import androidx.annotation.NonNull;

import com.example.wolny.IMain;
import com.example.wolny.Model.Bid;
import com.example.wolny.Utils.Constraint;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BidPresenter {
    IMain.IJobDetails iJobDetails;
    List<Bid> listBid;

    public BidPresenter(IMain.IJobDetails iJobDetails) {
        this.iJobDetails = iJobDetails;
    }

    public void getData(Query query, List<String> avatarUrl, List<String> listUsername, int totalBid) {

        
    }

}
