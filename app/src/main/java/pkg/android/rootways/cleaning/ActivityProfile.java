package pkg.android.rootways.cleaning;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import helper.DatabaseConnectionAPI;
import parser.GetProfile;

/**
 * Created by sphere65 on 19/3/16.
 */
public class ActivityProfile extends AppCompatActivity {

    EditText mEditTextFnam;
    EditText mEditTextLnam;
    EditText mEditTextAddress;
    EditText mEditTextZip;
    EditText mEditTextPh;
    EditText mEditTextCity;
    EditText mEditTextSt;
    EditText mEditTextCont;
    TextView mTextViewSave;
    Toolbar mToolbar;
    ImageView mImageViewBack;
    TextView mTextViewHeaderTitle;
    DatabaseConnectionAPI mDatabaseConnectionAPI;
    ArrayList<GetProfile>mArrayListProfiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profiel);
        mArrayListProfiles=new ArrayList<>();
        mEditTextFnam = (EditText) findViewById(R.id.edt_fname);
        mEditTextLnam = (EditText) findViewById(R.id.edt_lname);
        mEditTextAddress = (EditText) findViewById(R.id.edt_address);
        mEditTextZip = (EditText) findViewById(R.id.edt_pincde);
        mEditTextPh = (EditText) findViewById(R.id.edt_ph);
        mEditTextCity = (EditText) findViewById(R.id.edt_city);
        mEditTextSt = (EditText) findViewById(R.id.edt_st);
        mEditTextCont = (EditText) findViewById(R.id.edt_coont);
        mTextViewSave = (TextView) findViewById(R.id.txt_submot);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mImageViewBack = (ImageView) findViewById(R.id.imgBack);
        mTextViewHeaderTitle = (TextView) mToolbar.findViewById(R.id.txt_header_titel);
        mImageViewBack.setVisibility(View.VISIBLE);
        mTextViewHeaderTitle.setVisibility(View.VISIBLE);
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mDatabaseConnectionAPI=new DatabaseConnectionAPI(getApplicationContext());
        mTextViewHeaderTitle.setText("MY PROFILE");
        mArrayListProfiles=mDatabaseConnectionAPI.getProfile();
        if (mArrayListProfiles.size()>0)
        {
            mEditTextFnam.setText(mArrayListProfiles.get(0).getFname());
            mEditTextLnam.setText(mArrayListProfiles.get(0).getLnam());
            mEditTextAddress.setText(mArrayListProfiles.get(0).getAddress());
            mEditTextCity.setText(mArrayListProfiles.get(0).getCity());
            mEditTextCont.setText(mArrayListProfiles.get(0).getCountry());
            mEditTextSt.setText(mArrayListProfiles.get(0).getState());
            mEditTextZip.setText(mArrayListProfiles.get(0).getPin());
            mEditTextPh.setText(mArrayListProfiles.get(0).getMobile());

        }

        mTextViewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate=mDatabaseConnectionAPI.updateProfile(1,mEditTextFnam.getText().toString(),mEditTextLnam.getText().toString(),
                        mEditTextAddress.getText().toString(),mEditTextPh.getText().toString(),
                        mEditTextCity.getText().toString(),mEditTextSt.getText().toString(),mEditTextCont.getText().toString(),mEditTextZip.getText().toString());
                if(isUpdate==true)
                {
                    Toast.makeText(ActivityProfile.this,"Profile updated successfully.",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(ActivityProfile.this,"Profile not updated successfully.",Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
