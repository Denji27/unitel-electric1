package la.com.unitel.business.contract.dto;

import la.com.unitel.business.contract.view.ContractDetailView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PIC {
    private List<String> readerList;
    private List<String> cashierList;
}