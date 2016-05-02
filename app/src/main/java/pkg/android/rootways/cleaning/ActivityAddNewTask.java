package pkg.android.rootways.cleaning;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import helper.DatabaseConnectionAPI;
import parser.GetAreaActivity;
import parser.GetAreaCode;
import parser.GetCompany;
import parser.GetEmployee;

/**
 * Created by sphere65 on 2/5/16.
 */
public class ActivityAddNewTask extends AppCompatActivity {


    Toolbar mToolbar;
    TextView mTextViewHeaderTitel;
    TextView mTextViewHeaderRight;
    ImageView mImageViewBack;
    RelativeLayout mRelativeLayoutCart;
    ArrayList<GetCompany> mGetCompanies;
    DatabaseConnectionAPI mDatabaseConnectionAPI;
    AllMethods mAllMethods;
    CompanyAdapter mCompanyAdapter;
    Spinner mSpinnerCompany;
    int cid = 1;

    String mStringCName;
    ArrayList<GetEmployee> mGetEmployees;
    int eid;
    Spinner mSpinnerEmployee;
    EmplyeeAdapter mEmplyeeAdapter;


    ArrayList<GetAreaCode> mGetAreaCodes;
    Spinner mSpinnerAreaCode;
    String mStringCode = "";
    CodeAdapter mCodeAdapter;


    ArrayList<GetAreaActivity> mGetAreaActivities;
    Spinner mSpinnerActivity;
    String mStringActivty = "";
    AreaActivityAdapter mAreaActivityAdapter;

    Spinner mSpinnerStatus;
    Spinner mSpinnerPraority;
    Spinner mSpinnerFreq;


    String mStringGetStatus = "";
    String mStringGetPriority = "";
    String mStringGetFreq = "";
    String mStringstatus[] = {"Select Status", "Pending", "Completed", "Working on"};
    String mStringPraority[] = {"Select Priority", "Low", "Normal", "High"};
    String mStringFreq[] = {"Select Frequncy", "1 week", "2 week", "3 week"};

    StatusAdapter mStatusAdapter;
    ProirityAdapter mProirityAdapter;
    FrequncyAdapter mFrequncyAdapter;

    EditText mEditTextStime;
    EditText mEditTextEtime;
    static final int DATE_DIALOG_ID = 0;
    static final int EDATE_DIALOG_ID = 1;
    EditText mEditTextTimeAllocate;
    EditText mEditTextNote;
    String mStringGetArea="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_new_task);

        mEditTextNote=(EditText)findViewById(R.id.edt_note);
        mEditTextEtime=(EditText)findViewById(R.id.edt_etime);
        mEditTextStime=(EditText)findViewById(R.id.edt_stime);
        mEditTextTimeAllocate=(EditText)findViewById(R.id.edt_time_allocate);
        mSpinnerStatus = (Spinner) findViewById(R.id.spn_status);
        mSpinnerPraority = (Spinner) findViewById(R.id.spn_priority);
        mSpinnerFreq = (Spinner) findViewById(R.id.spn_freq);

        mStatusAdapter = new StatusAdapter(mStringstatus);
        mProirityAdapter = new ProirityAdapter(mStringPraority);
        mFrequncyAdapter = new FrequncyAdapter(mStringFreq);

        mSpinnerStatus.setAdapter(mStatusAdapter);
        mSpinnerPraority.setAdapter(mProirityAdapter);
        mSpinnerFreq.setAdapter(mFrequncyAdapter);

        mGetAreaCodes = new ArrayList<>();
        mSpinnerAreaCode = (Spinner) findViewById(R.id.spn_area_code);

        mGetAreaActivities = new ArrayList<>();
        mSpinnerActivity = (Spinner) findViewById(R.id.spn_activty);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mSpinnerCompany = (Spinner) findViewById(R.id.spn_company);
        mSpinnerEmployee = (Spinner) findViewById(R.id.spn_emp);
        mGetEmployees = new ArrayList<>();

        mGetCompanies = new ArrayList<>();
        mDatabaseConnectionAPI = new DatabaseConnectionAPI(getApplicationContext());
        mAllMethods = new AllMethods(ActivityAddNewTask.this);
        mGetCompanies = mDatabaseConnectionAPI.getCompanyDropDown(cid);


        mGetAreaCodes = mDatabaseConnectionAPI.getAreaCodeDropDown(cid);
        mGetAreaCodes = clearListFromUtiDuplicate(mGetAreaCodes);
        mCodeAdapter = new CodeAdapter(mGetAreaCodes);
        mSpinnerAreaCode.setAdapter(mCodeAdapter);

        mGetAreaActivities = mDatabaseConnectionAPI.getAreaActivityDropDown(cid);
        mAreaActivityAdapter = new AreaActivityAdapter(mGetAreaActivities);
        mSpinnerActivity.setAdapter(mAreaActivityAdapter);

        mTextViewHeaderTitel = (TextView) findViewById(R.id.txt_header_titel);
        mTextViewHeaderRight = (TextView) findViewById(R.id.txt_header_right);
        mImageViewBack = (ImageView) findViewById(R.id.imgBack);
        mRelativeLayoutCart = (RelativeLayout) findViewById(R.id.relCart);
        mRelativeLayoutCart.setVisibility(View.GONE);
        mImageViewBack.setVisibility(View.VISIBLE);

        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mTextViewHeaderRight.setVisibility(View.VISIBLE);
        mTextViewHeaderTitel.setVisibility(View.VISIBLE);
        mTextViewHeaderRight.setText("SAVE");
        mTextViewHeaderTitel.setText("ADD TASK");
        mCompanyAdapter = new CompanyAdapter(mGetCompanies);
        mSpinnerCompany.setAdapter(mCompanyAdapter);

        mGetEmployees = mDatabaseConnectionAPI.getEmployeeDropDown(cid);
        mEmplyeeAdapter = new EmplyeeAdapter(mGetEmployees);
        mSpinnerEmployee.setAdapter(mEmplyeeAdapter);

        mEditTextStime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (MotionEvent.ACTION_UP == event.getAction()) {

                } else if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(ActivityAddNewTask.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            String h = "";
                            String min = "";
                            if (selectedHour == 0 | selectedHour == 1 | selectedHour == 2 | selectedHour == 3 | selectedHour == 4 | selectedHour == 5 | selectedHour == 6 | selectedHour == 7 | selectedHour == 8 | selectedHour == 9) {
                                h = String.valueOf("0" + selectedHour);
                            } else {
                                h = String.valueOf(selectedHour);
                            }
                            if (selectedMinute == 0) {
                                min = String.valueOf("00");
                            } else if (selectedMinute == 1 | selectedMinute == 2 | selectedMinute == 3 | selectedMinute == 4 | selectedMinute == 5 | selectedMinute == 6 | selectedMinute == 7 | selectedMinute == 8 | selectedMinute == 9) {
                                min = String.valueOf("0" + selectedMinute);

                            } else {
                                min = String.valueOf(selectedMinute);
                            }


                            mEditTextStime.setText(h + ":" + min);
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Start Time");
                    mTimePicker.show();
                }
                return true;
            }
        });

        mEditTextEtime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (MotionEvent.ACTION_UP == event.getAction()) {

                } else if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(ActivityAddNewTask.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            String h = "";
                            String min = "";
                            if (selectedHour == 0 | selectedHour == 1 | selectedHour == 2 | selectedHour == 3 | selectedHour == 4 | selectedHour == 5 | selectedHour == 6 | selectedHour == 7 | selectedHour == 8 | selectedHour == 9) {
                                h = String.valueOf("0" + selectedHour);
                            } else {
                                h = String.valueOf(selectedHour);
                            }
                            Log.d("Minis", String.valueOf(selectedMinute));
                            if (selectedMinute == 0) {
                                min = String.valueOf("00");
                            } else if (selectedMinute == 1 | selectedMinute == 2 | selectedMinute == 3 | selectedMinute == 4 | selectedMinute == 5 | selectedMinute == 6 | selectedMinute == 7 | selectedMinute == 8 | selectedMinute == 9) {
                                min = String.valueOf("0" + selectedMinute);

                            } else {
                                min = String.valueOf(selectedMinute);
                            }

                            mEditTextEtime.setText(h + ":" + min);

                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select End Time");
                    mTimePicker.show();
                }
                return true;
            }
        });


        mSpinnerCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cid = mGetCompanies.get(i).getCmid();
                mStringCName=mGetCompanies.get(i).getCname();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mSpinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mStringGetStatus = mStringstatus[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mSpinnerFreq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mStringGetFreq = mStringFreq[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mSpinnerPraority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mStringGetPriority = mStringPraority[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mSpinnerEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                eid = mGetEmployees.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mSpinnerActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mStringActivty = mGetAreaActivities.get(i).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mSpinnerAreaCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mStringCode = mGetAreaCodes.get(i).getName();
                mStringGetArea=mGetAreaCodes.get(i).getArea();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mTextViewHeaderRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cid==0)
                {
                    mAllMethods.ShowDialog(ActivityAddNewTask.this,"","Select Company.","OK");
                }
                else if (mStringCode.equalsIgnoreCase("Select Area Code"))
                {
                    mAllMethods.ShowDialog(ActivityAddNewTask.this,"","Select Area Code.","OK");
                }
                else if (mStringActivty.equalsIgnoreCase("Select Activity"))
                {
                    mAllMethods.ShowDialog(ActivityAddNewTask.this,"","Select Activity.","OK");
                }
                else if (eid==0)
                {
                    mAllMethods.ShowDialog(ActivityAddNewTask.this,"","Select Employee.","OK");
                }
                else if(mEditTextTimeAllocate.getText().toString().equalsIgnoreCase(""))
                {
                    mAllMethods.ShowDialog(ActivityAddNewTask.this,"","Enter Time allocation.","OK");
                }
                else if(mEditTextStime.getText().toString().equalsIgnoreCase(""))
                {
                    mAllMethods.ShowDialog(ActivityAddNewTask.this,"","Enter Start Time.","OK");
                }
                else if(mEditTextEtime.getText().toString().equalsIgnoreCase(""))
                {
                    mAllMethods.ShowDialog(ActivityAddNewTask.this,"","Enter End Time.","OK");
                }
                else if(mStringGetStatus.equalsIgnoreCase("Select Status"))
                {
                    mAllMethods.ShowDialog(ActivityAddNewTask.this,"","Select Status.","OK");
                }
                else if(mStringGetPriority.equalsIgnoreCase("Select Priority"))
                {
                    mAllMethods.ShowDialog(ActivityAddNewTask.this,"","Select Priority.","OK");
                }
                else if(mStringGetFreq.equalsIgnoreCase("Select Frequncy"))
                {
                    mAllMethods.ShowDialog(ActivityAddNewTask.this,"","Select Frequncy.","OK");
                }
                else
                {
                    ContentValues mContentValues=new ContentValues();
                    mContentValues.put("cmid",String.valueOf(cid));
                    mContentValues.put("area_code",mStringCode);
                    mContentValues.put("activity",mStringActivty);
                    mContentValues.put("time_allocate",mEditTextTimeAllocate.getText().toString());
                    mContentValues.put("start_time",mEditTextStime.getText().toString());
                    mContentValues.put("end_time",mEditTextEtime.getText().toString());
                    mContentValues.put("status",mStringGetStatus);
                    mContentValues.put("priority",mStringGetPriority);
                    mContentValues.put("frequency",mStringGetFreq);
                    mContentValues.put("area",mStringGetArea);
                    mContentValues.put("emp_id",String.valueOf(eid));
                    mContentValues.put("notes",mEditTextNote.getText().toString());
                    int id=mDatabaseConnectionAPI.Insert("job_master", mContentValues);
                    if (id>0)
                    {
                        Toast.makeText(ActivityAddNewTask.this,"Task added successfully.",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(ActivityAddNewTask.this,"Task not added successfully.",Toast.LENGTH_LONG).show();
                    }


                }
            }
        });

    }

    public ArrayList<GetAreaCode> clearListFromUtiDuplicate(ArrayList<GetAreaCode> list1) {

        Map<String, GetAreaCode> cleanMap = new LinkedHashMap<String, GetAreaCode>();
        for (int i = 0; i < list1.size(); i++) {
            cleanMap.put(String.valueOf(list1.get(i).getName()), list1.get(i));
        }
        ArrayList<GetAreaCode> list = new ArrayList<GetAreaCode>(cleanMap.values());
        return list;
    }


    public class CompanyAdapter extends ArrayAdapter<GetCompany> {

        Context mContext;
        int layoutResourceId;
        ArrayList<GetCompany> list;

        public CompanyAdapter(ArrayList<GetCompany> list) {
            super(ActivityAddNewTask.this, R.layout.spin_layout, list);
            this.list = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) { // Ordinary
            // view
            // in
            // Spinner,
            // we
            // use
            // android.R.layout.simple_spinner_item

            return initView(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) { // This view starts when we click the
            // spinner.
            return initView(position, convertView, parent);
        }

        public View initView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflator = getLayoutInflater();
            convertView = inflator.inflate(R.layout.spin_layout, null);
            TextView mTextView = (TextView) convertView
                    .findViewById(android.R.id.text1);
            mTextView.setText(list.get(position).getCname());
            return convertView;
        }

    }

    public class StatusAdapter extends ArrayAdapter<String> {

        Context mContext;
        int layoutResourceId;
        String[] list;

        public StatusAdapter(String[] list) {
            super(ActivityAddNewTask.this, R.layout.spin_layout, list);
            this.list = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) { // Ordinary
            // view
            // in
            // Spinner,
            // we
            // use
            // android.R.layout.simple_spinner_item

            return initView(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) { // This view starts when we click the
            // spinner.
            return initView(position, convertView, parent);
        }

        public View initView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflator = getLayoutInflater();
            convertView = inflator.inflate(R.layout.spin_layout, null);
            TextView mTextView = (TextView) convertView
                    .findViewById(android.R.id.text1);
            mTextView.setText(list[position]);
            return convertView;
        }

    }

    public class FrequncyAdapter extends ArrayAdapter<String> {

        Context mContext;
        int layoutResourceId;
        String[] list;

        public FrequncyAdapter(String[] list) {
            super(ActivityAddNewTask.this, R.layout.spin_layout, list);
            this.list = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) { // Ordinary
            // view
            // in
            // Spinner,
            // we
            // use
            // android.R.layout.simple_spinner_item

            return initView(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) { // This view starts when we click the
            // spinner.
            return initView(position, convertView, parent);
        }

        public View initView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflator = getLayoutInflater();
            convertView = inflator.inflate(R.layout.spin_layout, null);
            TextView mTextView = (TextView) convertView
                    .findViewById(android.R.id.text1);
            mTextView.setText(list[position]);
            return convertView;
        }

    }

    public class ProirityAdapter extends ArrayAdapter<String> {

        Context mContext;
        int layoutResourceId;
        String[] list;

        public ProirityAdapter(String[] list) {
            super(ActivityAddNewTask.this, R.layout.spin_layout, list);
            this.list = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) { // Ordinary
            // view
            // in
            // Spinner,
            // we
            // use
            // android.R.layout.simple_spinner_item

            return initView(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) { // This view starts when we click the
            // spinner.
            return initView(position, convertView, parent);
        }

        public View initView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflator = getLayoutInflater();
            convertView = inflator.inflate(R.layout.spin_layout, null);
            TextView mTextView = (TextView) convertView
                    .findViewById(android.R.id.text1);
            mTextView.setText(list[position]);
            return convertView;
        }

    }

    public class CodeAdapter extends ArrayAdapter<GetAreaCode> {

        Context mContext;
        int layoutResourceId;
        ArrayList<GetAreaCode> list;

        public CodeAdapter(ArrayList<GetAreaCode> list) {
            super(ActivityAddNewTask.this, R.layout.spin_layout, list);
            this.list = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) { // Ordinary
            // view
            // in
            // Spinner,
            // we
            // use
            // android.R.layout.simple_spinner_item

            return initView(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) { // This view starts when we click the
            // spinner.
            return initView(position, convertView, parent);
        }

        public View initView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflator = getLayoutInflater();
            convertView = inflator.inflate(R.layout.spin_layout, null);
            TextView mTextView = (TextView) convertView
                    .findViewById(android.R.id.text1);
            mTextView.setText(list.get(position).getName());
            return convertView;
        }

    }


    public class AreaActivityAdapter extends ArrayAdapter<GetAreaActivity> {

        Context mContext;
        int layoutResourceId;
        ArrayList<GetAreaActivity> list;

        public AreaActivityAdapter(ArrayList<GetAreaActivity> list) {
            super(ActivityAddNewTask.this, R.layout.spin_layout, list);
            this.list = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) { // Ordinary
            // view
            // in
            // Spinner,
            // we
            // use
            // android.R.layout.simple_spinner_item

            return initView(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) { // This view starts when we click the
            // spinner.
            return initView(position, convertView, parent);
        }

        public View initView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflator = getLayoutInflater();
            convertView = inflator.inflate(R.layout.spin_layout, null);
            TextView mTextView = (TextView) convertView
                    .findViewById(android.R.id.text1);
            mTextView.setText(list.get(position).getName());
            return convertView;
        }

    }


    public class EmplyeeAdapter extends ArrayAdapter<GetEmployee> {

        Context mContext;
        int layoutResourceId;
        ArrayList<GetEmployee> list;

        public EmplyeeAdapter(ArrayList<GetEmployee> list) {
            super(ActivityAddNewTask.this, R.layout.spin_layout, list);
            this.list = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) { // Ordinary
            // view
            // in
            // Spinner,
            // we
            // use
            // android.R.layout.simple_spinner_item

            return initView(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) { // This view starts when we click the
            // spinner.
            return initView(position, convertView, parent);
        }

        public View initView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflator = getLayoutInflater();
            convertView = inflator.inflate(R.layout.spin_layout, null);
            TextView mTextView = (TextView) convertView
                    .findViewById(android.R.id.text1);
            mTextView.setText(list.get(position).getName());
            return convertView;
        }

    }




}
