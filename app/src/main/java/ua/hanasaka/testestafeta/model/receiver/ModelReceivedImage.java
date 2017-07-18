package ua.hanasaka.testestafeta.model.receiver;

import retrofit2.Call;
import ua.hanasaka.testestafeta.model.data.Img;

/**
 * Define interface for model of receive images
 */
public interface ModelReceivedImage {

    void getImages(String searchWord);

}
