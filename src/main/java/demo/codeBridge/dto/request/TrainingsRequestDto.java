package demo.codeBridge.dto.request;

public class TrainingsRequestDto {
    private String trainingName;

    public TrainingsRequestDto() {
    }
    public TrainingsRequestDto(String trainingName) {
        this.trainingName = trainingName;
    }
    public String getTrainingName() {
        return trainingName;
    }
    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }
}


