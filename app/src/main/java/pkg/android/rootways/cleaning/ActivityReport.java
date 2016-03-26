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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import helper.DatabaseConnectionAPI;
import parser.GetCompletePendingTask;

/**
 * Created by sphere65 on 26/3/16.
 */
public class ActivityReport extends AppCompatActivity {


    Toolbar mToolbar;
    ListView mListView;
    DatabaseConnectionAPI mDatabaseConnectionAPI;
    ArrayList<GetCompletePendingTask> mArrayListTasks;
    TaskAdapter mTaskAdapter;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_report);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mListView = (ListView) findViewById(R.id.lists);
        mArrayListTasks = new ArrayList<>();
        mDatabaseConnectionAPI = new DatabaseConnectionAPI(getApplicationContext());
        mArrayListTasks = mDatabaseConnectionAPI.getTaskReport();
        inflater = getLayoutInflater();

    }

    public class TaskAdapter extends BaseAdapter {

        Activity mActivity;
        ArrayList<GetCompletePendingTask> list;

        public TaskAdapter(ActivityCompleteTask activityCompleteTask, ArrayList<GetCompletePendingTask> mArrayListTasks) {
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
            TextView mTextViewDetail = (TextView) view.findViewById(R.id.txt_view);
            TextView mTextViewLoc = (TextView) view.findViewById(R.id.txt_desc);
            TextView mTextViewSubLoc = (TextView) view.findViewById(R.id.txt_status);
            mTextView.setText(list.get(i).getCname());
            mTextViewLoc.setText(list.get(i).getLocation());
            mTextViewSubLoc.setText(list.get(i).getSubcat());
            return view;
        }
    }

}
