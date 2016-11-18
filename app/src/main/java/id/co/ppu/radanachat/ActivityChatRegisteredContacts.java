package id.co.ppu.radanachat;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import id.co.ppu.radanachat.components.BasicActivity;
import id.co.ppu.radanachat.rest.ApiInterface;
import id.co.ppu.radanachat.rest.ServiceGenerator;
import id.co.ppu.radanachat.rest.response.ResponseChatContacts;
import id.co.ppu.radanachat.util.Storage;
import id.co.ppu.radanachat.util.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityChatRegisteredContacts extends ListActivity{

    private String collectorCode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_contacts);

        ButterKnife.bind(this);
/*
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
*/
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();

        ApiInterface fastService =
                ServiceGenerator.createService(ApiInterface.class, Utility.buildUrl(Storage.getPreferenceAsInt(getApplicationContext(), Storage.KEY_SERVER_ID, 0)));

        Call<ResponseChatContacts> call = fastService.getChatContacts(collectorCode);
        call.enqueue(new Callback<ResponseChatContacts>() {
            @Override
            public void onResponse(Call<ResponseChatContacts> call, Response<ResponseChatContacts> response) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ResponseChatContacts> call, Throwable t) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
            }
        });
    }
}
