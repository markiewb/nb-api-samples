/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.eclipsesamples.clock.ui.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * https://github.com/alblue/com.packtpub.e4/blob/chapter3/com.packtpub.e4.clock.ui/src/com/packtpub/e4/clock/ui/internal/TimeZoneComparator.java
 *
 */
public class TimeZoneComparator implements Comparator {

    public int compare(Object o1, Object o2) {
        if (o1 instanceof TimeZone && o2 instanceof TimeZone) {
            return ((TimeZone) o1).getID().compareTo(((TimeZone) o2).getID());
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static class TimeZoneRegion {

        String name;

        public String getName() {
            return name;
        }

        public Set<TimeZone> getTimeZones() {
            return timeZones;
        }
        Set<TimeZone> timeZones;

        public TimeZoneRegion(String name, Set<TimeZone> timeZones) {
            this.name = name;
            this.timeZones = timeZones;
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Set<TimeZone>> getTimeZones() {
        String[] ids = TimeZone.getAvailableIDs();
        Map<String, Set<TimeZone>> timeZones = new TreeMap<String, Set<TimeZone>>();
        for (int i = 0; i < ids.length; i++) {
            String[] parts = ids[i].split("/");
            if (parts.length == 2) {
                String region = parts[0];
                Set<TimeZone> zones = timeZones.get(region);
                if (zones == null) {
                    zones = new TreeSet<TimeZone>(new TimeZoneComparator());
                    timeZones.put(region, zones);
                }
                TimeZone timeZone = TimeZone.getTimeZone(ids[i]);
                zones.add(timeZone);
            }
        }
        return timeZones;
    }

    @SuppressWarnings("unchecked")
    public static Collection<TimeZoneRegion> getTimeZonesAsObjects() {
        Collection<TimeZoneRegion> result = new ArrayList<TimeZoneRegion>();

        Map<String, Set<TimeZone>> timeZones = getTimeZones();
        for (Map.Entry<String, Set<TimeZone>> entry : timeZones.entrySet()) {
            String region = entry.getKey();
            Set<TimeZone> zones = entry.getValue();
            result.add(new TimeZoneRegion(region, zones));
        }

        return result;
    }
}
