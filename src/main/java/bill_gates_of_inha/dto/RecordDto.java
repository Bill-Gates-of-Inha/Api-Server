package bill_gates_of_inha.dto;

import bill_gates_of_inha.domain.Record;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RecordDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Record {
        private Long id;
        private Double weight;
        private Long count;
        private Long setNum;
        private String workoutName;

        public static Record toDto(bill_gates_of_inha.domain.Record record) {

            return Record.builder()
                    .id(record.getId())
                    .count(record.getCount())
                    .setNum(record.getSetNum())
                    .weight(record.getWeight())
                    .workoutName(record.getWorkout().getName())
                    .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Creation {
        private Double weight;
        private Long count;
        private Long setNum;
        private String userId;
        private Long workoutId;

        public static bill_gates_of_inha.domain.Record toEntity(Creation req) {

            return bill_gates_of_inha.domain.Record.builder()
                    .count(req.count)
                    .setNum(req.setNum)
                    .weight(req.weight)
                    .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Update {
        private Double weight;
        private Long count;
        private Long setNum;
        private Long workoutId;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class FilterForDate {
        private String startDate;
        private String endDate;
    }
}
