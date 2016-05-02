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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import helper.DatabaseConnectionAPI;
import parser.GetAssignTask;
import parser.GetCompletePendingTask;

/**
 * Created by sphere65 on 2/5/16.
 */
public class ActivityAssignTaskByManager extends AppCompatActivity {


    Toolbar mToolbar;
    TextView mTextViewHeaderTitel;
    ImageView mImageViewBack;
    RelativeLayout mRelativeLayoutCart;
    ListView mListView;
    TextView mTextViewNoData;
    DatabaseConnectionAPI mDatabaseConnectionAPI;
    AllMethods mAllMethods;
    ArrayList<GetAssignTask> mGetAssignTasks;
    LayoutInflater inflater;

    AssignTaskAdapter mAssignTaskAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_comp_task);
        mGetAssignTasks = new ArrayList<>();
        inflater=getLayoutInflater();
        mDatabaseConnectionAPI = new DatabaseConnectionAPI(getApplicationContext());
        mAllMethods = new AllMethods(ActivityAssignTaskByManager.this);
        mTextViewNoData = (TextView) findViewById(R.id.txt_no_data);
        mListView = (ListView) findViewById(R.id.lists);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTextViewHeaderTitel = (TextView) mToolbar.findViewById(R.id.txt_header_titel);
        mImageViewBack = (ImageView) mToolbar.findViewById(R.id.imgBack);
        mRelativeLayoutCart = (RelativeLayout) mToolbar.findViewById(R.id.relCart);
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mImageViewBack.setVisibility(View.VISIBLE );
        mGetAssignTasks = mDatabaseConnectionAPI.getAssignTasks();
        mRelativeLayoutCart.setVisibility(View.GONE);
        mTextViewHeaderTitel.setVisibility(View.VISIBLE);
        mTextViewHeaderTitel.setText("TASKS");

        mAssignTaskAdapter=new AssignTaskAdapter(ActivityAssignTaskByManager.this,mGetAssignTasks);
        mListView.setAdapter(mAssignTaskAdapter);


    }

    public class AssignTaskAdapter extends BaseAdapter {

        Activity mActivity;
        ArrayList<GetAssignTask> list;

        public AssignTaskAdapter(ActivityAssignTaskByManager activityCompleteTask, ArrayList<GetAssignTask> mArrayListTasks) {
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
            view = inflater.inflate(R.layout.row_layout_tasks, viewGroup, false);
            TextView mTextView1 = (TextView) view.findViewById(R.id.row_txt_task_name);
            TextView mTextView2 = (TextView) view.findViewById(R.id.row_txt_area_code);
            TextView mTextView3 = (TextView) view.findViewById(R.id.row_txt_area);
            TextView mTextView4 = (TextView) view.findViewById(R.id.row_txt_company);
            TextView mTextView5 = (TextView) view.findViewById(R.id.row_txt_status);
            mTextView1.setText(list.get(i).getActivity());
            mTextView2.setText(list.get(i).getAreacode()+": ");
            mTextView3.setText(list.get(i).getArea());
            mTextView4.setText(list.get(i).getCname());
            mTextView5.setText(list.get(i).getStatus());
            return view;
        }
    }

}
