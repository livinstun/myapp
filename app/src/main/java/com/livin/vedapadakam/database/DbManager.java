package  com.livin.vedapadakam.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Typeface;

import com.livin.vedapadakam.model.ReadingModel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Livin D'cruz on 3/14/2016.
 */
public class DbManager extends DBConstants{

    private static DbManager _instance = null;
    Typeface mMalayalamTypeFace;

    public static DbManager getInstance()
    {
        if (_instance == null) {
            _instance = new DbManager();
        }
        return _instance;
    }
    private void initFont(Context paramContext)
    {
        this.mMalayalamTypeFace = Typeface.createFromAsset(paramContext.getAssets(), "fonts/Thiruvachanam.ttf");
    }

    public void checkDataBase(Context context) {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(DB_NAME, null, SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
           try {
               copyDataBase(context);
           }
           catch (IOException ie)
           {
             System.out.println(ie.getMessage());
           }
        }
    }

    private void copyDataBase(Context context) throws IOException {

        // Open your local db as the input stream
        InputStream myInput = context.getAssets().open("database/"+DB_NAME);
        // Path to the just created empty db
        String outFileName =  "/data/data/" +context.getPackageName() +"/databases/" + DB_NAME;
        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);

        }
        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
    public ArrayList<ReadingModel> getReading(Context context,String date)
    {
        ArrayList<ReadingModel> model = new ArrayList<ReadingModel>();
            SQLiteDatabase db;
            String path = context.getDatabasePath(DB_NAME).getPath();
            db = SQLiteDatabase.openDatabase(path, null, 0);
            //db = SQLiteDatabase.openDatabase("iMenu_app",SQLiteDatabase.OPEN_READWRITE, null);
            Cursor c = db.rawQuery("SELECT "+COL_ID+","+COL_DATE+","+COL_HEADING+","+COL_R1+","+COL_P+","+COL_R2+","+COL_S+","+COL_P2+" FROM "+TABLE_READING+" WHERE "+COL_DATE+" = '"+date+"' ORDER BY "+COL_ID, null);
            //System.out.println("SELECT "+COL_ID+","+COL_DATE+","+COL_HEADING+","+COL_R1+","+COL_P+","+COL_R2+","+COL_S+","+COL_P2+" FROM "+TABLE_READING+" WHERE "+COL_DATE+" = "+date);
            while (c.moveToNext() ){
              model.add(new ReadingModel(c));
            }
            c.close();
            db.close();

        return model;
    }
}
