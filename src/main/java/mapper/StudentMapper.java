package mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import main.Student;

/*
 * 인터페이스방식으로 Mapper사용하기
 * 1.Mybatis-config.xml의 mapper에 package로설정
 * 2.namespace: mapper.StudentMapper.인터페이스의 전체이름이 namespace임
 * 3.메서드의이름이 sql문장의 key값
 * 	=>Mapper인터페이스는 오버로딩불가능
 * */
public interface StudentMapper {

	@Select("select * from student")
	List<Student> select();

	@Select("select * from student where grade=#{i}")
	List<Student> selectGrade(int i);

	@Select("select * from student where studno=#{i}")
	Student selectStudno(int i);
	/*<select id="selectStudno" parameterType="string" resultType="Student">
		select * from student where studno=#{value}
	</select>*/

	@Select("select * from student where name=#{value}")
	List<Student> selectName(String st);

	@Select("select * from student where grade=#{grade} "
			+ " and height>=#{height}")
	List<Student> selectGradeHeight(Map<String, Object> map);

	@Select("select * from student where grade=#{grade} "
			+ " and height>=#{height}")
	//@Param("grade")int a : a변수를 grade key값으로설정
	List<Student> selectGradeHeight2
	(@Param("grade")int a, @Param("height")int b);

//--------Main5_interface의 쿼리들-------------------
	@Insert("insert into student(studno,name,jumin,id)"
			+ " values(#{studno},#{name},#{jumin},#{id})")
	int insert(Student st);

	@Update("update  student set grade=#{grade}"
			+ ", weight=#{weight} , height=#{height} "
			+ "where name=#{name}")
	int update(Student st);

	@Delete("delete from student where name=#{val}")
	int deleteName(String name);
	


	

}
