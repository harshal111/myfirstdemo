package pkg.android.rootways.cleaning;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import parser.Request;

/**
 * Created by sphere65 on 5/4/16.
 */
public class ActivityRequest extends AppCompatActivity {

    ArrayList<Request> mRequests;
    ListView mListViewRequest;
    Toolbar mToolbar;
    RelativeLayout mRelativeLayoutNoti;

    RequestAdapter mRequestAdapter;
    LayoutInflater inflater;
    TextView mTextViewTitle;
    ImageView mImageViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_request);
        mRequests = new ArrayList<>();
        mListViewRequest = (ListView) findViewById(R.id.lists);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTextViewTitle=(TextView)mToolbar.findViewById(R.id.txt_header_titel);
        mImageViewBack=(ImageView)mToolbar.findViewById(R.id.imgBack);
        mTextViewTitle.setText("REQUESTS");
        mTextViewTitle.setTextColor(Color.WHITE);
        mRelativeLayoutNoti = (RelativeLayout) findViewById(R.id.relCart);
        mRelativeLayoutNoti.setVisibility(View.GONE);
        inflater=getLayoutInflater();
        mImageViewBack.setVisibility(View.VISIBLE);
        mTextViewTitle.setVisibility(View.VISIBLE);
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Request mRequest = new Request();
        mRequest.setCompany("Roger Communication");
        mRequest.setAddress("1155 Queen Street West" +
                "Toronto, ON M6J 1J4");
        mRequest.setTime("2:30 pm to 3:30 pm");
        mRequest.setTotal("1 hour");
        mRequests.add(mRequest);


        Request mRequest1 = new Request();
        mRequest1.setCompany("Natalie Angeja");
        mRequest1.setAddress("5121 Sackville Street, Suite 201 " +
                "Halifax, NS " +
                "B3J 1K1");
        mRequest1.setTime("3:30 pm to 4:30 pm");
        mRequest1.setTotal("1 hour");
        mRequests.add(mRequest1);

        Request mRequest2 = new Request();
        mRequest2.setCompany("QUEBEC OFFICE");
        mRequest2.setAddress("Bureau 2400 " +
                "Montréal, QC " +
                "H3B 4W5");
        mRequest2.setTime("4:30 pm to 5:30 pm");
        mRequest2.setTotal("1 hour");
        mRequests.add(mRequest2);

        Request mRequest3 = new Request();
        mRequest3.setCompany("Dwayne Marling");
        mRequest3.setAddress("101-478 River Avenue, Suite 761\n" +
                "Winnipeg, MB\n" +
                "R3L 0B3");
        mRequest3.setTime("5:30 pm to 6:30 pm");
        mRequest3.setTotal("1 hour");
        mRequests.add(mRequest3);

        mRequestAdapter = new RequestAdapter(ActivityRequest.this, mRequests);
        mListViewRequest.setAdapter(mRequestAdapter);


    }

    public class RequestAdapter extends BaseAdapter {
        ArrayList<Request> list;
        Activity mActivity;

        public RequestAdapter(ActivityRequest activityRequest, ArrayList<Request> mRequest) {
            list = mRequest;
            mActivity = activityRequest;
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
            view = inflater.inflate(R.layout.row_requesr, viewGroup, false);
            TextView mTextView1=(TextView)view.findViewById(R.id.txt_name);
            TextView mTextView2=(TextView)view.findViewById(R.id.txt_desc);
            TextView mTextView3=(TextView)view.findViewById(R.id.txt_status);
            TextView mTextView4=(TextView)view.findViewById(R.id.txt_view);
            TextView mTextView5=(TextView)view.findViewById(R.id.txt_totla_hour);
            mTextView1.setText(list.get(i).getCompany());
            mTextView2.setText(list.get(i).getAddress());
            mTextView3.setText(list.get(i).getTime());
            mTextView5.setText(list.get(i).getTotal());
            mTextView4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse("geo:0,0?q="+list.get(i).getAddress());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });
            return view;
        }
    }
}
