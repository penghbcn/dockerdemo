package org.yohm.springcloud.fileupload.model;

/**
 * 功能简述
 * (测试对象)
 *
 * @author 海冰
 * @create 2019-04-07
 * @since 1.0.0
 */
public class TestModel {
    private Integer id;
    private String data;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TestModel{" +
                "id=" + id +
                ", data='" + data + '\'' +
                '}';
    }
}
