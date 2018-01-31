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
        mMediaManager = new MediaManager(getApplicationContext());
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
