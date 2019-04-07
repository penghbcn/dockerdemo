package org.yojm.springcloud.fileupload.model;

/**
 * 功能简述
 * (测试对象)
 *
 * @author 海冰
 * @create 2019-04-07
 * @since 1.0.0
 */
public class TestModel {
    private String grade;
    private String name;
    private String subject;
    private Integer score;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "TestModel{" +
                "grade='" + grade + '\'' +
                ", name='" + name + '\'' +
                ", subject='" + subject + '\'' +
                ", score=" + score +
                '}';
    }
}
