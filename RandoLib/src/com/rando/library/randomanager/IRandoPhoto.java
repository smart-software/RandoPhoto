package com.rando.library.randomanager;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by SERGant on 21.10.2014.
 */
public interface IRandoPhoto {
    public String GetRandoID();
    public int GetLikesCount();
    public String GetCommentsCount(); // not implemented, because of dynamically changing behaviour. Better do this throw methods. 
    public List<String> getReviewersIds();
    public Date LastLikeAt();
    public Date GetCreatedAt();
    public String GetCreatedBy();
    public File GetPhoto();
    public String GetPhotoUrl();
    public String GetTitle();
}
