package com.example.mavoitureapplicationmobile;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.security.AccessControlContext;

public class CarRVAdapter extends FirebaseRecyclerAdapter<CarRvModal, CarRVAdapter.personsViewholder> {
    MainActivity main = new MainActivity();
    Context context;

    ImageView imgCar;
    TextView nameCar , priceCar;
    Button delete , update;

    public CarRVAdapter(AccessControlContext context, @NonNull FirebaseRecyclerOptions<CarRvModal> options) {
        super(options);
    }

    @Override
//    update the ViewHolder contents with the item at the given position
    protected void onBindViewHolder(@NonNull personsViewholder holder, int position, @NonNull CarRvModal model) {
        // setting data to our recycler view item on below line.
        holder.carname.setText(model.getCarModel());

        holder.price.setText(model.getCarPrice());

        Glide.with(holder.img.getContext()).load(model.getCarImg()).into(holder.img);

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//               holder.showDialog(v.getContext());

                final Dialog dialog = new Dialog(v.getContext());

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.popup);
                dialog.setCanceledOnTouchOutside(true);

                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(layoutParams);

                imgCar = (ImageView) dialog.findViewById(R.id.imgCar);
                nameCar = (TextView) dialog.findViewById(R.id.nameCar);
                priceCar = (TextView) dialog.findViewById(R.id.priceCar);
                update = (Button) dialog.findViewById(R.id.update);

                nameCar.setText(model.getCarModel());

                priceCar.setText(model.getCarPrice());

                Glide.with(imgCar.getContext()).load(model.getCarImg()).into(imgCar);

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(v.getContext(), Edit_car.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        String modelCars = model.getCarModel();
                        String priceCars = model.getCarPrice();
                        String descCars = model.getCarDescription();
                        String BestSuitedForCars = model.getBestSuitedFor();

                        i.putExtra("modelCars", modelCars);
                        i.putExtra("priceCars", priceCars);
                        i.putExtra("descCars", descCars);
                        i.putExtra("BestSuitedForCars", BestSuitedForCars);

                        v.getContext().startActivity(i);

                    }
                });


                dialog.show();

            }
        });


    }

    @NonNull
    @Override
    public personsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_rv_item, parent, false);
        return new CarRVAdapter.personsViewholder(view);
    }


    class personsViewholder extends RecyclerView.ViewHolder {
        // creating variable for our image view and text view on below line
        ImageView img;
        TextView carname, price;

        public personsViewholder(@NonNull View itemView) {
            super(itemView);
            // initializing all our variables on below line
            img = itemView.findViewById(R.id.idIVCar);
            carname = itemView.findViewById(R.id.idTVCArName);
            price = itemView.findViewById(R.id.idTVCarPrice);

        }
    }

}
