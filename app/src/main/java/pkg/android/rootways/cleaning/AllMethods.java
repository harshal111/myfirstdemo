package pkg.android.rootways.cleaning;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class AllMethods {
	ConnectivityManager mConnectivityManager;
	NetworkInfo mNetworkInfo;
	boolean isNetError;
	public Activity mActivity;
	Context mContext;
	FragmentActivity mActivity2;
	Dialog mDialogRentRimDialog;
	public     String BYTES = " Bytes";
	public     String MEGABYTES = " MB";
	public     String KILOBYTES = " KB";
	public     String GIGABYTES = " GB";
	public     long KILO = 1024;
	public     long MEGA = KILO * 1024;
	public     long GIGA = MEGA * 1024;



	public     String formatPhoneNumber(String number){
		number  =   number.substring(0, number.length()-4) + "-" + number.substring(number.length()-4, number.length());
		number  =   number.substring(0,number.length()-8)+")"+number.substring(number.length()-8,number.length());
		number  =   number.substring(0, number.length()-12)+"("+number.substring(number.length()-12, number.length());
		number  =   number.substring(0, number.length()-14)+"+"+number.substring(number.length()-14, number.length());

		return number;
	}
	public void Snackbarview(String message ){

		  View rootView =  mActivity.getWindow().getDecorView().findViewById(android.R.id.content);
                Snackbar mysnack= Snackbar.make(rootView, message, Snackbar.LENGTH_LONG);

                View view= mysnack.getView();

                TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(Color.WHITE);
                mysnack.show();
	}
	public   String DateForamte(String amout) throws ParseException
	{
		String ss="";
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
//		format.setTimeZone(TimeZone.getTimeZone("etc/UTC"));
		try {
			date = format.parse(amout);
			System.out.println("date "+date);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");
		ss= df.format(date);
		System.out.println("String in   is: " + ss);
		return ss;
	}

	public   String TimeForamte(String amout) throws ParseException
	{
		String ss="";
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss", Locale.ENGLISH);
		format.setTimeZone(TimeZone.getTimeZone("etc/UTC"));
		try {
			date = format.parse(amout);
			System.out.println("date "+date);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		SimpleDateFormat df = new SimpleDateFormat("hh:mm");
		ss= df.format(date);
		System.out.println("String in   is: " + ss);

		return ss;
	}

	public   String formatFileSize(final long pBytes){
		if(pBytes < KILO){
			return pBytes + BYTES;
		}else if(pBytes < MEGA){
			return (int)(0.5 + (pBytes / (double)KILO)) + KILOBYTES;
		}else if(pBytes < GIGA){
			return (int)(0.5 + (pBytes / (double)MEGA)) + MEGABYTES;
		}else {
			return (int)(0.5 + (pBytes / (double)GIGA)) + GIGABYTES;
		}
	}

	public   long daysBetween(Date one, Date two) {
		long difference =  (one.getTime()-two.getTime())/86400000;
		return Math.abs(difference);
	}



	public   long getFileSizeInBytes(String fileName) {
		long ret = 0;
		File f = new File(fileName);
		if (f.isFile()) {
			return f.length();
		} else if (f.isDirectory()) {
			File[] contents = f.listFiles();
			for (int i = 0; i < contents.length; i++) {
				if (contents[i].isFile()) {
					ret += contents[i].length();
				} else if (contents[i].isDirectory())
					ret += getFileSizeInBytes(contents[i].getPath());
			}
		}
		return ret;
	}
	/*public   String AmountForamte(String amout)
	{
		String ss="";
		double amount = Double.parseDouble(amout);
//		DecimalFormat formatter = new DecimalFormat("#,##0.00");

		DecimalFormat formatter = new DecimalFormat(StaticValues.mStringCurrecyFormat);
		ss=formatter.format(amount);
		return ss;
	}*/
	
	/*public   String DateForamte(String amout) throws ParseException
	{
		String ss="";
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//		format.setTimeZone(TimeZone.getTimeZone("etc/UTC"));
		try {
			  date = format.parse(amout);
			System.out.println("date "+date);
		} catch (ParseException e) {
		 
			e.printStackTrace();
		}
//		 date = format.parse(amout);
		SimpleDateFormat df = new SimpleDateFormat(StaticValues.mStringDateFormat);
		ss= df.format(date);
        System.out.println("String in   is: " + ss);
		return ss;
	}*/
	public   boolean isTabletDevice(FragmentActivity activityContext) {
	    // Verifies if the Generalized Size of the device is XLARGE to be
	    // considered a Tablet
	    boolean large = ((activityContext.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
	    boolean xlarge= ((activityContext.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);
	    // If XLarge, checks if the Generalized Density is at least MDPI
	    // (160dpi)
	    if (large) {
	        DisplayMetrics metrics = new DisplayMetrics();
	        Activity activity = (Activity) activityContext;
	        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

	        // MDPI=160, DEFAULT=160, DENSITY_HIGH=240, DENSITY_MEDIUM=160,
	        // DENSITY_TV=213, DENSITY_XHIGH=320
	        if (metrics.densityDpi == DisplayMetrics.DENSITY_DEFAULT
	                || metrics.densityDpi == DisplayMetrics.DENSITY_HIGH
	                || metrics.densityDpi == DisplayMetrics.DENSITY_MEDIUM
	                || metrics.densityDpi == DisplayMetrics.DENSITY_TV
	                || metrics.densityDpi == DisplayMetrics.DENSITY_XHIGH) {
	        	System.out.println("7 Inch Tab");
	            // Yes, this is a tablet!
	            return true;
	        }
	    }
	    else if (xlarge) {
	    	   DisplayMetrics metrics = new DisplayMetrics();
		        Activity activity = (Activity) activityContext;
		        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
	    	if (metrics.densityDpi == DisplayMetrics.DENSITY_DEFAULT
	                || metrics.densityDpi == DisplayMetrics.DENSITY_HIGH
	                || metrics.densityDpi == DisplayMetrics.DENSITY_MEDIUM
	                || metrics.densityDpi == DisplayMetrics.DENSITY_TV
	                || metrics.densityDpi == DisplayMetrics.DENSITY_XHIGH) {
	    		System.out.println("10 Inch Tab");
	            // Yes, this is a tablet!
	            return true;
	        }
		}

	    // No, this is not a tablet!
	    return false;
	}
	
	
	public   void hideSoftKeyboard(Activity activity,EditText mEditText) {
		if (mEditText != null) {
			InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
		}
	}

	public   void hideSoftKeyboard(FragmentActivity activity) {
		InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
	}

	public   void hideSoftKeyboard(FragmentActivity activity,EditText mEditText) {

		if (mEditText != null) {
			InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
		}
 	}

	public AllMethods() {
		// TODO Auto-generated constructor stub

	}
	public AllMethods(Context mContext) {
		// TODO Auto-generated constructor stub
		this.mContext=mContext;

	}
	public AllMethods(Activity mContext) {
		// TODO Auto-generated constructor stub
		this.mActivity=mContext;

	}
	public AllMethods(FragmentActivity mContext) {
		// TODO Auto-generated constructor stub
		this.mActivity=mContext;


	}
	public   boolean isSDCARDPResent()
	{
		Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
		return isSDPresent;

	}
	public void ShowDialog(final Activity activity, String Title ,String Message,String Ok) 
	{
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
			alertDialogBuilder.setTitle(Title);
			alertDialogBuilder
				.setMessage(Message)
				.setCancelable(true)
				.setPositiveButton(Ok,new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					}
				  });
 
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();

	}

	public void unbindDrawables(View view)
	{
		if (view.getBackground() != null)
		{
			view.getBackground().setCallback(null);
		}
		if (view instanceof ViewGroup && !(view instanceof AdapterView))
		{
			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++)
			{
				unbindDrawables(((ViewGroup) view).getChildAt(i));
			}
			((ViewGroup) view).removeAllViews();
		}
	}


	public   void hideKeyboard(Activity  mActivity2,View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)mActivity2.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
	public void ShowDialog(FragmentActivity activity, String Title ,String Message,String Ok)
	{
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
			alertDialogBuilder.setTitle(Title);
			alertDialogBuilder
				.setMessage(Message)
				.setCancelable(true)
				.setPositiveButton(Ok,new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					}
				  });
 
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();

	}
	
	/*public   boolean checkEmail(String EdittextString) {
		return FragmentDashboard.emailPattern.matcher(EdittextString).matches();
	}*/
	
	public boolean check_Internet()
	{
		mConnectivityManager= (ConnectivityManager)mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
		mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();

		if(mNetworkInfo != null && mNetworkInfo.isConnectedOrConnecting())
		{
			isNetError = false;
			return true;
		}
		else
		{
			isNetError = true;
			return false;
		}
	}
	public boolean isExpiryDate(String edate )
	{
		boolean expir=false;
		Date mdate  =null;
		Date exdate=null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		  mdate = new Date();
		System.out.println(dateFormat.format(mdate));


		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		//Date startDate;
		  exdate=null;
		try {
			exdate = df.parse(edate);
			String newDateString = df.format(exdate);
			System.out.println(newDateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			  mdate = sdf.parse("2015-08-01");
			  exdate = sdf.parse("2015-08-03");
		}
		catch (Exception e)
		{

		}*/

		if(mdate.compareTo(exdate)>0){
			expir=true;
			System.out.println("Date1 is after Date2");
		}else if(mdate.compareTo(exdate)<0){
			expir=false;
			System.out.println("Date1 is before Date2");
		}else if(mdate.compareTo(exdate)==0){
			expir=true;
			System.out.println("Date1 is equal to Date2");
		}else{
			System.out.println("How to get here?");
		}


		return  expir;
	}
}
