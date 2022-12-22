package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttachDTO {

    private String id;
    private String originalName;
    private String path;
    private Long size;
    private String extension;
    private LocalDateTime createdData;

    private String url;

    @Override
    public String toString() {
        return "AttachDTO{" +
                "id='" + id + '\'' +
                ", originalName='" + originalName + '\'' +
                ", path='" + path + '\'' +
                ", size=" + size +
                ", extension='" + extension + '\'' +
                ", createdData=" + createdData +
                ", url='" + url + '\'' +
                '}';
    }
}
