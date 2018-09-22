package io.dev.tanners.wallpaperresources.models.links;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Links {
    private String self;
    private String html;
    private String download;
    private String download_location;


    /*
    "links":{
"self":"https://api.unsplash.com/photos/Dwu85P9SOIk",
"html":"https://unsplash.com/photos/Dwu85P9SOIk",
"download":"https://unsplash.com/photos/Dwu85P9SOIk/download""download_location":"https://api.unsplash.com/photos/Dwu85P9SOIk/download"
},
     */
}
