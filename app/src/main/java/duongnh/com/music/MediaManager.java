package duongnh.com.music;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class MediaManager {
    private static final String TAG = MediaManager.class.getName();
    private static final int sIDE = 0;
    private static final int sSTOPPED = 2;
    private static final int sPLAYING = 1;
    private static final int sPAUSED = 3;
    private ArrayList<Song> mListSong;
    private Context mContext;
    private MediaPlayer mPlayer;
    private int mState = sIDE;
    private int mIndex = 0;
    private boolean isShuffle;

    public MediaManager(Context mContext) {
        this.mContext = mContext;
        initData();
        mPlayer = new MediaPlayer();
    }

    private void initData() {
        Uri audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String projections[] = new String[]{
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.AudioColumns.ALBUM,
                MediaStore.Audio.AudioColumns.ARTIST,
                MediaStore.Audio.AudioColumns.DURATION

        };
        String where = MediaStore.Audio.AudioColumns.DISPLAY_NAME +" LIKE '%.mp3'";
        Cursor c = mContext.getContentResolver().query(audioUri, projections,
                where, null, null);

        if(c == null){
            Log.e(TAG, "Error: Could not get audio list...");
            return;
        }
        c.moveToFirst();
        int indexTitle = c.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE);
        int indexData = c.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
        int indexAlbum = c.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM);
        int indexArtist = c.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST);
        int indexDuration = c.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION);

        String name, path, album, artist;
        int duration;
        mListSong = new ArrayList<>();
        while (!c.isAfterLast()){
            name = c.getString(indexTitle);
            path = c.getString(indexData);
            album = c.getString(indexAlbum);
            artist = c.getString(indexArtist);
            duration = c.getInt(indexDuration);
            mListSong.add(new Song(name, path, album, artist, duration));
            c.moveToNext();
        }
        c.close();
        Log.i(TAG, mListSong.toString());
    }

//    public MediaPlayer initMediaPlayerByRawId(int soundId, boolean isLooping){
//        MediaPlayer player = MediaPlayer.create(mContext, soundId);
//        player.setLooping(isLooping);
//        return player;
//    }

    public ArrayList<Song> getListSong() {
        return mListSong;
    }

    public boolean play(){
        if(mState == sIDE || mState == sSTOPPED) {
            Song song = mListSong.get(mIndex);
        }
            try {
                if(mState == sIDE || mState == sSTOPPED) {
                    Song song = mListSong.get(mIndex);
                    mPlayer.setDataSource(song.getmPath());
                    mPlayer.prepare();
                    mPlayer.start();
                    mState = sPLAYING;
                    return true;
                }
                else if(mState == sPLAYING){
                    pause();
                    return false;
                }
                else if(mState == sPAUSED){
                    mPlayer.start();
                    mState = sPLAYING;
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        return false;

    }
    public boolean next(){
        if(isShuffle){
            mIndex = new Random().nextInt(mListSong.size());
        }
        else{
            mIndex = (mIndex + 1) % mListSong.size();
        }
        stop();
        return play();
    }
    public void stop(){
        if(mState == sPLAYING || mState == sPAUSED){
            mPlayer.stop();
            mPlayer.reset();
            mState = sSTOPPED;
        }
    }
    public boolean back(){
        if(mIndex == 0){
            mIndex = mListSong.size();
        }
        mIndex -- ;
        stop();
        return play();
    }
    public void pause(){
        mPlayer.pause();
        mState = sPAUSED;
    }
    public void repeatOne(){
        // lap 1 bai
    }
    public void repeatAll(){
        //lap tat ca
    }
    public void repeatOff(){
        //ko lap lai
    }


    public Song getCurrentSong() {
        return mListSong.get(mIndex);
    }

    public String getIndexText() {
        return ((mIndex+1) + "/"+ mListSong.size());
    }

    public String getDuration(int duration) {
        SimpleDateFormat date = new SimpleDateFormat("mm:ss");
        return date.format(new Date(duration));
    }

    public boolean play(int position) {
        stop();
        mIndex = position;
        return play();
    }

    public void seek(int progess) {
        mPlayer.seekTo(progess);
    }

    public boolean isStarted() {
        return mState == sPLAYING || mState == sPAUSED;
    }

    public String getCurrentTimeText() {
        int currentTime = mPlayer.getCurrentPosition();
        int total = mListSong.get(mIndex).getmDuration();
        return getDuration(currentTime);
    }

    public int getCurrentTime() {
        return mPlayer.getCurrentPosition();
    }
}
