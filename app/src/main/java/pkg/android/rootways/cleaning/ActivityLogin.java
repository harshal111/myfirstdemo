package pkg.android.rootways.cleaning;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;

import helper.DatabaseConnectionAPI;

/**
 * Created by sphere65 on 16/3/16.
 */
public class ActivityLogin extends AppCompatActivity {

    DatabaseConnectionAPI mDatabaseConnectionAPI;
    TextView mTextViewLogin;
    Spinner mSpinnerLoginOption;
    String option[] = {"Owner", "Manager", "Employee"};
    LoginOPtionAdapter mLoginOPtionAdapter;
    EditText mEditTextUN;
    AllMethods methods;
    EditText mEditTextPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        methods = new AllMethods(ActivityLogin.this);
        mSpinnerLoginOption = (Spinner) findViewById(R.id.spn_option);
        mTextViewLogin = (TextView) findViewById(R.id.txt_login);
        mEditTextUN = (EditText) findViewById(R.id.edt_name);
        mEditTextPass = (EditText) findViewById(R.id.edt_password);
        ;

        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mEditTextUN.getText().toString().toString().equalsIgnoreCase("")) {
                    methods.ShowDialog(ActivityLogin.this, "", "Enter Username", "OK");
                } else if (mEditTextPass.getText().toString().toString().equalsIgnoreCase("")) {
                    methods.ShowDialog(ActivityLogin.this, "", "Enter Password", "OK");
                } else {
                    mEditTextPass.setText("");
                    mEditTextUN.setText("");
                    if (CleaningApplication.mStringUserMode.equalsIgnoreCase("Owner"))
                    {
                        Intent mIntent = new Intent(ActivityLogin.this, ActivityDrawer.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mIntent);
                    }
                    else if (CleaningApplication.mStringUserMode.equalsIgnoreCase("Manager"))
                    {
                        Intent mIntent = new Intent(ActivityLogin.this, ActivityDrawerManager.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mIntent);
                    }
                    else
                    {
                        Intent mIntent = new Intent(ActivityLogin.this, ActivityDrawer.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mIntent);
                    }

                }

            }
        });

        mDatabaseConnectionAPI = new DatabaseConnectionAPI(getApplicationContext());
        try {
            mDatabaseConnectionAPI.createDataBase();
            mDatabaseConnectionAPI.openDataBase();

        } catch (IOException e) {
            e.printStackTrace();
        }
        mLoginOPtionAdapter = new LoginOPtionAdapter(ActivityLogin.this, R.layout.spin_layout, option);
        mSpinnerLoginOption.setAdapter(mLoginOPtionAdapter);
        mSpinnerLoginOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CleaningApplication.mStringUserMode = option[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public class LoginOPtionAdapter extends ArrayAdapter<String> {

        public LoginOPtionAdapter(Context context, int textViewResourceId,
                                  String[] objects) {
            super(context, textViewResourceId, objects);
            // TODO Auto-generated constructor stub
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView,
                                  ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.spin_layout, parent, false);
            TextView label = (TextView) row.findViewById(android.R.id.text1);
            label.setText(option[position]);


            return row;
        }

    }
}
