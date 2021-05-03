package com.example.ideaone;

public class topIdea {
    private String ideatitle;
    private String ideacontext;
    private String like;
    private String topId;
    private String ideaaddress;
    private String ideacontact;
    private String ideauid;

    public topIdea(String ideatitle,String ideacontext,String like,String topId){
        this.ideatitle = ideatitle;
        this.ideacontext = ideacontext;
        this.like = like;
        this.topId = topId;
    }

    public topIdea(String ideatitle,String ideacontext,String like,String topId,String ideaaddress,String ideacontact,String ideauid){
        this.ideatitle = ideatitle;
        this.ideacontext = ideacontext;
        this.like = like;
        this.topId = topId;
        this.ideaaddress = ideaaddress;
        this.ideacontact = ideacontact;
        this.ideauid = ideauid;
    }

    public String getIdeatitle(){
        return  ideatitle;
    }

    public String getIdeacontext(){
        return ideacontext;
    }

    public String getLike() {
        return like;
    }

    public String getTopId() {
        return topId;
    }

    public String getIdeaaddress() {
        return ideaaddress;
    }

    public String getIdeacontact() {
        return ideacontact;
    }

    public String getIdeauid() {
        return ideauid;
    }
}
