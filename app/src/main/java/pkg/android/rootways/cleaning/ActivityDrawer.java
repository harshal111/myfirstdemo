package pkg.android.rootways.cleaning;

/**
 * Created by NK on 11-09-2015.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Stack;

import helper.DatabaseConnectionAPI;
import parser.GetCompany;


public class ActivityDrawer extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerArrowDrawable drawerArrow;
    private Stack<Fragment> fragmentStack;
    private boolean drawerArrowColor;
    View mViewHeader;
    private View mHeaderView;
    //private View mToolbarView;
    //private ObservableScrollView mScrollView;

    TextView mTextViewHeaderName;
    TextView mTextViewHeaderEmail;
    public ArrayList<DrawerItems> mArrayList;
    ProgressDialog mProgressDialog;
    DrawerListAdapter mDrawerListAdapter;
    Activity mActivity;
    boolean draweropen = false;
    boolean pindone = false;
    LayoutInflater inflater;
    ViewGroup header;
    ViewGroup footer;
    RelativeLayout mRelativeLayoutDraqwerTopHeader;
    ImageView mImageViewMenu;
    Toolbar tb;


    RelativeLayout mRelativeLayoutCart;
    ViewPager pager;
    String mStringUName;
    String mStringPassword;
    String mStringEmail;
    String mStringFName;
    String mStringLName;

    RelativeLayout mRelativeLayoutALert;
    RelativeLayout mRelativeLayoutDrawer;
    TextView mTextViewDate;
    ListView mListView;
    DatabaseConnectionAPI mDatabaseConnectionAPI;
    ArrayList<GetCompany> mArrayListCompanies;
    int compid = 0;
    CompanyAdapter mCompanyAdapter;
    ImageView mImageViewNext;
    ImageView mImageViewPrev;
    TextView mTextViewUserName;
    TextView mTextViewProfileLink;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_drawer);
        tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        tb.setTitleTextColor(Color.WHITE);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.navigation);
        //ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4CAF50")));
        ab.setTitle("");
        ab.setDisplayHomeAsUpEnabled(true);
        mActivity = this;
        ab.setHomeButtonEnabled(true);
        mArrayListCompanies = new ArrayList<>();
        mRelativeLayoutDrawer = (RelativeLayout) findViewById(R.id.left_drawer);
        mDatabaseConnectionAPI = new DatabaseConnectionAPI(getApplicationContext());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navdrawer);
        inflater = getLayoutInflater();
        mImageViewPrev = (ImageView) findViewById(R.id.img_prev);
        mImageViewNext = (ImageView) findViewById(R.id.img_next);
        mRelativeLayoutALert = (RelativeLayout) tb.findViewById(R.id.relCart);
        mArrayList = new ArrayList<DrawerItems>();
        mTextViewUserName = (TextView) findViewById(R.id.txt_name);
        mTextViewProfileLink = (TextView) findViewById(R.id.txt_email);

        mTextViewDate = (TextView) findViewById(R.id.txt_dates);
        mListView = (ListView) findViewById(R.id.listitems);
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");
        String formattedDate = df.format(c.getTime());
        mTextViewDate.setText(formattedDate.toString());

        mArrayListCompanies = mDatabaseConnectionAPI.getComapny();
        if (mArrayListCompanies.size() > 0) {
            mCompanyAdapter = new CompanyAdapter(ActivityDrawer.this, mArrayListCompanies);
            mListView.setAdapter(mCompanyAdapter);
        }

        mTextViewProfileLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(mRelativeLayoutDrawer);
                Intent mIntent=new Intent(ActivityDrawer.this,ActivityProfile.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
            }
        });
        mImageViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mImageViewPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                compid = mArrayListCompanies.get(i).getCmid();
                Intent mIntent = new Intent(ActivityDrawer.this, ActivityCompanyWork.class);
                Bundle mBundle = new Bundle();
                mBundle.putInt("cid", compid);
                mIntent.putExtras(mBundle);

                startActivity(mIntent);
            }
        });
        mArrayList = new ArrayList<>();
        mArrayList.add(new DrawerItems("MY WORK SCHEDULE", R.drawable.ic_launcher, true, "-1"));
        mArrayList.add(new DrawerItems("VIEW COMPLETED TASK", R.drawable.ic_launcher, true, "-1"));
        mArrayList.add(new DrawerItems("VIEW PENDING TASK", R.drawable.ic_launcher, true, "-1"));
        mArrayList.add(new DrawerItems("SEND MESSAGE TO OFFICE", R.drawable.ic_launcher, true, "-1"));
        mArrayList.add(new DrawerItems("REQUESTS", R.drawable.ic_launcher, true, "-1"));

        mDrawerListAdapter = new DrawerListAdapter(ActivityDrawer.this, mArrayList);
        mDrawerList.setAdapter(mDrawerListAdapter);

        drawerArrow = new DrawerArrowDrawable(ActivityDrawer.this) {
            @Override
            public boolean isLayoutRtl() {
                return false;
            }
        };
        mDrawerToggle = new ActionBarDrawerToggle(ActivityDrawer.this, mDrawerLayout,
                drawerArrow, R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                draweropen = false;
                // mAllMethods.hideSoftKeyboard(DrawerActivity.this);
                // invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                draweropen = true;
                //mAllMethods.hideSoftKeyboard(DrawerActivity.this);
                // mDrawerListAdapter.loadData();
                //invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        if (savedInstanceState == null) {
            /*FragmentTransaction ft =  getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new HomeFragmentTest());
            ft.commit();*/
        }


        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

              /*  Intent mIntent = new Intent(ActivityDrawer.this, ActivityCategoryList.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);*/

                mDrawerLayout.closeDrawer(mRelativeLayoutDrawer);

                if (position == 0) {
                    Intent mIntent = new Intent(ActivityDrawer.this, ActivityDrawer.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mIntent);

                } else if (position == 1) {
                    Intent mIntent = new Intent(ActivityDrawer.this, ActivityCompleteTask.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mIntent);

                } else if (position == 2) {
                    Intent mIntent = new Intent(ActivityDrawer.this, ActivityPendingTask.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mIntent);
                } else if (position == 3) {
                    Intent mIntent = new Intent(ActivityDrawer.this, ActivityMessage.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mIntent);
                } else if (position == 4) {

                }
                /*if (position==1)
                {
                    FragmentTransaction ft =  getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new HomeFragment());
                    ft.commit();
                    mDrawerList.setItemChecked(position, true);
                    mDrawerLayout.closeDrawer(mDrawerList);
                }
                else  if (position==2)
                {
                    Intent mIntent=new Intent(ActivityDrawer.this,ActivityCategory.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mIntent);
                    mDrawerLayout.closeDrawer(mDrawerList);
                }*/
                //selectItem(position);
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            if (mDrawerLayout.isDrawerOpen(mRelativeLayoutDrawer)) {
                mDrawerLayout.closeDrawer(mRelativeLayoutDrawer);
            } else {
                mDrawerLayout.openDrawer(mRelativeLayoutDrawer);
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    private void selectItem(int position) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        // Locate Position
        switch (position) {


            default:

                /* ft.replace(R.id.content_frame, new HomeFragmentTest());
                ft.commit();*/
                break;

        }

        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);


    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public class CompanyAdapter extends BaseAdapter {

        ArrayList<GetCompany> list;
        Activity mActivity;

        public CompanyAdapter(Activity mActivity, ArrayList<GetCompany> list) {
            this.mActivity = mActivity;
            this.list = list;

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_dashboard_item, viewGroup, false);
            TextView mTextViewTitle = (TextView) view.findViewById(R.id.txt_title);
            TextView mTextViewTiming = (TextView) view.findViewById(R.id.txt_timeing);
            TextView mTextViewStatus = (TextView) view.findViewById(R.id.txt_status);

            mTextViewTitle.setText(list.get(i).getCname());
            mTextViewTiming.setText(list.get(i).getCarea());
            if (list.get(i).getCstatus().equalsIgnoreCase("p")) {
                mTextViewStatus.setText("Pending");
            } else if (list.get(i).getCstatus().equalsIgnoreCase("w")) {
                mTextViewStatus.setText("Working");
            } else {
                mTextViewStatus.setText("Completed");
            }

            return view;
        }
    }

    public class DrawerListAdapter extends BaseAdapter {

        Activity mActivity2;
        private String[] mtitle;
        private String[] msubTitle;
        private int[] micon;
        private LayoutInflater inflater;
        ArrayList<DrawerItems> list;
        int mode;
        AllMethods mAllMethods;
        Activity mActivity;

        public DrawerListAdapter(Activity applicationContext, ArrayList<DrawerItems> dataList) {

            list = dataList;
            mActivity = applicationContext;
            mAllMethods = new AllMethods(mActivity);
        }

        public void loadData() {


            notifyDataSetChanged();
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int arg0) {
            return arg0;
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(final int position, View arg1, ViewGroup parent) {

            TextView title;

//		TextView mTextViewRed;
            DrawerItems dItem = (DrawerItems) this.list.get(position);
            TextView count;

            inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            //		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.drawr_roow, parent, false);

            title = (TextView) itemView.findViewById(R.id.drawer_itemName);
            title.setText(dItem.getNmae());

            return itemView;

        }
    }
}