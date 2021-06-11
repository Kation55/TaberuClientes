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
import com.example.ubereats.user.PaymentDetails;
import com.example.ubereats.user.User_Delete_Update_Address;
import com.example.ubereats.user.User_Delete_Update_Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreditCardsRecycleView extends RecyclerView.Adapter<CreditCardsRecycleView.ViewHolder> {

    private Context context;
    private ArrayList<PaymentDetails> cards;
    private boolean isCheckout;
    private CreditCardsRecycleView.ViewHolder lastCard;

    public CreditCardsRecycleView(ArrayList<PaymentDetails> cards, boolean isCheckout) {
        this.cards = cards;
        this.isCheckout = isCheckout;
    }

    @NonNull
    @Override
    public CreditCardsRecycleView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_credit_card, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditCardsRecycleView.ViewHolder holder, int position) {

        holder.getType().setText(cards.get(position).getType());
        holder.getOwner().setText(cards.get(position).getNumerodeTarjeta());

        holder.getCreditCard().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, User_Delete_Update_Card.class);

                intent.putExtra("CARD_ID", cards.get(position).getId());
                context.startActivity(intent);
            }
        });

        if (isCheckout) {
            checkoutFunction(holder, position);
        }


    }

    public void checkoutFunction (CreditCardsRecycleView.ViewHolder holder, int position) {
        holder.getCreditCard().setOnClickListener((view) -> {
            holder.getOwner().setTextAppearance(R.style.SubtituloMediumSelect);
            holder.getType().setTextAppearance(R.style.SubtituloLightSelect);

            if (lastCard != null) {
                lastCard.getOwner().setTextAppearance(R.style.SubtituloMedium);
                lastCard.getType().setTextAppearance(R.style.SubtituloLight);
            }
            lastCard = holder;

            CheckoutService.getInstance().setPaymentId(cards.get(position).getId());
        });
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout creditCard;
        private TextView type, owner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            creditCard = (ConstraintLayout) itemView.findViewById(R.id.creditCardRecycle);
            type = (TextView) creditCard.findViewById(R.id.cardType);
            owner = (TextView) creditCard.findViewById(R.id.cardOwner);
        }

        public TextView getOwner() {
            return owner;
        }

        public TextView getType() {
            return type;
        }

        public ConstraintLayout getCreditCard() {
            return creditCard;
        }

        public void setStringOwner(String owner) {
            this.owner.setText(owner);
        }

        public void setStringType(String type) {
            this.type.setText(type);
        }
    }
}


