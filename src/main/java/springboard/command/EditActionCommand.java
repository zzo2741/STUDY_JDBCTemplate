package springboard.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import springboard.model.JDBCTemplateDAO;
import springboard.model.SpringBbsVO;

/*
 * @Service
 * 		: 해당 어노테이션은 컴파일러에게 해당 클래스가 서비스 역할을 하는 클래스임을 표기하는 역할을 함.
 * 		@Override처럼 반드시 있어야 하는건 아님.
 */
@Service
public class EditActionCommand implements BbsCommandImpl
{
	JDBCTemplateDAO dao;

	@Autowired
	public void setDao(JDBCTemplateDAO dao)
	{
		this.dao = dao;
		System.out.println("JDBCTemplateDAO자동주입(EditAction)");
	}

	@Override
	public void execute(Model model)
	{
		Map<String, Object> map = model.asMap();
		HttpServletRequest req = (HttpServletRequest) map.get("req");
		// 폼값을 한꺼번에 받아 저장한 커맨드 객체를 model객체에서 가져옴.
		SpringBbsVO vo = (SpringBbsVO) map.get("springBbsVO");
		//JDBCTemplateDAO dao = new JDBCTemplateDAO();
		dao.edit(vo);
	}
}
