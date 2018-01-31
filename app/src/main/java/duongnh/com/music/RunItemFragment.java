package duongnh.com.music;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Admin on 1/24/2018.
 */

public class RunItemFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private MainActivity mMainActivity;
    private TextView mName, mSingger, mTimeS, mTimeE;
    private ImageView mLike, mPrevious, mPlay, mNext, mDisLike;
    private SeekBar mSeekBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.click_item_music, container, false);
        mMainActivity = (MainActivity) getActivity();
        initView(view);
        binData();
        initEvent();
        return view;
    }

    private void initView(View view) {
        mName = view.findViewById(R.id.tv_name);
        mSingger = view.findViewById(R.id.tv_singger);
        mLike = view.findViewById(R.id.iv_like);
        mDisLike = view.findViewById(R.id.iv_dislike);
        mNext = view.findViewById(R.id.iv_next);
        mPrevious = view.findViewById(R.id.iv_pre);
        mPlay = view.findViewById(R.id.iv_play);
        mSeekBar = view.findViewById(R.id.seek_bar);
        mTimeS = view.findViewById(R.id.tv_time_start);
        mTimeE = view.findViewById(R.id.tv_time_end);
    }

    public void refresh() {
        binData();
    }

    private void binData() {
        Song mSong = mMainActivity.getListSong().get(mMainActivity.index);

        updateMusic();
    }

    private void initEvent() {
        mPlay.setOnClickListener(this);
        mPrevious.setOnClickListener(this);
        mNext.setOnClickListener(this);
        mDisLike.setOnClickListener(this);
        mLike.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_dislike:
                Toast.makeText(mMainActivity, "Không yêu thích", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_pre:
                mMainActivity.back();
                updateMusic();
                break;
            case R.id.iv_play:
                if (!mMainActivity.playMusic()) {
                    mPlay.setImageResource(R.mipmap.ic_play_arrow_black_24dp);
                } else {
                    updateMusic();
                }
                break;
            case R.id.iv_next:
                mMainActivity.next();
                updateMusic();
                break;
            case R.id.iv_like:
                Toast.makeText(mMainActivity, "Yêu thích", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void updateMusic() {
        Song s = mMainActivity.getCurrentSong();
        mName.setText(s.getmName());
        mSingger.setText(s.getmArtist());
        mPlay.setImageResource(R.mipmap.ic_pause_black_24dp);
        mTimeE.setText(getDuration(s.getmDuration()));
        new UpdateSeekBar().execute();

    }
    public String getDuration(int duration) {
        SimpleDateFormat date = new SimpleDateFormat("mm:ss");
        return date.format(new Date(duration));
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mMainActivity.seek(seekBar.getProgress());
    }

    public class UpdateSeekBar extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            while (mMainActivity.isStarted()) {
                try {
                    Thread.sleep(1000);
                    publishProgress();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            mTimeS.setText(mMainActivity.getCurrentTimeText());
            mSeekBar.setProgress(mMainActivity.getCurrentTime());
        }
    }
}
