package main.java.javaserver.core.helpers;

public class HTML {
    public static String link(String address, String name) {
        return "<a href=\"/" + address + "\">" + name + "</a>";
    }
}