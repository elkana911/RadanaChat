package id.co.ppu.radanachat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.ppu.radanachat.pojo.chats.TrnChatContact;
import id.co.ppu.radanachat.rest.ApiInterface;
import id.co.ppu.radanachat.rest.ServiceGenerator;
import id.co.ppu.radanachat.rest.request.RequestChatStatus;
import id.co.ppu.radanachat.util.NetUtil;
import id.co.ppu.radanachat.util.Storage;
import id.co.ppu.radanachat.util.Utility;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements FragmentChatActiveContacts.OnContactsListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case 11:
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.fab)
    public void onClickFab(View view) {
        Intent i = new Intent(this, ActivityChatRegisteredContacts.class);
        startActivityForResult(i, 11);
    }

    @Override
    public void onContactSelected(TrnChatContact contact) {
        // open fragmentchatwith
    }

    @Override
    public void onContactClearChats(TrnChatContact contact) {
        // why ?
    }

    @Override
    public void onStatusOnline() {
        // send to server that current contact is online
        if (!NetUtil.isConnected(this)) {
            return;
        }

        ApiInterface fastService =
                ServiceGenerator.createService(ApiInterface.class, Utility.buildUrl(Storage.getPreferenceAsInt(getApplicationContext(), Storage.KEY_SERVER_ID, 0)));

        Long userStatus = 1L;
        String userMsg = "Available";

        RequestChatStatus req = new RequestChatStatus();
        req.setDate(new Date());
        req.setStatus(userStatus);
        req.setMessage(userMsg);

        Call<ResponseBody> call = fastService.sendStatus(req);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // update display status here
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
