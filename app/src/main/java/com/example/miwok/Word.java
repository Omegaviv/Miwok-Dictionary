package com.example.miwok;

public class Word {

    private String mDefaultTranslation;

    private String  mMiwokTranslation;

    private static final int NO_IMAGW = -1;

    private int mImageResourceId = NO_IMAGW;

    private int mAudioResourceId;

    public Word(String defaultTranslation, String miwokTranslation ,int audioResourceId){

        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;

    }
    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId , int audioResourceId){

        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;

    }
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public int getImageResourceId(){ return mImageResourceId;}

    public int getmAudioResourceId(){return  mAudioResourceId;}

    public boolean hasImage(){
        return mImageResourceId != NO_IMAGW;
    }
}
