package life.majiang.community.dto;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO<T> {
    private List<T> data;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private long totalPage;

    public void setPagination(PageInfo pageInfo) {
        page = pageInfo.getPageNum();
        totalPage = pageInfo.getPages();
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }

            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }

        showPrevious = pageInfo.isHasPreviousPage();
        showNext = pageInfo.isHasNextPage();
        showFirstPage = !pageInfo.isIsFirstPage();
        showEndPage = !pageInfo.isIsLastPage();
    }
}