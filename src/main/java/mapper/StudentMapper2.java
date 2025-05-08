package mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import main.Student;

/*
 * 동적쿼리(인터페이스)
 * */
public interface StudentMapper2 {

	@Select({"<script>",
		"select * from student",
			"<where>",
			"<if test='grade != null'>grade=#{grade}</if>",
			"<if test='height != null'>height>=#{height}</if>",
			"<if test='weight != null'> weight&lt;=#{weight}</if>",
			//< 를 사용시 태그로인식하므로(&lt(작다))를 이용하자
			"</where>",
			"</script>"})
	List<Student> select(Map<String, Object> map);

	
	@Select({"<script>",
		"select * from student",
			"<trim prefix='where' prefixOverrides='AND || OR'>",
			"<if test='grade != null'>and grade=#{grade}</if>",
			"<if test='height != null'>and height>=#{height}</if>",
			"</trim>",
			"</script>"})
	List<Student> select2(Map<String, Object> map);

}
