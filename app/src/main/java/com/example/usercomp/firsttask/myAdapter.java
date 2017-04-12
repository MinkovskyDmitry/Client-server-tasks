package com.example.usercomp.firsttask;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by UserComp on 10.04.2017.
 */

public class myAdapter extends RecyclerView.Adapter<myAdapter.MyHolder> {
    List<Customer> customers;
    Activity activity;
    AlertDialog dialog;
    private MainActivity.EditCostumer listener;



    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item,parent,false);
        return new  MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {

        final Customer customer=customers.get(position);

        setTextViewParams(customer,holder);

        holder.fabShowDialogBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomerDialogBox dialog = new CustomerDialogBox(activity,customer,listener,holder);
                dialog.show();
            }
        });



    }

    private void setTextViewParams(Customer customer, MyHolder holder){
        if( !customer.getFirstName().equals(""))  holder.textViewFirstName.setText(customer.getFirstName());
        else
            holder.textViewFirstName.setVisibility(View.GONE);

        if( !customer.getLastName().equals(""))  holder.textViewLastName.setText(customer.getLastName());
        else
            holder.textViewLastName.setVisibility(View.GONE);

        if( !customer.getPatronymic().equals(""))  holder.textViewPatronymic.setText(customer.getPatronymic());
        else
            holder.textViewPatronymic.setVisibility(View.GONE);

        if( !customer.getEmail().equals(""))
            holder.textViewEmail.setText(customer.getEmail());
        else
            holder.textViewEmail.setVisibility(View.GONE);

        if(customer.getCreatedAt() != null)
        {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String reportDate = df.format(customer.getCreatedAt());

            holder.textViewCreatedAt.setText(reportDate);
        }
        else
            holder.textViewCreatedAt.setVisibility(View.GONE);

        if(customer.getBirthday() != null){
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String reportDate = df.format(customer.getBirthday());

            holder.textViewBirthday.setText(reportDate);
        }
        else
            holder.textViewBirthday.setVisibility(View.GONE);

        if( !customer.getSite().equals(""))  holder.textViewSite.setText(customer.getSite());
        else {
            holder.textViewBirthday.setVisibility(View.GONE);
            holder.fabShowDialogBox.setVisibility(View.GONE);
        }
    }

    public myAdapter(Activity activity,List<Customer> customerList,MainActivity.EditCostumer listener){
        this.activity=activity;
        this.customers=customerList;
        this.listener=listener;
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView textViewFirstName;
        TextView textViewLastName;
        TextView textViewPatronymic;
        TextView textViewEmail;
        TextView textViewCreatedAt;
        TextView textViewBirthday;
        TextView textViewSite;
        FloatingActionButton fabShowDialogBox;
        public MyHolder(View itemView) {
            super(itemView);

            textViewFirstName=(TextView) itemView.findViewById(R.id.firstName);
            textViewLastName=(TextView) itemView.findViewById(R.id.lastName);
            textViewPatronymic=(TextView) itemView.findViewById(R.id.patronymic);
            textViewEmail=(TextView) itemView.findViewById(R.id.email);
            textViewCreatedAt=(TextView) itemView.findViewById(R.id.createdAt);
            textViewBirthday=(TextView) itemView.findViewById(R.id.birthday);
            textViewSite = (TextView) itemView.findViewById(R.id.site);
            fabShowDialogBox=(FloatingActionButton) itemView.findViewById(R.id.buttonFabEdit);
        }
    }


}
