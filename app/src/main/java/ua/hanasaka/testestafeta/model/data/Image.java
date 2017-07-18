
package ua.hanasaka.testestafeta.model.data;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("display_sizes")
    @Expose
    private List<DisplaySize> displaySizes = null;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("uri_oembed")
    @Expose
    private Object uriOembed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<DisplaySize> getDisplaySizes() {
        return displaySizes;
    }

    public void setDisplaySizes(List<DisplaySize> displaySizes) {
        this.displaySizes = displaySizes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getUriOembed() {
        return uriOembed;
    }

    public void setUriOembed(Object uriOembed) {
        this.uriOembed = uriOembed;
    }

}
