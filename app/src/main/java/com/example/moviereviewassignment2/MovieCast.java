
package com.example.moviereviewassignment2;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieCast implements Serializable
{

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("cast")
    @Expose
    private List<CastModel> cast = null;
    @SerializedName("crew")
    @Expose
    private List<CrewModel> crew = null;
    private final static long serialVersionUID = 1227730618350775398L;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<CastModel> getCast() {
        return cast;
    }

    public void setCast(List<CastModel> cast) {
        this.cast = cast;
    }

    public List<CrewModel> getCrew() {
        return crew;
    }

    public void setCrew(List<CrewModel> crew) {
        this.crew = crew;
    }

}
