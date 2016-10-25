package id.co.ppu.radanachat.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView.OnEditorActionListener;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import co.moonmonkeylabs.realmsearchview.ClearableEditText;
import co.moonmonkeylabs.realmsearchview.R.id;
import co.moonmonkeylabs.realmsearchview.R.layout;
import co.moonmonkeylabs.realmsearchview.R.string;
import co.moonmonkeylabs.realmsearchview.R.styleable;
import co.moonmonkeylabs.realmsearchview.RealmSearchAdapter;

/**
 * Created by Eric on 31-Aug-16.
 */
public class RealmSearchView extends LinearLayout {
    private RealmRecyclerView realmRecyclerView;
    private ClearableEditText searchBar;
    private RealmSearchAdapter adapter;
    private boolean addFooterOnIdle;
    private Handler handler = null;

    public RealmSearchView(Context context) {
        super(context);
        this.init(context, null);
    }

    public RealmSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs);
    }

    public RealmSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, layout.realm_search_view, this);
        this.setOrientation(VERTICAL);
        this.realmRecyclerView = (RealmRecyclerView)this.findViewById(id.realm_recycler_view);
        this.searchBar = (ClearableEditText)this.findViewById(id.search_bar);
        this.initAttrs(context, attrs);
        this.searchBar.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                RealmSearchView.this.addFooterHandler(s.toString());
                if (RealmSearchView.this.adapter == null) {
                    return;
                }
                RealmSearchView.this.adapter.filter(s.toString());
            }
        });
    }

    private void addFooterHandler(final String search) {
        if(this.addFooterOnIdle) {
            if(this.handler == null) {
                this.adapter.removeFooter();
                this.handler = new Handler();
                this.handler.postDelayed(new Runnable() {
                    public void run() {
                        if(search.equals(RealmSearchView.this.searchBar.getText().toString())) {
                            RealmSearchView.this.adapter.addFooter();
                        }

                        RealmSearchView.this.handler = null;
                    }
                }, 300L);
            }
        }
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, styleable.RealmSearchView);
        int hintTextResId = typedArray.getResourceId(styleable.RealmSearchView_rsvHint, string.rsv_default_search_hint);
        this.searchBar.setHint(hintTextResId);
        int clearDrawableResId = typedArray.getResourceId(styleable.RealmSearchView_rsvClearDrawable, -1);
        if(clearDrawableResId != -1) {
            this.searchBar.setClearDrawable(this.getResources().getDrawable(clearDrawableResId));
        }

        this.addFooterOnIdle = typedArray.getBoolean(styleable.RealmSearchView_rsvAddFooter, false);
        typedArray.recycle();
    }

    public void setAdapter(RealmSearchAdapter adapter) {
        this.adapter = adapter;
        this.realmRecyclerView.setAdapter(adapter);
        if (adapter != null){
            this.adapter.filter("");
        }
    }

    public String getSearchBarText() {
        return this.searchBar.getText().toString();
    }

    public void addSearchBarTextChangedListener(TextWatcher watcher) {
        this.searchBar.addTextChangedListener(watcher);
    }

    public void removeSearchBarTextChangedListener(TextWatcher watcher) {
        this.searchBar.removeTextChangedListener(watcher);
    }

    public void setOnEditorActionListener(OnEditorActionListener onEditorActionListener) {
        this.searchBar.setOnEditorActionListener(onEditorActionListener);
    }

    public ClearableEditText getSearchBar() {
        return searchBar;
    }

    public RealmRecyclerView getRealmRecyclerView() { return realmRecyclerView; }

    public void setSearchBar(ClearableEditText searchBar) {
        this.searchBar = searchBar;
    }
}
