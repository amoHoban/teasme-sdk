package net.netm.apps.libs.teaseme.services;

/**
 * Created by ahoban on 01.04.15.
 */
public enum TeaserFilterParameter {

    FSK("fsk"),
    SERVICE("service"),
    PLATFORM("platform"),
    /**
     * <p>Can be one of
     * "default"
     * "smartphone",
     * "featuredphone"
     * "tablet"
     * "set_top_box"
     * "smart_tv"
     * </p>
     */
    DEVICEGROUP("devicegroup"),
    DEVICEWIDTH("devicewidth"),
    DEVICEHEIGHT("deviceheight"),
    /**
     * number
     */
    DEVICEDENSITY("devicedensity");

    private final String name;

    TeaserFilterParameter(String name) {
        this.name = name;
    }

}
