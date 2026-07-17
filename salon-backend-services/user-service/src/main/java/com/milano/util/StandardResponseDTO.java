package com.milano.util;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StandardResponseDTO {
    private int code;
    private String message;
    private Object data;
}
