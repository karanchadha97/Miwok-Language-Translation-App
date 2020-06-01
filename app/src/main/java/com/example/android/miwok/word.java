package com.example.android.miwok;

public class word {
    private String englishTranslation;
    private String mivokTranslation;
    private int resourceId=-1;
    private int audioID;
    public word(String eT,String mT,int id)
    {
        englishTranslation = eT;
        mivokTranslation = mT;
        audioID=id;
    }
    public word(String eT,String mT,int id,int id1)
    {
        englishTranslation = eT;
        mivokTranslation = mT;
        resourceId=id;
        audioID=id1;
    }

    public String getEnglishTranslation()
    {
        return englishTranslation;
    }
    public String getMivokTranslation()
    {
        return mivokTranslation;
    }
    public int getResourceId()
    {
        return resourceId;
    }
    public boolean hasImage()
    {
        if(resourceId==-1)
            return false;
        else
            return true;
    }
    public int getAudioId()
    {
        return audioID;
    }

}
