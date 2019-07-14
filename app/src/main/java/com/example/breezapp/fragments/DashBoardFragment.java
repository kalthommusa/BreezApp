package com.example.breezapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.breezapp.adapters.DashBoardAdapter;
import com.example.breezapp.R;
import com.example.breezapp.models.Thing;
import com.example.breezapp.rest.RestThing;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashBoardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private List<Thing> t;
    private RecyclerView recyclerView;
    private DashBoardAdapter mAdapter;
    // private ArrayList<Thing> data;


/*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dash_board, container, false);

         recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView_dash);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        /*
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView_dash);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new DashBoardAdapter(thingList,getContext());
        recyclerView.setAdapter(mAdapter);
*/


        response();


        return v;
}

    public void response()  {

            try {
                Call<List<Thing>> call = RestThing.getInstance().getApi().getInbox();

                call.enqueue(new Callback<List<Thing>>() {


                    @Override
                    public void onResponse(Call<List<Thing>> call, Response<List<Thing>> response) {
                      //  Log.e("Response 1", "Response code: " + response.code());

                        if (response.isSuccessful()) {
                            Log.e("Response 2", "Response code: " + response.code());

                            t = response.body();

                            showIt(t);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Thing>> call, Throwable t) {
                        Toast.makeText(getContext(), " Error connecting to the server.. Trying Again...", Toast.LENGTH_SHORT).show();
                       // Log.e("Not Correct ", "Not Working");
                        response();

                    }
                });

            }catch (Exception e){
                Log.e("Error", ""+e);
                }


    }


    private void showIt(List<Thing> response) {


         mAdapter = new DashBoardAdapter(response,getContext());
         recyclerView.setAdapter(mAdapter);
    }

}
