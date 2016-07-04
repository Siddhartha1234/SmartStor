package com.example.hp.smartstor;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.hp.smartstor.CloudFileExplorer.ViewExplorer;
import com.example.hp.smartstor.CloudMusicManager.MusicActivity;
import com.nononsenseapps.filepicker.FilePickerActivity;

import net.gotev.uploadservice.UploadServiceBroadcastReceiver;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private DrawerLayout fullLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private int selectedNavItemId;
    public static String url,rooturl;
    public static String filesToUpload;
    public static ArrayList<rowItem> devices=new ArrayList<rowItem>();
    public String TAG = "UploadService";
    public int FILE_CODE=1;
    public boolean askedToPause=Boolean.FALSE;
    public static int index;
    private final UploadServiceBroadcastReceiver uploadReceiver =
            new UploadServiceBroadcastReceiver() {
                @Override
                public void onProgress(String uploadId, int progress) {
                    Log.i(TAG, "The progress of the upload with ID " + uploadId + " is: " + progress);

                }

                @Override
                public void onError(String uploadId, Exception exception) {
                    Log.e(TAG, "Error in upload with ID: " + uploadId + ". "
                            + exception.getLocalizedMessage(), exception);
                }

                @Override
                public void onCompleted(String uploadId, int serverResponseCode, byte[] serverResponseBody) {
                    Log.i(TAG, "Upload with ID " + uploadId + " is completed: " + serverResponseCode + ", "
                            + new String(serverResponseBody));
                }

                @Override
                public void onCancelled(String uploadId) {
                    Log.i(TAG, "Upload with ID " + uploadId + " is cancelled");
                }
            };

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        /**
         * This is going to be our actual root layout.
         */
        fullLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        /**
         * {@link FrameLayout} to inflate the child's view. We could also use a {@link android.view.ViewStub}
         */
        FrameLayout activityContainer = (FrameLayout) fullLayout.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);

        /**
         * Note that we don't pass the child's layoutId to the parent,
         * instead we pass it our inflated layout.
         */
        super.setContentView(fullLayout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigationView);

        if (useToolbar())
        {
            setSupportActionBar(toolbar);
        }
        else
        {
            toolbar.setVisibility(View.GONE);
        }

        setUpNavView();
    }

    /**
     * Helper method that can be used by child classes to
     * specify that they don't want a {@link Toolbar}
     * @return true
     */
    protected boolean useToolbar()
    {
        return true;
    }

    protected void setUpNavView()
    {
        navigationView.setNavigationItemSelectedListener(this);

        if( useDrawerToggle()) { // use the hamburger menu
            drawerToggle = new ActionBarDrawerToggle(this, fullLayout, toolbar,
                    R.string.nav_drawer_opened,
                    R.string.nav_drawer_closed);

            fullLayout.setDrawerListener(drawerToggle);
            drawerToggle.syncState();
        } else if(useToolbar() && getSupportActionBar() != null) {
            // Use home/back button instead
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(getResources()
                    .getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        }
    }

    /**
     * Helper method to allow child classes to opt-out of having the
     * hamburger menu.
     * @return
     */
    protected boolean useDrawerToggle()
    {
        return true;
    }



    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        fullLayout.closeDrawer(GravityCompat.START);
        selectedNavItemId = menuItem.getItemId();

        return onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id)
        {
            case R.id.nav_peers:
                startActivity(new Intent(this, NetworkDiscovery.class));
                finish();
                return true;

            case R.id.nav_music:
                startActivity(new Intent(this, MusicActivity.class));
                return true;

            case R.id.nav_files :
                startActivity(new Intent(this, MainActivity.class));
                return true;

            case R.id.nav_gallery :
                startActivity(new Intent(this, NoToolbar.class));
                return true;
            case R.id.explorer_rview:
                startActivity(new Intent(this, ViewExplorer.class));
        }

        return super.onOptionsItemSelected(item);
    }
    public void addfiles(View v)
    {
        if(url==null)
        {
            Toast.makeText(getApplicationContext(),"Connect to any peer before uploading",Toast.LENGTH_LONG).show();
        }
        else
        {
            Snackbar.make(v, "Select One or more than one files to upload.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            // Starts NoNonsense-FilePicker (https://github.com/spacecowboy/NoNonsense-FilePicker)
            Intent intent = new Intent(this, FilePickerActivity.class);

            intent.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, true);
            intent.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
            intent.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);

            // Configure initial directory by specifying a String.
            // You could specify a String like "/storage/emulated/0/", but that can
            // dangerous. Always use Android's API calls to get paths to the SD-card or
            // internal memory.
            intent.putExtra(FilePickerActivity.EXTRA_START_PATH,
                    Environment.getExternalStorageDirectory().getPath());

            startActivityForResult(intent, FILE_CODE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_CODE && resultCode == Activity.RESULT_OK) {
            List<Uri> resultUris = new ArrayList<>();

            if (data.getBooleanExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false)) {
                // For JellyBean and above
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ClipData clip = data.getClipData();

                    if (clip != null) {
                        for (int i = 0; i < clip.getItemCount(); i++) {
                            resultUris.add(clip.getItemAt(i).getUri());
                        }
                    }

                    // For Ice Cream Sandwich
                } else {
                    ArrayList<String> paths = data.getStringArrayListExtra(FilePickerActivity.EXTRA_PATHS);

                    if (paths != null) {
                        for (String path : paths) {
                            resultUris.add(Uri.parse(path));
                        }
                    }
                }
            } else {
                resultUris.add(data.getData());
            }

            StringBuilder absolutePathsConcat = new StringBuilder();
            for (Uri uri : resultUris) {
                if (absolutePathsConcat.length() == 0) {
                    absolutePathsConcat.append(new File(uri.getPath()).getAbsolutePath());
                } else {
                    absolutePathsConcat.append(",").append(new File(uri.getPath()).getAbsolutePath());
                }
            }
            filesToUpload = absolutePathsConcat.toString();

                MultiPartUpload Upload = new MultiPartUpload(getApplicationContext());
                Toast.makeText(getApplicationContext(), "Uploading" + filesToUpload + "to" + url, Toast.LENGTH_LONG).show();
                Upload.Upload(url, filesToUpload);

        }
    }


}
