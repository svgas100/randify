package de.sloth.spotiregx.lib.spotify.api.model;

import java.util.Map;

import lombok.Data;


@Data
public class ArtistVO {
    private Map<String, String> externalUrls;
    private String href;
    private String id;
    private String name;
    private String type;
    private String uri;
}
