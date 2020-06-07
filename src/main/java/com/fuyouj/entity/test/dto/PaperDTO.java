package com.fuyouj.entity.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/6/7 13:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperDTO {

    private Integer paper_id;

    private String URL;

    private Integer examId;
}