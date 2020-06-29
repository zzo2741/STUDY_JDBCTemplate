package springboard.model;

import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcTemplateConst
{
	/*
	 * JDBCTemplate을 웹어플리케이션 어디에서나 사용할 수 있도록(전체)하기 위해 static(정적)변수를 가진 클래스 하나 생성한다.
	 */
	public static JdbcTemplate template;
}
