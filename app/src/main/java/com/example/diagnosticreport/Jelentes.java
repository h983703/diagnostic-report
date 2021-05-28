package com.example.diagnosticreport;

import java.io.Serializable;

public class Jelentes implements Serializable {
    //Firestore ID
    String id;

    // jelentés ID
    String identifier;

    // minta típusa (vér, vizelet, széklet)
    String category;

    //diagnosztikai jelentés neve(bakteriális, gombás, vírusos)
    String code;

    String paciens;
    String conclusion;

    public Jelentes() {
    }

    public Jelentes( String identifier, String category, String code, String paciens, String conclusion) {
        this.identifier = identifier;
        this.category = category;
        this.code = code;
        this.paciens = paciens;
        this.conclusion = conclusion;
    }

    public String _getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPaciens() {
        return paciens;
    }

    public void setPaciens(String paciens) {
        this.paciens = paciens;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }
}
