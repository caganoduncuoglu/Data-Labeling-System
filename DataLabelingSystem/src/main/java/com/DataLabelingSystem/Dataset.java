package com.DataLabelingSystem;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Dataset {

    @JsonProperty("dataset id")
    private int id;
    @JsonProperty("dataset name")
    private String name;
    @JsonProperty("instance type")
    private String instanceType;
    @JsonProperty("maximum number of labels per instance")
    private int maxNumberOfLabelsPerInstance;

    @JsonProperty("class labels")
    private ArrayList<Label> labels = new ArrayList<Label>();
    @JsonProperty("instances")
    private ArrayList<Instance> instances = new ArrayList<Instance>();
    @JsonProperty("class label assignments")
    private ArrayList<LabelAssignment> labelAssignments = new ArrayList<LabelAssignment>();

    Dataset() {
    }

    Dataset(int id, String name, String instanceType, int maxNumberOfLabelsPerInstance,
            ArrayList<Label> labels, ArrayList<Instance> instances) {
        this.id = id;
        this.name = name;
        this.instanceType = instanceType;
        this.maxNumberOfLabelsPerInstance = maxNumberOfLabelsPerInstance;
        this.labels = labels;
        this.instances = instances;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstanceType() {
        return instanceType;
    }

    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }

    public int getMaxNumberOfLabelsPerInstance() {
        return maxNumberOfLabelsPerInstance;
    }

    public void setMaxNumberOfLabelsPerInstance(int maxNumberOfLabelsPerInstance) {
        this.maxNumberOfLabelsPerInstance = maxNumberOfLabelsPerInstance;
    }

    public ArrayList<Label> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<Label> labels) {
        this.labels = labels;
    }

    public ArrayList<Instance> getInstances() {
        return instances;
    }

    public void setInstances(ArrayList<Instance> instances) {
        this.instances = instances;
    }

    public ArrayList<LabelAssignment> getLabelAssignments() {
        return labelAssignments;
    }

    public void setLabelAssignments(ArrayList<LabelAssignment> labelAssignments) {
        this.labelAssignments = labelAssignments;
    }

    @JsonGetter("users")
    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        for (LabelAssignment labelAssignment :
                this.labelAssignments) {
            users.add(labelAssignment.getUser());
        }
        return users;
    }
}
