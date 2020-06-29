package springboard.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import springboard.model.JDBCTemplateDAO;
import springboard.model.SpringBbsVO;

@Service
public class WriteActionCommand implements BbsCommandImpl
{
	JDBCTemplateDAO dao;

	@Autowired
	public void setDao(JDBCTemplateDAO dao)
	{
		this.dao = dao;
		System.out.println("JDBCTemplateDAO자동주입(WriteAction)");
	}

	@Override
	public void execute(Model model)
	{
		// 파라미터 한꺼번에 전달받기
		Map<String, Object> paramMap = model.asMap();
		HttpServletRequest req = (HttpServletRequest) paramMap.get("req");
		SpringBbsVO springBbsVO = (SpringBbsVO) paramMap.get("springBbsVO");
		// 커멘드 객체에 저장한 title을 로그인에 출력(폼값 확인용)
		System.out.println("springBbsVO.title = " + springBbsVO.getTitle());
		// DAO객체 생성 후 insert처리
		//JDBCTemplateDAO dao = new JDBCTemplateDAO();
		dao.write(springBbsVO);
		dao.close();
	}
}
