package com.example.ideaone;

public class idea {
    private String ideatitle;
    private String ideacontext;
    private String like;

    public idea(String ideatitle,String ideacontext,String like){
        this.ideatitle = ideatitle;
        this.ideacontext = ideacontext;
        this.like = like;
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

}
