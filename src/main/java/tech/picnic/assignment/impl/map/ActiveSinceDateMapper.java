package tech.picnic.assignment.impl.map;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActiveSinceDateMapper {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public static Date getActiveSinceDate(String activeSince) throws Exception {
        return simpleDateFormat.parse(activeSince);
    }
}
