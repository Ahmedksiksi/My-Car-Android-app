package com.example.mavoitureapplicationmobile;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Visitor extends AppCompatActivity {
    // creating variables for fab, firebase database, recycler view and relative layout
    private RecyclerView recyclerView;
    CarRVAdapter adapter;
    DatabaseReference mbase;
    private FloatingActionButton idFBtLogin;
    private ImageView idIVCar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);
        idFBtLogin = findViewById(R.id.idFBtLogin);
//        idIVCar = findViewById(R.id.idIVCar);
        // on below line we are getting database reference
        mbase = FirebaseDatabase.getInstance().getReference("Car");
        // on below line adding a click listener for our floating action button

        idFBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Visitor.this, Login.class);
                startActivity(i);
            }

        });


        recyclerView = findViewById(R.id.idRVCars);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<CarRvModal> options = new FirebaseRecyclerOptions.Builder<CarRvModal>().setQuery(mbase, CarRvModal.class).build();
        adapter = new CarRVAdapter(getContext(), options);
        recyclerView.setAdapter(adapter);

    }

    public void navigation() {
        Intent i = new Intent(Visitor.this, SheetLayout.class);
        startActivity(i);
    }










    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void onButtonShowPopupWindowClick(View view) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.bottom_sheet_layout, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

}