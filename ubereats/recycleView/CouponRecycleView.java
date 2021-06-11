package com.example.ubereats.recycleView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ubereats.R;
import com.example.ubereats.user.CuponDetails;
import com.example.ubereats.user.AddressDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CouponRecycleView extends RecyclerView.Adapter<CouponRecycleView.ViewHolder> {

    private Context context;
    private ArrayList<CuponDetails> coupons;

    public CouponRecycleView(ArrayList<CuponDetails> coupons) {
        this.coupons = coupons;
    }

    @NonNull
    @Override
    public CouponRecycleView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_coupon, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponRecycleView.ViewHolder holder, int position) {

        holder.getOwner().setText(coupons.get(position).getNombreCupon());
        holder.getType().setText(coupons.get(position).getCantidad()+"");
    }

    @Override
    public int getItemCount() {
        return coupons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout coupon;
        private TextView discount, description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            coupon = (ConstraintLayout) itemView.findViewById(R.id.couponRecycle);
            discount = (TextView) coupon.findViewById(R.id.discountAmount);
            description = (TextView) coupon.findViewById(R.id.discountDescription);
        }

        public TextView getOwner() {
            return description;
        }

        public TextView getType() {
            return discount;
        }

        public ConstraintLayout getCoupon() {
            return coupon;
        }

        public void setStringDescription(String description) {
            this.description.setText(description);
        }

        public void setStringDiscount(String discount) {
            this.discount.setText(discount);
        }
    }
}

