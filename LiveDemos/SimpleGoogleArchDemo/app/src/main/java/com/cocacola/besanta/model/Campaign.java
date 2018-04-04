package com.cocacola.besanta.model;

import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.PropertyName;

import java.util.Date;

/**
 * Story POJO.
 */
@IgnoreExtraProperties
public class Campaign extends Model {

    public static final String FIELD_USER_FULL_NAME = "user_full_name";

    @PropertyName("image_url")
    public String imageUrl;
    @PropertyName("timestamp")
    public Date timestamp;
    @PropertyName("text")
    public String text;
    @PropertyName("campaign_goal")
    public int campaignGoal;
    @PropertyName("campaign_current_points")
    public int campaignCurrentPoints;

    public Campaign() {
    }

    public Campaign(String imageUrl, String text) {
        this.imageUrl = imageUrl;
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Campaign campaign = (Campaign) o;

        if (campaignGoal != campaign.campaignGoal) return false;
        if (campaignCurrentPoints != campaign.campaignCurrentPoints) return false;
        if (imageUrl != null ? !imageUrl.equals(campaign.imageUrl) : campaign.imageUrl != null)
            return false;
        if (timestamp != null ? !timestamp.equals(campaign.timestamp) : campaign.timestamp != null)
            return false;
        return text != null ? text.equals(campaign.text) : campaign.text == null;
    }

    @Override
    public int hashCode() {
        int result = imageUrl != null ? imageUrl.hashCode() : 0;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + campaignGoal;
        result = 31 * result + campaignCurrentPoints;
        return result;
    }
}
