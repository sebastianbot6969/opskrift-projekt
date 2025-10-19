package com.mitprojekt.model;

public class Recipe {
    private int id;
    private String name;
    private String linkToPage;
    private int time; // in minutes
    private String procedure;
    private boolean hasMade;

    public Recipe(int id, String name, String linkToPage, int time, String procedure, boolean hasMade) {
        setId(id);
        setName(name);
        setLinkToPage(linkToPage);
        setTime(time);
        setProcedure(procedure);
        setHasMade(hasMade);
    }

    public Recipe(String name, String linkToPage, int time, String procedure, boolean hasMade) {
    setName(name);
    setLinkToPage(linkToPage);
    setTime(time);
    setProcedure(procedure);
    setHasMade(hasMade);
}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID cannot be negative");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }
        this.name = name.trim();
    }

    public String getLinkToPage() {
        return linkToPage;
    }

    public void setLinkToPage(String linkToPage) {
        if (linkToPage == null || linkToPage.trim().isEmpty()) {
            throw new IllegalArgumentException("link cannot be null or empty");
        }
        this.linkToPage = linkToPage.trim();
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        if (time < 0) {
            throw new IllegalArgumentException("Time cannot be negative");
        }
        this.time = time;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        if (procedure == null || procedure.trim().isEmpty()) {
            throw new IllegalArgumentException("procedure cannot be null or empty");
        }
        this.procedure = procedure.trim();
    }

    public boolean getHasMade() {
        return hasMade;
    }

    public void setHasMade(boolean hasMade) {
        this.hasMade = hasMade;
    }

    public boolean matches(String keyword) {
        if (keyword == null || keyword.isBlank())
            return false;
        keyword = keyword.toLowerCase();
        return name.toLowerCase().contains(keyword)
                || procedure.toLowerCase().contains(keyword)
                || linkToPage.toLowerCase().contains(keyword);
    }

    @Override
    public String toString() {
        return String.format(
                "Recipe{id=%d, name='%s', linkToPage='%s', time=%d, procedure='%s', hasMade=%s}", id, name,
                linkToPage, time, procedure, hasMade);
    }

}
