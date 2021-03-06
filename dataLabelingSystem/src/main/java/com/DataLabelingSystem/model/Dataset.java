package com.DataLabelingSystem.model;

import com.DataLabelingSystem.assignment.LabelAssignment;
import com.DataLabelingSystem.metric.DatasetMetric;
import com.fasterxml.jackson.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Optional;

@JsonPropertyOrder({"dataset id", "dataset name", "instance type", "maximum number of labels per instance", "class labels", "instances", "class label assignments"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(scope = Dataset.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "dataset id")
public class Dataset {
    private static final Logger logger = LogManager.getLogger();

    @JsonProperty("dataset id")
    private final int id;
    @JsonProperty("dataset name")
    private final String name;
    @JsonProperty("instance type")
    private final String instanceType;
    @JsonProperty("maximum number of labels per instance")
    private final int maxNumberOfLabelsPerInstance;
    @JsonIgnore
    private final DatasetMetric datasetMetric;

    @JsonProperty("class labels")
    private final ArrayList<Label> labels;
    @JsonProperty("instances")
    private final ArrayList<Instance> instances;
    @JsonProperty("class label assignments")
    private ArrayList<LabelAssignment> labelAssignments = new ArrayList<>();
    @JsonProperty("assigned users")
    private ArrayList<User> assignedUsers = new ArrayList<>();

    @JsonCreator
    Dataset(@JsonProperty("dataset id") int id,
            @JsonProperty("dataset name") String name,
            @JsonProperty("instance type") String instanceType,
            @JsonProperty("maximum number of labels per instance") int maxNumberOfLabelsPerInstance,
            @JsonProperty("class labels") ArrayList<Label> labels,
            @JsonProperty("instances") ArrayList<Instance> instances,
            @JsonProperty("class label assignments") ArrayList<LabelAssignment> labelAssignments) {
        this.id = id;
        this.name = name;
        this.instanceType = instanceType;
        this.maxNumberOfLabelsPerInstance = maxNumberOfLabelsPerInstance;
        this.labels = labels;
        this.instances = instances;
        if (labelAssignments != null)
            this.labelAssignments = labelAssignments;

        for (Label label : labels) {
            label.setDataset(this);
        }

        for (Instance instance : instances) {
            instance.setDataset(this);
        }

        this.datasetMetric = new DatasetMetric(this);

        logger.trace("Created dataset with information " + toString());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInstanceType() {
        return instanceType;
    }

    public int getMaxNumberOfLabelsPerInstance() {
        return maxNumberOfLabelsPerInstance;
    }

    public ArrayList<Label> getLabels() {
        return labels;
    }

    public ArrayList<Instance> getInstances() {
        return instances;
    }

    public ArrayList<LabelAssignment> getLabelAssignments() {
        return labelAssignments;
    }

    public ArrayList<User> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(ArrayList<User> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public DatasetMetric getDatasetMetric() {
        return datasetMetric;
    }

    public void fixUserReferences(ArrayList<User> users) throws InvalidObjectException {
        ArrayList<User> fixedUsers = new ArrayList<>(getAssignedUsers().size());
        for (User user : getAssignedUsers()) {
            Optional<User> fixedUser = users.stream().filter(u -> u.getId() == user.getId()).findFirst();
            if (fixedUser.isPresent()) {
                fixedUsers.add(fixedUser.get());
            } else {
                throw new InvalidObjectException("User " + user.toString() + " assigned to dataset " + this.toString() + " does not exist in the config file.");
            }
        }
        this.setAssignedUsers(fixedUsers);

        for (LabelAssignment labelAssignment : getLabelAssignments()) {
            Optional<User> fixedUser = users.stream().filter(u -> u.getId() == labelAssignment.getUser().getId()).findFirst();
            if (fixedUser.isPresent()) {
                labelAssignment.setUser(fixedUser.get());
            } else {
                throw new InvalidObjectException("User " + labelAssignment.getUser().toString() + " who labeled an instance in dataset " + this.toString() + " does not exist in the config file.");
            }
        }
    }

    @Override
    public String toString() {
        return "Dataset{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
