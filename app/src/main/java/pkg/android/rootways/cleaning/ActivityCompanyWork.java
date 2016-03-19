package pkg.android.rootways.cleaning;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import helper.DatabaseConnectionAPI;
import parser.GetCompany;
import parser.GetLocation;

/**
 * Created by sphere65 on 17/3/16.
 */
public class ActivityCompanyWork extends AppCompatActivity {

    Toolbar mToolbar;
    ImageView mImageViewBack;
    Bundle mBundle;
    int cid;
    TextView mTextViewTitle;
    LayoutInflater inflater;

    TextView mTextViewDate;
    DatabaseConnectionAPI mDatabaseConnectionAPI;
    ArrayList<GetLocation> mArrayListLocations;
    CompanyLocationAdapter mCompanyLocationAdapter;
    ListView mListView;
    int locid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_company_work);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mListView = (ListView) findViewById(R.id.lists);
        mImageViewBack = (ImageView) findViewById(R.id.imgBack);
        mImageViewBack.setVisibility(View.VISIBLE);
        mBundle = getIntent().getExtras();
        cid = mBundle.getInt("cid");
        Log.d("CID ", String.valueOf(cid));
        mTextViewDate = (TextView) findViewById(R.id.txt_date);
        mDatabaseConnectionAPI = new DatabaseConnectionAPI(getApplicationContext());
        mArrayListLocations = new ArrayList<>();
        inflater = getLayoutInflater();
        mTextViewTitle = (TextView) findViewById(R.id.txt_comp_title);
        mArrayListLocations = mDatabaseConnectionAPI.getLocation(cid);
        mTextViewTitle.setText(mArrayListLocations.get(0).getCname());
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");
        String formattedDate = df.format(c.getTime());
        mTextViewDate.setText(formattedDate.toString());

        if (mArrayListLocations.size() > 0) {
            mCompanyLocationAdapter = new CompanyLocationAdapter(ActivityCompanyWork.this, mArrayListLocations);
            mListView.setAdapter(mCompanyLocationAdapter);
        }
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                locid = mArrayListLocations.get(i).getLocid();
                Intent mIntent = new Intent(ActivityCompanyWork.this, ActivitySubTask.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Bundle mBundle = new Bundle();
                mBundle.putInt("loc", locid);
                mBundle.putString("cname", mArrayListLocations.get(i).getCname());
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public class CompanyLocationAdapter extends BaseAdapter {

        ArrayList<GetLocation> list;
        Activity mActivity;

        public CompanyLocationAdapter(Activity mActivity, ArrayList<GetLocation> list) {
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
            view = inflater.inflate(R.layout.row_location_task, viewGroup, false);
            TextView mTextView = (TextView) view.findViewById(R.id.txt_name);
            TextView mTextViewDesc = (TextView) view.findViewById(R.id.txt_desc);
            TextView mTextViewStatus = (TextView) view.findViewById(R.id.txt_status);
            ImageView mImageView = (ImageView) view.findViewById(R.id.img_status);

            mTextView.setText(list.get(i).getLocname());
            mTextViewDesc.setText(list.get(i).getTaskname());
            if (list.get(i).getStaus().equalsIgnoreCase("p")) {
                mTextViewStatus.setText("Pending");
                mImageView.setImageResource(R.drawable.ring_pending);

            } else if (list.get(i).getStaus().equalsIgnoreCase("w")) {
                mTextViewStatus.setText("Working");
                mImageView.setImageResource(R.drawable.ring_working);

            } else {
                mTextViewStatus.setText("Completed");
                mImageView.setImageResource(R.drawable.ring_completed);

            }

            return view;
        }
    }
}
