package de.nralbrecht.apps.worktimelogger;

import java.util.ArrayList;

class ArrayHelper {
    static ArrayList<WorkTime> Reverse(ArrayList<WorkTime> array) {
        ArrayList<WorkTime> invertedList = new ArrayList<>();
        for (int i = array.size() - 1; i >= 0; i--) {
            invertedList.add(array.get(i));
        }

        return invertedList;
    }
}
