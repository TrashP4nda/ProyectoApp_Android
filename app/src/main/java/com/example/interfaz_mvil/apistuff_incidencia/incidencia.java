package com.example.interfaz_mvil.apistuff_incidencia;

import com.google.gson.annotations.SerializedName;

public class incidencia {

    @SerializedName("autonomousRegion")
    private String autonomousRegion;

    @SerializedName("carRegistration")
    private String carRegistration;

    @SerializedName("cause")
    private String cause;

    @SerializedName("cityTown")
    private String cityTown;

    @SerializedName("direction")
    private String direction;

    @SerializedName("endDate")
    private String endDate;

    @SerializedName("incidenceDescription")
    private String incidenceDescription;

    @SerializedName("incidenceId")
    private String incidenceId;

    @SerializedName("incidenceLevel")
    private String incidenceLevel;

    public String getAutonomousRegion() {
        return autonomousRegion;
    }

    public String getCarRegistration() {
        return carRegistration;
    }

    public String getCause() {
        return cause;
    }

    public String getCityTown() {
        return cityTown;
    }

    public String getDirection() {
        return direction;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getIncidenceDescription() {
        return incidenceDescription;
    }

    public String getIncidenceId() {
        return incidenceId;
    }

    public String getIncidenceLevel() {
        return incidenceLevel;
    }

    public String getIncidenceName() {
        return incidenceName;
    }

    public String getIncidenceType() {
        return incidenceType;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getPkEnd() {
        return pkEnd;
    }

    public String getPkStart() {
        return pkStart;
    }

    public String getProvince() {
        return province;
    }

    public String getRoad() {
        return road;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getStartDate() {
        return startDate;
    }

    @SerializedName("incidenceName")
    private String incidenceName;

    @SerializedName("incidenceType")
    private String incidenceType;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("pkEnd")
    private String pkEnd;

    @SerializedName("pkStart")
    private String pkStart;

    @SerializedName("province")
    private String province;

    @SerializedName("road")
    private String road;

    @SerializedName("sourceId")
    private String sourceId;

    @SerializedName("startDate")
    private String startDate;

    // Getters and Setters for each field
    // ...
}
