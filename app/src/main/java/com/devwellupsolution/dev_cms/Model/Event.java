package com.devwellupsolution.dev_cms.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by OnesTech on 23/08/2017.
 */

public class Event {
    private String title, message, startDate, endDate;

    public String getJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("title", title);
        jsonObject.accumulate("message", message);
        jsonObject.accumulate("startDate", startDate);
        jsonObject.accumulate("endDate", endDate);

        JSONObject rootObject = new JSONObject();
        rootObject.accumulate("event", jsonObject);
        return rootObject.toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
