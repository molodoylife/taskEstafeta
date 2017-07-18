package ua.hanasaka.testestafeta.model.dataBase;

import ua.hanasaka.testestafeta.model.data.Image;

/**
 * Interface for DataBaseModel
 */
public interface ModelDataBase {
    public void saveToDb(Image image);
}
