package duongnh.com.music;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.widget.Toast;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.checkSelfPermission;

public class MainActivity extends AppCompatActivity{

    //
    private Fragment mTemp = null;
    private ListMusicFragment mListMusicFragment = new ListMusicFragment();
    private RunItemFragment mRunItemFragment = new RunItemFragment();
    private MediaManager mMediaManager;
    public int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        initFragment();
        initPermission();
        mMediaManager = new MediaManager(getApplicationContext());
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Permision Write File is Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Permision Write File is Denied", Toast.LENGTH_SHORT).show();

            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void initPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                //Permisson don't granted
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(MainActivity.this, "Permission isn't granted ", Toast.LENGTH_SHORT).show();
                }
                // Permisson don't granted and dont show dialog again.
                else {
                    Toast.makeText(MainActivity.this, "Permisson don't granted and dont show dialog again ", Toast.LENGTH_SHORT).show();
                }
                //Register permission
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            }
        }
    }
    public ArrayList<Song> getListSong(){
        return mMediaManager.getListSong();
    }
    public boolean playMusic(int position){

       return mMediaManager.play(position);
    }
    public boolean playMusic(){
        return mMediaManager.play();
    }
    public Song getCurrentSong(){
        return mMediaManager.getCurrentSong();
    }
    public boolean next(){
        return mMediaManager.next();
    }
    public boolean back(){
        return mMediaManager.back();
    }
    public void seek(int a){
        mMediaManager.seek(a);
    }
    public boolean isStarted(){
        return mMediaManager.isStarted();
    }
    public int getCurrentTime(){
        return  mMediaManager.getCurrentTime();
    }
    public String getCurrentTimeText(){
        return mMediaManager.getCurrentTimeText();
    }
    private void initFragment() {
        mTemp = mListMusicFragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.ll_main, mListMusicFragment);
        transaction.add(R.id.ll_main, mRunItemFragment);
        transaction.hide(mRunItemFragment);
        transaction.commit();
    }
    public void showFragmnet(Fragment hide, Fragment show){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide(hide);
        transaction.show(show);
        mTemp = show;
        transaction.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_view).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                adapter.filter(query.trim());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    public ListMusicFragment getmListMusicFragment() {
        return mListMusicFragment;
    }

    public RunItemFragment getmRunItemFragment() {
        mRunItemFragment.refresh();
        return mRunItemFragment;
    }

    @Override
    public void onBackPressed() {
        showFragmnet(getmRunItemFragment(), getmListMusicFragment());

    }
}
