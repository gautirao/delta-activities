package com.delta.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.net.URI;
import java.util.List;

/**
 * Created by grao on 23/03/2017.
 */

public class DetailActivity extends Activity {

    private Spinner mSpinner = null;
    private Button mReturnButton = null;
    private Button mPerformButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle extras = getIntent().getExtras();

        if(extras!= null){
            String detailedValue = extras.getString("KeyForSending");
            if(detailedValue!=null){
                Toast.makeText(this,detailedValue,Toast.LENGTH_SHORT).show();
            }
        }
        mSpinner = (Spinner) findViewById(R.id.spinnerSelection);
        mReturnButton=(Button)findViewById(R.id.returnToSecondActivity);
        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                String mySelection = mSpinner.getSelectedItem().toString();
                returnIntent.putExtra("KeyForReturning",mySelection);
                setResult(RESULT_OK,returnIntent);
                finish();
            }
        });

        mPerformButton = (Button)findViewById(R.id.performImplicit);
        mPerformButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = mSpinner.getSelectedItemPosition();
                Intent implicitIntent = null;

                switch(position){

                    case 0:
                        break;
                    case 1:
                        implicitIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://google.co.uk"));
                        break;
                    case 2: // call some one
                        implicitIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:(+91)9886966047"));
                        break;
                    case 3: // map
                        implicitIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:38.124,-97.123"));
                        break;
                    case 4: //take a picture
                        implicitIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                        break;
                    case 5: // edit first contact
                        implicitIntent= new Intent(Intent.ACTION_EDIT,Uri.parse("content://contacts/people/1"));
                        break;

                }

                if(implicitIntent!=null){
                    if(isIntentAvailable(implicitIntent)){
                        startActivity(implicitIntent);
                    }else{
                        Toast.makeText(view.getContext(),"no application is available",Toast.LENGTH_SHORT);
                    }
                }
            }
        });

    }

    private boolean isIntentAvailable(Intent implicitIntent) {

        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(implicitIntent,0);
        boolean isIntentSafe = activities.size()>0;
        return isIntentSafe;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.first, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
