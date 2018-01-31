package duongnh.com.music;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.checkSelfPermission;

/**
 * Created by Admin on 1/24/2018.
 */

public class ListMusicFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private MainActivity mMainActivity;
    private ListView mListView;
    private SongAdapter mSongAdapter;
    private LinearLayout mLinearLayout;
    private TextView mName, mSingger;
    private ImageView mPlay;
    private ArrayList<Song> mListSong;
    private View oldView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        mMainActivity = (MainActivity) getActivity();
        initView(view);
        binData();
        initEvent();
        return view;
    }

    private void initView(View view) {
        mListView = view.findViewById(R.id.list_music);
        mLinearLayout = view.findViewById(R.id.tab_bottom);
        mName = view.findViewById(R.id.tv_name);
        mSingger = view.findViewById(R.id.tv_singger);
        mPlay = view.findViewById(R.id.iv_play);

    }

    private void binData() {

        mListSong = new ArrayList<>();
//        mediaManager = new MediaManager(mMainActivity);
        mListSong.addAll(mMainActivity.getListSong());
        mSongAdapter = new SongAdapter(mMainActivity, R.layout.item_music, mListSong);
        mListView.setAdapter(mSongAdapter);
        mLinearLayout.setVisibility(View.GONE);

    }


    private boolean checkAndroidPermissions() {
//        int permissionCAMERA = ContextCompat.checkSelfPermission(mMainActivity, Manifest.permission.CAMERA);
        int storagePermission = checkSelfPermission(mMainActivity, Manifest.permission.READ_EXTERNAL_STORAGE);
//        int callPhonePermission = ContextCompat.checkSelfPermission(mMainActivity, Manifest.permission.CALL_PHONE);
//        int locationPermission = ContextCompat.checkSelfPermission(mMainActivity, Manifest.permission.ACCESS_FINE_LOCATION);
//        int writePermission = ContextCompat.checkSelfPermission(mMainActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        int groupStorePermission = ContextCompat.checkSelfPermission(mMainActivity, Manifest.permission_group.STORAGE);
//
//
        List<String> listPermissionsNeeded = new ArrayList<>();
//        if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.CAMERA);
//        }
//        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
//        }
//        if (callPhonePermission != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
//        }
//        if (writePermission != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        }
//        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
//        }
//        if (groupStorePermission != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission_group.STORAGE);
//        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(mMainActivity,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
            return false;
        }

        return true;
    }

    private void initEvent() {
        mListView.setOnItemClickListener(this);
        mPlay.setOnClickListener(this);
        mLinearLayout.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mMainActivity.index = position;
        mMainActivity.playMusic(position);
        updateMusic();
        mLinearLayout.setVisibility(View.VISIBLE);
//        ImageView iv = view.findViewById(R.id.iv_click);
//
//        if(oldView != null){
//            oldView.setBackgroundColor(Color.parseColor("#00000000"));
//            iv.setVisibility(View.INVISIBLE);
//            Toast.makeText(mMainActivity, "AAAAAA", Toast.LENGTH_SHORT).show();
//        }
//        view.setBackgroundColor(Color.parseColor("#FBE9E7"));
//        oldView = view;
//        iv.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_play:
                if (mMainActivity.playMusic() == false) {
                    mPlay.setImageResource(R.mipmap.ic_play_arrow_black_24dp);
                } else {
                    updateMusic();
                }
                break;
            case R.id.tab_bottom:
                mMainActivity.showFragmnet(mMainActivity.getmListMusicFragment(), mMainActivity.getmRunItemFragment());
                break;
        }
    }
    public void updateMusic() {
        Song s = mMainActivity.getCurrentSong();
        mName.setText(s.getmName());
        mSingger.setText(s.getmArtist());
        mPlay.setImageResource(R.mipmap.ic_pause_black_24dp);
    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case 1:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    //Permission Granted Successfully. Write working code here.
//                } else {
//                    //You did not accept the request can not use the functionality.
//                }
//                break;
//            default:
//        }
//    }
}
