package pl.jj.app.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author JNartowicz
 */
public class ChartModel {

    private ChartModel(Builder builder){
        this.labels = builder.labels;
        this.datasets = Arrays.asList(builder.dataset);
    }

    private List<String> labels;

    private List<DataSet> datasets;

    public List<String> getLabels() {
        return labels;
    }

    public static class Builder{

        private List<String> labels;

        private DataSet dataset;

        public Builder() {
            dataset = new DataSet();
        }

        public Builder addLabel(String label){
            if(labels == null){
                labels = new ArrayList<>();
            }
            labels.add(label);
            return this;
        }

        public Builder setMainLabel(String label){
            dataset.setLabel(label);
            return this;
        }

        public Builder setBgColor(String color){
            dataset.setBackgroundColor(color);
            return this;
        }

        public Builder setBorderColor(String color){
            dataset.setBorderColor(color);
            return this;
        }

        public Builder addData(Integer data){
            if(dataset.getData() == null){
                dataset.data = new ArrayList<>();
            }
            dataset.getData().add(data);
            return this;
        }

        public ChartModel build(){
            return new ChartModel(this);
        }

    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<DataSet> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<DataSet> datasets) {
        this.datasets = datasets;
    }

    private static class DataSet{

        private String label;

        private String backgroundColor;

        private String borderColor;

        private List<Integer> data;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getBackgroundColor() {
            return backgroundColor;
        }

        public void setBackgroundColor(String backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public String getBorderColor() {
            return borderColor;
        }

        public void setBorderColor(String borderColor) {
            this.borderColor = borderColor;
        }

        public List<Integer> getData() {
            return data;
        }

        public void setData(List<Integer> data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "DataSet{" +
                    "label='" + label + '\'' +
                    ", backgroundColor='" + backgroundColor + '\'' +
                    ", borderColor='" + borderColor + '\'' +
                    ", data=" + data +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ChartModel{" +
                "labels=" + labels +
                ", datasets=" + datasets +
                '}';
    }
}
