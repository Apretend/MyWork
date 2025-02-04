package com.lyh.storyapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyh.storyapi.model.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BookMapper extends BaseMapper<Book> {
    @Select("select * from book_details where book_name LIKE CONCAT('%', #{BookName}, '%') AND user_id = #{UserId}")
    List<Book> getBooksByNameAndUserId(String BookName, int UserId);

    @Update({
            "<script>",
            "UPDATE book_details",
            "<set>",
            "  <if test='bookName != null'>book_name = #{bookName},</if>",
            "  <if test='alternateName1 != null'>alternate_name_1 = #{alternateName1},</if>",
            "  <if test='alternateName2 != null'>alternate_name_2 = #{alternateName2},</if>",
            "  <if test='alternateName3 != null'>alternate_name_3 = #{alternateName3},</if>",
            "  <if test='alternateName4 != null'>alternate_name_4 = #{alternateName4},</if>",
            "  <if test='description != null'>description = #{description},</if>",
            "  <if test='coverLink != null'>cover_link = #{coverLink},</if>",
            "  <if test='platformId != null'>platform_id = #{platformId},</if>",
            "  <if test='isPublished != null'>is_published = #{isPublished},</if>",
            "  <if test='bookContentDescription != null'>book_content_description = #{bookContentDescription},</if>",
            "  update_time = NOW()",
            "</set>",
            "WHERE book_id = #{bookId}",
            "</script>"
    })
    int updateBookSelective(Book book);
}
