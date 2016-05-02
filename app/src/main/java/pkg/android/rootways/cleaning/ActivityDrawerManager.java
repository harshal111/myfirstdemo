package pkg.android.rootways.cleaning;

/**
 * Created by NK on 11-09-2015.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Stack;

import helper.DatabaseConnectionAPI;
import parser.GetCompany;


public class ActivityDrawerManager extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerArrowDrawable drawerArrow;
    private Stack<Fragment> fragmentStack;
    private boolean drawerArrowColor;
    View mViewHeader;
    private int pYear;
    private int pMonth;
    private int pDay;

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

    int month = 0;
    int year = 0;
    String mStringOptionMOnth = "";
    String StartDate = "";
    static final int DATE_DIALOG_ID = 0;
    AllMethods methods;
    RelativeLayout mRelativeLayoutNoitification;

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
        mRelativeLayoutNoitification = (RelativeLayout) tb.findViewById(R.id.relCart);
        methods = new AllMethods(ActivityDrawerManager.this);
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
        final Calendar cal = Calendar.getInstance();
        pYear = cal.get(Calendar.YEAR);
        pMonth = cal.get(Calendar.MONTH);
        pDay = cal.get(Calendar.DAY_OF_MONTH);
/*
        mRelativeLayoutNoitification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // clearing app data
                    Runtime runtime = Runtime.getRuntime();
                    runtime.exec("pm clear pkg.android.rootways.cleaning");

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });*/
        mTextViewDate = (TextView) findViewById(R.id.txt_dates);
        mListView = (ListView) findViewById(R.id.listitems);
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");
        String formattedDate = df.format(c.getTime());
        mTextViewDate.setText(formattedDate.toString());

        mArrayListCompanies = mDatabaseConnectionAPI.getComapny();
        if (mArrayListCompanies.size() > 0) {
            mCompanyAdapter = new CompanyAdapter(ActivityDrawerManager.this, mArrayListCompanies);
            mListView.setAdapter(mCompanyAdapter);
        }

        mTextViewProfileLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(mRelativeLayoutDrawer);
                Intent mIntent = new Intent(ActivityDrawerManager.this, ActivityProfile.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
            }
        });
        mImageViewPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        mImageViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        mTextViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });
/*
        mImageViewNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //if (mStringCurrentState.equalsIgnoreCase("Month")) {
                    String StartDate = "";
                    String mStringOptionMOnth = "";
                     month++;
                    System.out.println("Year " + year + "MOntrh " + month);
                    System.out.println("month " + month);
                    if (month == 13) {
                        month = 1;
                        mStringOptionMOnth = "January";
                        year++;
                        StartDate = String.valueOf(year) + "-0"
                                + String.valueOf(month);
                        mTextViewDate.setText(mStringOptionMOnth + " "
                                + year);
                    }
                    else {
                        if (month == 10 | month == 11 | month == 12) {
                            if (month == 10) {
                                mStringOptionMOnth = "October";
                            } else if (month == 11) {
                                mStringOptionMOnth = "Novermber";

                            } else if (month == 12) {
                                mStringOptionMOnth = "December";

                            }
                            StartDate = String.valueOf(year) + "-"
                                    + String.valueOf(month);
                            System.out.println("Start DAte " + StartDate);

                        } else {
                            if (month == 1) {
                                mStringOptionMOnth = "January";
                            } else if (month == 2) {
                                mStringOptionMOnth = "February";

                            } else if (month == 3) {
                                mStringOptionMOnth = "March";

                            } else if (month == 4) {
                                mStringOptionMOnth = "April";

                            } else if (month == 5) {
                                mStringOptionMOnth = "May";

                            } else if (month == 6) {
                                mStringOptionMOnth = "June";

                            } else if (month == 7) {
                                mStringOptionMOnth = "July";

                            } else if (month == 8) {
                                mStringOptionMOnth = "August";

                            } else if (month == 9) {
                                mStringOptionMOnth = "September";

                            }
                            StartDate = String.valueOf(year) + "-0"
                                    + String.valueOf(month);
                            System.out.println("Start DAte " + StartDate);
                        }

                        mTextViewDate.setText(mStringOptionMOnth + " "
                                + year);


                     }
               // }


            }
        });
        mImageViewPrev.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                    String StartDate = "";
                    String mStringOptionMOnth = "";


                    month--;
                    System.out.println("month " + month);
                    if (month == 0) {
                        month = 12;
                        mStringOptionMOnth = "December";
                        year--;
                        StartDate = String.valueOf(year) + "-"
                                + String.valueOf(month);
                        mTextViewDate.setText(mStringOptionMOnth + " "
                                + year);
                     }
                    else
                    {
                        if (month == 10 | month == 11 | month == 12) {
                            if (month == 10) {
                                mStringOptionMOnth = "October";
                            } else if (month == 11) {
                                mStringOptionMOnth = "Novermber";

                            } else if (month == 12) {
                                mStringOptionMOnth = "December";

                            }
                            StartDate = String.valueOf(year) + "-"
                                    + String.valueOf(month);
                            System.out.println("Start DAte " + StartDate);
                        } else {
                            if (month == 1) {
                                mStringOptionMOnth = "January";
                            } else if (month == 2) {
                                mStringOptionMOnth = "February";

                            } else if (month == 3) {
                                mStringOptionMOnth = "March";

                            } else if (month == 4) {
                                mStringOptionMOnth = "April";

                            } else if (month == 5) {
                                mStringOptionMOnth = "May";

                            } else if (month == 6) {
                                mStringOptionMOnth = "June";

                            } else if (month == 7) {
                                mStringOptionMOnth = "July";

                            } else if (month == 8) {
                                mStringOptionMOnth = "August";

                            } else if (month == 9) {
                                mStringOptionMOnth = "September";

                            }
                            StartDate = String.valueOf(year) + "-0"
                                    + String.valueOf(month);
                            System.out.println("Start DAte " + StartDate);
                        }

                        mTextViewDate.setText(mStringOptionMOnth + " "
                                + year);

                    }
            }
        });
*/


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                compid = mArrayListCompanies.get(i).getCmid();
                Intent mIntent = new Intent(ActivityDrawerManager.this, ActivityCompanyWork.class);
                Bundle mBundle = new Bundle();
                mBundle.putInt("cid", compid);
                mIntent.putExtras(mBundle);

                startActivity(mIntent);
            }
        });
        mArrayList = new ArrayList<>();
        mArrayList.add(new DrawerItems("DASHBOARD", R.drawable.ic_launcher, true, "-1"));
        mArrayList.add(new DrawerItems("TASK", R.drawable.ic_launcher, true, "-1"));
        mArrayList.add(new DrawerItems("ADD NEW TASK", R.drawable.ic_launcher, true, "-1"));
        mArrayList.add(new DrawerItems("EMPLOYEE", R.drawable.ic_launcher, true, "-1"));
        // mArrayList.add(new DrawerItems("REPORT", R.drawable.ic_launcher, true, "-1"));

        mDrawerListAdapter = new DrawerListAdapter(ActivityDrawerManager.this, mArrayList);
        mDrawerList.setAdapter(mDrawerListAdapter);

        drawerArrow = new DrawerArrowDrawable(ActivityDrawerManager.this) {
            @Override
            public boolean isLayoutRtl() {
                return false;
            }
        };
        mDrawerToggle = new ActionBarDrawerToggle(ActivityDrawerManager.this, mDrawerLayout,
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
                    Intent mIntent = new Intent(ActivityDrawerManager.this, ActivityDrawerManager.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mIntent);

                } else if (position == 1) {
                    Intent mIntent = new Intent(ActivityDrawerManager.this, ActivityAssignTaskByManager.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mIntent);

                } else if (position == 2) {
                    Intent mIntent = new Intent(ActivityDrawerManager.this, ActivityAddNewTask.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mIntent);
                } else if (position == 3) {
                    Intent mIntent = new Intent(ActivityDrawerManager.this, ActivityMessage.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mIntent);
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
                int imgResource = R.drawable.pending;
                mTextViewStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, imgResource, 0);

            } else if (list.get(i).getCstatus().equalsIgnoreCase("w")) {
                mTextViewStatus.setText("Working");
                int imgResource = R.drawable.working;
                mTextViewStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, imgResource, 0);

            } else {
                mTextViewStatus.setText("Completed");
                int imgResource = R.drawable.complited;
                mTextViewStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, imgResource, 0);

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

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        pDateSetListener,
                        pYear, pMonth, pDay);

        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    pYear = year;
                    pMonth = monthOfYear;
                    pDay = dayOfMonth;
                    try {
                        updateDisplay();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            };


    private void updateDisplay() throws ParseException {

        /*if ((pMonth + 1) == 9 | (pMonth + 1) == 10 | (pMonth + 1) == 11) {
            if (pDay == 1 | pDay == 2 | pDay == 3 | pDay == 4 | pDay == 5 | pDay == 6 | pDay == 7 | pDay == 8 | pDay == 9) {
                StartDate = String.valueOf(new StringBuilder()
                        .append("0")
                        .append(String.valueOf(pDay)).append("-").append(String.valueOf((pMonth + 1))).append("-")
                        .append(String.valueOf(pYear)));
            } else {
                StartDate = String.valueOf(new StringBuilder()
                        .append(String.valueOf(pDay)).append("-").append(String.valueOf((pMonth + 1))).append("-")
                        .append(String.valueOf(pYear)));
            }

        } else {
            if (pDay == 1 | pDay == 2 | pDay == 3 | pDay == 4 | pDay == 5 | pDay == 6 | pDay == 7 | pDay == 8 | pDay == 9) {
                StartDate = String.valueOf(new StringBuilder()
                        .append("0")
                        .append(String.valueOf(pDay)).append("-").append("0").append(String.valueOf((pMonth + 1))).append("-")
                        .append(String.valueOf(pYear)));
            } else {
                StartDate = String.valueOf(new StringBuilder()
                        .append(String.valueOf(pDay)).append("-").append("0").append(String.valueOf((pMonth + 1))).append("-")
                        .append(String.valueOf(pYear)));
            }

        }*/

        if ((pMonth + 1) == 9 | (pMonth + 1) == 10 | (pMonth + 1) == 11) {
            if (pDay == 1 | pDay == 2 | pDay == 3 | pDay == 4 | pDay == 5 | pDay == 6 | pDay == 7 | pDay == 8 | pDay == 9) {
                StartDate = String.valueOf(new StringBuilder()
                        .append("0")
                        .append(String.valueOf(pDay)).append("-").append(String.valueOf((pMonth + 1))).append("-")
                        .append(String.valueOf(pYear)));
            } else {
                StartDate = String.valueOf(new StringBuilder()
                        .append(String.valueOf(pDay)).append("-").append(String.valueOf((pMonth + 1))).append("-")
                        .append(String.valueOf(pYear)));
            }

        } else {
            if (pDay == 1 | pDay == 2 | pDay == 3 | pDay == 4 | pDay == 5 | pDay == 6 | pDay == 7 | pDay == 8 | pDay == 9) {
                StartDate = String.valueOf(new StringBuilder()
                        .append("0")
                        .append(String.valueOf(pDay)).append("-").append("0").append(String.valueOf((pMonth + 1))).append("-")
                        .append(String.valueOf(pYear)));
            } else {
                StartDate = String.valueOf(new StringBuilder()
                        .append(String.valueOf(pDay)).append("-").append("0").append(String.valueOf((pMonth + 1))).append("-")
                        .append(String.valueOf(pYear)));
            }

        }

        Log.d("StartDate ", StartDate);

        mTextViewDate.setText(methods.DateForamte(StartDate));
    }


}