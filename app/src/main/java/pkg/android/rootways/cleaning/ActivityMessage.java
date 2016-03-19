package pkg.android.rootways.cleaning;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import helper.DatabaseConnectionAPI;
import parser.GetCompany;

/**
 * Created by sphere65 on 18/3/16.
 */
public class ActivityMessage extends AppCompatActivity {

    EditText mEditTextCName;
    EditText mEditTextMEssage;
    TextView mTextViewSubmit;
    AllMethods mAllMethods;
    ArrayList<GetCompany> mArrayListCompanies;
    DatabaseConnectionAPI mDatabaseConnectionAPI;
    Toolbar mToolbar;
    ImageView imageViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mesage);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        imageViewBack = (ImageView) mToolbar.findViewById(R.id.imgBack);
        mEditTextCName = (EditText) findViewById(R.id.edt_copn_name);
        mEditTextMEssage = (EditText) findViewById(R.id.edt_mesage);
        mTextViewSubmit = (TextView) findViewById(R.id.txt_submot);
        mAllMethods = new AllMethods(ActivityMessage.this);
        mDatabaseConnectionAPI = new DatabaseConnectionAPI(ActivityMessage.this);
        mArrayListCompanies = new ArrayList<>();
        imageViewBack.setVisibility(View.VISIBLE);
        mArrayListCompanies = mDatabaseConnectionAPI.getComapnyName();
        mEditTextCName.setText(mArrayListCompanies.get(0).getCname());
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTextViewSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEditTextMEssage.getText().toString().equalsIgnoreCase("")) {
                    mAllMethods.ShowDialog(ActivityMessage.this, "", "Enter message.", "OK");
                } else {
                    ContentValues mContentValues = new ContentValues();
                    mContentValues.put("message", mEditTextMEssage.getText().toString());
                    mContentValues.put("emp_id", "1");
                    mContentValues.put("cmid", "1");
                    int id = mDatabaseConnectionAPI.Insert("message_master", mContentValues);
                    if (id > 0) {
                        mEditTextMEssage.setText("");
                        mAllMethods.Snackbarview("Message inserted successfully.");
                    } else {
                        mAllMethods.Snackbarview("Message not inserted successfully.");

                    }
                }
            }
        });
    }
}
