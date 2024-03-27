package com.aoyou.entertainment.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: PageResult
 * Package: com.aoyou.entertainment.common.domain
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/3/16 17:47
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> implements Serializable {
    private List<T> list;
    private Long total;
}
