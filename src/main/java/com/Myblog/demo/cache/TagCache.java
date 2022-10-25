package com.Myblog.demo.cache;

import com.Myblog.demo.dto.TagDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagCache {
    public static List<TagDTO> get(){
        List<TagDTO> tagDTOS = new ArrayList<>();

        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("js","java","html","php","css","node","python","c","c++","shell","c#","golong"));

        TagDTO frameWork = new TagDTO();
        frameWork.setCategoryName("平台框架");
        frameWork.setTags(Arrays.asList("spring","springboot"));

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux","nginx","docker", "tomcat","unix","hadoop"));

        TagDTO db = new TagDTO();
        db.setCategoryName("服务器");
        db.setTags(Arrays.asList("mysql","redis","oracle", "tomcat","unix","hadoop","sqlserver","nosql"));

        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("github","vim","vscode", "maven","idea","vs"));

        tagDTOS.add(db);
        tagDTOS.add(tool);
        tagDTOS.add(server);
        tagDTOS.add(frameWork);
        tagDTOS.add(program);

        return tagDTOS;
    }

    public static String filterInValid(String tags){
        String[] spilt = tags.split(",");
        List<TagDTO> tagDTOS = get();
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String inValidList = Arrays.stream(spilt).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return inValidList;
    }
}
