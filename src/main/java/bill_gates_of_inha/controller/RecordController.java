package bill_gates_of_inha.controller;

import bill_gates_of_inha.dto.RecordDto;
import bill_gates_of_inha.dto.ResponseDto;
import bill_gates_of_inha.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecordController {
    private final RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping("/api/records")
    public ResponseDto.Created createRecord(@RequestBody RecordDto.Creation req) {
        RecordDto.Record recordDto = recordService.createRecord(req);

        return new ResponseDto.Created(recordDto, "운동 기록 생성");
    }

    @GetMapping("/api/users/{userId}/records")
    public ResponseDto.Ok getRecordListByUserIdByDate(@PathVariable("userId") String userId, @ModelAttribute RecordDto.FilterForDate req) {
        List<RecordDto.Record> recordDtoList = recordService.getRecordListByUserIdByDate(userId, req);

        return new ResponseDto.Ok(recordDtoList, "운동 기록 리스트");
    }

    @PatchMapping("/api/records/{id}")
    public ResponseDto.Ok updateRecord(@PathVariable("id") Long id, @RequestBody RecordDto.Update req) {
        recordService.updateRecord(id, req);
        RecordDto.Record recordDto = recordService.getRecordById(id);

        return new ResponseDto.Ok(recordDto, "운동 기록 수정");
    }
}
