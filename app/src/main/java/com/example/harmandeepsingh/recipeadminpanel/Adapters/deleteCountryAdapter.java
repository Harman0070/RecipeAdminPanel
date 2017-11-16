package com.example.harmandeepsingh.recipeadminpanel.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harmandeepsingh.recipeadminpanel.Interface.ApiClient;
import com.example.harmandeepsingh.recipeadminpanel.Interface.ApiService;
import com.example.harmandeepsingh.recipeadminpanel.Models.CountryName;
import com.example.harmandeepsingh.recipeadminpanel.Models.CountryrecipeModel;
import com.example.harmandeepsingh.recipeadminpanel.R;
import com.example.harmandeepsingh.recipeadminpanel.checkInternetState;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Harmandeep singh on 11/14/2017.
 */

public class deleteCountryAdapter extends RecyclerView.Adapter<deleteCountryAdapter.MyViewHolder> {
    private Context mContext;
    private List<CountryName> countryList;

    ProgressDialog pDialog;
    CountryName countryName;
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView deleteCountryTxt;
        ImageButton imagebtDelete;

        public MyViewHolder(View view) {
            super(view);
            deleteCountryTxt = (TextView) view.findViewById(R.id.deleteCountryTxt);
            imagebtDelete=(ImageButton)view.findViewById(R.id.imagebtDelete);
        }
    }
    public deleteCountryAdapter(Context mContext, List<CountryName> countryList) {
        this.mContext = mContext;
        this.countryList = countryList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_delete_country, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        countryName = new CountryName();
        countryName = countryList.get(position);

        holder.deleteCountryTxt.setText(countryName.getCountryName());

        holder.imagebtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // String id=CategoryRecipesCardItems.getIngId();
                alertdialog(countryList.get(position).getCountryType());
                // Log.d("positioncard"," "+position);
                Log.d("idcard"," "+countryList.get(position).getCountryType());
                // dishListingre.remove(position);
                //notifyDataSetChanged();
            }
        });

    }
    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public void alertdialog(final String id) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        dialogBuilder.setTitle("Delete");
        dialogBuilder.setMessage("Are you Sure?");

        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();

                deletefromDatabase(id);
                Log.d("idcardDelete:",""+id);

                //  hidepDialog();
                //deletefromDatabase();
                // Toast.makeText(AddRecipe2.this, "added", Toast.LENGTH_SHORT).show();

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void deletefromDatabase(String id){
        pDialog=new ProgressDialog(mContext);
        pDialog.setMessage("fetching data...");
        pDialog.setCancelable(false);

        showpDialog();
        //check internet state
        if (!checkInternetState.getInstance(mContext).isOnline()) {
            hidepDialog();

            Toast.makeText(mContext, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();

        }else {
            Log.d("deleteid:",""+id);
            ApiService service = ApiClient.getClient().create(ApiService.class);
            Call<CountryrecipeModel> userCall = service.getcountrytype(id);
            userCall.enqueue(new Callback<CountryrecipeModel>() {
                @Override
                public void onResponse(Call<CountryrecipeModel> call, Response<CountryrecipeModel> response) {
                    hidepDialog();

                    Log.d("Indian"," "+response.body().getSuccess());
                    Log.d("DAta",""+response);
                    if (response.isSuccessful()){
                        if (response.body().getSuccess()) {
                            //dishListingre.remove(position);
                            Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT).show();
                            Toast.makeText(mContext, "Swipe To Refresh", Toast.LENGTH_SHORT).show();


                        }else{
                            Toast.makeText(mContext, "Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(mContext, "not success", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CountryrecipeModel> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                }
            });
        }
        notifyDataSetChanged();
    }

    //to show progress dialog
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
    //to hide progress
    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}


