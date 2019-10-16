package lhb.blog.com.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具
 */
@Data
public class PageDTO<T> {

    private List<T> data;
    private boolean showPrevious;  //上一页
    private boolean showFirstPage; //第一页
    private boolean showNext;  //下一页
    private boolean showEndPage; //最后一页
    private Integer page; //当前页
    private List<Integer> pages = new ArrayList<>(); //显示页数
    private Integer totalPage; //总共页数

    /**
     *
     * @param totalCount 查询的总数
     * @param page 第几页
     * @param size 想显示的数量
     */
    public void setPagination(Integer totalCount,Integer page,Integer size){

        //获取总页数
        if (totalCount % size == 0){
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount / size + 1;
        }



        this.page = page;  //当前类的page等于传进来的page

        //展示页码
        pages.add(page);
        for (int i=1;i<=3;i++){
            //向前展示三个页码
            if (page-i>0){
                pages.add(0, page - i);
            }
            //向后展示三个页码
            if (page + i <= totalPage) {
                pages.add(page+i);
            }
        }

        //是否展示上一页
        if (page <= 4){
            showPrevious = false;
        }else {
           showPrevious = true;
        }

        //是否展示下一页
        if (page >= totalPage-3){
            showNext = false;
        }else {
            showNext = true;
        }

        //是否展示第一页
        if (pages.contains(1)){
            showFirstPage =false;
        }else {
            showFirstPage = true;
        }

        //是否展示最后一页
        if (pages.contains(totalCount)) {
            showEndPage = false;
        }else {
            showEndPage = false;
        }

    }




}
