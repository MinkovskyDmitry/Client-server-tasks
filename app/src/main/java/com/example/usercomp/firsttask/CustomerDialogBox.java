package com.example.usercomp.firsttask;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;



public class CustomerDialogBox extends AlertDialog {

    private Context context;
    private Customer customer;
    private LinearLayout root_layout;
    private MainActivity.EditCostumer listener;
    ProgressDialog pd;
    myAdapter.MyHolder holder;

    protected CustomerDialogBox(final Context context, final Customer customer, MainActivity.EditCostumer listener, myAdapter.MyHolder holder)
    {
        super(context);
        this.context=context;
        this.customer=customer;
        this.listener = listener;
        this.holder = holder;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.dialog_box_edit_customer, null);
        setView(view);

        root_layout=(LinearLayout) view.findViewById(R.id.dialog_box_root);
        final Button btnOk= (Button) view.findViewById(R.id.buttonOk);
        final Button btnCancel = (Button) view.findViewById(R.id.buttonCancel);

        final EditText firstname = (EditText) view.findViewById(R.id.firstNameEdit);
        final EditText lastname = (EditText) view.findViewById(R.id.lastNameEdit);
        final EditText patronymic = (EditText) view.findViewById(R.id.patronymicEdit);
        final EditText email = (EditText) view.findViewById(R.id.emailEdit);
        final EditText createdAt = (EditText) view.findViewById(R.id.createdAtEdit);
        final EditText birthday = (EditText) view.findViewById(R.id.birthdayEdit);

        firstname.setText( customer.getFirstName()!=null ? customer.getFirstName() : "" );
        lastname.setText( customer.getLastName()!=null ? customer.getLastName() : "" );
        patronymic.setText( customer.getPatronymic()!=null ? customer.getPatronymic() : "" );
        email.setText( customer.getEmail()!=null ? customer.getEmail() : "" );

        if (customer.getCreatedAt() != null) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String reportDate = df.format(customer.getCreatedAt());
            createdAt.setText(customer.getCreatedAt() != null ? reportDate : "");
        }

        if (customer.getBirthday() != null) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String reportDate = df.format(customer.getBirthday());
            birthday.setText(customer.getBirthday() != null ? reportDate : "");
        }




        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 1; i < root_layout.getChildCount(); i+=2) {
                    View text = root_layout.getChildAt(i);
                    if (text instanceof EditText) {
                        EditText t = (EditText) text;
                        switch (i/2) {
                            case 0:
                                customer.setFirstName(t.getText().toString());
                                break;
                            case 1:
                                customer.setLastName(t.getText().toString());
                                break;
                            case 2:
                                customer.setPatronymic(t.getText().toString());
                                break;
                            case 3:
                                customer.setEmail(t.getText().toString());
                                break;
                            case 4: {
                                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                try {
                                    customer.setCreatedAt(format.parse(t.getText().toString()));
                                } catch (ParseException e) {
                                    e.getMessage();
                                }
                                break;
                            }
                            case 5:
                                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    customer.setBirthday(format.parse(t.getText().toString()));
                                } catch (ParseException e) {
                                    e.getMessage();
                                }
                                break;
                        }
                    }
                }

                pd = new ProgressDialog(context);
                pd.setMessage("Оправляем изменения на сервер");
                pd.setCancelable(false);
                pd.show();

                SendChanges op = new SendChanges();
                op.execute(customer);

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    private void setTextViewParams(Customer customer, myAdapter.MyHolder holder){

        if( !customer.getFirstName().equals("")) {
            holder.textViewFirstName.setText(customer.getFirstName());
            holder.textViewFirstName.setVisibility(View.VISIBLE);
        }
        else
            holder.textViewFirstName.setVisibility(View.GONE);

        if( !customer.getLastName().equals(""))  {
            holder.textViewLastName.setText(customer.getLastName());
            holder.textViewLastName.setVisibility(View.VISIBLE);
        }
        else
            holder.textViewLastName.setVisibility(View.GONE);

        if( !customer.getPatronymic().equals("")) {
            holder.textViewPatronymic.setText(customer.getPatronymic());
            holder.textViewPatronymic.setVisibility(View.VISIBLE);
        }
        else
            holder.textViewPatronymic.setVisibility(View.GONE);

        if( !customer.getEmail().equals("")) {
            holder.textViewEmail.setText(customer.getEmail());
            holder.textViewEmail.setVisibility(View.VISIBLE);
        }
        else
            holder.textViewEmail.setVisibility(View.GONE);

        if(customer.getCreatedAt() != null)
        {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String reportDate = df.format(customer.getCreatedAt());

            holder.textViewCreatedAt.setText(reportDate);
            holder.textViewCreatedAt.setVisibility(View.VISIBLE);
        }
        else
            holder.textViewCreatedAt.setVisibility(View.GONE);

        if(customer.getBirthday() != null)
        {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String reportDate = df.format(customer.getBirthday());

            holder.textViewBirthday.setText(reportDate);
            holder.textViewBirthday.setVisibility(View.VISIBLE);
        }
        else {
            holder.textViewBirthday.setVisibility(View.GONE);
        }

        if( !customer.getSite().equals("")) {
            holder.textViewSite.setText(customer.getSite());
            holder.textViewSite.setVisibility(View.VISIBLE);
        }
        else
            holder.textViewSite.setVisibility(View.GONE);

    }

    public void onAnswerReceived(String response) {


        if (response == "no site") Toast.makeText(context, "have not site", Toast.LENGTH_LONG).show();
        else {
            JSONObject jObject = null; // json
            try {
                jObject = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "ServerError", Toast.LENGTH_SHORT).show();

                pd.cancel();
                dismiss();
                return;
            }
            boolean resp;
            try {
                resp = jObject.getBoolean("success");
                Toast.makeText(context, resp == true ? "200. OK" : "something wrong", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        pd.cancel();
        dismiss();
        listener.onCostumerEdit(customer);
        setTextViewParams(customer,holder);

    }

    public class SendChanges extends AsyncTask<Customer, Void, Void> {

        @Override
        protected Void doInBackground(Customer... params) {
            final Customer customer = params[0];
            if (customer.getSite() == "") {
                onAnswerReceived("no_site");
                return null;
            }
            try {
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                String URL = "https://fsfss.retailcrm.ru/api/v4/customers/" + customer.getId() + "/edit";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onAnswerReceived(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onAnswerReceived(error.toString());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("apiKey", "X8PBNzZE7h7f6BtDuiJn0bLbtYjFpI7r");
                        params.put("by", "id");
                        params.put("site", customer.getSite());
                        JSONObject jsonBody = new JSONObject();

                        try {
                            jsonBody.put("id", String.valueOf(customer.getId()));
                            jsonBody.put("firstName", customer.getFirstName());
                            jsonBody.put("lastName", customer.getLastName());
                            jsonBody.put("email", customer.getEmail());
                            jsonBody.put("patronymic",customer.getPatronymic());
                           // jsonBody.put("createdAt", customer.getCreatedAt());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        params.put("customer", jsonBody.toString());
                        return params;
                    }
                };

                requestQueue.add(stringRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }
    }


}
