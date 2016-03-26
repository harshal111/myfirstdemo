package pkg.android.rootways.cleaning;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import helper.DatabaseConnectionAPI;
import parser.GetCompletePendingTask;

/**
 * Created by sphere65 on 26/3/16.
 */
public class ActivityTaskDetail extends AppCompatActivity {

    TextView mTextViewMSTime;
    TextView mTextViewMETime;
    TextView mTextViewESTime;
    TextView mTextViewEETime;

    TextView mTextViewmDate;
    TextView mTextViewEDate;
    TextView mTextViewLocation;
    DatabaseConnectionAPI mDatabaseConnectionAPI;
    ArrayList<GetCompletePendingTask> mTasks;
    int subid = 0;
    TextView mTextViewHeader;
    Toolbar mToolbar;
    ImageView imageView;
    TextView mTextViewSubloc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_task_detail);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTextViewSubloc = (TextView) findViewById(R.id.txt_sname);
        mTextViewHeader = (TextView) mToolbar.findViewById(R.id.txt_header_titel);
        mTextViewHeader.setVisibility(View.VISIBLE);
        mTextViewHeader.setText("TASK DETAIL");
        imageView = (ImageView) mToolbar.findViewById(R.id.imgBack);
        imageView.setVisibility(View.VISIBLE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTextViewMSTime = (TextView) findViewById(R.id.txt_msdate_value);
        mTextViewMETime = (TextView) findViewById(R.id.txt_medate_value);
        mTextViewESTime = (TextView) findViewById(R.id.txt_emsdate_value);
        mTextViewEETime = (TextView) findViewById(R.id.txt_emedate_value);
        mTextViewLocation = (TextView) findViewById(R.id.txt_name);
        mTextViewmDate = (TextView) findViewById(R.id.txt_massign_date_value);
        mTextViewEDate = (TextView) findViewById(R.id.txt_assign_emp_date);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            subid = extras.getInt("uid");
        }

        mDatabaseConnectionAPI = new DatabaseConnectionAPI(getApplicationContext());
        mTasks = new ArrayList<>();
        mTasks = mDatabaseConnectionAPI.getTaskDetail(subid);
        mTextViewLocation.setText(mTasks.get(0).getCname());
        mTextViewSubloc.setText(mTasks.get(0).getLocation() + " " + mTasks.get(0).getSubcat());
        mTextViewMSTime.setText(mTasks.get(0).getMstime());
        mTextViewmDate.setText(mTasks.get(0).getMsdate());
        mTextViewMETime.setText(mTasks.get(0).getMetime());

        mTextViewEDate.setText(mTasks.get(0).getEdate());
        mTextViewEETime.setText(mTasks.get(0).getEetime());
        mTextViewESTime.setText(mTasks.get(0).getEstime());


    }
}
