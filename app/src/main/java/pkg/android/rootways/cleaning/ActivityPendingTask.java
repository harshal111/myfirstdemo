package pkg.android.rootways.cleaning;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import helper.DatabaseConnectionAPI;
import parser.GetCompletePendingTask;

/**
 * Created by sphere65 on 18/3/16.
 */
public class ActivityPendingTask extends AppCompatActivity {

    Toolbar mToolbar;
    TextView mTextViewHeader;
    ImageView imageView;

    DatabaseConnectionAPI mDatabaseConnectionAPI;
    AllMethods methods;
    LayoutInflater inflater;
    ArrayList<GetCompletePendingTask> mArrayListTasks;
    ListView mListView;
    TaskAdapter mTaskAdapter;
    TextView mTextViewNOData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_comp_task);
        mDatabaseConnectionAPI = new DatabaseConnectionAPI(getApplicationContext());
        methods = new AllMethods(ActivityPendingTask.this);
        mArrayListTasks = new ArrayList<>();
        mTextViewNOData=(TextView)findViewById(R.id.txt_no_data);
        mArrayListTasks = mDatabaseConnectionAPI.getCompletePendingTask("w");
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTextViewHeader = (TextView) mToolbar.findViewById(R.id.txt_header_titel);
        mTextViewHeader.setVisibility(View.VISIBLE);
        mTextViewHeader.setText("PENDING TASK");
        imageView = (ImageView) mToolbar.findViewById(R.id.imgBack);
        imageView.setVisibility(View.VISIBLE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        inflater = getLayoutInflater();
        mListView = (ListView) findViewById(R.id.lists);
        if (mArrayListTasks.size() > 0) {
            mTaskAdapter = new TaskAdapter(ActivityPendingTask.this, mArrayListTasks);
            mListView.setAdapter(mTaskAdapter);
            mTextViewNOData.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);

        }
        else {
            mTextViewNOData.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);

        }
    }

    public class TaskAdapter extends BaseAdapter {

        Activity mActivity;
        ArrayList<GetCompletePendingTask> list;

        public TaskAdapter(ActivityPendingTask activityCompleteTask, ArrayList<GetCompletePendingTask> mArrayListTasks) {
            mActivity = activityCompleteTask;
            list = mArrayListTasks;
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
        public View getView(final int i, View view, ViewGroup viewGroup) {
            inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_task, viewGroup, false);
            TextView mTextView = (TextView) view.findViewById(R.id.txt_name);
            TextView mTextViewLoc = (TextView) view.findViewById(R.id.txt_desc);
            TextView mTextViewSubLoc = (TextView) view.findViewById(R.id.txt_status);
            TextView mTextViewDetail = (TextView) view.findViewById(R.id.txt_view);
            TextView mTextViewComplete = (TextView) view.findViewById(R.id.txt_complete);
            mTextViewDetail.setVisibility(View.VISIBLE);
            mTextViewComplete.setVisibility(View.VISIBLE);

            mTextView.setText(list.get(i).getCname());
            mTextViewLoc.setText(list.get(i).getLocation());
            mTextViewSubLoc.setText(list.get(i).getSubcat());
            mTextViewDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int scanid=list.get(i).getEsubid();
                    Intent mIntent=new Intent(ActivityPendingTask.this,ActivityTaskDetail.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mIntent.putExtra("uid",scanid);

                    startActivity(mIntent);
                }
            });

            mTextViewComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int scanid=list.get(i).getEsubid();
                    Calendar c = Calendar.getInstance();
                    System.out.println("Current time => " + c.getTime());
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat df1 = new SimpleDateFormat("hh:mm");

                    String formattedDate = df.format(c.getTime());
                    String formattedDate1= df1.format(c.getTime());

                    boolean isUpdate=mDatabaseConnectionAPI.updateEndTask(scanid, formattedDate.toString(), formattedDate1.toString(), "c");

                    if (isUpdate==true)
                    {
                        Toast.makeText(ActivityPendingTask.this,"Task completed successfully.",Toast.LENGTH_LONG).show();
                        mArrayListTasks = new ArrayList<>();
                        mArrayListTasks = mDatabaseConnectionAPI.getCompletePendingTask("w");
                        if (mArrayListTasks.size() > 0) {
                            mTaskAdapter = new TaskAdapter(ActivityPendingTask.this, mArrayListTasks);
                            mListView.setAdapter(mTaskAdapter);
                            mTextViewNOData.setVisibility(View.GONE);
                            mListView.setVisibility(View.VISIBLE);

                        }
                        else {
                            mTextViewNOData.setVisibility(View.VISIBLE);
                            mListView.setVisibility(View.GONE);

                        }
                    }
                    else
                    {
                        Toast.makeText(ActivityPendingTask.this,"Task not completed successfully.",Toast.LENGTH_LONG).show();
                    }
                }
            });

            return view;
        }
    }
}
