package com.Myblog.demo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO<T> {
    private static int showCount = 3;
    private List<T> data;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private Integer totalPage;
    private List<Integer> pages = new ArrayList<>();

    public void setPagination(Integer totalCount, Integer page, Integer size){
        int totalPage = totalCount / size + (totalCount % size == 0 ? 0 : 1) ;
        this.page = page;
        this.totalPage = totalPage;
        //保证列表为2 * showCount + 1 个
        pages.add(page);
        for (int i = 1; i <= showCount; i++) {
            if(page - i > 0)pages.add(0,page - i);
            if(page + i <= totalPage)pages.add(page + i);
        }

        //page为1不展示上一页， 返回FALSE
        showPrevious = (page != 1);
        //page为totalPage不展示下一页， 返回FALSE
        showNext = !page.equals(totalPage);
        //是否展示第一页，有第一页返回false
        showFirstPage = !pages.contains(1);
        //是否展示最后一页，有最后一页返回false
        showEndPage = !pages.contains(totalPage);
    }
}
