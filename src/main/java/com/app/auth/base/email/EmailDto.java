package com.app.auth.base.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {
    // Class data members
    private String recipient;
    private String messageBody;
    private String subject;
}
