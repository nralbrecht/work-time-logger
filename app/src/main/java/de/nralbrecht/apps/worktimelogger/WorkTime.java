package de.nralbrecht.apps.worktimelogger;

class WorkTime {
    private String start;
    private String end;

    WorkTime(String start) {
        this.start = start;
        this.end = null;
    }

    WorkTime(String start, String end) {
        this.start = start;
        this.end = end;
    }

    String getStart() {
        return start;
    }

    String getEnd() {
        if (end == null) {
            return "-";
        } else {
            return end;
        }
    }

    void setStart(String value) {
        start = value;
    }

    void setEnd(String value) {
        end = value;
    }

    boolean hasEnd() {
        return end != null;
    }

    @Override
    public String toString() {
        return "{ " + getStart() + ", " + getEnd() + "}";
    }
}
