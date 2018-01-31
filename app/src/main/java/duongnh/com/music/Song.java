package duongnh.com.music;

/**
 * Created by Admin on 3/28/2017.
 */
public class Song {
    private String mName, mPath, mAlbum, mArtist;
    private int mDuration;

    public Song(String name, String path, String album, String artist, int duration) {
        this.mName = name;
        this.mPath = path;
        this.mAlbum = album;
        this.mArtist = artist;
        this.mDuration = duration;
    }

    public String getmName() {
        return mName;
    }

    public String getmPath() {
        return mPath;
    }

    public String getmAlbum() {
        return mAlbum;
    }

    public String getmArtist() {
        return mArtist;
    }

    public int getmDuration() {
        return mDuration;
    }

    @Override
    public String toString() {
        return "Name: "+ mName +"\n"
                +"Path: "+ mPath +"\n"
                +"Album: "+ mAlbum +"\n"
                +"Artist: "+ mArtist +"\n"
                +"Duration: "+ mDuration +"\n";
    }
}
