package com.example.dto;

import com.example.enums.ReportType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RepostInfoDto {

    private Integer id;
                                    // profile(id,name,surname,photo(id,url));
    private Integer profileId;
    private String profileName;
    private String profileSurname;
    private String photoId;
    private String photoUrl;
    private String content;
    private String entityId;    //(channel/video)),
    private ReportType type;   // type(channel,video)

}
