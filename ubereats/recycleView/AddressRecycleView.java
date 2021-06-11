package com.example.ubereats.recycleView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ubereats.R;
import com.example.ubereats.checkout.CheckoutService;
import com.example.ubereats.user.AddressDetails;
import com.example.ubereats.user.UserAddressActivity;
import com.example.ubereats.user.User_Delete_Update_Address;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddressRecycleView extends RecyclerView.Adapter<AddressRecycleView.ViewHolder> {

    private Context context;
    private ArrayList<AddressDetails> address;
    private boolean isCheckout;
    private AddressRecycleView.ViewHolder lastAddress;

    public AddressRecycleView(ArrayList<AddressDetails> address, boolean isCheckout) {
        this.address = address;
        this.isCheckout = isCheckout;
    }

    @NonNull
    @Override
    public AddressRecycleView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_address, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressRecycleView.ViewHolder holder, int position) {

        holder.getNickname().setText(address.get(position).getAlias());
        holder.getType().setText(address.get(position).getDescripcion());


        holder.address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, User_Delete_Update_Address.class);

                intent.putExtra("ADDRESS_ID", address.get(position).getId());
                context.startActivity(intent);
            }
        });

        if (isCheckout) {
            checkoutFunction(holder, position);
        }
    }

    public void checkoutFunction (AddressRecycleView.ViewHolder holder, int position) {
        holder.getAddress().setOnClickListener((view) -> {
            holder.getNickname().setTextAppearance(R.style.SubtituloMediumSelect);
            holder.getType().setTextAppearance(R.style.SubtituloLightSelect);

            if (lastAddress != null) {
                lastAddress.getNickname().setTextAppearance(R.style.SubtituloMedium);
                lastAddress.getType().setTextAppearance(R.style.SubtituloLight);
            }
            lastAddress = holder;

            CheckoutService.getInstance().setAddressId(address.get(position).getId());
        });
    }

    @Override//contador del numero de elementos en address
    public int getItemCount() {
        return address.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout address;
        private TextView type, nickname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            address = (ConstraintLayout) itemView.findViewById(R.id.addressRecycle);
            type = (TextView) address.findViewById(R.id.addressType);
            nickname = (TextView) address.findViewById(R.id.addressNickname);
        }

        public TextView getNickname() {
            return nickname;
        }

        public TextView getType() {
            return type;
        }

        public ConstraintLayout getAddress() {
            return address;
        }

        public void setStringNickname(String nickname) {
            this.nickname.setText(nickname);
        }

        public void setStringType(String type) {
            this.type.setText(type);
        }
    }
}

