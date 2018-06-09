package me.eirinimitsopoulou.bakingapp.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eirinimitsopoulou on 30/05/2018.
 */

public class Images {

    public List<String> CreateImages (){

        String image ;
        List<String> images = new ArrayList<>();

        image = "http://static.ngengs.com/images/udacity/image_nutella_pie.webp";
        images.add(image);

        image = "http://static.ngengs.com/images/udacity/image_brownies.webp";
        images.add(image);

        image = "http://static.ngengs.com/images/udacity/image_yellow_cake.webp";
        images.add(image);

        image = "http://static.ngengs.com/images/udacity/image_cheese_cake.webp";
        images.add(image);

        return images ;
    }
}
