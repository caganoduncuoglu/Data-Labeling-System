package com.DataLabelingSystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

@JsonPropertyOrder({"id", "instance"})
public class Instance {

    @JsonProperty("id")
    private int id;
    @JsonProperty("instance")
    private String content;
    @JsonIgnore
    private Dataset dataset;
    @JsonIgnore
    private ArrayList<SubInstance> subInstances = new ArrayList<>();

    Instance() {
    }

    Instance(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public ArrayList<SubInstance> getSubInstances() {
        return subInstances;
    }

    public void setSubInstances(ArrayList<SubInstance> subInstances) {
        this.subInstances = subInstances;
    }

    @Override
    public String toString() {
        return "Instance{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
