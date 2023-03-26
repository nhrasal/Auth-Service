package com.app.auth.base.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {
    // Class data members
    private String recipient;
    private String messageBody;
    private String subject;

    private String template;

    private Map<String, Object> props;
}
