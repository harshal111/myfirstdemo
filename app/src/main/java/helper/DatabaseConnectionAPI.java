package helper;

/**
 * Created by sphere65 on 16/3/16.
 */
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import parser.GetAreaActivity;
import parser.GetAreaCode;
import parser.GetAssignTask;
import parser.GetCompany;
import parser.GetCompletePendingTask;
import parser.GetEmployee;
import parser.GetLocation;
import parser.GetProfile;
import parser.GetSubLocation;

public class DatabaseConnectionAPI extends SQLiteOpenHelper{

    //The Android's default system path of your application database.
    private final static String DB_PATH = "/data/data/pkg.android.rootways.cleaning/databases/";

    private final static String DB_NAME = "db_cleaning.sqlite";

    private final Context myContext;

    private static SQLiteDatabase db;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DatabaseConnectionAPI(Context context)
    {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    public  boolean  updateProfile(int c_id,String fname,String lname,String address,String phone,String city,String st,String cont,String pin)
    {
        boolean b = false;
        openDataBase();
        try {
            String sql="UPDATE employee_profile_master SET fname="+ "'"+fname +"'"+" , lname= "+ "'"+lname +"'"+" , address= "+ "'"+address +"'"+" , ephone= "+ "'"+phone +"'"+" , ecity= "+ "'"+city +"'"+" , estate= "+ "'"+st +"'"+" , ecountry= "+ "'"+cont +"'"+" , epincode= "+ "'"+pin +"'" + " WHERE  prid = "+c_id+" ";
            System.out.println("SQL UPDATE "+sql);
            b=true;
            db.execSQL(sql);
            //			db.execSQL("UPDATE chat SET is_filetransfer_complete = "+master.getFileTransferStatus()+" WHERE  chat_id = "+master.getChat_id()+" ");

        } 	catch (Exception e) {
            e.printStackTrace();
            b=false;
        }
        return b;
    }

    public  boolean  updateStartTask(int sub_id,String fname,String time,String status)
    {
        boolean b = false;
        openDataBase();
        try {
            String sql="UPDATE emp_sub_task_master SET sub_s_time="+ "'"+time +"'"+" , sub_cat_status= "+ "'"+status +"'"+" , emp_e_assign_date= "+ "'"+fname +"'"+" WHERE  esub_id = "+sub_id+" ";
            System.out.println("SQL UPDATE "+sql);
            b=true;
            db.execSQL(sql);
            //			db.execSQL("UPDATE chat SET is_filetransfer_complete = "+master.getFileTransferStatus()+" WHERE  chat_id = "+master.getChat_id()+" ");

        } 	catch (Exception e) {
            e.printStackTrace();
            b=false;
        }
        return b;
    }


    public  boolean  updateEndTask(int sub_id,String fname,String time,String status)
    {
        boolean b = false;
        openDataBase();
        try {
            String sql="UPDATE emp_sub_task_master SET sub_e_time="+ "'"+time +"'"+" , sub_cat_status= "+ "'"+status +"'"+" , emp_e_assign_date= "+ "'"+fname +"'"+" WHERE  esub_id = "+sub_id+" ";
            System.out.println("SQL UPDATE "+sql);
            b=true;
            db.execSQL(sql);
            //			db.execSQL("UPDATE chat SET is_filetransfer_complete = "+master.getFileTransferStatus()+" WHERE  chat_id = "+master.getChat_id()+" ");

        } 	catch (Exception e) {
            e.printStackTrace();
            b=false;
        }
        return b;
    }


    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException
    {

        boolean dbExist = checkDataBase();

        if(dbExist)
        {
//do nothing - database already exist
        }
        else
        {

//By calling this method and empty database will be created into the default system path
//of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try
            {
                copyDataBase();
            }
            catch (IOException e)
            {

                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;
        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

        }catch(SQLiteException e){
//database does't exist yet.
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

//Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

// Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

//Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

//transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[2048];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

//Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException{
        try
        {
            db.close();
        }
        catch(Exception e)
        {
            System.out.println("no database connected to close");
        }
//Open the database
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
    public synchronized void close() {
        if(db != null)
            db.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    /**
     * Use this function to set the value of a particular column
     *
     * @param columnName The column name whose value is to be changed
     * @param newColumnValue The value to be replaced in the column
     * @param whereColumnName The column name to be compared with the where clause
     * @param whereColumnValue The value to be compared in the where clause
     */


    /**
     * Query the given table, returning a Cursor over the result set.
     *
     * @param table The table name to compile the query against.
     * @param columns A list of which columns to return. Passing null will return all columns,
     * which is discouraged to prevent reading data from storage that isn't going to be used.
     * @param selection A filter declaring which rows to return, formatted
     * as an SQL WHERE clause (excluding the WHERE itself). Passing null
     * will return all rows for the given table.
     * @param selectionArgs You may include ?s in selection, which will be
     * replaced by the values from selectionArgs, in order that they appear
     * in the selection. The values will be bound as Strings.
     * @param groupBy A filter declaring how to group rows, formatted as an
     * SQL GROUP BY clause (excluding the GROUP BY itself). Passing null
     * will cause the rows to not be grouped.
     * @param having A filter declare which row groups to include in the
     * cursor, if row grouping is being used, formatted as an SQL HAVING
     * clause (excluding the HAVING itself). Passing null will cause all
     * row groups to be included, and is required when row grouping is
     * not being used.
     * @param orderBy How to order the rows, formatted as an SQL
     * ORDER BY clause (excluding the ORDER BY itself). Passing
     * null will use the default sort order, which may be unordered.
     * @return A Cursor object, which is positioned before the first entry
     */
    Cursor onQueryGetCursor(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
    {
        Cursor query = null;
        try
        {
            openDataBase();
            query = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
            System.out.println("@@@@@ Query is :"+query);
        }
        catch(Exception e)
        {
            System.out.println("Query couldnt complete "+e);
        }
        return query;
    }
    public Cursor Query(String sql) {

        Cursor mCursor = null;
        try {
            mCursor = db.rawQuery(sql, null);
            System.out.println("Query is :---" + sql);
        } catch (Exception e) {
            System.out.println("Query couldnot complete" + e);
            e.printStackTrace();
        }
        return mCursor;
    }

    public ArrayList<GetAssignTask> getAssignTasks() {

        openDataBase();
        ArrayList<GetAssignTask> mGetCategoryList = new ArrayList<GetAssignTask>();
        try {
            String sqlQuery = "SELECT * FROM job_master j, company_master c, employee_master e where j.cmid=c.cmid and j.emp_id=e.emp_id and j.cmid=1";
            Cursor mCursorCategory = Query(sqlQuery);
            if (mCursorCategory != null) {
                mCursorCategory.moveToFirst();
                while (!mCursorCategory.isAfterLast()) {

                    GetAssignTask mParserCategory = new GetAssignTask();

                    mParserCategory
                            .setCid((Integer.parseInt(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("cmid")))));

                    mParserCategory
                            .setEid((Integer.parseInt(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("emp_id")))));

                    mParserCategory
                            .setJid((Integer.parseInt(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("jid")))));

                    mParserCategory
                            .setActivity(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("activity")));
                    mParserCategory
                            .setActivity(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("activity")));

                    mParserCategory
                            .setAreacode(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("area_code")));
                    mParserCategory
                            .setArea(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("area")));
                    mParserCategory
                            .setCname(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("cname")));

                    mParserCategory
                            .setStatus(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("status")));

                    mParserCategory
                            .setEtime(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("end_time")));

                    mParserCategory
                            .setFreq(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("frequency")));

                    mParserCategory
                            .setNote(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("notes")));

                    mParserCategory
                            .setTimeallocat(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("time_allocate")));

                    mParserCategory
                            .setPriority(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("priority")));

                    mParserCategory
                            .setStime(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("start_time")));

                    mParserCategory
                            .setEname(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("emp_name")));
                    mGetCategoryList.add(mParserCategory);

                    mCursorCategory.moveToNext();
                }
            }
            mCursorCategory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mGetCategoryList;
    }

    public ArrayList<GetSubLocation> getSubLocation(int id) {

        openDataBase();
        ArrayList<GetSubLocation> mGetCategoryList = new ArrayList<GetSubLocation>();
        try {
            String sqlQuery = "SELECT * FROM location_master l, emp_task_master e, emp_sub_task_master s where l.loc_id= e.loc_id and e.loc_id=s.loc_id  and s.loc_id ="+id;
            Cursor mCursorCategory = Query(sqlQuery);
            if (mCursorCategory != null) {
                mCursorCategory.moveToFirst();
                while (!mCursorCategory.isAfterLast()) {

                    GetSubLocation mParserCategory = new GetSubLocation();

                    mParserCategory
                            .setSubid((Integer.parseInt(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("esub_id")))));

                    mParserCategory
                            .setCmid((Integer.parseInt(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("cmid")))));

                    mParserCategory
                            .setLocid((Integer.parseInt(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("loc_id")))));

                    mParserCategory
                            .setCmid((Integer.parseInt(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("cmid")))));

                    mParserCategory
                            .setDesc(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("sub_cat_desc")));

                    mParserCategory
                            .setEnddate(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("sub_e_time")));

                    mParserCategory
                            .setStartdate(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("sub_s_time")));

                    mParserCategory
                            .setStatus(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("sub_cat_status")));

                    mParserCategory
                            .setSubname(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("sub_cat_name")));

                    mParserCategory
                            .setWorkmode(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("working_mode")));

                    mParserCategory
                            .setEmpstime(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("emp_start_time")));

                    mParserCategory
                            .setEmpetime(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("emp_end_time")));

                    mGetCategoryList.add(mParserCategory);

                    mCursorCategory.moveToNext();
                }
            }
            mCursorCategory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mGetCategoryList;
    }


    public ArrayList<GetLocation> getLocation(int id) {

        openDataBase();
        ArrayList<GetLocation> mGetCategoryList = new ArrayList<GetLocation>();
        try {
            String sqlQuery = "SELECT * FROM location_master l, emp_task_master e, company_master c where l.loc_id= e.loc_id and e.cmid=c.cmid  and e.cmid ="+id;
            Cursor mCursorCategory = Query(sqlQuery);
            if (mCursorCategory != null) {
                mCursorCategory.moveToFirst();
                while (!mCursorCategory.isAfterLast()) {

                    GetLocation mParserCategory = new GetLocation();

                    mParserCategory
                            .setCmid((Integer.parseInt(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("cmid")))));
                    mParserCategory
                            .setLocid((Integer.parseInt(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("loc_id")))));
                    mParserCategory
                            .setEtid((Integer.parseInt(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("et_id")))));
                    mParserCategory
                            .setLocname(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("loc_name")));
                    mParserCategory
                            .setTaskname(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("et_name")));
                    mParserCategory
                            .setStime(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("et_start_time")));
                    mParserCategory
                            .setEtime(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("et_end_time")));
                    mParserCategory
                            .setEstime(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("et_emp_start_time")));
                    mParserCategory
                            .setEetime(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("et_emp_end_time")));
                    mParserCategory
                            .setStaus(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("et_status")));
                    mParserCategory
                            .setCname(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("cname")));
                    mGetCategoryList.add(mParserCategory);

                    mCursorCategory.moveToNext();
                }
            }
            mCursorCategory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mGetCategoryList;
    }
    public ArrayList<GetCompletePendingTask> getTaskReport() {

        openDataBase();
        ArrayList<GetCompletePendingTask> mGetCategoryList = new ArrayList<GetCompletePendingTask>();
        try {
            String sqlQuery = "SELECT * FROM emp_sub_task_master et, location_master l, company_master c  where et.loc_id=l.loc_id and l.cmid=c.cmid  and et.emp_id=1";
            Cursor mCursorCategory = Query(sqlQuery);
            if (mCursorCategory != null) {
                mCursorCategory.moveToFirst();
                while (!mCursorCategory.isAfterLast()) {

                    GetCompletePendingTask mParserCategory = new GetCompletePendingTask();

                    mParserCategory
                            .setCmid((Integer.parseInt(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("cmid")))));
                    mParserCategory
                            .setLocid((Integer.parseInt(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("loc_id")))));

                    mParserCategory
                            .setEsubid((Integer.parseInt(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("esub_id")))));

                    mParserCategory
                            .setCname(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("cname")));

                    mParserCategory
                            .setLocation(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("loc_name")));
                    mParserCategory
                            .setSubcat(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("sub_cat_name")));
                    mParserCategory
                            .setWorkmode(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("working_mode")));

                    mParserCategory
                            .setEdate(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("emp_e_assign_date")));

                    mParserCategory
                            .setEetime(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("sub_e_time")));

                    mParserCategory
                            .setEstime(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("sub_s_time")));

                    mParserCategory
                            .setMsdate(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("emp_m_assign_date")));
                    mParserCategory
                            .setMstime(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("emp_start_time")));

                    mParserCategory
                            .setMetime(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("emp_end_time")));
                    mGetCategoryList.add(mParserCategory);

                    mCursorCategory.moveToNext();
                }
            }
            mCursorCategory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mGetCategoryList;
    }

    public ArrayList<GetCompletePendingTask> getTaskDetail(int status) {

        openDataBase();
        ArrayList<GetCompletePendingTask> mGetCategoryList = new ArrayList<GetCompletePendingTask>();
        try {
            String sqlQuery = "SELECT * FROM emp_sub_task_master et, location_master l, company_master c  where et.loc_id=l.loc_id and l.cmid=c.cmid and  et.esub_id="+status;
            Cursor mCursorCategory = Query(sqlQuery);
            if (mCursorCategory != null) {
                mCursorCategory.moveToFirst();
                while (!mCursorCategory.isAfterLast()) {

                    GetCompletePendingTask mParserCategory = new GetCompletePendingTask();

                    mParserCategory
                            .setCmid((Integer.parseInt(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("cmid")))));
                    mParserCategory
                            .setLocid((Integer.parseInt(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("loc_id")))));

                    mParserCategory
                            .setEsubid((Integer.parseInt(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("esub_id")))));

                    mParserCategory
                            .setCname(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("cname")));

                    mParserCategory
                            .setLocation(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("loc_name")));
                    mParserCategory
                            .setSubcat(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("sub_cat_name")));
                    mParserCategory
                            .setWorkmode(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("working_mode")));

                    mParserCategory
                            .setEdate(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("emp_e_assign_date")));

                    mParserCategory
                            .setEetime(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("sub_e_time")));

                    mParserCategory
                            .setEstime(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("sub_s_time")));

                    mParserCategory
                            .setMsdate(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("emp_m_assign_date")));
                    mParserCategory
                            .setMstime(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("emp_start_time")));

                    mParserCategory
                            .setMetime(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("emp_end_time")));
                    mGetCategoryList.add(mParserCategory);

                    mCursorCategory.moveToNext();
                }
            }
            mCursorCategory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mGetCategoryList;
    }


    public ArrayList<GetCompletePendingTask> getCompletePendingTask(String status) {

        openDataBase();
        ArrayList<GetCompletePendingTask> mGetCategoryList = new ArrayList<GetCompletePendingTask>();
        try {
            String sqlQuery = "SELECT * FROM emp_sub_task_master et, location_master l, company_master c  where et.loc_id=l.loc_id and l.cmid=c.cmid and et.sub_cat_status like "+"'"+status+"'"+" and et.emp_id=1";
            Cursor mCursorCategory = Query(sqlQuery);
            if (mCursorCategory != null) {
                mCursorCategory.moveToFirst();
                while (!mCursorCategory.isAfterLast()) {

                    GetCompletePendingTask mParserCategory = new GetCompletePendingTask();

                    mParserCategory
                            .setCmid((Integer.parseInt(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("cmid")))));
                    mParserCategory
                            .setLocid((Integer.parseInt(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("loc_id")))));

                    mParserCategory
                            .setEsubid((Integer.parseInt(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("esub_id")))));

                    mParserCategory
                            .setCname(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("cname")));

                    mParserCategory
                            .setLocation(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("loc_name")));
                    mParserCategory
                            .setSubcat(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("sub_cat_name")));
                    mParserCategory
                            .setWorkmode(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("working_mode")));
                    mGetCategoryList.add(mParserCategory);

                    mCursorCategory.moveToNext();
                }
            }
            mCursorCategory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mGetCategoryList;
    }

    public ArrayList<GetProfile> getProfile( ) {

        openDataBase();
        ArrayList<GetProfile> mGetCategoryList = new ArrayList<GetProfile>();
        try {
            String sqlQuery = "SELECT * FROM employee_profile_master where prid=1";
            Cursor mCursorCategory = Query(sqlQuery);
            if (mCursorCategory != null) {
                mCursorCategory.moveToFirst();
                while (!mCursorCategory.isAfterLast()) {

                    GetProfile mParserCategory = new GetProfile();

                    mParserCategory
                            .setPrid((Integer.parseInt(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("prid")))));
                    mParserCategory
                            .setAddress(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("address")));
                    mParserCategory
                            .setPin(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("epincode")));
                    mParserCategory
                            .setCity(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("ecity")));
                    mParserCategory
                            .setCountry(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("ecountry")));
                    mParserCategory
                            .setFname(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("fname")));

                    mParserCategory
                            .setLnam(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("lname")));
                    mParserCategory
                            .setState(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("estate")));
                    mParserCategory
                            .setMobile(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("ephone")));

                     mGetCategoryList.add(mParserCategory);

                    mCursorCategory.moveToNext();
                }
            }
            mCursorCategory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mGetCategoryList;
    }

    public ArrayList<GetCompany> getComapnyName( ) {

        openDataBase();
        ArrayList<GetCompany> mGetCategoryList = new ArrayList<GetCompany>();
        try {
            String sqlQuery = "SELECT * FROM company_master c, employee_master e where e.cmid=c.cmid and e.emp_id=1";
            Cursor mCursorCategory = Query(sqlQuery);
            if (mCursorCategory != null) {
                mCursorCategory.moveToFirst();
                while (!mCursorCategory.isAfterLast()) {

                    GetCompany mParserCategory = new GetCompany();

                    mParserCategory
                            .setCmid((Integer.parseInt(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("cmid")))));
                    mParserCategory
                            .setCaddress(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("caddress")));
                    mParserCategory
                            .setCarea(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("carea")));
                    mParserCategory
                            .setCname(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("cname")));
                    mParserCategory
                            .setCphone(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("cphone")));
                    mParserCategory
                            .setCstatus(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("cstatus")));

                    mGetCategoryList.add(mParserCategory);

                    mCursorCategory.moveToNext();
                }
            }
            mCursorCategory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mGetCategoryList;
    }

    public ArrayList<GetAreaActivity> getAreaActivityDropDown(int cid) {

        openDataBase();
        ArrayList<GetAreaActivity> mGetCategoryList = new ArrayList<GetAreaActivity>();
        GetAreaActivity mParserCategory1 = new GetAreaActivity();
        mParserCategory1.setName("Select Activity");
        mGetCategoryList.add(0, mParserCategory1);

        try {
            String sqlQuery = "SELECT * FROM cleaning_area_master where cmid="+cid;
            Cursor mCursorCategory = Query(sqlQuery);
            if (mCursorCategory != null) {
                mCursorCategory.moveToFirst();
                while (!mCursorCategory.isAfterLast()) {

                    GetAreaActivity mParserCategory = new GetAreaActivity();

                    mParserCategory
                            .setName(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("activity")));

                    mGetCategoryList.add(mParserCategory);

                    mCursorCategory.moveToNext();
                }
            }
            mCursorCategory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mGetCategoryList;
    }


    public ArrayList<GetAreaCode> getAreaCodeDropDown(int cid) {

        openDataBase();
        ArrayList<GetAreaCode> mGetCategoryList = new ArrayList<GetAreaCode>();
        GetAreaCode mParserCategory1 = new GetAreaCode();
        mParserCategory1.setName("Select Area Code");
        mGetCategoryList.add(0, mParserCategory1);

        try {
            String sqlQuery = "SELECT * FROM cleaning_area_master where cmid="+cid;
            Cursor mCursorCategory = Query(sqlQuery);
            if (mCursorCategory != null) {
                mCursorCategory.moveToFirst();
                while (!mCursorCategory.isAfterLast()) {

                    GetAreaCode mParserCategory = new GetAreaCode();

                     mParserCategory
                            .setName(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("area_code")));
                    mParserCategory
                            .setArea(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("area")));

                    mGetCategoryList.add(mParserCategory);

                    mCursorCategory.moveToNext();
                }
            }
            mCursorCategory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mGetCategoryList;
    }

    public ArrayList<GetEmployee> getEmployeeDropDown(int cid) {

        openDataBase();
        ArrayList<GetEmployee> mGetCategoryList = new ArrayList<GetEmployee>();
        GetEmployee mParserCategory1 = new GetEmployee();
        mParserCategory1.setId(0);
        mParserCategory1.setName("Select Employee");
        mGetCategoryList.add(0, mParserCategory1);

        try {
            String sqlQuery = "SELECT * FROM employee_master where cmid="+cid;
            Cursor mCursorCategory = Query(sqlQuery);
            if (mCursorCategory != null) {
                mCursorCategory.moveToFirst();
                while (!mCursorCategory.isAfterLast()) {

                    GetEmployee mParserCategory = new GetEmployee();

                    mParserCategory
                            .setId((Integer.parseInt(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("emp_id")))));
                    mParserCategory
                            .setName(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("emp_name")));

                    mGetCategoryList.add(mParserCategory);

                    mCursorCategory.moveToNext();
                }
            }
            mCursorCategory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mGetCategoryList;
    }

    public ArrayList<GetCompany> getCompanyDropDown(int id) {

        openDataBase();
        ArrayList<GetCompany> mGetCategoryList = new ArrayList<GetCompany>();
        GetCompany mParserCategory1 = new GetCompany();
        mParserCategory1.setCmid(0);
        mParserCategory1.setCname("Select Company");
        mGetCategoryList.add(0,mParserCategory1);

        try {
            String sqlQuery = "SELECT * FROM company_master where cmid="+id;
            Cursor mCursorCategory = Query(sqlQuery);
            if (mCursorCategory != null) {
                mCursorCategory.moveToFirst();
                while (!mCursorCategory.isAfterLast()) {

                    GetCompany mParserCategory = new GetCompany();

                    mParserCategory
                            .setCmid((Integer.parseInt(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("cmid")))));
                    mParserCategory
                            .setCaddress(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("caddress")));
                    mParserCategory
                            .setCarea(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("carea")));
                    mParserCategory
                            .setCname(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("cname")));
                    mParserCategory
                            .setCphone(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("cphone")));
                    mParserCategory
                            .setCstatus(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("cstatus")));

                    mGetCategoryList.add(mParserCategory);

                    mCursorCategory.moveToNext();
                }
            }
            mCursorCategory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mGetCategoryList;
    }

    public ArrayList<GetCompany> getComapny() {

        openDataBase();
        ArrayList<GetCompany> mGetCategoryList = new ArrayList<GetCompany>();
        try {
            String sqlQuery = "SELECT * FROM company_master";
            Cursor mCursorCategory = Query(sqlQuery);
            if (mCursorCategory != null) {
                mCursorCategory.moveToFirst();
                while (!mCursorCategory.isAfterLast()) {

                    GetCompany mParserCategory = new GetCompany();

                    mParserCategory
                            .setCmid((Integer.parseInt(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("cmid")))));
                    mParserCategory
                            .setCaddress(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("caddress")));
                    mParserCategory
                            .setCarea(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("carea")));
                    mParserCategory
                            .setCname(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("cname")));
                    mParserCategory
                            .setCphone(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("cphone")));
                    mParserCategory
                            .setCstatus(mCursorCategory.getString(mCursorCategory
                                    .getColumnIndex("cstatus")));

                    mGetCategoryList.add(mParserCategory);

                    mCursorCategory.moveToNext();
                }
            }
            mCursorCategory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mGetCategoryList;
    }
    public int Insert(String table, ContentValues values)
    {
        int id = 0;
        try
        {
            openDataBase();
            id = (int) db.insertOrThrow(table, null, values);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return id;
    }
    public int onInsertProject(String table, ContentValues Values) {
        int id = -1;
        try {
            openDataBase();
            // id=(int) db.insertOrThrow(table, null,Values);
            db.insertOrThrow(table, null, Values);
            // String sql="INSERT INTO " +table+ " VALUES('" +Values+ "')";
            // db.execSQL(sql);
            System.out.println("Record successfully...Inserted..###");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Query couldnt complete...## " + e);
        }
        return id;
    }
    /**
     * Use this method to search a particular String in the provided field.
     *
     *
     * @param columns The array of columns to be returned
     * @param table The table name
     * @param whereColumn The where clause specifying a particular columns
     * @param keyword The keyword which is to be searched
     *
     * @return The cursor containing the result of the query
     */

}