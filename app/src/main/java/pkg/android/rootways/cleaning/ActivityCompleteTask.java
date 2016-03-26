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

import java.util.ArrayList;

import helper.DatabaseConnectionAPI;
import parser.GetCompletePendingTask;

/**
 * Created by sphere65 on 18/3/16.
 */
public class ActivityCompleteTask extends AppCompatActivity {

    Toolbar mToolbar;
    TextView mTextViewHeader;
    ImageView imageView;

    DatabaseConnectionAPI mDatabaseConnectionAPI;
    AllMethods methods;
    LayoutInflater inflater;
    ArrayList<GetCompletePendingTask> mArrayListTasks;
    ListView mListView;
    TaskAdapter mTaskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_comp_task);
        mDatabaseConnectionAPI = new DatabaseConnectionAPI(getApplicationContext());
        methods = new AllMethods(ActivityCompleteTask.this);
        mArrayListTasks = new ArrayList<>();
        mArrayListTasks = mDatabaseConnectionAPI.getCompletePendingTask("c");
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTextViewHeader = (TextView) mToolbar.findViewById(R.id.txt_header_titel);
        mTextViewHeader.setVisibility(View.VISIBLE);
        mTextViewHeader.setText("COMPLETED TASK");
        imageView = (ImageView) mToolbar.findViewById(R.id.imgBack);
        imageView.setVisibility(View.VISIBLE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        inflater = getLayoutInflater();
        mListView=(ListView)findViewById(R.id.lists);
        if (mArrayListTasks.size()>0)
        {
            mTaskAdapter=new TaskAdapter(ActivityCompleteTask.this,mArrayListTasks);
            mListView.setAdapter(mTaskAdapter);
        }
    }

    public class TaskAdapter extends BaseAdapter {

        Activity mActivity;
        ArrayList<GetCompletePendingTask> list;

        public TaskAdapter(ActivityCompleteTask activityCompleteTask, ArrayList<GetCompletePendingTask> mArrayListTasks) {
            mActivity=activityCompleteTask;
            list=mArrayListTasks;
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
            TextView mTextViewDetail=(TextView)view.findViewById(R.id.txt_view);
            TextView mTextViewLoc = (TextView) view.findViewById(R.id.txt_desc);
            TextView mTextViewSubLoc = (TextView) view.findViewById(R.id.txt_status);
            mTextView.setText(list.get(i).getCname());
            mTextViewLoc.setText(list.get(i).getLocation());
            mTextViewSubLoc.setText(list.get(i).getSubcat());
            mTextViewDetail.setVisibility(View.VISIBLE);
            mTextViewDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int scanid=list.get(i).getEsubid();
                    Intent mIntent=new Intent(ActivityCompleteTask.this,ActivityTaskDetail.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mIntent.putExtra("uid",scanid);

                    startActivity(mIntent);

                }
            });
            return view;
        }
    }
}
