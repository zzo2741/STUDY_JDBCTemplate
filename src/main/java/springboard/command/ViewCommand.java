package springboard.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import springboard.model.JDBCTemplateDAO;
import springboard.model.SpringBbsVO;

@Service
public class ViewCommand implements BbsCommandImpl
{

	JDBCTemplateDAO dao;

	@Autowired
	public void setDao(JDBCTemplateDAO dao)
	{
		this.dao = dao;
		System.out.println("JDBCTemplateDAO 자동주입(View)");
	}

	@Override
	public void execute(Model model)
	{
		Map<String, Object> paramMap = model.asMap();
		HttpServletRequest req = (HttpServletRequest) paramMap.get("req");
		// request객체를 통해 폼값을 받음
		String idx = req.getParameter("idx");
		String nowPage = req.getParameter("nowPage");
		// DAO의 view메소드를 호출하여 idx에 해당하는 레코드 하나를 반환받음
		//JDBCTemplateDAO dao = new JDBCTemplateDAO();
		SpringBbsVO vo = new SpringBbsVO();
		vo = dao.view(idx);
		vo.setContents(vo.getContents().replace("\r\n", "<br/>"));
		model.addAttribute("viewRow", vo);
		model.addAttribute("nowPage", nowPage);

	}

}
