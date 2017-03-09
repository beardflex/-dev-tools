package com.beardflex.ui;

import javafx.scene.image.Image;

import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by David on 09/03/2017.
 */
public class AssetManager {
    public Map<String, Image> imageCache = new HashMap<>();

    public void loadImages() {
        ResourceBundle imageBundle = ResourceBundle.getBundle("bundles.Images");
        for (Enumeration<String> keys = imageBundle.getKeys(); keys.hasMoreElements();) {
            String key = keys.nextElement();
            URL url = getClass().getResource(imageBundle.getString(key));
            if(url != null) {
                Image image = new Image(url.toString());
                imageCache.put(key, image);
            }

        }
    }

    public Image getImage(String key) {
        Image image = imageCache.get(key);
        return image;
    }
}
