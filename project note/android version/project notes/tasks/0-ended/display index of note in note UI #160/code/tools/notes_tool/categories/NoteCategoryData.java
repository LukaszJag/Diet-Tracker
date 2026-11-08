package com.lukaszjag.diet_tracker_android.tools.notes_tool.categories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NoteCategoryData {
    public static final Map<String, List<String>> CATEGORY_MAP = new LinkedHashMap<>();

    static {
        CATEGORY_MAP.put("Java ecosystem", Arrays.asList(
                "Java", "Spring", "JUNIT", "Android", "Java – Web Scraping", "Java Diet Tracker"
        ));
        CATEGORY_MAP.put("Frontend", Arrays.asList(
                "HTML, CSS", "JavaScript"
        ));
        CATEGORY_MAP.put("Cloud and Infrastructure", Arrays.asList(
                "Linux", "PowerShell", "Network", "Cloud", "Windows Server",
                "Windows", "Containers", "SCCM - System Center Configuration Manager (MECM - Microsoft Endpoint Configuration Manager)"
        ));
        CATEGORY_MAP.put("GameDev", Arrays.asList(
                "UE5"
        ));
        CATEGORY_MAP.put("Computer Science", Arrays.asList(
                "Algorytmy", "General IT Knowledge"
        ));
        CATEGORY_MAP.put("Soft Skills", Arrays.asList(
                "Szukanie pracy", "Japoński", "Angielski"
        ));
        CATEGORY_MAP.put("Versatile Frameworks", Arrays.asList(
                "Python", ".NET (C#)"
        ));
        CATEGORY_MAP.put("WSB", Arrays.asList(
                "WSB"
        ));
        CATEGORY_MAP.put("Certs", Arrays.asList(
                "CompTIA A+", "CompTIA Network+", "CompTIA Linux+"
        ));
        CATEGORY_MAP.put("Backend", Arrays.asList(
                "x"
        ));
        CATEGORY_MAP.put("Microsoft Office", Arrays.asList(
                "Microsoft Office"
        ));
        CATEGORY_MAP.put("Low-Level Languages", Arrays.asList(
                "C", "C++"
        ));
        CATEGORY_MAP.put("Dev Tools", Arrays.asList(
                "Git", "Jira", "Postman", "IDE"
        ));
        CATEGORY_MAP.put("AI & Machine Learning", Arrays.asList(
                "AI Assistants", "Machine Learning Basics"
        ));
        CATEGORY_MAP.put("UWR", Collections.emptyList());
    }

    public static List<String> getMainCategories() {
        return new ArrayList<>(CATEGORY_MAP.keySet());
    }

    public static List<String> getSubcategories(String mainCategory) {
        if (mainCategory == null || !CATEGORY_MAP.containsKey(mainCategory)) {
            return Collections.emptyList();
        }
        return CATEGORY_MAP.get(mainCategory);
    }
}