package com.Myblog.demo.service;

import com.Myblog.demo.dao.BlogDao;
import com.Myblog.demo.dao.UserDao;
import com.Myblog.demo.domain.Blog;
import com.Myblog.demo.domain.User;
import com.Myblog.demo.dto.BlogDTO;
import com.Myblog.demo.dto.PaginationDTO;
import com.Myblog.demo.exception.GlobalException;
import com.Myblog.demo.result.CodeMsg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BlogService {

    @Autowired
    BlogDao blogDao;
    @Autowired
    UserDao userDao;

    public void create(Blog blog) {
        blogDao.insertBlog(blog);
    }

    public Integer getBlogSize(){
        return blogDao.getBlogSize();
    }

    public PaginationDTO getBlogDTOList(String search ,Integer page, Integer size) {
        int offset;
        if(page > (offset = size * (page - 1)) + 1 || page <= 0){
            throw new GlobalException(CodeMsg.PARAMETER_ERROR);
        }
        List<Blog> blogs;
        Integer blogSize;
        if(StringUtils.isNotBlank(search)){
            String tags = search.replaceAll(" +", "|");
            blogs = blogDao.getSearchBlogList(tags,offset, size);
            blogSize = blogDao.getSearchBlogSize(tags);
        }else{
            blogs = getBlogList(offset, size);
            blogSize = getBlogSize();
        }
        List<BlogDTO> blogDTOS = new ArrayList<>();
        for (Blog blog : blogs){
            User user = userDao.findById(blog.getCreator());
            BlogDTO blogDTO = new BlogDTO();
            BeanUtils.copyProperties(blog,blogDTO);
            blogDTO.setUser(user);
            blogDTOS.add(blogDTO);
        }
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setData(blogDTOS);

        paginationDTO.setPagination(blogSize, page, size);
        return  paginationDTO;
    }

    private List<Blog> getBlogList(Integer offset, Integer size) {
        return blogDao.getBlogList(offset, size);
    }

    private List<Blog> getBlogListById(Integer id,Integer offset, Integer size) {
        return blogDao.getBlogListById(id ,offset, size);
    }

    public PaginationDTO list(Integer id, Integer page, int size) {
        int offset;
        if(page > (offset = size * (page - 1)) + 1 || page <= 0){
            throw new GlobalException(CodeMsg.PARAMETER_ERROR);
        }
        List<Blog> blogs = getBlogListById(id, offset, size);
        List<BlogDTO> blogDTOS = new ArrayList<>();
        User user = userDao.findById(id);
        for (Blog blog : blogs){
            BlogDTO blogDTO = new BlogDTO();
            BeanUtils.copyProperties(blog,blogDTO);
            blogDTO.setUser(user);
            blogDTOS.add(blogDTO);
        }
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setData(blogDTOS);
        paginationDTO.setPagination(getBlogSizeById(id), page, size);
        return paginationDTO;
    }

    private Integer getBlogSizeById(Integer id) {
        return blogDao.getBlogSizeById(id);
    }

    public BlogDTO getBlogDTOById(Integer id) {
        Blog blog = blogDao.getBlogById(id);
        if(blog == null){
            throw new GlobalException(CodeMsg.PARAMETER_ERROR);
        }
        BlogDTO blogDTO = new BlogDTO();
        BeanUtils.copyProperties(blog,blogDTO);
        User user = userDao.findById(blog.getCreator());
        blogDTO.setUser(user);
        return  blogDTO;
    }

    public Blog getBlogById(Integer id) {
        return blogDao.getBlogById(id);
    }

    public void creatOrUpdateBlog(Blog blog, User user) {
        blog.setGmtModify(System.currentTimeMillis());
        if(blog.getId() == null){
            blog.setCreator(user.getId());
            blog.setGmtCreate(System.currentTimeMillis());
            blogDao.insertBlog(blog);
        }else {
            blogDao.updateBlog(blog);
        }
    }

    public void incView(Integer id) {
        blogDao.incView(id);
    }

    public void incComment(Integer id) {
        blogDao.incComment(id);
    }

    public List<BlogDTO> selectRelatedBlogs(BlogDTO blogDTO) {
        if(StringUtils.isBlank(blogDTO.getTag())){
            return new ArrayList<>();
        }
        String tag = blogDTO.getTag().replace(',','|');
        Blog blog = new Blog();
        blog.setId(blogDTO.getId());
        blog.setTag(tag);
        List<Blog> blogs = blogDao.getRelatedBlogs(blog);
        List<BlogDTO> blogDTOS = blogs.stream().map(q ->{
            BlogDTO blogDTO1 = new BlogDTO();
            BeanUtils.copyProperties(q, blogDTO1);
            return blogDTO1;
        }).collect(Collectors.toList());
        return blogDTOS;
    }
}
