package pkg.android.rootways.cleaning;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import helper.DatabaseConnectionAPI;
import parser.GetLocation;
import parser.GetSubLocation;

/**
 * Created by sphere65 on 17/3/16.
 */
public class ActivitySubTask extends AppCompatActivity {

    Toolbar mToolbar;
    ImageView imageViewBack;
    DatabaseConnectionAPI mDatabaseConnectionAPI;
    ArrayList<GetSubLocation> mArrayListGetSubLocations;
    Bundle mBundle;
    LayoutInflater inflater;
    ListView mListView;
    String name;
    TextView mTextViewTitle;;
    RelativeLayout mRelativeLayoutRoot;
    int scanid=0;
    SubCategoryAdapter mSubCategoryAdapter;
    int loc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_employee_work);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mBundle=getIntent().getExtras();
        loc=mBundle.getInt("loc");
        name=mBundle.getString("cname");
        inflater=getLayoutInflater();
        mRelativeLayoutRoot=(RelativeLayout)findViewById(R.id.root);
        mTextViewTitle=(TextView)findViewById(R.id.txt_comp_title);
        mListView=(ListView)findViewById(R.id.lists);
        imageViewBack = (ImageView) mToolbar.findViewById(R.id.imgBack);
        imageViewBack.setVisibility(View.VISIBLE);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTextViewTitle.setText(name);
        mDatabaseConnectionAPI = new DatabaseConnectionAPI(getApplicationContext());
        mArrayListGetSubLocations = new ArrayList<>();
        mArrayListGetSubLocations=mDatabaseConnectionAPI.getSubLocation(loc);
        if (mArrayListGetSubLocations.size()>0)
        {
            mSubCategoryAdapter=new SubCategoryAdapter(ActivitySubTask.this,mArrayListGetSubLocations);
            mListView.setAdapter(mSubCategoryAdapter);
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                IntentIntegrator integrator = new IntentIntegrator(ActivitySubTask.this);
                  scanid=mArrayListGetSubLocations.get(i).getSubid();
                Log.d("scanid ",String.valueOf(scanid));
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

    }

    public class SubCategoryAdapter extends BaseAdapter
    {

        Activity mActivity;
        ArrayList<GetSubLocation>list;
        public SubCategoryAdapter (Activity mActivity, ArrayList<GetSubLocation>list){
            this.mActivity=mActivity;
            this.list=list;

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
            view = inflater.inflate(R.layout.row_sublocation_task, viewGroup, false);
            TextView mTextView=(TextView)view.findViewById(R.id.txt_name);
            TextView mTextViewMode=(TextView)view.findViewById(R.id.txt_desc);
            TextView mTextViewStatus=(TextView)view.findViewById(R.id.txt_viewtasks);
            TextView mTextViewDesc=(TextView)view.findViewById(R.id.txt_status);

            mTextView.setText(list.get(i).getSubname());
            mTextViewMode.setText("("+list.get(i).getWorkmode()+")");
            mTextViewDesc.setText(list.get(i).getDesc());
            if (list.get(i).getStatus().equalsIgnoreCase("p"))
            {
                mTextViewStatus.setText("START WORK");
                mTextViewStatus.setBackgroundResource(R.drawable.rect_start);
            }
            else if (list.get(i).getStatus().equalsIgnoreCase("w"))
            {
                mTextViewStatus.setText("WORKING ON");
                mTextViewStatus.setBackgroundResource(R.drawable.rect_working);
            }
            else
            {
                mTextViewStatus.setText("COMPLETED");
                mTextViewStatus.setBackgroundResource(R.drawable.rect_comp);
            }
            /*mTextViewStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IntentIntegrator integrator = new IntentIntegrator(mActivity);
                    integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                    integrator.setPrompt("Scan");
                    integrator.setCameraId(0);
                    integrator.setBeepEnabled(false);
                    integrator.setBarcodeImageEnabled(false);
                    integrator.initiateScan();
                }
            });
*/

            return view;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                //Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                Calendar c = Calendar.getInstance();
                System.out.println("Current time => " + c.getTime());
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = df.format(c.getTime());

                boolean isUpdate=mDatabaseConnectionAPI.updateStartTask(scanid,formattedDate.toString(),"w");
                if (isUpdate==true)
                {
                    Toast.makeText(this, "Your work is started.", Toast.LENGTH_LONG).show();
                    mArrayListGetSubLocations = new ArrayList<>();
                    mArrayListGetSubLocations=mDatabaseConnectionAPI.getSubLocation(loc);
                    if (mArrayListGetSubLocations.size()>0)
                    {
                        mSubCategoryAdapter=new SubCategoryAdapter(ActivitySubTask.this,mArrayListGetSubLocations);
                        mListView.setAdapter(mSubCategoryAdapter);
                    }


                }
                else
                {
                    Toast.makeText(this, "Your work is not started due to wrong QR code scanned.", Toast.LENGTH_LONG).show();
                }

              /*  View rootView =  getWindow().getDecorView().findViewById(android.R.id.content);
                Snackbar mysnack= Snackbar.make(rootView, result.getContents(), Snackbar.LENGTH_LONG);

                View view= mysnack.getView();

                TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(Color.WHITE);
                mysnack.show();*/

                //Snackbar.make(mRelativeLayoutRoot,result.getContents(),Snackbar.LENGTH_LONG).show();

            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
