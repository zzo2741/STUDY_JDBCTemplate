package springboard.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import model.JdbcTemplateConst;

/*
 * JdbcTemplate 관련 주요메소드
 * - List query(String sql, RowMapper rowMapper)
 * 		: 여러개의 레코드를 반환하는 select계열의 쿼리문인 경우 사용한다.
 * - List query(String sql, Object[] args, RowMapper rowMapper)
 * 		: 인파라미터를 가진 여러개의 레코드를 반환하는 select계열의 쿼리문인 경우 사용한다.
 *
 * - int queryForInt(String sql) 혹은 queryForInt(String sql, Object[] args)
 * 		: 쿼리문의 실행결과가 숫자를 반환하는 select계열의 쿼리문에 사용한다.
 * - Object queryForObject(String sql, RowMapper rowMapper) 혹은 Object queryForObject(String sql, Object[] args, RowMapper rowMapper)
 * 		: 하나의 레코드를 반환하는 select계열의 쿼리문 실행 시 사용한다.
 *
 * - int update(String sql)
 * 		: 인파라미터가 없는 update/delete/insert 쿼리문을 처리할 때 사용함.
 * - int update(String sql, Object[] args)
 * 		: 인파라미터가 있는 update/delete/insert 쿼리문을 처리할 때 사용함.
 */
public class JDBCTemplateDAO
{
	private String BOARD_COUNT = "SELECT COUNT(*) FROM springboard ";
	private String BOARD_LIST = " SELECT * FROM springboard ";
	private String HITS_UPDATE = " UPDATE springboard SET hits = hits + 1 WHERE idx = ? ";
	private String BOARD_DELETE = " DELETE FROM springboard WHERE idx = ? AND pass = ? ";
	private String REPLY_STEP_UPDATE = " UPDATE springboard SET bstep = bstep + 1 WHERE bgroup = ? AND bstep > ? ";
	/*
	 * 해당 게시판에서 패스워드는 변경 대상이 아니라 검증의 대상으로만 사용됨. 따라서 set절이 아니라 WHERE절에 삽입된다.
	 */
	private String BOARD_EDIT = " UPDATE springboard SET name = ?, title = ?, contents = ? WHERE idx = ? AND pass = ?";
	/*
	 * 답변형 게시판에서 원본글인 경우에는 idx(일련번호)와 bgroup(그룹번호)가 반드시 일치해야 한다.
	 * 또한 nextVal은 한문장에서 여러번 사용하더라도 같은 시퀀스를 반환한다.
	 */
	private final String BOARE_WIRTE = " INSERT INTO springboard ( idx, name, title, contents, hits, bgroup, bstep, bindent, pass) VALUES (springboard_seq.nextval, ?, ?, ?, 0, springboard_seq.nextval, 0, 0, ? )";
	/*
	 * 원본글의 경우 idx와 bgroup은 동일한 값을 입력함. 답변글의 경우 원본글의 group번호를 그대로 가져와서 입력함.
	 * idx는 시퀀스를 통해 bgroup은 원본글과 동일하게 입력함.
	 */
	private final String REPLY_WIRTE = " INSERT INTO springboard ( idx, name, title, contents, pass, bgroup, bstep, bindent) VALUES (springboard_seq.nextval, ?, ?, ?, ?, ?, ?, ? )";

	// 멤버 변수
	JdbcTemplate template;

	public JDBCTemplateDAO()
	{
		/*
		 * 컨트롤러에서 @Autowired를 통해 자동주입 받았던 빈을 정적변수인 JdbcTemplateConst.template을 통해 가져온다.
		 * 즉, DB연결정보를 웹어플리케이션 어디서든 사용할 수 있다.
		 */
		this.template = JdbcTemplateConst.template;
		System.out.println("JDBCTemplateDAO() 생성자 호출");
	}

	public void close()
	{
		// JDBCTemplate 에서는 사용하지 않음
	}

	// 게시물 카운트 수
	public int getTotalCount(Map<String, Object> map)
	{
		if (map.get("Word") != null)
		{
			BOARD_COUNT += " WHERE " + map.get("Column") + " LIKE '%" + map.get("Word") + "%' ";
		}
		return template.queryForObject(BOARD_COUNT, Integer.class);
	}

	// 게시판 리스트(페이지 처리 O)
	public ArrayList<SpringBbsVO> list(Map<String, Object> map)
	{

		int start = Integer.parseInt(map.get("start").toString());
		int end = Integer.parseInt(map.get("end").toString());

		String sql = "" + "SELECT * FROM (" + "    SELECT Tb.*, rownum rNum FROM (" + "        SELECT * FROM springboard ";
		if (map.get("Word") != null)
		{
			sql += " WHERE " + map.get("Column") + " " + " LIKE '%" + map.get("Word") + "%' ";
		}
		sql += " ORDER BY bgroup DESC, bstep ASC" + "    ) Tb" + ")" + " WHERE rNum BETWEEN " + start + " and " + end;

		return (ArrayList<SpringBbsVO>) template.query(sql, new BeanPropertyRowMapper<SpringBbsVO>(SpringBbsVO.class));
	}

	// 글쓰리 처리
	public void write(final SpringBbsVO springBbsVO)
	{
		template.update(new PreparedStatementCreator()
		{

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException
			{
				PreparedStatement psmt = con.prepareStatement(BOARE_WIRTE);
				psmt.setString(1, springBbsVO.getName());
				psmt.setString(2, springBbsVO.getTitle());
				psmt.setString(3, springBbsVO.getContents());
				psmt.setString(4, springBbsVO.getPass());
				return psmt;
			}
		});
	}

	// 조회수 증가
	public void updateHit(final String idx)
	{
		/*
		 * 매개변수로 전달되는 idx를 아래 익명클래스내부에서 사용하기 위해서는 반드시 final로 선언해야 사용 가능하다.(자바의 규칙)
		 */
		template.update(HITS_UPDATE, new PreparedStatementSetter()
		{

			@Override
			public void setValues(PreparedStatement ps) throws SQLException
			{
				ps.setInt(1, Integer.parseInt(idx));
			}
		});
	}

	// 상세보기 처리
	public SpringBbsVO view(String idx)
	{
		// 조회수 증가
		updateHit(idx);
		String BOARD_VIEW = " SELECT * FROM springboard WHERE idx = " + idx;

		SpringBbsVO vo = new SpringBbsVO();
		/*
		 * queryForObject()메소드는 반환결과가 0개이거나 2개 이상인 경우 예외가 발생하므로 반드시 예외처리를 해주는 것이 좋다.
		 */
		try
		{
			vo = template.queryForObject(BOARD_VIEW, new BeanPropertyRowMapper<SpringBbsVO>(SpringBbsVO.class));
		} catch (Exception e)
		{
			System.out.println("View()실행시 예외발생");
		}
		return vo;
	}

	public int password(String idx, String pass)
	{
		int retNum = 0;
		String PASSWORD_SELECT = "SELECT * FROM springboard WHERE pass=" + pass + " AND idx = " + idx;
		/*
		 * 만약 패스워드가 틀린경우라면 반환되는 행이 0개이므로 예외처리를 하고있다.
		 * queryForObject()는 반환되는 행이 1개일때만 정상 동작한다.
		 */
		try
		{
			SpringBbsVO vo = template.queryForObject(PASSWORD_SELECT, new BeanPropertyRowMapper<SpringBbsVO>(SpringBbsVO.class));
			/*
			 * idx와 pass에 해당하는 게시물이 정상적으로 가져와졌을 때는 해당 idx값을 반환값으로 사용한다.
			 */
			retNum = vo.getIdx();
		} catch (Exception e)
		{
			/*
			 * 만약 일치하지 않아 예외가 발생되면 0을 반환한다. 일련번호는 시퀀스를 사용하므로 항상 0보다는 큰 값을 가지게 된다.
			 */
			System.out.println("password() 예외발생");
		}
		return retNum;
	}

	// 수정처리
	public void edit(final SpringBbsVO vo)
	{
		/*
		 * 매개변수 vo객체를 아래 익명클래스 내부에서 사용해야 하므로 반드시 final을 붙여줘야 한다.
		 */
		template.update(BOARD_EDIT, new PreparedStatementSetter()
		{
			@Override
			public void setValues(PreparedStatement ps) throws SQLException
			{
				ps.setString(1, vo.getName());
				ps.setString(2, vo.getTitle());
				ps.setString(3, vo.getContents());
				ps.setInt(4, vo.getIdx());
				ps.setString(5, vo.getPass());
			}
		});
	}

	// 삭제처리
	public void delete(final String idx, final String pass)
	{
		template.update(BOARD_DELETE, new PreparedStatementSetter()
		{

			@Override
			public void setValues(PreparedStatement ps) throws SQLException
			{
				ps.setString(1, idx);
				ps.setString(2, pass);
			}
		});
	}

	// 답변글쓰기
	public void reply(final SpringBbsVO vo)
	{
		// 답변 글쓰기 전 레코드 업데이트
		replyPrevUpdate(vo.getBgroup(), vo.getBstep());

		/*
		 * 답변글인 경우 원본글의 step + 1, indent + 1 처리하여 입력한다.
		 */
		template.update(REPLY_WIRTE, new PreparedStatementSetter()
		{
			@Override
			public void setValues(PreparedStatement ps) throws SQLException
			{
				ps.setString(1, vo.getName());
				ps.setString(2, vo.getTitle());
				ps.setString(3, vo.getContents());
				ps.setString(4, vo.getPass());
				ps.setInt(5, vo.getBgroup());
				ps.setInt(6, vo.getBstep() + 1);
				ps.setInt(7, vo.getBindent() + 1);
			}
		});
	}

	// 답변글 입력 전 레코드 일괄 업데이트(step을 뒤로 밀어주기 위한 로직)
	public void replyPrevUpdate(final int strGroup, final int strStep)
	{
		template.update(REPLY_STEP_UPDATE, new PreparedStatementSetter()
		{

			@Override
			public void setValues(PreparedStatement ps) throws SQLException
			{
				ps.setInt(1, strGroup);
				ps.setInt(2, strStep);
			}
		});
	}
}
