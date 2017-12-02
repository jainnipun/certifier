package org.jbossoutreach.certifier.model;

public class Student {

    private String  name;
    private String email;
    private String certificate;

    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Student(){

    }

    public Student(String name, String email, String certificate) {
        this.name = name;
        this.email = email;
        this.certificate = certificate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public static StudentCsv toStudentCsv(StudentCsv student){
        return new StudentCsv();
    }
}
