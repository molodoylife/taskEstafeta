package ua.hanasaka.testestafeta.model.downloader;

import java.util.List;

import ua.hanasaka.testestafeta.model.data.Image;

/**
 * Interface for downloading files
 */
public interface ModelDownloader {
    void downloadFiles(List<Image> list);
}
