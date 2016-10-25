package id.co.ppu.radanachat;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.moonmonkeylabs.realmsearchview.RealmSearchAdapter;
import co.moonmonkeylabs.realmsearchview.RealmSearchViewHolder;
import id.co.ppu.radanachat.components.DividerItemDecoration;
import id.co.ppu.radanachat.components.RealmSearchView;
import id.co.ppu.radanachat.listener.OnContactsListener;
import id.co.ppu.radanachat.pojo.chats.TrnChatContact;
import id.co.ppu.radanachat.pojo.chats.TrnChatLog;
import id.co.ppu.radanachat.util.Utility;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private Realm realm;

    @BindView(R.id.contacts)
    RealmSearchView contacts;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private OnContactsListener mListener;

    private ContactListAdapter mAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        contacts.getRealmRecyclerView().addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        this.realm = Realm.getDefaultInstance();

        loadRecentContacts();
    }

    private void loadRecentContacts() {
        contacts.setAdapter(null);

        RealmResults<TrnChatContact> contacts = this.realm.where(TrnChatContact.class)
                .findAll();

        if (contacts.size() < 1) {
            // sementara create dummy ?
            RealmResults<TrnChatLog> chatLogs = this.realm.where(TrnChatLog.class)
                    .findAll();


            return;
        }

        this.realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //populate

            }
        });

        long count = this.realm.where(TrnChatLog.class)
                .count();

        if (count < 1) {
            this.contacts.setVisibility(View.INVISIBLE);
        } else {
            this.contacts.setVisibility(View.VISIBLE);
        }

        // due to the limitations of realmsearchview, searchable column has been disabled
        // because when sort by seqNo, i cant search by custname
        mAdapter = new ContactListAdapter(
                getContext(),
                this.realm,
                "nickName"
        );

        this.contacts.setAdapter(mAdapter);
        this.contacts.getRealmRecyclerView().invalidate();

    }

    @Override
    public void onStop() {
        super.onStop();

        if (this.realm != null) {
            this.realm.close();
            this.realm = null;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnContactsListener) {
            mListener = (OnContactsListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnContactsListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void search(String query) {
        contacts.getSearchBar().setText(query);
    }


    @OnClick(R.id.fab)
    public void onClickFab() {

    }

    public class ContactListAdapter extends RealmSearchAdapter<TrnChatContact, ContactListAdapter.DataViewHolder> {

        public ContactListAdapter(@NonNull Context context, @NonNull Realm realm, @NonNull String filterKey) {
            super(context, realm, filterKey);
        }

        @Override
        public DataViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
            View v = inflater.inflate(R.layout.row_contact_list, viewGroup, false);
            return new DataViewHolder((FrameLayout) v);
        }

        @Override
        public void onBindRealmViewHolder(DataViewHolder dataViewHolder, int position) {
            final TrnChatContact detail = realmResults.get(position);
            dataViewHolder.llRowContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getContext() instanceof OnContactsListener) {
                        ((OnContactsListener)getContext()).onContactSelected();
                    }

                }
            });

            dataViewHolder.llRowContact.setBackgroundColor(Color.WHITE);    // must

            TextView contactName = dataViewHolder.tvContactName;
            if (Build.VERSION.SDK_INT >= 24) {
                contactName.setText(Html.fromHtml("<strong>" + detail.getNickName() + "</strong>", Html.FROM_HTML_MODE_LEGACY));
            } else {
                contactName.setText(Html.fromHtml("<strong>" + detail.getNickName() + "</strong>"));
            }
        }

        public class DataViewHolder extends RealmSearchViewHolder {

            public FrameLayout container;

            @BindView(R.id.llRowContact)
            LinearLayout llRowContact;

            @BindView(R.id.tvContactName)
            TextView tvContactName;

            public DataViewHolder(FrameLayout container) {
                super(container);

                this.container = container;
                ButterKnife.bind(this, container);
            }

        }
    }

}
