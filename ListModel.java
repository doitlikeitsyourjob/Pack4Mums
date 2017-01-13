package com.doitlikeitsyourjob.pack4mums;

/**
 * Created by ndRandall on 13/01/2017.
 */

public class ListModel {

    private  String CompanyName="";
    private  String Image="";
    private  String Url="";

    /*********** Set Methods ******************/

    public void setCompanyName(String CompanyName)
    {
        this.CompanyName = CompanyName;
    }

    public void setImage(String Image)
    {
        this.Image = Image;
    }

    public void setUrl(String Url)
    {
        this.Url = Url;
    }

    /*********** Get Methods ****************/

    public String getCompanyName()
    {
        return this.CompanyName;
    }

    public String getImage()
    {
        return this.Image;
    }

    public String getUrl()
    {
        return this.Url;
    }
}
